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

package io.github.teambluemods.healthmod.core.config

import net.minecraftforge.common.ForgeConfigSpec
import org.apache.commons.lang3.tuple.Pair

object HealthModConfig {
    private val specPair: Pair<ServerConfig, ForgeConfigSpec> = ForgeConfigSpec.Builder().configure(::ServerConfig)

    val SERVER_CONFIG: ServerConfig = specPair.left
    val SERVER_SPEC: ForgeConfigSpec = specPair.right
}