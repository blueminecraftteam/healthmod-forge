package io.github.blueminecraftteam.healthmod.core.util.extensions

import net.minecraft.item.ItemStack

operator fun ItemStack.minus(amount: Int): ItemStack = this.copy().apply { shrink(amount) }
operator fun ItemStack.minusAssign(amount: Int) = this.shrink(amount)