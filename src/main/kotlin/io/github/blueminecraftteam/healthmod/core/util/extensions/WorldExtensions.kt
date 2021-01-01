package io.github.blueminecraftteam.healthmod.core.util.extensions

import net.minecraft.world.World

val World.isClient get() = this.isRemote
val World.isServer get() = !this.isRemote