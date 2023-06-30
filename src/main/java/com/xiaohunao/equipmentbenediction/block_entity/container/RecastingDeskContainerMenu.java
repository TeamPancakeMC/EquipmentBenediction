package com.xiaohunao.equipmentbenediction.block_entity.container;

import com.xiaohunao.equipmentbenediction.EquipmentBenediction;
import com.xiaohunao.equipmentbenediction.data.dao.QualityData;
import com.xiaohunao.equipmentbenediction.registry.BlockRegistry;
import com.xiaohunao.equipmentbenediction.registry.CapabilityRegistry;
import com.xiaohunao.equipmentbenediction.registry.MenuTypeRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

public class RecastingDeskContainerMenu extends AbstractContainerMenu {
    private static ItemStack ItemInFirstSlot = null;
    public Inventory playerInv;
    public BlockEntity blockEntity;
    public Level level;


    public RecastingDeskContainerMenu(int windowId, Inventory playerInv, FriendlyByteBuf extraData) {
        this(windowId, playerInv, playerInv.player.level.getBlockEntity(extraData.readBlockPos()));
    }

    public RecastingDeskContainerMenu(int windowId, Inventory playerInv, BlockEntity blockEntity) {
        super(MenuTypeRegistry.RECASTING_DESK.get(), windowId);
        this.playerInv = playerInv;
        this.blockEntity = blockEntity;
        this.level = playerInv.player.level;


        addPlayerInventory(playerInv);
        addPlayerHotbar(playerInv);


        this.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            this.addSlot(new InputSlot(handler, 0, 44, 24));
            this.addSlot(new OutputSlot(handler, 1, 44, 59));
        });
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, BlockRegistry.RECASTING_DESK.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    private static class InputSlot extends SlotItemHandler {

        public InputSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(@Nullable ItemStack stack) {
            if (stack != null) {
                return EquipmentBenediction.QUALITY_DATA.isValid(stack);
            }
            return false;
        }

        @Override
        public void setChanged() {
            super.setChanged();
            ItemInFirstSlot = this.getItem();
        }
    }

    private static class OutputSlot extends SlotItemHandler {

        public OutputSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(@Nullable ItemStack stack) {
            AtomicBoolean valid = new AtomicBoolean(false);
            if (stack != null && ItemInFirstSlot != null) {
                ItemInFirstSlot.getCapability(CapabilityRegistry.QUALITY).ifPresent(cap -> {
                    QualityData qualityData = EquipmentBenediction.QUALITY_DATA.get(cap.getId());
                    qualityData.getRecastingRequirement().forEach(recastingRequirement -> {
                        valid.set(recastingRequirement.isValid(stack));
                    });
                });
            }
            return valid.get();
        }
    }
}
