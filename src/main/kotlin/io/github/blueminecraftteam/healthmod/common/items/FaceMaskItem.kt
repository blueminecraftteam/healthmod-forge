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

package io.github.blueminecraftteam.healthmod.common.items

import io.github.blueminecraftteam.healthmod.client.armor.FaceMaskModel
import io.github.blueminecraftteam.healthmod.core.HealthMod
import io.github.blueminecraftteam.healthmod.core.registries.EffectRegistries
import net.minecraft.client.renderer.entity.model.BipedModel
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.EquipmentSlotType
import net.minecraft.item.ArmorItem
import net.minecraft.item.IArmorMaterial
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import kotlin.math.floor

// NOTE: Model should work with texture and it does, atm FaceMaskModel doesn't do anything and acts as a placeholder for modelers to put their custom models there. -BioAstroiner
class FaceMaskItem(materialIn: IArmorMaterial, slot: EquipmentSlotType, builderIn: Properties) : ArmorItem(materialIn, slot, builderIn) {
    @OnlyIn(Dist.CLIENT)
    override fun <A : BipedModel<*>?> getArmorModel(entity: LivingEntity, itemStack: ItemStack, armorSlot: EquipmentSlotType, originalModel: A): A? {
        if (itemStack.item is ArmorItem) {
            val model = FaceMaskModel()
            model.bipedHead.showModel = armorSlot == EquipmentSlotType.HEAD
            model.isChild = originalModel!!.isChild
            model.isSitting = originalModel.isSitting
            model.isSneak = originalModel.isSneak
            model.rightArmPose = originalModel.rightArmPose
            model.leftArmPose = originalModel.leftArmPose

            // noinspection unchecked
            return model as A
        }
        return null
    }

    override fun getArmorTexture(stack: ItemStack, entity: Entity, slot: EquipmentSlotType, type: String): String {
        return HealthMod.MOD_ID + ":textures/models/armor/face_mask.png"
    }

    override fun getEquipmentSlot(stack: ItemStack): EquipmentSlotType {
        return EquipmentSlotType.HEAD
    }

    override fun getIsRepairable(toRepair: ItemStack, repair: ItemStack): Boolean {
        return repair.item === Items.SLIME_BALL
    }

    override fun onArmorTick(stack: ItemStack, world: World, player: PlayerEntity) {
        // if the player is infected it would damage the mask
        // TODO make player suffer less from bad potion effects while wearing masks
        if (player.isPotionActive(EffectRegistries.WOUND_INFECTION)) {
            stack.damageItem(
                    floor((world.rand.nextFloat() * 5).toDouble()).toInt(),
                    player,
                    { playerEntity: PlayerEntity -> playerEntity.sendBreakAnimation(playerEntity.activeHand) }
            )
        }
    }
}