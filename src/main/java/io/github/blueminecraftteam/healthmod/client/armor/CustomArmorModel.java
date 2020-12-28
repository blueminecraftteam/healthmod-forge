package io.github.blueminecraftteam.healthmod.client.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.player.PlayerEntity;

// TODO: Model
// this class acts as a placeholder for our face mask custom model if we wanted to add one
public class CustomArmorModel extends BipedModel<PlayerEntity> {
    public ModelRenderer renderer;
    public CustomArmorModel() {
        super(0.0F, 0.0F, 64, 32);
        this.textureHeight = 128;
        this.textureWidth = 128;
        this.renderer = new ModelRenderer(this,82,0);
        this.bipedHead.addChild(renderer);
    }
}
