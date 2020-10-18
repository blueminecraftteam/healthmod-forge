package io.github.blueminecraftteam.healthmod.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BloodExtractorThing extends Item {
    public BloodExtractorThing(Properties properties) {
        super(properties);
    }

    //TODO: IGNORE THIS

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        if (!worldIn.isRemote) {
            ItemStack stack = playerIn.getHeldItem(handIn);
            CompoundNBT compoundNBT = stack.getTag();

            if (playerIn.isSneaking()) {
                if (compoundNBT == null) {
                    compoundNBT = new CompoundNBT();
                    stack.setTag(compoundNBT);

                    compoundNBT.putString("uuid", playerIn.getUniqueID().toString());
                    playerIn.attackEntityFrom(new DamageSource("blood_extraction"), 2.5f);
                    //System.out.println();
                    return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);

                }

            }
        }

            return super.onItemRightClick(worldIn, playerIn, handIn);
        }

        @Override
        public boolean hitEntity (ItemStack stack, LivingEntity target, LivingEntity attacker){

            CompoundNBT compoundNBT = stack.getTag();
            if (compoundNBT == null) {
                compoundNBT = new CompoundNBT();
                stack.setTag(compoundNBT);
            } else {
                compoundNBT.putString("uuid", target.getUniqueID().toString());
            }
            return super.hitEntity(stack, target, attacker);
        }

        @Override
        public void addInformation (ItemStack stack, @Nullable World
        worldIn, List < ITextComponent > tooltip, ITooltipFlag flagIn){
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
}

