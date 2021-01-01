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

import io.github.blueminecraftteam.healthmod.common.items.AntibioticsItem;
import io.github.blueminecraftteam.healthmod.common.items.BandageItem;
import io.github.blueminecraftteam.healthmod.common.items.FaceMaskItem;
import io.github.blueminecraftteam.healthmod.common.items.SyringeItem;
import io.github.blueminecraftteam.healthmod.core.HealthMod;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("unused")
public class ItemRegistries {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HealthMod.MOD_ID);

    public static final RegistryObject<Item> BANDAGE = ITEMS.register(
            "bandage",
            () -> new BandageItem(new Item.Properties()
                    .maxStackSize(16)
                    .maxDamage(1)
                    .rarity(Rarity.UNCOMMON))
    );

    public static final RegistryObject<Item> SYRINGE = ITEMS.register(
            "syringe",
            () -> new SyringeItem(new Item.Properties()
                    .group(HealthMod.ITEM_GROUP)
                    .maxStackSize(1))
    );

    public static final RegistryObject<Item> FACE_MASK = ITEMS.register(
            "face_mask",
            () -> new FaceMaskItem(
                    ArmorMaterial.LEATHER,
                    EquipmentSlotType.HEAD,
                    new Item.Properties().group(HealthMod.ITEM_GROUP).maxDamage(100)
            )
    );

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

    public static final RegistryObject<Item> FIRST_AID_KIT = ITEMS.register(
            "first_aid_kit",
            () -> new Item(new Item.Properties().group(HealthMod.ITEM_GROUP))
    );

    public static final RegistryObject<Item> BLOOD_VIAL = ITEMS.register(
            "blood_vial",
            () -> new Item(new Item.Properties().group(HealthMod.ITEM_GROUP))
    );

    // TODO functionality
    public static final RegistryObject<Item> ISOPROPYL_ALCOHOl = ITEMS.register(
            "isopropyl_alcohol",
            () -> new Item(new Item.Properties().group(HealthMod.ITEM_GROUP).maxStackSize(1))
    );
}

