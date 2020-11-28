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
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
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
}

