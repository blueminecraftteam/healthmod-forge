/*
 * Copyright (c) 2020 Blue Minecraft Team.
 *
 * This file is part of HealthMod.
 *
 * HealthMod is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HealthMod is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HealthMod.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.blueminecraftteam.healthmod.core.registries;

import io.github.blueminecraftteam.healthmod.HealthMod;
import io.github.blueminecraftteam.healthmod.common.items.BandAidItem;
import io.github.blueminecraftteam.healthmod.common.items.FaceMaskItem;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistries {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HealthMod.MOD_ID);

    public static final RegistryObject<Item> BAND_AID = ITEMS.register("band_aid", () -> new BandAidItem(new Item.Properties().group(HealthMod.itemGroup).maxDamage(2).rarity(Rarity.UNCOMMON)));
    //TODO: Stuff the syringe. e.g. extract blood etc
    public static final RegistryObject<Item> SYRINGE = ITEMS.register("syringe", () -> new Item(new Item.Properties().maxStackSize(1).group(HealthMod.itemGroup)));
    //TODO: Model
    //Do this later, i cba to do the functionality for it -AG6 28/11/2020
    //public static final RegistryObject<Item> FACEMASK = ITEMS.register("facemask", () -> new FaceMaskItem(new Item.Properties().maxStackSize(1).group(HealthMod.itemGroup)));

    public static final RegistryObject<Item> ECHINACEA_SEED = ITEMS.register("echinacea_seed", () -> new BlockItem(BlockRegistries.ECHINACEA_PLANT.get() , new Item.Properties().group(ItemGroup.FOOD)));
    public static final RegistryObject<Item> ECHINACEA = ITEMS.register("echinacea", () -> new Item(new Item.Properties().group(ItemGroup.FOOD).group(ItemGroup.FOOD).food(new Food.Builder().hunger(3).saturation(2.5F).meat().effect(new EffectInstance(Effects.REGENERATION, 100, 1), 0.9F).fastToEat().build())));
}

