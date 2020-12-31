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

package io.github.blueminecraftteam.healthmod.common.container;

import io.github.blueminecraftteam.healthmod.common.tileentity.BandAidBoxTileEntity;
import io.github.blueminecraftteam.healthmod.core.registries.BlockRegistries;
import io.github.blueminecraftteam.healthmod.core.registries.ContainerRegistries;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;

import java.util.Objects;

public class BandAidBoxContainer extends Container {
    public final BandAidBoxTileEntity tileEntity;
    private final IWorldPosCallable callable;

    public BandAidBoxContainer(final int windowId, final PlayerInventory playerInventory, final BandAidBoxTileEntity tileEntity) {
        super(ContainerRegistries.BAND_AID_BOX.get(), windowId);
        this.tileEntity = tileEntity;
        this.callable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());

        int slotSizePlus2 = 18;
    }

    public BandAidBoxContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
        this(windowId, playerInventory, getTileEntity(playerInventory, data));
    }

    private static BandAidBoxTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "PlayerInventory cannot be null");
        Objects.requireNonNull(data, "PacketBuffer cannot be null");

        final TileEntity tileEntity = playerInventory.player.world.getTileEntity(data.readBlockPos());

        if (tileEntity instanceof BandAidBoxTileEntity) {
            return (BandAidBoxTileEntity) tileEntity;
        }

        throw new IllegalStateException("Tile entity " + tileEntity + " is not correct!");
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(callable, playerIn, BlockRegistries.BAND_AID_BOX.get());
    }
}
