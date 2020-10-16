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

package io.github.xf8b.healthmod.items;

import io.github.xf8b.healthmod.registries.EffectRegistries;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.concurrent.ThreadLocalRandom;

public class BandAidItem extends Item {
    public BandAidItem(Item.Properties settings) {
        super(settings);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if(!worldIn.isRemote){
            ItemStack itemStack = playerIn.getHeldItem(handIn);
            if(itemStack.getDamage() == 1){
                switch(ThreadLocalRandom.current().nextInt(4)){
                    case 0:
                    case 1:
                    case 2:
                        playerIn.addPotionEffect(new EffectInstance(Effects.REGENERATION, 15 * 20, 0));
                        break;
                    case 3:
                        playerIn.addPotionEffect(new EffectInstance(EffectRegistries.WOUND_INFECTION.get(), 15 * 20, 0));
                    default:
                        throw new IllegalStateException("bruh moment exxx deeee");
                }
            }else{
                playerIn.addPotionEffect(new EffectInstance(Effects.REGENERATION, 15 * 20, 0));
            }
            itemStack.damageItem(1, playerIn, playerEntity -> playerEntity.sendBreakAnimation(handIn));
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
