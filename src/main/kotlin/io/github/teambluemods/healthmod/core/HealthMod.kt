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

package io.github.teambluemods.healthmod.core

import io.github.teambluemods.healthmod.common.blocks.BandageBoxBlock
import io.github.teambluemods.healthmod.core.config.HealthModConfig
import io.github.teambluemods.healthmod.core.registries.*
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig
import org.apache.logging.log4j.LogManager
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.ObjectHolderDelegate
import thedarkcolour.kotlinforforge.forge.registerConfig

@Mod(HealthMod.MOD_ID)
@Mod.EventBusSubscriber(modid = HealthMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
object HealthMod {
    const val MOD_ID = "healthmod"
    val ITEM_GROUP = object : ItemGroup(MOD_ID) {
        @OnlyIn(Dist.CLIENT)
        override fun createIcon() = ItemStack(ItemRegistries.BANDAGE)
    }
    private val LOGGER = LogManager.getLogger()

    init {
        BlockRegistries.BLOCKS.register(MOD_BUS)
        ItemRegistries.ITEMS.register(MOD_BUS)
        EffectRegistries.EFFECTS.register(MOD_BUS)
        ContainerTypeRegistries.CONTAINER_TYPES.register(MOD_BUS)
        TileEntityTypeRegistries.TILE_ENTITY_TYPES.register(MOD_BUS)
        VillagerProfessionRegistries.POI_TYPES.register(MOD_BUS)
        VillagerProfessionRegistries.PROFESSIONS.register(MOD_BUS)

        LOGGER.debug("Registered deferred registers to mod event bus!")

        registerConfig(ModConfig.Type.SERVER, HealthModConfig.SERVER_SPEC)

        LOGGER.debug("Registered config!")
    }

    fun rl(path: String) = ResourceLocation(MOD_ID, path)

    @SubscribeEvent
    fun onRegisterBlockItems(event: RegistryEvent.Register<Item>) {
        BlockRegistries.BLOCKS.getEntries().map(ObjectHolderDelegate<out Block>::get).forEach { block ->
            val properties = if (block is BandageBoxBlock) {
                Item.Properties().group(ITEM_GROUP).maxStackSize(1)
            } else {
                Item.Properties().group(ITEM_GROUP)
            }

            val blockItem = BlockItem(block, properties).apply { registryName = block.registryName }

            event.registry.register(blockItem)
        }

        LOGGER.debug("Registered block items!")
    }
}