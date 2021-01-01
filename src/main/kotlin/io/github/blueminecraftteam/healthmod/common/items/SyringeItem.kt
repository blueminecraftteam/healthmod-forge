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

package io.github.blueminecraftteam.healthmod.common.items

import net.minecraft.client.util.ITooltipFlag
import net.minecraft.client.world.ClientWorld
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.MobEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.IItemPropertyGetter
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.particles.ParticleTypes
import net.minecraft.stats.Stats
import net.minecraft.util.*
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

// TODO due to kotlin move over code may not be good! xf8b please review this
// TODO: add more functionalities to syringe
class SyringeItem(properties: Properties?) : Item(properties) {
    override fun onItemRightClick(world: World, player: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        val heldItem = player.getHeldItem(hand)

        // TODO: readd code
        // this is bad code - it doesn't check for isRemote and i dont think it would still work
        /*
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
        */return if (getBlood(heldItem) >= 1) {
            ActionResult.resultPass(heldItem)
        } else {
            player.addStat(Stats.DAMAGE_TAKEN)
            player.attackEntityFrom(DamageSource.GENERIC, 1f)
            setBlood(heldItem, 1)
            world.playSound(
                    player,
                    player.position,
                    SoundEvents.ENTITY_GENERIC_HURT,
                    SoundCategory.PLAYERS,
                    0.3f,
                    1f
            )
            ActionResult.resultSuccess(heldItem)
        }
    }

    //TODO: syringe still crashes when used on an entity mob fix it later
    override fun itemInteractionForEntity(stack: ItemStack, playerIn: PlayerEntity, target: LivingEntity, hand: Hand): ActionResultType {
        if (getBlood(stack) >= 1) return ActionResultType.PASS
        if (!playerIn.world.isRemote) {
            if (target is MobEntity) {
                target.attackEntityAsMob(playerIn)
                setBlood(stack, 1)
                playerIn.world.addParticle(
                        ParticleTypes.DRIPPING_LAVA,
                        target.getPosX(),
                        target.getPosY(),
                        target.getPosZ(), 0.0, -1.0, 0.0)
                return ActionResultType.SUCCESS
            }
        }
        return super.itemInteractionForEntity(stack, playerIn, target, hand)
    }

    override fun getUseDuration(stack: ItemStack): Int {
        return 20
    }

    @OnlyIn(Dist.CLIENT)
    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<ITextComponent>, flagIn: ITooltipFlag) {
        tooltip.add(TranslationTextComponent("text.healthmod.syringe.tooltip.1", getBlood(stack) >= 1))
    }

    companion object {
        private const val BLOOD_NBT_TAG = "blood"
        val BLOOD_PROPERTY = IItemPropertyGetter { stack: ItemStack, world: ClientWorld?, entity: LivingEntity? -> getBlood(stack).toFloat() }
        private fun getBlood(stack: ItemStack): Int {
            return stack.orCreateTag.getInt(BLOOD_NBT_TAG)
        }

        private fun setBlood(stack: ItemStack, amount: Int) {
            stack.orCreateTag.putInt(BLOOD_NBT_TAG, Math.min(amount, 1))
        }
    }
}