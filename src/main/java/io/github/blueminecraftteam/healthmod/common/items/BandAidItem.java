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
import io.github.blueminecraftteam.healthmod.core.registries.EffectRegistries;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BandAidItem extends Item {
    public BandAidItem(Item.Properties settings) {
        super(settings);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("text." + HealthMod.MOD_ID + ".band_aid.1"));
        tooltip.add(new TranslationTextComponent("text." + HealthMod.MOD_ID + ".band_aid.2"));
        tooltip.add(new TranslationTextComponent("text." + HealthMod.MOD_ID + ".band_aid.3"));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        if (!world.isRemote) {
            final ItemStack itemStack = player.getHeldItem(hand);

            if (player.getMaxHealth() > player.getHealth()) {
                final int chance;

                if (player.getActivePotionEffect(EffectRegistries.HEALTHY.get()) != null) {
                    chance = HealthModConfig.SERVER_CONFIG.bandAidInfectionChanceWhenHealthy.get();
                } else {
                    chance = HealthModConfig.SERVER_CONFIG.bandAidInfectionChance.get();
                }

                // 1 in 4 chance (or 1 in 10 if healthy) to have it not apply correct
                if (ThreadLocalRandom.current().nextInt(1, chance + 1) == 1) {
                    player.addPotionEffect(new EffectInstance(EffectRegistries.WOUND_INFECTION.get(), 15 * 20, 0));
                    player.sendStatusMessage(
                            new TranslationTextComponent("text." + HealthMod.MOD_ID + ".band_aid.failed_apply"),
                            true
                    );
                } else {
                    player.addPotionEffect(new EffectInstance(Effects.REGENERATION, 15 * 20, 0));
                }

                itemStack.damageItem(1, player, playerEntity -> playerEntity.sendBreakAnimation(hand));
            }
        }

        return super.onItemRightClick(world, player, hand);
    }
}
