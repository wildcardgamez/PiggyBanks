package com.wildcard.piggybanks;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;

public class FindMyPiggy {
    public static PiggyBankTile findMyPiggy(PacketBuffer buffer) {
        return (PiggyBankTile) Minecraft.getInstance().level.getBlockEntity(buffer.readBlockPos());
    }
}
