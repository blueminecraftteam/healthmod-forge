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

package io.github.blueminecraftteam.healthmod.client.armor

import net.minecraft.client.renderer.entity.model.BipedModel
import net.minecraft.client.renderer.model.ModelRenderer
import net.minecraft.entity.player.PlayerEntity

// TODO: Model
class FaceMaskModel : BipedModel<PlayerEntity?>(0.0f, 0.0f, 64, 32) {
    private val renderer: ModelRenderer

    init {
        textureHeight = 128
        textureWidth = 128
        renderer = ModelRenderer(this, 82, 0)
        bipedHead.addChild(renderer)
    }
}