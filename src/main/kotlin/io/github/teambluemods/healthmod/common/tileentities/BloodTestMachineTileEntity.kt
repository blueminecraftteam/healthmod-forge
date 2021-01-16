/*
 * Copyright (c) 2021 Team Blue Mods.
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

package io.github.blueminecraftteam.healthmod.common.tileentities

import io.github.blueminecraftteam.healthmod.core.registries.TileEntityTypeRegistries
import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityType

// TODO functionality
class BloodTestMachineTileEntity constructor(tileEntityTypeIn: TileEntityType<*> = TileEntityTypeRegistries.BLOOD_TEST_MACHINE) :
    TileEntity(tileEntityTypeIn)