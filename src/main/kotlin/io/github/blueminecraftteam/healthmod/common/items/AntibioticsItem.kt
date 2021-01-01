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

import io.github.blueminecraftteam.healthmod.core.HealthMod
import io.github.blueminecraftteam.healthmod.core.config.HealthModConfig
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.potion.EffectInstance
import net.minecraft.potion.EffectType
import net.minecraft.potion.Effects
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.World
import net.minecraftforge.fml.common.ObfuscationReflectionHelper
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.roundToInt

class AntibioticsItem(properties: Properties) : Item(properties) {
    override fun onItemRightClick(world: World, player: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        if (!world.isRemote) {
            if (ThreadLocalRandom.current().nextInt(1, HealthModConfig.SERVER_CONFIG.bacterialResistanceChance.get() + 1) != 1) {
                for (effectInstance in player.activePotionEffects) {
                    if (effectInstance.potion.effectType == EffectType.HARMFUL && effectInstance.potion !== Effects.POISON) {
                        player.removePotionEffect(effectInstance.potion)
                    }
                }
            } else {
                player.sendStatusMessage(
                        TranslationTextComponent("text." + HealthMod.MOD_ID + ".antibiotics.resistant_bacteria"),
                        true
                )
                for (effectInstance in player.activePotionEffects) {
                    if (effectInstance.potion.effectType == EffectType.HARMFUL && effectInstance.potion !== Effects.POISON) {
                        player.removePotionEffect(effectInstance.potion)
                        effectInstance.combine(EffectInstance(
                                effectInstance.potion,
                                (effectInstance.duration * 1.5f).roundToInt(),
                                effectInstance.amplifier + 1,
                                effectInstance.isAmbient,
                                effectInstance.doesShowParticles(),
                                effectInstance.isShowIcon,
                                ObfuscationReflectionHelper.getPrivateValue(
                                        EffectInstance::class.java,
                                        effectInstance,
                                        "field_230115_j_"
                                )
                        ))
                        player.addPotionEffect(effectInstance)
                    }
                }
            }
        }
        val stack = player.getHeldItem(hand)
        stack.shrink(1)
        return ActionResult.resultConsume(stack)
    }
}