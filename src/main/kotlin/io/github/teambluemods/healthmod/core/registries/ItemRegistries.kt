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

package io.github.teambluemods.healthmod.core.registries

import io.github.teambluemods.healthmod.common.items.AntibioticsItem
import io.github.teambluemods.healthmod.common.items.BandageItem
import io.github.teambluemods.healthmod.common.items.FaceMaskItem
import io.github.teambluemods.healthmod.common.items.SyringeItem
import io.github.teambluemods.healthmod.core.HealthMod
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
    private val DEFAULT_PROPERTIES: Item.Properties get() = Item.Properties().group(HealthMod.ITEM_GROUP)

    val BANDAGE by ITEMS.registerObject("bandage") {
        BandageItem(
            DEFAULT_PROPERTIES.maxStackSize(16)
                .maxDamage(1)
                .rarity(Rarity.UNCOMMON)
        )
    }

    val SYRINGE by ITEMS.registerObject("syringe") {
        SyringeItem(DEFAULT_PROPERTIES.maxStackSize(1))
    }

    val FACE_MASK by ITEMS.registerObject("face_mask") {
        FaceMaskItem(
            material = ArmorMaterial.LEATHER,
            slot = EquipmentSlotType.HEAD,
            properties = DEFAULT_PROPERTIES.maxDamage(100)
        )
    }

    val ANTIBIOTICS by ITEMS.registerObject("antibiotics") {
        AntibioticsItem(DEFAULT_PROPERTIES.maxStackSize(16))
    }

    val BROCCOLI by ITEMS.registerObject("broccoli") {
        Item(
            DEFAULT_PROPERTIES.food(
                Food.Builder()
                    .hunger(3)
                    .saturation(2f)
                    .effect({ EffectInstance(EffectRegistries.HEALTHY, 60 * 20) }, 1f)
                    .build()
            )
        )
    }

    val FIRST_AID_KIT by ITEMS.registerObject("first_aid_kit") {
        Item(DEFAULT_PROPERTIES)
    }

    val BLOOD_VIAL by ITEMS.registerObject("blood_vial") {
        Item(DEFAULT_PROPERTIES)
    }

    // TODO functionality
    val ISOPROPYL_ALCOHOL by ITEMS.registerObject("isopropyl_alcohol") {
        Item(DEFAULT_PROPERTIES.maxStackSize(1))
    }
}