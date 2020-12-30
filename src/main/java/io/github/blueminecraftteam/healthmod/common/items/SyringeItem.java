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
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

// TODO: add more functionalities to syringe
public class SyringeItem extends Item {
    private static final String BLOOD_NBT_TAG = "blood";
    public static final IItemPropertyGetter BLOOD_PROPERTY = (stack, world, entity) -> getBlood(stack);

    public SyringeItem(Properties properties) {
        super(properties);
    }

    private static int getBlood(ItemStack stack) {
        return stack.getOrCreateTag().getInt(BLOOD_NBT_TAG);
    }

    private static void setBlood(ItemStack stack, int amount) {
        stack.getOrCreateTag().putInt(BLOOD_NBT_TAG, Math.min(amount, 1));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getHeldItem(hand);

        if (getBlood(heldItem) >= 1) {
            return ActionResult.resultPass(heldItem);
        } else {
            player.addStat(Stats.DAMAGE_TAKEN);
            player.attackEntityFrom(DamageSource.GENERIC, 1);

            setBlood(heldItem, 1);

            world.playSound(
                    player,
                    player.getPosition(),
                    SoundEvents.ENTITY_GENERIC_HURT,
                    SoundCategory.PLAYERS,
                    0.3F,
                    1F
            );

            return ActionResult.resultSuccess(heldItem);
        }
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 20;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("text.healthmod.syringe.tooltip.1", getBlood(stack) >= 1));
    }
}
