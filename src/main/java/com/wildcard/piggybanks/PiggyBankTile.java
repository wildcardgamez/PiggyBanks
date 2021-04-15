package com.wildcard.piggybanks;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IClearable;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PiggyBankTile extends TileEntity implements INamedContainerProvider, IClearable {
    private ITextComponent name;
    private int bank = 0;
    private final PiggyBankBlock block;
    private int lastRecDay = -1;

    public PiggyBankTile(PiggyBankBlock blockIn) {
        super(RegistryHandler.PB_TILE.get());
        block = blockIn;
    }

    public PiggyBankTile() {
        this((PiggyBankBlock) RegistryHandler.GOLD_PB.get());
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        if (name != null)
            return name;
        return getBlockState().getBlock().getName();
    }

    public void setDisplayName(ITextComponent nameIn) {
        name = nameIn;
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory playerInv, @Nonnull PlayerEntity player) {
        return new PiggyBankContainer(id, playerInv, this);
    }

    public int getBank() {
        return bank;
    }

    public void depositToBank(int amt) {
        bank += amt;
        setChanged();
    }

    public void collectInterest() {
        depositToBank((int) (bank * block.RATE));
    }

    public String getVisualBank() {
        if(block.HAS_NUGGET) {
            if (bank % 9 > 0)
                return (bank / 9) + "+" + (bank % 9);
            return "" + (bank / 9);
        }
        return "" + bank;
    }

    public String getVisualInterest() {
        if(block.HAS_NUGGET) {
            if ((int) (bank * block.RATE % 9) > 0)
                return (int) (bank * block.RATE / 9) + "+" + (int) (bank * block.RATE % 9);
            return "" + (int) (bank * block.RATE / 9);
        }
        return "" + (int) (bank * block.RATE);
    }

    public int getItemValue(Item itemIn) {
        if(itemIn.equals(block.ITEM)) {
            if(block.HAS_NUGGET)
                return 9;
            return 1;
        }
        if(itemIn.equals(block.BLOCK)) {
            if(block.HAS_NUGGET)
                return 81;
            return 9;
        }
        if(block.HAS_NUGGET && itemIn.equals(block.NUGGET))
            return 1;
        return -1;
    }

    public int getItemStackValue(ItemStack stack) {
        return getItemValue(stack.getItem()) * stack.getCount();
    }

    public boolean hasNugget() {
        return block.HAS_NUGGET;
    }

    public PiggyBankBlock getBlock() {
        return block;
    }

    @Nonnull
    @Override
    public CompoundNBT save(@Nonnull CompoundNBT nbt) {
        super.save(nbt);
        nbt.putInt("bank", bank);
        nbt.putString("name", ITextComponent.Serializer.toJson(name));
        return nbt;
    }

    @Override
    public void load(@Nonnull BlockState state, @Nonnull CompoundNBT nbt) {
        super.load(state, nbt);
        bank = nbt.getInt("bank");
        name = ITextComponent.Serializer.fromJson(nbt.getString("name"));
    }

    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        return this.save(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.load(this.getBlockState(), pkt.getTag());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(worldPosition, 0, this.getUpdateTag());
    }

    @Override
    public void clearContent() {
        bank = 0;
    }
}
