/*
 * Copyright (c) 2021 Team Blue.
 *
 * This file is part of HealthMod.
 *
 * HealthMod is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HealthMod is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with HealthMod.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.teambluemods.healthmod.common.tileentities

import io.github.blueminecraftteam.healthmod.core.registries.TileEntityTypeRegistries
import io.github.teambluemods.healthmod.common.blocks.BandageBoxBlock
import io.github.teambluemods.healthmod.common.container.BandageBoxContainer
import io.github.teambluemods.healthmod.core.registries.ItemRegistries
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.ItemStackHelper
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.tileentity.LockableLootTileEntity
import net.minecraft.tileentity.TileEntityType
import net.minecraft.util.Direction
import net.minecraft.util.NonNullList
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.IBlockReader
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.ItemStackHandler
import net.minecraftforge.items.wrapper.InvWrapper
import javax.annotation.Nonnull


class BandageBoxTileEntity(type: TileEntityType<*> = TileEntityTypeRegistries.BANDAGE_BOX) :
    LockableLootTileEntity(type) {
    private val items = InvWrapper(this)
    private var numPlayersUsing = 0
    private var contents = NonNullList.withSize(6, ItemStack.EMPTY)
    private val handler: ItemStackHandler = createHandler()
    private var itemHandler = LazyOptional.of { handler }

    override fun getSizeInventory() = 6

    public override fun getItems(): NonNullList<ItemStack> = contents

    override fun setItems(itemsIn: NonNullList<ItemStack>) {
        contents = itemsIn
    }

    override fun getDefaultName() = TranslationTextComponent("container.band_aid_box")

    override fun createMenu(id: Int, player: PlayerInventory) = BandageBoxContainer(id, player, this)

    override fun write(compound: CompoundNBT): CompoundNBT {
        super.write(compound)

        if (!checkLootAndWrite(compound)) {
            ItemStackHelper.saveAllItems(compound, contents)
        }

        return compound
    }

    override fun read(state: BlockState, nbt: CompoundNBT) {
        super.read(state, nbt)

        contents = NonNullList.withSize(this.sizeInventory, ItemStack.EMPTY)

        if (!checkLootAndRead(nbt)) {
            ItemStackHelper.loadAllItems(nbt, contents)
        }
    }

    override fun receiveClientEvent(id: Int, type: Int) = if (id == 1) {
        numPlayersUsing = type

        true
    } else {
        super.receiveClientEvent(id, type)
    }

    override fun openInventory(player: PlayerEntity) {
        if (!player.isSpectator) {
            if (numPlayersUsing < 0) numPlayersUsing = 0
            numPlayersUsing++
            onOpenOrClose()
        }
    }

    override fun closeInventory(player: PlayerEntity) {
        if (!player.isSpectator) {
            numPlayersUsing--
            onOpenOrClose()
        }
    }

    private fun onOpenOrClose() {
        val block = this.blockState.block

        if (block is BandageBoxBlock) {
            world!!.addBlockEvent(pos, block, 1, numPlayersUsing)
            world!!.notifyNeighborsOfStateChange(pos, block)
        }
    }

    override fun updateContainingBlockInfo() {
        super.updateContainingBlockInfo()
        this.itemHandler.invalidate()
        this.itemHandler = null
    }

    override fun <T> getCapability(capability: Capability<T>, side: Direction?): LazyOptional<T> =
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            itemHandler.cast()
        } else {
            super.getCapability(capability, side)
        }

    private fun createHandler(): ItemStackHandler {
        return object : ItemStackHandler(1) {
            override fun onContentsChanged(slot: Int) {
                // To make sure the TE persists when the chunk is saved later we need to
                // mark it dirty every time the item handler changes
                markDirty()
            }

            override fun isItemValid(slot: Int, @Nonnull stack: ItemStack): Boolean {
                return stack.item === ItemRegistries.BANDAGE
            }

            @Nonnull
            override fun insertItem(slot: Int, @Nonnull stack: ItemStack, simulate: Boolean): ItemStack {
                return if (stack.item !== ItemRegistries.BANDAGE) {
                    stack
                } else super.insertItem(slot, stack, simulate)
            }
        }
    }

    override fun remove() {
        super.remove()
        itemHandler.invalidate()
    }

    @Suppress("UNUSED")
    companion object {
        fun getPlayersUsing(reader: IBlockReader, pos: BlockPos): Int {
            val state = reader.getBlockState(pos)

            if (state.hasTileEntity()) {
                val tileEntity = reader.getTileEntity(pos)

                if (tileEntity is BandageBoxTileEntity) {
                    return tileEntity.numPlayersUsing
                }
            }

            return 0
        }

        fun swapContents(tileEntity: BandageBoxTileEntity, otherTileEntity: BandageBoxTileEntity) {
            val list = tileEntity.getItems()
            tileEntity.setItems(otherTileEntity.getItems())
            otherTileEntity.setItems(list)
        }
    }
}
