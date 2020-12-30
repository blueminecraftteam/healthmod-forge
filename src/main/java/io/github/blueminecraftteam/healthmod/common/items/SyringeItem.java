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

package io.github.blueminecraftteam.healthmod.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

// TODO : add more functionalities to syringe
public class SyringeItem extends Item {
    private static final String TAG_BLOOD = "blood_fill";
    public static final IItemPropertyGetter bloodFillProperty = (stack, world, entity) -> {
        if (getBloodAmount(stack) < 0.2f) {
            return 0.0f;
        } else if (getBloodAmount(stack) < 0.4f) {
            return 0.2f;
        } else if (getBloodAmount(stack) < 0.6f) {
            return 0.4f;
        } else if (getBloodAmount(stack) < 0.8f) {
            return 0.6f;
        } else if (getBloodAmount(stack) < 1.0f) {
            return 0.8f;
        } else if (getBloodAmount(stack) == 1.0f) {
            return 1.0f;
        }
        return 0.0f;
    };

    public SyringeItem(Properties properties) {
        super(properties);
    }

    /**
     * @param amountIn value between 0 and 1
     */
    // TODO : change this horrible math later
    public static void storeBlood(ItemStack stack, float amountIn) {
        amountIn = Math.abs(amountIn);
        amountIn = Math.min(amountIn, 1.0f);
        amountIn += stack.getOrCreateTag().getFloat(TAG_BLOOD);
        stack.getOrCreateTag().putFloat(TAG_BLOOD,
                Math.min(amountIn, 1.0f));
    }

    /**
     * @param amountOut value between 0 and 1
     */
    // TODO : change this horrible math later
    public static void extractBlood(ItemStack stack, float amountOut) {
        amountOut = Math.abs(amountOut);
        amountOut = Math.min(amountOut, 1.0f);
        float amountStored = stack.getOrCreateTag().getFloat(TAG_BLOOD);
        amountStored -= amountOut;
        amountStored = Math.min(Math.abs(amountStored), 0.0f);
        stack.getOrCreateTag().putFloat(TAG_BLOOD, amountStored);
    }

    public static boolean isFull(ItemStack stack) {
        if (!(stack.getOrCreateTag().contains(TAG_BLOOD))) {
            return false;
        }
        float blood = stack.getOrCreateTag().getFloat(TAG_BLOOD);
        return blood == 1.0f;
    }

    public static float getBloodAmount(ItemStack stack) {
        if (!(stack.getOrCreateTag().contains(TAG_BLOOD))) {
            return 0.0f;
        }
        return stack.getOrCreateTag().getFloat(TAG_BLOOD);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemStack = playerIn.getHeldItem(handIn);
        if (isFull(itemStack)) {
            return ActionResult.resultPass(itemStack);
        }
        playerIn.addStat(Stats.DAMAGE_TAKEN);
        playerIn.attackEntityFrom(DamageSource.CACTUS, 1);
        storeBlood(itemStack, 0.1f);
        worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.PLAYERS,
                0.3F, 1F);
        return ActionResult.resultSuccess(itemStack);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 20;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip,
                               ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent(String.valueOf(getBloodAmount(stack))));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
