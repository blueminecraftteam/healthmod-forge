package io.github.blueminecraftteam.healthmod.client.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.player.PlayerEntity;

// TODO: Model
public class FaceMaskModel extends BipedModel<PlayerEntity> {
    private final ModelRenderer renderer;

    public FaceMaskModel() {
        super(0.0F, 0.0F, 64, 32);

        this.textureHeight = 128;
        this.textureWidth = 128;
        this.renderer = new ModelRenderer(this, 82, 0);
        this.bipedHead.addChild(renderer);
    }
}
