package com.xiaohunao.equipmentbenediction.network.message;


import com.xiaohunao.equipmentbenediction.EquipmentBenediction;
import com.xiaohunao.equipmentbenediction.block_entity.container.RecastingDeskContainerMenu;
import com.xiaohunao.equipmentbenediction.registry.CapabilityRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class QualitySyncMessage {
    public boolean hasQuality;
    public String id;

    public QualitySyncMessage(String id, boolean hasQuality) {
        this.hasQuality = hasQuality;
        this.id = id;
    }

    public static void encode(QualitySyncMessage message, FriendlyByteBuf buffer) {
        buffer.writeBoolean(message.hasQuality);
        buffer.writeUtf(message.id);
    }

    public static QualitySyncMessage decode(FriendlyByteBuf buffer) {
        boolean hasQuality = buffer.readBoolean();
        String id = buffer.readUtf();
        return new QualitySyncMessage(id, hasQuality);
    }

    public static void handle(QualitySyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayer sender = context.getSender();
        if (sender == null) return;
        AbstractContainerMenu containerMenu = context.getSender().containerMenu;
        if (containerMenu instanceof RecastingDeskContainerMenu menu) {
            menu.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(cap -> {
                ItemStack stack = cap.getStackInSlot(0);
                stack.getCapability(CapabilityRegistry.QUALITY).ifPresent(quality -> {
                    boolean recasting = EquipmentBenediction.QUALITY_DATA.get(quality.getId()).Recasting(cap.getStackInSlot(1));
                    if (recasting) {
                        quality.setHasQuality(message.hasQuality);
                        quality.setId(message.id);
                        stack.getCapability(CapabilityRegistry.GLOSSARY).ifPresent(glossary -> {
                            glossary.setHasGlossary(false);
                            glossary.clearGlossaryIDList();
                        });
                    }
                });

            });
        }
        context.setPacketHandled(true);
    }


}
