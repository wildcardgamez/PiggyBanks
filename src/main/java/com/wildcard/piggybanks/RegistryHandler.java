package com.wildcard.piggybanks;

import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PiggyBanks.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PiggyBanks.MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, PiggyBanks.MOD_ID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, PiggyBanks.MOD_ID);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ScreenManager.register(PB_CONTAINER.get(), PiggyBankScreen::new));
    }

    public static final RegistryObject<Block> GOLD_PB = BLOCKS.register("gold_pb", PiggyBankBlock::new);
    public static final RegistryObject<Item> GOLD_PB_ITEM = ITEMS.register("gold_pb", () -> new BlockItem(GOLD_PB.get(), new Item.Properties().tab(PiggyBanks.TAB)));

    public static final RegistryObject<TileEntityType<PiggyBankTile>> PB_TILE = TILES.register("piggy_bank",
            () -> TileEntityType.Builder.of(PiggyBankTile::new, GOLD_PB.get()).build(null));

    public static final RegistryObject<ContainerType<PiggyBankContainer>> PB_CONTAINER = CONTAINERS.register("piggy_bank",
            () -> new ContainerType<>((PiggyBankContainer::new)));
}
