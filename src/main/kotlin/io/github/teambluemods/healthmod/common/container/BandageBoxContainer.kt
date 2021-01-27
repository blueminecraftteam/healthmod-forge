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

package io.github.teambluemods.healthmod.common.container

import io.github.teambluemods.healthmod.common.tileentities.BandageBoxTileEntity
import io.github.teambluemods.healthmod.core.registries.BlockRegistries
import io.github.teambluemods.healthmod.core.registries.ContainerTypeRegistries
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.container.Container
import net.minecraft.inventory.container.Slot
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketBuffer
import net.minecraft.util.IWorldPosCallable
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.SlotItemHandler
import net.minecraftforge.items.wrapper.InvWrapper


// TODO this part needs a lot, but first need da the da, the gui texture
class BandageBoxContainer(
    windowId: Int,
    playerInventory: PlayerInventory,
    tileEntity: BandageBoxTileEntity
) : Container(ContainerTypeRegistries.BANDAGE_BOX, windowId) {
    private val callable = IWorldPosCallable.of(tileEntity.world!!, tileEntity.pos)
    private val playerInventory = InvWrapper(playerInventory)

    constructor(
        windowId: Int,
        playerInventory: PlayerInventory,
        data: PacketBuffer
    ) : this(windowId, playerInventory, getTileEntity(playerInventory, data)) {
        val tileEntity = getTileEntity(playerInventory, data)

        // main inventory
        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent { handler ->
            this.addSlotRange(
                handler,
                0,
                64,
                24,
                8, // TODO amount of inventory
                18 // TODO texture gap
            )
        }

        layoutPlayerInventorySlots(0, 0) // TODO enter values
    }

    private fun layoutPlayerInventorySlots(leftColumn: Int, topRow: Int) {
        // player inventory
        addSlotBox(playerInventory, 9, leftColumn, topRow, 9, 18, 3, 18)

        // hotbar
        addSlotRange(playerInventory, 0, leftColumn, topRow, 9, 18)
    }

    /**
     * NOTE: Unlike [addSlotBox], this method adds only a single row of boxes.
     */
    private fun addSlotRange(
        handler: IItemHandler,
        columnIndex: Int,
        x: Int,
        y: Int,
        columnAmount: Int,
        gapSize: Int
    ): Int {
        var index = columnIndex
        var currentX = x

        for (i in 0 until columnAmount) {
            this.addSlot(SlotItemHandler(handler, index, currentX, y))
            currentX += gapSize
            index++
        }

        return index
    }

    /**
     * This method makes a huge Box slot with different rows and columns,
     * like inventory slots. So, it consists of smaller slot ranges.
     * @see addSlotBox
     */
    private fun addSlotBox(
        handler: IItemHandler,
        index: Int,
        x: Int,
        y: Int,
        horizontalAmount: Int,
        xGap: Int,
        verticalAmount: Int,
        yGap: Int
    ): Int {
        var currentIndex = index
        var currentY = y

        for (j in 0 until verticalAmount) {
            currentIndex = addSlotRange(handler, currentIndex, x, currentY, horizontalAmount, xGap)
            currentY += yGap
        }

        return currentIndex
    }

    override fun canInteractWith(playerIn: PlayerEntity) =
        isWithinUsableDistance(callable, playerIn, BlockRegistries.BANDAGE_BOX)

    override fun transferStackInSlot(playerIn: PlayerEntity?, index: Int): ItemStack? {
        var itemStack = ItemStack.EMPTY
        val slot: Slot? = inventorySlots[index]

        if (slot != null && slot.hasStack) {
            val slotStack: ItemStack = slot.stack
            itemStack = slotStack.copy()

            if (index < 36) {
                if (!mergeItemStack(slotStack, 6, inventorySlots.size, true)) {
                    return ItemStack.EMPTY
                } else if (!mergeItemStack(slotStack, 0, 6, false)) {
                    return ItemStack.EMPTY
                }

                if (slotStack.isEmpty) {
                    slot.putStack(ItemStack.EMPTY)
                } else {
                    slot.onSlotChanged()
                }
            }
        }

        return itemStack
    }

    companion object {
        private fun getTileEntity(playerInventory: PlayerInventory, data: PacketBuffer) =
            playerInventory.player.world.getTileEntity(data.readBlockPos()) as? BandageBoxTileEntity
                ?: error("Tile entity is not correct!")
    }
}