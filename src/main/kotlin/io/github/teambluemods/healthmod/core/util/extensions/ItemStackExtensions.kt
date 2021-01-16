/*
 * Copyright (c) 2021 Team Blue Mods.
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

package io.github.blueminecraftteam.healthmod.core.util.extensions

import net.minecraft.item.ItemStack

operator fun ItemStack.minus(amount: Int): ItemStack = this.copy().apply { shrink(amount) }
operator fun ItemStack.minusAssign(amount: Int) = this.shrink(amount)
operator fun ItemStack.plus(amount: Int): ItemStack = this.copy().apply { grow(amount) }
operator fun ItemStack.plusAssign(amount: Int) = this.grow(amount)