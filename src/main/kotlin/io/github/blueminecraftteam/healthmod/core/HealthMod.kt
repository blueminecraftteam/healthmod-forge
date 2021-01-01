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

package io.github.blueminecraftteam.healthmod.core

import io.github.blueminecraftteam.healthmod.client.HealthModClient
import io.github.blueminecraftteam.healthmod.common.blocks.BandageBoxBlock
import io.github.blueminecraftteam.healthmod.core.config.HealthModConfig
import io.github.blueminecraftteam.healthmod.core.registries.*
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig
import org.apache.logging.log4j.LogManager
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import java.util.*

@Mod(HealthMod.MOD_ID)
@Mod.EventBusSubscriber(modid = HealthMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
object HealthMod {
    const val MOD_ID = "healthmod"
    val ITEM_GROUP = object : ItemGroup(MOD_ID) {
        @OnlyIn(Dist.CLIENT)
        override fun createIcon(): ItemStack {
            return ItemStack(ItemRegistries.BANDAGE)
        }
    }

    private val LOGGER = LogManager.getLogger()

    init {
        BlockRegistries.BLOCKS.register(MOD_BUS)
        ItemRegistries.ITEMS.register(MOD_BUS)
        EffectRegistries.EFFECTS.register(MOD_BUS)
        ContainerRegistries.CONTAINERS.register(MOD_BUS)
        TileEntityRegistries.TILE_ENTITIES.register(MOD_BUS)
        VillagerProfessionRegistries.POI_TYPES.register(MOD_BUS)
        VillagerProfessionRegistries.PROFESSIONS.register(MOD_BUS)

        LOGGER.debug("Registered deferred registers to mod event bus!")

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, HealthModConfig.SERVER_SPEC)

        LOGGER.debug("Registered config!")

        FORGE_BUS.register(this)
        FORGE_BUS.addListener(HealthModClient::clientEvent)
    }


    @SubscribeEvent
    fun onRegisterBlockItems(event: RegistryEvent.Register<Item>) {
        val registry = event.registry

        BlockRegistries.BLOCKS.getEntries().map { it.get() }.forEach {

            val properties: Item.Properties = if (it is BandageBoxBlock) {
                Item.Properties().group(ITEM_GROUP).maxStackSize(1)
            } else {
                Item.Properties().group(ITEM_GROUP)
            }

            val blockItem = BlockItem(it, properties)
            blockItem.registryName = Objects.requireNonNull(it.registryName)
            registry.register(blockItem)
        }

        LOGGER.debug("Registered block items!")
    }
}