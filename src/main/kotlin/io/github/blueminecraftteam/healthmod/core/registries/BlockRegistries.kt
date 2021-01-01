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

package io.github.blueminecraftteam.healthmod.core.registries

import io.github.blueminecraftteam.healthmod.common.blocks.BandageBoxBlock
import io.github.blueminecraftteam.healthmod.common.blocks.BloodTestMachineBlock
import io.github.blueminecraftteam.healthmod.core.HealthMod
import net.minecraft.block.AbstractBlock
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
import net.minecraft.item.DyeColor
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.KDeferredRegister

object BlockRegistries {
    val BLOCKS = KDeferredRegister(ForgeRegistries.BLOCKS, HealthMod.MOD_ID)

    val BANDAGE_BOX by BLOCKS.register("bandage_box") {
        BandageBoxBlock(
            AbstractBlock.Properties.create(Material.WOOL, MaterialColor.SNOW)
                .sound(SoundType.CLOTH)
                .noDrops()
                .zeroHardnessAndResistance()
        )
    }

    val BLOOD_TEST_MACHINE by BLOCKS.register("blood_test_machine") {
        BloodTestMachineBlock(
            AbstractBlock.Properties.create(Material.GLASS, DyeColor.WHITE)
                .sound(SoundType.GLASS)
                .hardnessAndResistance(0f, 0f)
        )
    }
}