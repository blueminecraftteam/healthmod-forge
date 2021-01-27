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

package io.github.teambluemods.healthmod.common.items

import io.github.teambluemods.healthmod.core.HealthMod
import io.github.teambluemods.healthmod.core.config.HealthModConfig
import io.github.teambluemods.healthmod.core.registries.EffectRegistries
import io.github.teambluemods.healthmod.core.util.extensions.isServer
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.potion.EffectInstance
import net.minecraft.potion.Effects
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.World

class BandageItem(settings: Properties) : Item(settings) {
    override fun addInformation(
        stack: ItemStack,
        worldIn: World?,
        tooltip: MutableList<ITextComponent>,
        flagIn: ITooltipFlag
    ) {
        tooltip.add(TranslationTextComponent("text.${HealthMod.MOD_ID}.bandage.1"))
        tooltip.add(TranslationTextComponent("text.${HealthMod.MOD_ID}.bandage.2"))
        tooltip.add(TranslationTextComponent("text.${HealthMod.MOD_ID}.bandage.3"))
    }

    override fun onItemRightClick(world: World, player: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        if (world.isServer) {
            val itemStack = player.getHeldItem(hand)

            if (player.maxHealth > player.health) {
                val chance = if (player.getActivePotionEffect(EffectRegistries.HEALTHY) != null) {
                    HealthModConfig.SERVER_CONFIG.bandageInfectionChanceWhenHealthy.get()
                } else {
                    HealthModConfig.SERVER_CONFIG.bandageInfectionChance.get()
                }

                // 1 in 4 chance (or 1 in 10 if healthy) to have it not apply correct
                if ((1..chance + 1).random() == 1) {
                    player.addPotionEffect(EffectInstance(EffectRegistries.WOUND_INFECTION, 15 * 20, 0))
                    player.sendStatusMessage(
                        TranslationTextComponent("text." + HealthMod.MOD_ID + ".bandage.failed_apply"),
                        true
                    )
                } else {
                    player.addPotionEffect(EffectInstance(Effects.REGENERATION, 15 * 20, 0))
                }

                itemStack.damageItem(1, player) { it.sendBreakAnimation(hand) }
            }
        }

        return super.onItemRightClick(world, player, hand)
    }
}