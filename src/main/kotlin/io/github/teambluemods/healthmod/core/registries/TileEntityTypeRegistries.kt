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

package io.github.teambluemods.healthmod.core.registries

import io.github.teambluemods.healthmod.common.tileentities.BandageBoxTileEntity
import io.github.teambluemods.healthmod.common.tileentities.BloodTestMachineTileEntity
import io.github.teambluemods.healthmod.core.HealthMod
import net.minecraft.tileentity.TileEntityType
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.KDeferredRegister

object TileEntityTypeRegistries {
    val TILE_ENTITY_TYPES = KDeferredRegister(ForgeRegistries.TILE_ENTITIES, HealthMod.MOD_ID)

    val BANDAGE_BOX: TileEntityType<BandageBoxTileEntity> by TILE_ENTITY_TYPES.registerObject("bandage_box") {
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        TileEntityType.Builder
            .create(::BandageBoxTileEntity, BlockRegistries.BANDAGE_BOX)
            .build(null)
    }

    val BLOOD_TEST_MACHINE: TileEntityType<BloodTestMachineTileEntity> by TILE_ENTITY_TYPES.registerObject("blood_test_machine") {
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        TileEntityType.Builder
            .create(::BloodTestMachineTileEntity, BlockRegistries.BLOOD_TEST_MACHINE)
            .build(null)
    }
}