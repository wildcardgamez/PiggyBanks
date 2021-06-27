package com.wildcard.piggybanks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PiggyBankBlock extends ContainerBlock {
    public static final DirectionProperty DIR = BlockStateProperties.HORIZONTAL_FACING;
    protected static final VoxelShape NSHAPE = Block.box(3.0D, 2.0D, 0.0D, 13.0D, 12.0D, 13.0D);
    protected static final VoxelShape ESHAPE = Block.box(3.0D, 2.0D, 3.0D, 16.0D, 12.0D, 13.0D);
    protected static final VoxelShape SSHAPE = Block.box(3.0D, 2.0D, 3.0D, 13.0D, 12.0D, 16.0D);
    protected static final VoxelShape WSHAPE = Block.box(0.0D, 2.0D, 3.0D, 13.0D, 12.0D, 13.0D);

    public PiggyBankBlock(Block.Properties properties, BlockItem block, Item item, Item nugget, ForgeConfigSpec.DoubleValue intRate, ForgeConfigSpec.IntValue maxInt) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(DIR, Direction.NORTH));
        BLOCK = block;
        ITEM = item;
        NUGGET = nugget;
        HAS_NUGGET = true;
        RATE = intRate;
        MAX_RATE = maxInt;
        BLOCK_VAL = 9;
    }

    public PiggyBankBlock(Block.Properties properties, BlockItem block, Item item, ForgeConfigSpec.DoubleValue intRate, ForgeConfigSpec.IntValue maxInt) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(DIR, Direction.NORTH));
        BLOCK = block;
        ITEM = item;
        NUGGET = null;
        HAS_NUGGET = false;
        RATE = intRate;
        MAX_RATE = maxInt;
        BLOCK_VAL = 9;
    }

    public PiggyBankBlock(Block.Properties properties, BlockItem block, Item item, Item nugget, ForgeConfigSpec.DoubleValue intRate, ForgeConfigSpec.IntValue maxInt, int blockVal) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(DIR, Direction.NORTH));
        BLOCK = block;
        ITEM = item;
        NUGGET = nugget;
        HAS_NUGGET = true;
        RATE = intRate;
        MAX_RATE = maxInt;
        BLOCK_VAL = blockVal;
    }

    public PiggyBankBlock(Block.Properties properties, BlockItem block, Item item, ForgeConfigSpec.DoubleValue intRate, ForgeConfigSpec.IntValue maxInt, int blockVal) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(DIR, Direction.NORTH));
        BLOCK = block;
        ITEM = item;
        NUGGET = null;
        HAS_NUGGET = false;
        RATE = intRate;
        MAX_RATE = maxInt;
        BLOCK_VAL = blockVal;
    }

    public final Item BLOCK;
    public final Item ITEM;
    public final Item NUGGET;
    public final boolean HAS_NUGGET;
    public final ForgeConfigSpec.DoubleValue RATE;
    public final ForgeConfigSpec.IntValue MAX_RATE;
    public final int BLOCK_VAL;

    @Nullable
    @Override
    public TileEntity newBlockEntity(@Nonnull IBlockReader p_196283_1_) {
        return new PiggyBankTile(this);
    }

    @Nonnull
    @Override
    public ActionResultType use(@Nonnull BlockState blockState, World worldIn, @Nonnull BlockPos pos, @Nonnull PlayerEntity playerIn, @Nonnull Hand handIn, BlockRayTraceResult hit) {
        TileEntity tileentity = worldIn.getBlockEntity(pos);
        if(playerIn instanceof ServerPlayerEntity && tileentity instanceof PiggyBankTile) {
            NetworkHooks.openGui((ServerPlayerEntity) playerIn, (PiggyBankTile)tileentity, pos);
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public void setPlacedBy(World world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nullable LivingEntity placer, @Nonnull ItemStack stack) {
        TileEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof PiggyBankTile) {
            tileEntity.clearRemoved();
            if(stack.hasCustomHoverName())
                ((PiggyBankTile) tileEntity).setDisplayName(stack.getHoverName());
        }
    }

    @Nonnull
    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction direction = state.getValue(DIR);
        switch(direction) {
            case NORTH:
            default:
                return NSHAPE;
            case EAST:
                return ESHAPE;
            case SOUTH:
                return SSHAPE;
            case WEST:
                return WSHAPE;
        }
    }

    @Override
    public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation direction) {
        switch(direction) {
            case CLOCKWISE_90:
                switch(state.getValue(DIR)) {
                    case NORTH:
                        return state.setValue(DIR, Direction.EAST);
                    case EAST:
                        return state.setValue(DIR, Direction.SOUTH);
                    case SOUTH:
                        return state.setValue(DIR, Direction.WEST);
                    case WEST:
                        return state.setValue(DIR, Direction.NORTH);
                }
            case CLOCKWISE_180:
                switch(state.getValue(DIR)) {
                    case NORTH:
                        return state.setValue(DIR, Direction.SOUTH);
                    case EAST:
                        return state.setValue(DIR, Direction.WEST);
                    case SOUTH:
                        return state.setValue(DIR, Direction.NORTH);
                    case WEST:
                        return state.setValue(DIR, Direction.EAST);
                }
            case COUNTERCLOCKWISE_90:
                switch(state.getValue(DIR)) {
                    case NORTH:
                        return state.setValue(DIR, Direction.WEST);
                    case EAST:
                        return state.setValue(DIR, Direction.NORTH);
                    case SOUTH:
                        return state.setValue(DIR, Direction.EAST);
                    case WEST:
                        return state.setValue(DIR, Direction.SOUTH);
                }
        }
        return state;
    }

    @Override
    public BlockState getStateForPlacement (BlockItemUseContext context) {
        return this.defaultBlockState().setValue(DIR, context.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(DIR);
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        TileEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof PiggyBankTile) {
            int value = ((PiggyBankTile) tileEntity).getBank();
            int itemVal = 1;
            if (HAS_NUGGET)
                itemVal = 9;
            if (value > 0) {
                while (value >= 64 * BLOCK_VAL * itemVal) {
                    InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(BLOCK, 64));
                    value -= 64 * BLOCK_VAL * itemVal;
                }
                if (value >= BLOCK_VAL * itemVal) {
                    InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(BLOCK, value / (BLOCK_VAL * itemVal)));
                    value -= (value / (BLOCK_VAL * itemVal)) * BLOCK_VAL * itemVal;
                }
                if (value >= itemVal)
                    InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ITEM, value / itemVal));
                value -= (value / itemVal) * itemVal;
                if (HAS_NUGGET && value > 0)
                        InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(NUGGET, value));
            }
        }
        super.onRemove(state, world, pos, newState, isMoving);
    }
}
