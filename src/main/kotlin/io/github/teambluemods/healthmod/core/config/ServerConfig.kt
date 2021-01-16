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

package io.github.blueminecraftteam.healthmod.core.config

import net.minecraftforge.common.ForgeConfigSpec

class ServerConfig(builder: ForgeConfigSpec.Builder) {
    @JvmField
    val bandageInfectionChance: ForgeConfigSpec.IntValue

    @JvmField
    val bandageInfectionChanceWhenHealthy: ForgeConfigSpec.IntValue

    @JvmField
    val bacterialResistanceChance: ForgeConfigSpec.IntValue

    init {
        builder.comment("This is the configuration for HealthMod.")

        builder.push("Wound Infection")
        builder.comment("Chance for bandages to fail and to give you a wound infection. Chance is 1 divided by this.")
        bandageInfectionChance = builder.defineInRange(
            "bandageInfectionChance",
            8,
            1, Int.MAX_VALUE
        )

        builder.comment("Chance for bandages to fail and to give you a wound infection when healthy. Chance is 1 divided by this.")
        bandageInfectionChanceWhenHealthy = builder.defineInRange(
            "bandageInfectionChanceWhenHealthy",
            16,
            1, Int.MAX_VALUE
        )

        builder.pop()

        builder.push("Other")
        builder.comment("Chance for bacteria to grow resistance to antibiotics (makes harmful effects stronger). Chance is 1 divided by this.")
        bacterialResistanceChance = builder.defineInRange(
            "bacterialResistanceChance",
            500,
            1, Int.MAX_VALUE
        )
        builder.pop()
    }
}