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
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.SoundEvents;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;

//dont work maybe cus no trades? -ag6
@SuppressWarnings("UNUSED")
public class VillagerProfessionRegistries {

    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, HealthMod.MOD_ID);
    public static final DeferredRegister<PointOfInterestType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, HealthMod.MOD_ID);

    public static final RegistryObject<PointOfInterestType> DOCTOR_POI = POI_TYPES.register("", () -> new PointOfInterestType(
            "doctor",
            getBlockStates(BlockRegistries.BANDAGE_BOX.get()),
            1,
            20));
    public static final RegistryObject<VillagerProfession> DOCTOR = PROFESSIONS.register("doctor", () -> new VillagerProfession(
            "doctor",
            DOCTOR_POI.get(),
            ImmutableSet.of(ItemRegistries.FIRST_AID_KIT.get()),
            ImmutableSet.of(BlockRegistries.BANDAGE_BOX.get()),
            SoundEvents.BLOCK_BREWING_STAND_BREW));

    private static Set<BlockState> getBlockStates(Block block) {
        return ImmutableSet.copyOf(block.getStateContainer().getValidStates());
    }
}
