package com.wildcard.piggybanks;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;

public class PiggyBankContainer extends Container {
    public final PiggyBankTile tile;
    Inventory inv;
    ITextComponent balanceText;
    ITextComponent rateText;

    public PiggyBankContainer(int id, PlayerInventory playerInventory) {
        this(id, playerInventory, new PiggyBankTile(new PiggyBankBlock()));
    }

    public PiggyBankContainer(int id, PlayerInventory playerInventory, PiggyBankTile tileIn) {
        super(RegistryHandler.PB_CONTAINER.get(), id);
        inv = new Inventory(4);
        tile = tileIn;
        //Set up specific slots
        this.addSlot(new DepositSlot(inv, 0, 71, 28));
        this.addSlot(new WithdrawlSlot(inv, 1, 101, 53));
        this.addSlot(new WithdrawlSlot(inv, 2, 125, 53));
        if(tile.hasNugget())
            this.addSlot(new WithdrawlSlot(inv, 3, 149, 53));
        refreshPiggyBank();
        //Set up slots for inventory
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                this.addSlot(new Slot(playerInventory, x + (y * 9) + 9, 8 + x * 18, 84 + y * 18));
            }
        }
        //Set up slots for hotbar
        for (int x = 0; x < 9; x++) {
            this.addSlot(new Slot(playerInventory, x, 8 + x * 18, 142));
        }
    }

    private class DepositSlot extends Slot {
        public DepositSlot(IInventory inventory, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
            super(inventory, p_i1824_2_, p_i1824_3_, p_i1824_4_);
        }

        @Override
        public boolean mayPlace(@Nonnull ItemStack stack) {
            return (tile.getItemStackValue(stack) > 0);
        }

        @Override
        public void set(@Nonnull ItemStack stack) {
            if(tile.getItemStackValue(stack) > 0)
                tile.depositToBank(tile.getItemStackValue(stack));
            refreshPiggyBank();
        }
    }

    private class WithdrawlSlot extends Slot {
        public WithdrawlSlot(IInventory inventory, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
            super(inventory, p_i1824_2_, p_i1824_3_, p_i1824_4_);
        }

        @Override
        public boolean mayPlace(@Nonnull ItemStack stack) {
            return false;
        }

        @Nonnull
        @Override
        public ItemStack onTake(@Nonnull PlayerEntity player, @Nonnull ItemStack stack) {
            tile.depositToBank(-tile.getItemStackValue(stack));
            refreshPiggyBank();
            this.setChanged();
            return stack;
        }
    }

    @Override
    public boolean stillValid(@Nonnull PlayerEntity p_75145_1_) {
        return true;
    }

    private void refreshPiggyBank() {
        int bal = tile.getBank();
        if(tile.hasNugget()) {
            if (bal >= 81) {
                if (bal >= 5184)
                    inv.setItem(1, new ItemStack(tile.getBlock().BLOCK, 64));
                else
                    inv.setItem(1, new ItemStack(tile.getBlock().BLOCK, bal/81));
            }
            else
                inv.setItem(1, ItemStack.EMPTY);
            if (bal >= 9) {
                if (bal >= 576)
                    inv.setItem(2, new ItemStack(tile.getBlock().ITEM, 64));
                else
                    inv.setItem(2, new ItemStack(tile.getBlock().ITEM, bal/9));
            }
            else
                inv.setItem(2, ItemStack.EMPTY);
            if (bal >= 1) {
                if (bal >= 64)
                    inv.setItem(3, new ItemStack(tile.getBlock().NUGGET, 64));
                else
                    inv.setItem(3, new ItemStack(tile.getBlock().NUGGET, bal));
            }
            else
                inv.setItem(3, ItemStack.EMPTY);
        }
        else {
            if (bal >= 9) {
                if (bal >= 576)
                    inv.setItem(1, new ItemStack(tile.getBlock().BLOCK, 64));
                else
                    inv.setItem(1, new ItemStack(tile.getBlock().BLOCK, bal/9));
            }
            else
                inv.setItem(1, ItemStack.EMPTY);
            if (bal >= 1) {
                if (bal >= 64)
                    inv.setItem(2, new ItemStack(tile.getBlock().ITEM, 64));
                else
                    inv.setItem(2, new ItemStack(tile.getBlock().ITEM, bal));
            }
            else
                inv.setItem(2, ItemStack.EMPTY);
        }
        balanceText = new TranslationTextComponent("gui.piggybanks.balance").append(tile.getVisualBank());
        rateText = new TranslationTextComponent("gui.piggybanks.rate").append(tile.getVisualInterest());
    }

    public ITextComponent getBalanceText() {
        return balanceText;
    }

    public ITextComponent getRateText() {
        return rateText;
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(PlayerEntity playerEntity, int i) {
        Slot slot = slots.get(i);
        if(slot != null && slot.hasItem()) {
            if(i > 0 && i <= 3) {
                ItemStack stack = slot.getItem().copy();
                if(this.moveItemStackTo(slot.getItem(), 4, slots.size(), true)) {
                    tile.depositToBank(-tile.getItemStackValue(stack));
                    refreshPiggyBank();
                    slot.setChanged();
                    return ItemStack.EMPTY;
                }
                return stack;
            }
            else if(i >= 4 && this.moveItemStackTo(slot.getItem(), 0, 1, false)) {
                return ItemStack.EMPTY;
            }
            return slot.getItem().copy();
        }
        return ItemStack.EMPTY;
    }
}
