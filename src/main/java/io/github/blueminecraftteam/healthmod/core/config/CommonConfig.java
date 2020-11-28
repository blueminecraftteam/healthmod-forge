package io.github.blueminecraftteam.healthmod.core.config;

import io.github.blueminecraftteam.healthmod.HealthMod;
import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {
    public final ForgeConfigSpec.BooleanValue canSleepWithoutMask;
    public CommonConfig(ForgeConfigSpec.Builder builder){
           builder.comment("This is the configuration for HealthMod.");
           builder.push("Misc");
           canSleepWithoutMask =
                   builder.comment("Whether you can sleep without a mask.")
                   .translation(HealthMod.MOD_ID + ".config." + "canSleepWithoutMask")
                   .define("canSleepWithoutMask", true);
           builder.pop();
    }
}
