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

package io.github.blueminecraftteam.healthmod.common.items;

import io.github.blueminecraftteam.healthmod.client.armor.CustomArmorModel;
import io.github.blueminecraftteam.healthmod.core.HealthMod;
import io.github.blueminecraftteam.healthmod.core.registries.EffectRegistries;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;


// NOTE: To take the piss i might just make it so when u right click it puts it on your head, since its not an ArmorItem I don't think it can do this?
// NOTE: Model should work with texture and it dose , atm {@link CustomArmorModel} don't do anything and acts as a placeholder for modlers to put their custom models there. -BioAstroiner
public class FaceMaskItem extends ArmorItem {
    public FaceMaskItem(Properties builderIn) {
        super(ArmorMaterial.LEATHER, EquipmentSlotType.HEAD, builderIn);
    }

    @Nullable
    @OnlyIn(Dist.CLIENT)
    @Override
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack,
                                                     EquipmentSlotType armorSlot, A _default) {
        if(itemStack.getItem() instanceof ArmorItem)
        {
            CustomArmorModel model = new CustomArmorModel();

            model.bipedHead.showModel = armorSlot == EquipmentSlotType.HEAD;

            model.isChild = _default.isChild;
            model.isSitting = _default.isSitting;
            model.isSneak = _default.isSneak;
            model.rightArmPose = _default.rightArmPose;
            model.leftArmPose = _default.leftArmPose;
            return (A) model;
        }
        return null;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return HealthMod.MOD_ID + ":textures/models/armor/facemask.png";
    }

    @Nullable
    @Override
    public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
        return EquipmentSlotType.HEAD;
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == Items.SLIME_BALL; // why not?
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        //if the player is infected it would damage the mask
        //TODO make player suffer less from bad potion effects while wearing masks
        if(player.isPotionActive(EffectRegistries.WOUND_INFECTION.get())){
            stack.damageItem((int)Math.floor( world.rand.nextFloat() * 5),player,playerEntity -> playerEntity.sendBreakAnimation(playerEntity.getActiveHand()));
        }
    }
}
