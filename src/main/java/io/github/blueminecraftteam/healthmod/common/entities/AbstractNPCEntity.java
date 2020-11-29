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

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.INPC;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


//Credit Mr Crayfish for some bits
public abstract class AbstractNPCEntity extends CreatureEntity implements INPC, IMerchant {
    @Nullable
    private PlayerEntity customer;
    private MerchantOffers offers;

    public AbstractNPCEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public abstract ResourceLocation getTexture();

    protected abstract void populateTradeData();

    @Nullable
    @Override
    public PlayerEntity getCustomer() {
        return this.customer;
    }

    @Override
    public void setCustomer(@Nullable PlayerEntity player) {
        this.customer = player;
    }

    public boolean hasCustomer() {
        return this.customer != null;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        this.updateArmSwingProgress();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 0.4D));
        this.goalSelector.addGoal(8, new MoveTowardsRestrictionGoal(this, 0.4D));
        this.goalSelector.addGoal(9, new LookAtWithoutMovingGoal(this, PlayerEntity.class, 4.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
    }

    @Override
    protected ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getHeldItem(hand);
        if (heldItem.getItem() == Items.NAME_TAG) {
            heldItem.interactWithEntity(player, this, hand);
            return ActionResultType.SUCCESS;
        } else if (this.isAlive() && !this.hasCustomer() && !this.isChild()) {
            if (this.getOffers().isEmpty()) {
                return super.func_230254_b_(player, hand);
            } else if (!this.world.isRemote && (this.getRevengeTarget() == null || this.getRevengeTarget() != player)) {
                this.setCustomer(player);
                this.openMerchantContainer(player, this.getDisplayName(), 1);
            }
            return ActionResultType.SUCCESS;
        }
        return super.func_230254_b_(player, hand);
    }

    @Override
    public MerchantOffers getOffers() {
        if (this.offers == null) {
            this.offers = new MerchantOffers();
            this.populateTradeData();
        }
        return this.offers;
    }

    protected void addTrades(MerchantOffers offers, @Nullable VillagerTrades.ITrade[] trades, int max) {
        if (trades == null)
            return;
        List<Integer> randomIndex = IntStream.range(0, trades.length).boxed().collect(Collectors.toList());
        Collections.shuffle(randomIndex);
        randomIndex = randomIndex.subList(0, Math.min(trades.length, max));
        for (Integer index : randomIndex) {
            VillagerTrades.ITrade trade = trades[index];
            MerchantOffer offer = trade.getOffer(this, this.rand);
            if (offer != null) {
                offers.add(offer);
            }
        }
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("Offers", 10)) {
            this.offers = new MerchantOffers(compound.getCompound("Offers"));
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        MerchantOffers merchantOffers = this.getOffers();
        if (!merchantOffers.isEmpty()) {
            compound.put("Offers", merchantOffers.write());
        }
    }

    @Override
    public void setClientSideOffers(@Nullable MerchantOffers offers) {
    }

    @Override
    public void onTrade(MerchantOffer offer) {
        offer.increaseUses();
    }

    @Override
    public void verifySellingItem(ItemStack stack) {
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    @Override
    public int getXp() {
        return 0;
    }

    @Override
    public void setXP(int xpIn) {
    }

    @Override
    public boolean hasXPBar() {
        return false;
    }

    @Override
    public boolean canRestockTrades() {
        return false;
    }

    @Override
    public SoundEvent getYesSound() {
        return SoundEvents.ENTITY_VILLAGER_YES;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return super.getHurtSound(damageSourceIn);
    }
}
