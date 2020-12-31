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

import com.google.common.collect.ImmutableSet;
import io.github.blueminecraftteam.healthmod.core.HealthMod;
import net.minecraft.block.BlockState;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.SoundEvents;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.Set;

// dont work maybe cus no trades? -ag6
@SuppressWarnings("unused")
public class VillagerProfessionRegistries {
    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, HealthMod.MOD_ID);
    public static final DeferredRegister<PointOfInterestType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, HealthMod.MOD_ID);

    private static final Set<BlockState> DOCTOR_POINT_OF_INTEREST_STATES = new HashSet<>();

    public static final RegistryObject<PointOfInterestType> DOCTOR_POINT_OF_INTEREST = POI_TYPES.register(
            "doctor_point_of_interest",
            () -> new PointOfInterestType(
                    "doctor_point_of_interest",
                    ImmutableSet.copyOf(DOCTOR_POINT_OF_INTEREST_STATES),
                    1,
                    1
            )
    );

    public static final RegistryObject<VillagerProfession> DOCTOR = PROFESSIONS.register(
            "doctor",
            () -> new VillagerProfession(
                    "doctor_point_of_interest",
                    DOCTOR_POINT_OF_INTEREST.get(),
                    ImmutableSet.of(ItemRegistries.FIRST_AID_KIT.get()),
                    ImmutableSet.of(BlockRegistries.BAND_AID_BOX.get()),
                    SoundEvents.BLOCK_BREWING_STAND_BREW
            )
    );

    static {
        DOCTOR_POINT_OF_INTEREST_STATES.addAll(PointOfInterestType.getAllStates(BlockRegistries.BAND_AID_BOX.get()));
        DOCTOR_POINT_OF_INTEREST_STATES.addAll(PointOfInterestType.getAllStates(BlockRegistries.BAND_AID_BOX.get()));
    }
}
