/*
 * Copyright (c) 2020 xf8b.
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

package io.github.xf8b.healthmod;

import io.github.xf8b.healthmod.registries.BlockRegistries;
import io.github.xf8b.healthmod.registries.ItemRegistries;
import io.github.xf8b.healthmod.registries.StatusEffectRegistries;
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
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("healthmod")
@Mod.EventBusSubscriber(modid = HealthMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HealthMod {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "healthmod";
    public static final ItemGroup itemGroup = new ItemGroup(MOD_ID) {
        @OnlyIn(Dist.CLIENT)
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemRegistries.BAND_AID.get());
        }
    };


    public HealthMod(){
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BlockRegistries.BLOCKS.register(modEventBus);
        ItemRegistries.ITEMS.register(modEventBus);
        StatusEffectRegistries.EFFECTS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public void clientSetup(final FMLClientSetupEvent event){

    }

    public void setup(final FMLCommonSetupEvent event){

    }

    @SubscribeEvent
    public static void onRegisterBlockItems(final RegistryEvent.Register<Item> event){
        final IForgeRegistry<Item> registry = event.getRegistry();

        BlockRegistries.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
            final Item.Properties properties = new Item.Properties().group(itemGroup);
            final BlockItem blockItem = new BlockItem(block, properties);
            blockItem.setRegistryName(block.getRegistryName());
            registry.register(blockItem);

            LOGGER.log(Level.INFO, "Registered BlockItems.");
        });


    }
}
