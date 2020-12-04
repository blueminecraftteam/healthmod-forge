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

package io.github.blueminecraftteam.healthmod.core;

import io.github.blueminecraftteam.healthmod.common.blocks.BandAidBoxBlock;
import io.github.blueminecraftteam.healthmod.core.config.HealthModConfig;
import io.github.blueminecraftteam.healthmod.core.registries.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

@Mod(HealthMod.MOD_ID)
@Mod.EventBusSubscriber(modid = HealthMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HealthMod {
    public static final String MOD_ID = "healthmod";
    public static final ItemGroup ITEM_GROUP = new ItemGroup(MOD_ID) {
        @OnlyIn(Dist.CLIENT)
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemRegistries.BAND_AID.get());
        }
    };
    private static final Logger LOGGER = LogManager.getLogger();

    public HealthMod() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BlockRegistries.BLOCKS.register(modEventBus);
        ItemRegistries.ITEMS.register(modEventBus);
        EffectRegistries.EFFECTS.register(modEventBus);
        ContainerRegistries.CONTAINERS.register(modEventBus);
        TileEntityRegistries.TILE_ENTITIES.register(modEventBus);

        LOGGER.debug("Registered deferred registers to mod event bus!");

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, HealthModConfig.SERVER_SPEC);

        LOGGER.debug("Registered config!");

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void onRegisterBlockItems(final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();

        BlockRegistries.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
            final Item.Properties properties;

            if (block instanceof BandAidBoxBlock) {
                properties = new Item.Properties().group(ITEM_GROUP).maxStackSize(1);
            } else {
                properties = new Item.Properties().group(ITEM_GROUP);
            }

            final BlockItem blockItem = new BlockItem(block, properties);
            blockItem.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
            registry.register(blockItem);
        });

        LOGGER.debug("Registered block items!");
    }
}
