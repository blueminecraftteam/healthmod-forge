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
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
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

    /**
     * ray traces from a player to an entity
     */
    private static EntityRayTraceResult rayTrace(World worldIn, PlayerEntity player, Entity entityIn) {
        return ProjectileHelper.rayTraceEntities(worldIn, player, player.getPositionVec(), entityIn.getPositionVec(),
                player.getBoundingBox().expand(player.getMotion()).grow(1.0D), Entity::isAlive);
    }

    /**
     * ray traces from a player in favor of a vector3d
     */
    private static EntityRayTraceResult rayTrace(World worldIn, PlayerEntity player, Vector3d vto) {
        return ProjectileHelper.rayTraceEntities(worldIn, player, player.getPositionVec(), vto,
                player.getBoundingBox().expand(player.getMotion()).grow(1.0D), Entity::isAlive);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getHeldItem(hand);
        EntityRayTraceResult raytraceresult = rayTrace(world, player, player.getPositionVec().add(player.getLookVec()));

        try {
            // it crashes if entity is null
            Entity entity = raytraceresult.getEntity();
            if (raytraceresult.getEntity() instanceof MobEntity) {
                return ActionResult.resultPass(heldItem);
            }
        } catch (NullPointerException e) {
            // pretend nothing happened...
        }

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

    //TODO : syringe still crashes when used on an entity mob fix it later
    @Override
    public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target,
                                                     Hand hand) {
        if (getBlood(stack) >= 1) {
            return ActionResultType.PASS;
        }
        if (playerIn.world.isRemote) {
            if (target instanceof MobEntity) {
                target.attackEntityAsMob(playerIn);

                setBlood(stack, 1);

                playerIn.world.addParticle(
                        ParticleTypes.DRIPPING_LAVA,
                        target.getPosX(),
                        target.getPosY(),
                        target.getPosZ(),
                        0,
                        -1,
                        0
                );

                return ActionResultType.SUCCESS;
            }
        }

        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 20;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn,
                               List<ITextComponent> tooltip,
                               ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("text.healthmod.syringe.tooltip.1", getBlood(stack) >= 1));
    }
}
