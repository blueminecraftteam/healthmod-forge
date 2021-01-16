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

package io.github.blueminecraftteam.healthmod.core.registries

import io.github.blueminecraftteam.healthmod.common.items.AntibioticsItem
import io.github.blueminecraftteam.healthmod.common.items.BandageItem
import io.github.blueminecraftteam.healthmod.common.items.FaceMaskItem
import io.github.blueminecraftteam.healthmod.common.items.SyringeItem
import io.github.blueminecraftteam.healthmod.core.HealthMod
import net.minecraft.inventory.EquipmentSlotType
import net.minecraft.item.ArmorMaterial
import net.minecraft.item.Food
import net.minecraft.item.Item
import net.minecraft.item.Rarity
import net.minecraft.potion.EffectInstance
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.KDeferredRegister

@Suppress("unused")
object ItemRegistries {
    val ITEMS = KDeferredRegister(ForgeRegistries.ITEMS, HealthMod.MOD_ID)

    val BANDAGE by ITEMS.registerObject("bandage") {
        BandageItem(
            Item.Properties()
                .maxStackSize(16)
                .maxDamage(1)
                .rarity(Rarity.UNCOMMON)
        )
    }

    val SYRINGE by ITEMS.registerObject("syringe") {
        SyringeItem(
            Item.Properties()
                .group(HealthMod.ITEM_GROUP)
                .maxStackSize(1)
        )
    }

    val FACE_MASK by ITEMS.registerObject("face_mask") {
        FaceMaskItem(
            ArmorMaterial.LEATHER,
            EquipmentSlotType.HEAD,
            Item.Properties().group(HealthMod.ITEM_GROUP).maxDamage(100)
        )
    }

    val ANTIBIOTICS by ITEMS.registerObject("antibiotics") {
        AntibioticsItem(Item.Properties().group(HealthMod.ITEM_GROUP).maxStackSize(16))
    }

    val BROCCOLI by ITEMS.registerObject("broccoli") {
        Item(
            Item.Properties()
                .group(HealthMod.ITEM_GROUP)
                .food(
                    Food.Builder()
                        .hunger(3)
                        .saturation(2f)
                        .effect({ EffectInstance(EffectRegistries.HEALTHY, 60 * 20) }, 1f)
                        .build()
                )
        )
    }

    val FIRST_AID_KIT by ITEMS.registerObject("first_aid_kit") {
        Item(Item.Properties().group(HealthMod.ITEM_GROUP))
    }

    val BLOOD_VIAL by ITEMS.registerObject("blood_vial") {
        Item(Item.Properties().group(HealthMod.ITEM_GROUP))
    }

    // TODO functionality
    val ISOPROPYL_ALCOHOL by ITEMS.registerObject("isopropyl_alcohol") {
        Item(Item.Properties().group(HealthMod.ITEM_GROUP).maxStackSize(1))
    }
}