package com.wildcard.piggybanks;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.io.File;

@Mod.EventBusSubscriber
public class ConfigManager {
    private static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec config;

    static {
        init();
        config = builder.build();
    }

    public static void loadConfig(String path)
    {
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }

    public static ForgeConfigSpec.IntValue daysPerCompound;

    public static ForgeConfigSpec.DoubleValue coalIR;
    public static ForgeConfigSpec.IntValue coalMI;
    public static ForgeConfigSpec.DoubleValue ironIR;
    public static ForgeConfigSpec.IntValue ironMI;
    public static ForgeConfigSpec.DoubleValue redstoneIR;
    public static ForgeConfigSpec.IntValue redstoneMI;
    public static ForgeConfigSpec.DoubleValue goldIR;
    public static ForgeConfigSpec.IntValue goldMI;
    public static ForgeConfigSpec.DoubleValue lapisIR;
    public static ForgeConfigSpec.IntValue lapisMI;
    public static ForgeConfigSpec.DoubleValue emeraldIR;
    public static ForgeConfigSpec.IntValue emeraldMI;
    public static ForgeConfigSpec.DoubleValue diamondIR;
    public static ForgeConfigSpec.IntValue diamondMI;
    public static ForgeConfigSpec.DoubleValue netheriteIR;
    public static ForgeConfigSpec.IntValue netheriteMI;

    public static void init() {
        builder.comment("Piggybanks Config");
        daysPerCompound = builder.comment("The amount of days that must pass for interest to be added, default is 6 days per compound, 72 for an IRL day (Reccomended for servers)")
                .defineInRange("general.days_per_compound", 6, 1, 504);
        coalIR = builder.comment("Interest rate of coal, 0 will give no interest, 1 will give 100% interest. Default is 0.015625, 1 per stack")
                .defineInRange("vanilla.coal.interest_rate", 1/64f, 0, 1);
        coalMI = builder.comment("Maximum amount of coal a piggybank can generate in 1 compound. Default is 2304 a compound")
                .defineInRange("vanilla.coal.max_interest", 2304, 0, 5184);
        ironIR = builder.comment("Interest rate of iron, 0 will give no interest, 1 will give 100% interest. Default is 0.0078125, 1 per 2 stacks")
                .defineInRange("vanilla.iron.interest_rate", 1/128f, 0, 1);
        ironMI = builder.comment("Maximum amount of iron nuggets a piggybank can generate in 1 compound. Default is 20736 a compound")
                .defineInRange("vanilla.iron.max_interest", 20736, 0, 46656);
        redstoneIR = builder.comment("Interest rate of redstone, 0 will give no interest, 1 will give 100% interest. Default is 0.015625, 1 per stack")
                .defineInRange("vanilla.redstone.interest_rate", 1/64f, 0, 1);
        redstoneMI = builder.comment("Maximum amount of redstone a piggybank can generate in 1 compound. Default is 2304 a compound")
                .defineInRange("vanilla.redstone.max_interest", 2304, 0, 5184);
        goldIR = builder.comment("Interest rate of gold, 0 will give no interest, 1 will give 100% interest. Default is 0.0078125, 1 per 2 stacks")
                .defineInRange("vanilla.gold.interest_rate", 1/128f, 0, 1);
        goldMI = builder.comment("Maximum amount of gold nuggets a piggybank can generate in 1 compound. Default is 20736 a compound")
                .defineInRange("vanilla.gold.max_interest", 20736, 0, 46656);
        lapisIR = builder.comment("Interest rate of lapis, 0 will give no interest, 1 will give 100% interest. Default is 0.0078125, 1 per 2 stacks")
                .defineInRange("vanilla.lapis.interest_rate", 1/128f, 0, 1);
        lapisMI = builder.comment("Maximum amount of lapis a piggybank can generate in 1 compound. Default is 2304 a compound")
                .defineInRange("vanilla.lapis.max_interest", 2304, 0, 5184);
        emeraldIR = builder.comment("Interest rate of emerald, 0 will give no interest, 1 will give 100% interest. Default is 0.0078125, 1 per 2 stacks")
                .defineInRange("vanilla.emerald.interest_rate", 1/128f, 0, 1);
        emeraldMI = builder.comment("Maximum amount of emerald a piggybank can generate in 1 compound. Default is 2304 a compound")
                .defineInRange("vanilla.emerald.max_interest", 2304, 0, 5184);
        diamondIR = builder.comment("Interest rate of diamond, 0 will give no interest, 1 will give 100% interest. Default is 0.00390625, 1 per 4 stacks")
                .defineInRange("vanilla.diamond.interest_rate", 1/256f, 0, 1);
        diamondMI = builder.comment("Maximum amount of diamond a piggybank can generate in 1 compound. Default is 2304 a compound")
                .defineInRange("vanilla.diamond.max_interest", 2304, 0, 5184);
        netheriteIR = builder.comment("Interest rate of netherite, 0 will give no interest, 1 will give 100% interest. Default is 0.00390625, 1 per 4 stacks")
                .defineInRange("vanilla.netherite.interest_rate", 1/256f, 0, 1);
        netheriteMI = builder.comment("Maximum amount of netherite a piggybank can generate in 1 compound. Default is 2304 a compound")
                .defineInRange("vanilla.netherite.max_interest", 2304, 0, 5184);
    }
}
