package io.github.blueminecraftteam.healthmod.common.effects;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

/**
 * Class to bypass a protected constructor in the {@link Effect effect class}.
 */
public class ModEffect extends Effect {
    public ModEffect(EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }
}
