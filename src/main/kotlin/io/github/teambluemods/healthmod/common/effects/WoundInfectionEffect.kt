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

package io.github.blueminecraftteam.healthmod.common.effects

import net.minecraft.entity.LivingEntity
import net.minecraft.potion.Effect
import net.minecraft.potion.EffectType
import net.minecraft.util.DamageSource

class WoundInfectionEffect(type: EffectType, color: Int) : Effect(type, color) {
    override fun performEffect(entity: LivingEntity, amplifier: Int) {
        entity.attackEntityFrom(DamageSource("wound_infection"), 2.5f)
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        val k = 25 shr amplifier

        return if (k > 0) duration % k == 0 else true
    }
}