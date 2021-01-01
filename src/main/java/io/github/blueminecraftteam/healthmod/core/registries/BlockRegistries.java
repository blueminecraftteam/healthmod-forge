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

package io.github.blueminecraftteam.healthmod.core.registries;

import io.github.blueminecraftteam.healthmod.common.blocks.BandageBoxBlock;
import io.github.blueminecraftteam.healthmod.common.blocks.BloodTestMachineBlock;
import io.github.blueminecraftteam.healthmod.core.HealthMod;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.DyeColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegistries {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, HealthMod.MOD_ID);

    public static final RegistryObject<Block> BANDAGE_BOX = BLOCKS.register(
            "bandage_box",
            () -> new BandageBoxBlock(AbstractBlock.Properties.create(Material.WOOL, MaterialColor.SNOW)
                    .sound(SoundType.CLOTH)
                    .noDrops()
                    .zeroHardnessAndResistance())
    );

    public static final RegistryObject<Block> BLOOD_TEST_MACHINE = BLOCKS.register(
            "blood_test_machine",
            () -> new BloodTestMachineBlock(AbstractBlock.Properties.create(Material.GLASS, DyeColor.WHITE)
                    .sound(SoundType.GLASS)
                    .hardnessAndResistance(0, 0))
    );
}
