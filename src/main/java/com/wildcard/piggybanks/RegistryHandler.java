package com.wildcard.piggybanks;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.extensions.IForgeContainerType;
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

    public static final RegistryObject<Block> GOLD_PB = createPiggyBank("gold_pb", (BlockItem) Items.GOLD_BLOCK, Items.GOLD_INGOT, Items.GOLD_NUGGET, (float) 1/81, 46656);
    public static final RegistryObject<Block> IRON_PB = createPiggyBank("iron_pb", (BlockItem) Items.IRON_BLOCK, Items.IRON_INGOT, Items.IRON_NUGGET, (float) 1/64, 46656);
    public static final RegistryObject<Block> EMERALD_PB = createPiggyBank("emerald_pb", (BlockItem) Items.EMERALD_BLOCK, Items.EMERALD, (float) 1/64, 5184);

    public static final RegistryObject<TileEntityType<PiggyBankTile>> PB_TILE = TILES.register("piggy_bank",
            () -> TileEntityType.Builder.of(PiggyBankTile::new, GOLD_PB.get(), IRON_PB.get(), EMERALD_PB.get()).build(null));

    public static final RegistryObject<ContainerType<PiggyBankContainer>> PB_CONTAINER = CONTAINERS.register("piggy_bank",
            () -> IForgeContainerType.create(PiggyBankContainer::new));

    public static RegistryObject<Block> createPiggyBank(String name, BlockItem block, Item item, Item nugget, float intRate, int maxInt) {
        RegistryObject<Block> pbBlock = BLOCKS.register(name, () -> new PiggyBankBlock(block, item, nugget, intRate, maxInt));
        ITEMS.register(name, () -> new BlockItem(pbBlock.get(), new Item.Properties().tab(PiggyBanks.TAB)));
        return pbBlock;
    }

    public static RegistryObject<Block> createPiggyBank(String name, BlockItem block, Item item, float intRate, int maxInt) {
        RegistryObject<Block> pbBlock = BLOCKS.register(name, () -> new PiggyBankBlock(block, item, intRate, maxInt));
        ITEMS.register(name, () -> new BlockItem(pbBlock.get(), new Item.Properties().tab(PiggyBanks.TAB)));
        return pbBlock;
    }
}
