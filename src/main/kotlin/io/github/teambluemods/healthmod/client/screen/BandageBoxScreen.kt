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

package io.github.teambluemods.healthmod.client.screen

import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.systems.RenderSystem
import io.github.teambluemods.healthmod.common.container.BandageBoxContainer
import io.github.teambluemods.healthmod.core.HealthMod
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.Style
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

// TODO
@OnlyIn(Dist.CLIENT)
class BandageBoxScreen(
    screenContainer: BandageBoxContainer,
    inventory: PlayerInventory,
    titleIn: ITextComponent
) :
    ContainerScreen<BandageBoxContainer>(screenContainer, inventory, titleIn) {

    init {
        this.guiLeft = 0
        this.guiTop = 0
        this.xSize = 175
        this.ySize = 183
    }

//    constructor() : this(ContainerTypeRegistries.BANDAGE_BOX.create(10,inventory),inventory = ) {
//      FIXME figure out kotling
//    }

    private val TEXTURE = ResourceLocation(HealthMod.MOD_ID, "textures/gui/bandage_box.png")

    //Oh god mappings... this is hard to look at
    override fun render(matrixStack: MatrixStack, mouseX: Int, mouseY: Int, partTicks: Float) {
        this.renderBackground(matrixStack)
        super.render(matrixStack, mouseX, mouseY, partTicks)
        this.renderComponentHoverEffect(matrixStack, Style.EMPTY, mouseX, mouseY)
    }

    override fun drawGuiContainerForegroundLayer(matrixStack: MatrixStack, mouseX: Int, mouseY: Int) {
        super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY)
        font.drawString(
            matrixStack,
            title.unformattedComponentText,
            8.0f,
            6.0f,
            500
        )
        font.drawString(
            matrixStack,
            this.playerInventory.displayName.unformattedComponentText,
            8.0f,
            90.0f,
            500 // don't know what is number is
        )
    }

    override fun drawGuiContainerBackgroundLayer(matrixStack: MatrixStack, partialTicks: Float, x: Int, y: Int) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f)
        minecraft!!.getTextureManager().bindTexture(TEXTURE)
        val x: Int = (width - this.xSize) / 2
        val y: Int = (height - this.ySize) / 2
        this.blit(matrixStack, x, y, 0, 0, this.xSize, this.ySize)
    }
}