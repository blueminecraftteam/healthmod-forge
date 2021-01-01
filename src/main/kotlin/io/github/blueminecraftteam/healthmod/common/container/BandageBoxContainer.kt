/*
 * Copyright (c) 2020 Blue Minecraft Team.
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

package io.github.blueminecraftteam.healthmod.common.container

import io.github.blueminecraftteam.healthmod.common.tileentities.BandageBoxTileEntity
import io.github.blueminecraftteam.healthmod.core.registries.BlockRegistries
import io.github.blueminecraftteam.healthmod.core.registries.ContainerRegistries
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.container.Container
import net.minecraft.network.PacketBuffer
import net.minecraft.util.IWorldPosCallable

class BandageBoxContainer(windowId: Int, playerInventory: PlayerInventory, tileEntity: BandageBoxTileEntity) :
        Container(ContainerRegistries.BANDAGE_BOX, windowId) {
    private val callable: IWorldPosCallable
    val slotSizePlus2 = 18

    init {
        val world = tileEntity.world ?: throw NullPointerException("the world was null, for some reason.")
        callable = IWorldPosCallable.of(world, tileEntity.pos)
    }

    constructor(windowId: Int, playerInventory: PlayerInventory, data: PacketBuffer) : this(windowId,
            playerInventory,
            getTileEntity(playerInventory, data))

    override fun canInteractWith(playerIn: PlayerEntity): Boolean {
        return isWithinUsableDistance(callable, playerIn, BlockRegistries.BANDAGE_BOX)
    }

    companion object {
        private fun getTileEntity(playerInventory: PlayerInventory, data: PacketBuffer): BandageBoxTileEntity {
            val tileEntity = playerInventory.player.world.getTileEntity(data.readBlockPos())
            if (tileEntity is BandageBoxTileEntity) {
                return tileEntity
            }
            throw IllegalStateException("Tile entity $tileEntity is not correct!")
        }
    }
}