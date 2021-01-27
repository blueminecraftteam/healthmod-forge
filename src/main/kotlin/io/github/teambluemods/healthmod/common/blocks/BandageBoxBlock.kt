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

import io.github.blueminecraftteam.healthmod.core.registries.TileEntityTypeRegistries
import io.github.teambluemods.healthmod.common.tileentities.BandageBoxTileEntity
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
import sun.audio.AudioPlayer.player


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class BandageBoxBlock(properties: Properties) : Block(properties) {
    // TODO: Model
    override fun hasTileEntity(state: BlockState) = true

    override fun createTileEntity(state: BlockState, world: IBlockReader) =
        TileEntityTypeRegistries.BANDAGE_BOX.create()

    override fun onBlockActivated(
        state: BlockState,
        worldIn: World,
        pos: BlockPos,
        playerEntity: PlayerEntity,
        handIn: Hand,
        hit: BlockRayTraceResult
    ): ActionResultType {
        if (!worldIn.isRemote) {
            val tile = worldIn.getTileEntity(pos)
            if (tile is BandageBoxTileEntity) {
                NetworkHooks.openGui(
                    player as ServerPlayerEntity?,
                    tile as BandageBoxTileEntity?,
                    pos
                )
            }
            return ActionResultType.SUCCESS
        }
        return ActionResultType.FAIL
    }

    override fun onReplaced(
        state: BlockState,
        worldIn: World,
        pos: BlockPos?,
        newState: BlockState,
        isMoving: Boolean
    ) {
        if (state.block !== newState.block) {
            val te = worldIn.getTileEntity(pos)
            if (te is BandageBoxTileEntity) {
                InventoryHelper.dropItems(
                    worldIn,
                    pos,
                    (te as BandageBoxTileEntity?)?.items
                )
            }
        }
    }
}