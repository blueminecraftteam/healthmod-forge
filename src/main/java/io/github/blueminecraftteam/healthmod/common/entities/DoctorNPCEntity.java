/*
 * Copyright (c) 2020 Blue Minecraft Team.
 *
 * This file is part of HealthMod.
 *
 * HealthMod is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HealthMod is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HealthMod.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.blueminecraftteam.healthmod.common.entities;

import io.github.blueminecraftteam.healthmod.core.HealthMod;
import io.github.blueminecraftteam.healthmod.core.registries.EntityRegistries;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.MerchantOffers;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class DoctorNPCEntity extends AbstractNPCEntity {
    public DoctorNPCEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
        super(EntityRegistries.DOCTOR.get(), worldIn);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 20D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 20.0D);
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(HealthMod.MOD_ID, "textures/entity/npc_doctor.png");
    }

    @Override
    protected void populateTradeData() {
        MerchantOffers offers = this.getOffers();
        this.addTrades(offers, EntityTrades.DOCTOR.get(0), Math.max(4, this.rand.nextInt(6) + 1));
    }
}
