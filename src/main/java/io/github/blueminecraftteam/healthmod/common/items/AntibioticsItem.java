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

package io.github.blueminecraftteam.healthmod.common.items;

import io.github.blueminecraftteam.healthmod.core.HealthMod;
import io.github.blueminecraftteam.healthmod.core.config.HealthModConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.concurrent.ThreadLocalRandom;

public class AntibioticsItem extends Item {
    public AntibioticsItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        if (!world.isRemote) {
            if (ThreadLocalRandom.current().nextInt(1, HealthModConfig.SERVER_CONFIG.bacterialResistanceChance.get() + 1) != 1) {
                for (EffectInstance effectInstance : player.getActivePotionEffects()) {
                    if (effectInstance.getPotion().getEffectType() == EffectType.HARMFUL && effectInstance.getPotion() != Effects.POISON) {
                        player.removePotionEffect(effectInstance.getPotion());
                    }
                }
            } else {
                player.sendStatusMessage(
                        new TranslationTextComponent("text." + HealthMod.MOD_ID + ".antibiotics.resistant_bacteria"),
                        true
                );

                for (EffectInstance effectInstance : player.getActivePotionEffects()) {
                    if (effectInstance.getPotion().getEffectType() == EffectType.HARMFUL && effectInstance.getPotion() != Effects.POISON) {
                        player.removePotionEffect(effectInstance.getPotion());

                        effectInstance.combine(new EffectInstance(
                                effectInstance.getPotion(),
                                Math.round(effectInstance.getDuration() * 1.5F),
                                effectInstance.getAmplifier() + 1,
                                effectInstance.isAmbient(),
                                effectInstance.doesShowParticles(),
                                effectInstance.isShowIcon(),
                                ObfuscationReflectionHelper.getPrivateValue(
                                        EffectInstance.class,
                                        effectInstance,
                                        "field_230115_j_"
                                )
                        ));

                        player.addPotionEffect(effectInstance);
                    }
                }
            }
        }

        final ItemStack stack = player.getHeldItem(hand);

        stack.shrink(1);

        return ActionResult.resultConsume(stack);
    }
}
