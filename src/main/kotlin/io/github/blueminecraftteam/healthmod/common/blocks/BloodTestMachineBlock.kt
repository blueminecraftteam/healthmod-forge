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

package io.github.blueminecraftteam.healthmod.common.blocks

import io.github.blueminecraftteam.healthmod.core.registries.TileEntityTypeRegistries
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.world.IBlockReader

// TODO functionality
class BloodTestMachineBlock(properties: Properties) : Block(properties) {
    override fun hasTileEntity(state: BlockState) = true

    override fun createTileEntity(state: BlockState, world: IBlockReader) =
        TileEntityTypeRegistries.BLOOD_TEST_MACHINE.create()
}