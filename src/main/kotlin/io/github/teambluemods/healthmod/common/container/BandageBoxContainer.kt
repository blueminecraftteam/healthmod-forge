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


// TODO
class BandageBoxContainer(
    windowId: Int,
    playerInventory: PlayerInventory,
    tileEntity: BandageBoxTileEntity
) : Container(ContainerTypeRegistries.BANDAGE_BOX, windowId) {
    private val callable = IWorldPosCallable.of(tileEntity.world!!, tileEntity.pos)

    constructor(
        windowId: Int,
        playerInventory: PlayerInventory,
        data: PacketBuffer
    ) : this(windowId, playerInventory, getTileEntity(playerInventory, data))

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