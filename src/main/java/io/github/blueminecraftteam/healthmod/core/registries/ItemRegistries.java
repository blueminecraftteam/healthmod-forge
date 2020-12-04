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

import io.github.blueminecraftteam.healthmod.common.items.AntibioticsItem;
import io.github.blueminecraftteam.healthmod.common.items.BandAidItem;
import io.github.blueminecraftteam.healthmod.core.HealthMod;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("UNUSED")
public class ItemRegistries {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HealthMod.MOD_ID);

    public static final RegistryObject<Item> BAND_AID = ITEMS.register(
            "band_aid",
            () -> new BandAidItem(new Item.Properties()
                    .maxStackSize(16)
                    .maxDamage(1)
                    .rarity(Rarity.UNCOMMON))
    );

    // TODO: do the syringe e.g. extract blood etc
    public static final RegistryObject<Item> SYRINGE = ITEMS.register(
            "syringe",
            () -> new Item(new Item.Properties()
                    .group(HealthMod.ITEM_GROUP)
                    .maxStackSize(1))
    );

    // TODO: Model
    // Do this later, i cba to do the functionality for it -AG6 28/11/2020
    /*
    public static final RegistryObject<Item> FACEMASK = ITEMS.register(
            "facemask",
            () -> new FaceMaskItem(new Item.Properties()
                    .group(HealthMod.ITEM_GROUP)
                    .maxStackSize(1))
    );
    */

    public static final RegistryObject<Item> ANTIBIOTICS = ITEMS.register(
            "antibiotics",
            () -> new AntibioticsItem(new Item.Properties().group(HealthMod.ITEM_GROUP).maxStackSize(16))
    );

    public static final RegistryObject<Item> BROCCOLI = ITEMS.register(
            "broccoli",
            () -> new Item(new Item.Properties()
                    .group(HealthMod.ITEM_GROUP)
                    .food(new Food.Builder()
                            .hunger(3)
                            .saturation(2F)
                            .effect(() -> new EffectInstance(EffectRegistries.HEALTHY.get(), 60 * 20), 1F)
                            .build()))
    );
}

