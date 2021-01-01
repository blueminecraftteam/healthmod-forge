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

import io.github.blueminecraftteam.healthmod.common.tileentity.BandageBoxTileEntity;
import io.github.blueminecraftteam.healthmod.common.tileentity.BloodTestMachineTileEntity;
import io.github.blueminecraftteam.healthmod.core.HealthMod;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityRegistries {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, HealthMod.MOD_ID);

    @SuppressWarnings("ConstantConditions")
    public static final RegistryObject<TileEntityType<BandageBoxTileEntity>> BANDAGE_BOX = TILE_ENTITIES.register(
            "band_aid_box",
            () -> TileEntityType.Builder.create(BandageBoxTileEntity::new, BlockRegistries.BANDAGE_BOX.get())
                    .build(null)
    );

    public static final RegistryObject<TileEntityType<BloodTestMachineTileEntity>> BLOOD_TEST_MACHINE = TILE_ENTITIES.register(
            "blood_test_machine",
            () -> TileEntityType.Builder.create(BloodTestMachineTileEntity::new, BlockRegistries.BLOOD_TEST_MACHINE.get())
            .build(null)
    );
}
