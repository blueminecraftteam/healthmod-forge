package io.github.blueminecraftteam.healthmod.common.tileentity;

import io.github.blueminecraftteam.healthmod.core.registries.TileEntityRegistries;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class BloodTestMachineTileEntity extends TileEntity {

    public BloodTestMachineTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public BloodTestMachineTileEntity(){
        this(TileEntityRegistries.BLOOD_TEST_MACHINE.get());
    }



}
