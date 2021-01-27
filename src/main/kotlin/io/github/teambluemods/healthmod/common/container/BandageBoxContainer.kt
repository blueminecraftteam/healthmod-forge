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
    private val playerInventory: IItemHandler = InvWrapper(playerInventory)

    constructor(
        windowId: Int,
        playerInventory: PlayerInventory,
        data: PacketBuffer
    ) : this(windowId, playerInventory, getTileEntity(playerInventory, data)) {

        val tileEntity = getTileEntity(playerInventory, data)
        //MainInventory
        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent { h ->
            addSlotRange(
                h,
                0,
                64,
                24,
                8,//TODO amount of inventory
                18 //TODO texture gap
            )
        }
        layoutPlayerInventorySlots(0, 0) //TODO enter values
    }

    private fun layoutPlayerInventorySlots(leftCol: Int, topRow: Int) {
        // Player inventory
        var topRow = topRow //TODO
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18)

        // Hotbar
        topRow += 0 //TODO player Hotbar topRow coord
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18)
    }

    /**
     * unlike [addSlotBox] this method adds only a single row of boxes
     * @param dx is gap size
     * @param amount is number of columbus
     * @param index of first column box , it gets incremented as amount's value
     */
    private fun addSlotRange(handler: IItemHandler, index: Int, x: Int, y: Int, amount: Int, dx: Int): Int {
        var index = index
        var x = x
        for (i in 0 until amount) {
            addSlot(SlotItemHandler(handler, index, x, y))
            x += dx
            index++
        }
        return index
    }

    /**
     * this method makes a huge Box slot with different rows
     * and collumns, like an Inventory Slots
     * so it is infact consists of smaller [addSlotRange]s
     * @param dx x gap
     * @param dy y gap
     * @see [addSlotBox]'s doc
     */
    private fun addSlotBox(
        handler: IItemHandler,
        index: Int,
        x: Int,
        y: Int,
        horAmount: Int,
        dx: Int,
        verAmount: Int,
        dy: Int
    ): Int {
        var index = index
        var y = y
        for (j in 0 until verAmount) {
            index = addSlotRange(handler, index, x, y, horAmount, dx)
            y += dy
        }
        return index
    }

    override fun canInteractWith(playerIn: PlayerEntity) =
        isWithinUsableDistance(callable, playerIn, BlockRegistries.BANDAGE_BOX)

    override fun transferStackInSlot(playerIn: PlayerEntity?, index: Int): ItemStack? {
        var itemstack = ItemStack.EMPTY
        val slot: Slot? = inventorySlots[index]
        if (slot != null && slot.hasStack) {
            val itemstack1: ItemStack = slot.stack
            itemstack = itemstack1.copy()
            if (index < 36) {
                if (!mergeItemStack(itemstack1, 6, inventorySlots.size, true)) {
                    return ItemStack.EMPTY
                } else if (!mergeItemStack(itemstack1, 0, 6, false)) {
                    return ItemStack.EMPTY
                }
                if (itemstack1.isEmpty) {
                    slot.putStack(ItemStack.EMPTY)
                } else {
                    slot.onSlotChanged()
                }
            }
        }
        return itemstack
    }

    companion object {
        private fun getTileEntity(playerInventory: PlayerInventory, data: PacketBuffer) =
            playerInventory.player.world.getTileEntity(data.readBlockPos()) as? BandageBoxTileEntity
                ?: error("Tile entity is not correct!")
    }
}