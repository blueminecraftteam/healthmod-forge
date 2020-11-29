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

import com.google.common.collect.ImmutableMap;
import io.github.blueminecraftteam.healthmod.core.registries.ItemRegistries;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.Util;

import java.util.Random;

public class EntityTrades {
    protected static final Int2ObjectMap<VillagerTrades.ITrade[]> DOCTOR = Util.make(() -> {
        VillagerTrades.ITrade[] offers = new VillagerTrades.ITrade[]{
                new ItemsForEmeraldsTrade(ItemRegistries.BAND_AID.get(), 1, 2, 1),
                new ItemsForEmeraldsTrade(ItemRegistries.BAND_AID.get(), 1, 2, 1),
                new ItemsForEmeraldsTrade(ItemRegistries.BAND_AID.get(), 1, 2, 1)

        };

        return getAsIntMap(ImmutableMap.of(0, offers));
    });

    private static Int2ObjectMap<VillagerTrades.ITrade[]> getAsIntMap(ImmutableMap<Integer, VillagerTrades.ITrade[]> map) {
        return new Int2ObjectOpenHashMap<>(map);
    }


    static class ItemsForEmeraldsTrade implements VillagerTrades.ITrade {
        private final ItemStack offerStack;
        private final int price;
        private final int offerStackCount;
        private final int maxUses;
        private final int experience;
        private final float priceMultiplier;

        public ItemsForEmeraldsTrade(Block block, int price, int offerStackCount, int maxUses, int experience) {
            this(new ItemStack(block), price, offerStackCount, maxUses, experience);
        }

        public ItemsForEmeraldsTrade(Item item, int price, int offerStackCount, int experience) {
            this(new ItemStack(item), price, offerStackCount, 12, experience);
        }

        public ItemsForEmeraldsTrade(Item item, int price, int offerStackCount, int maxUses, int experience) {
            this(new ItemStack(item), price, offerStackCount, maxUses, experience);
        }

        public ItemsForEmeraldsTrade(ItemStack stack, int price, int offerStackCount, int maxUses, int experience) {
            this(stack, price, offerStackCount, maxUses, experience, 0.05F);
        }

        public ItemsForEmeraldsTrade(ItemStack stack, int price, int offerStackCount, int maxUses, int experience, float priceMultiplier) {
            this.offerStack = stack;
            this.price = price;
            this.offerStackCount = offerStackCount;
            this.maxUses = maxUses;
            this.experience = experience;
            this.priceMultiplier = priceMultiplier;
        }

        @Override
        public MerchantOffer getOffer(Entity trader, Random rand) {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.price), new ItemStack(this.offerStack.getItem(), this.offerStackCount), this.maxUses, this.experience, this.priceMultiplier);
        }
    }
}
