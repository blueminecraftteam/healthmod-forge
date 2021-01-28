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

package io.github.teambluemods.healthmod.common.blocks

import io.github.teambluemods.healthmod.common.tileentities.BandageBoxTileEntity
import io.github.teambluemods.healthmod.core.registries.TileEntityTypeRegistries
import io.github.teambluemods.healthmod.core.util.extensions.isServer
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.inventory.InventoryHelper
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.world.IBlockReader
import net.minecraft.world.World
import net.minecraftforge.fml.network.NetworkHooks

class BandageBoxBlock(properties: Properties) : Block(properties) {
    // TODO: Model
    override fun hasTileEntity(state: BlockState) = true

    override fun createTileEntity(state: BlockState, world: IBlockReader) =
        TileEntityTypeRegistries.BANDAGE_BOX.create()

    override fun onBlockActivated(
        state: BlockState,
        world: World,
        pos: BlockPos,
        playerEntity: PlayerEntity,
        hand: Hand,
        hit: BlockRayTraceResult
    ): ActionResultType {
        if (world.isServer) {
            val tileEntity = world.getTileEntity(pos)

            if (tileEntity is BandageBoxTileEntity) {
                NetworkHooks.openGui(playerEntity as ServerPlayerEntity, tileEntity, pos)
            }

            return ActionResultType.SUCCESS
        }

        return ActionResultType.FAIL
    }

    override fun onReplaced(
        state: BlockState,
        world: World,
        pos: BlockPos,
        newState: BlockState,
        isMoving: Boolean
    ) {
        if (state.block != newState.block) {
            val tileEntity = world.getTileEntity(pos)

            if (tileEntity is BandageBoxTileEntity) {
                InventoryHelper.dropItems(world, pos, tileEntity.items)
            }
        }
    }
}