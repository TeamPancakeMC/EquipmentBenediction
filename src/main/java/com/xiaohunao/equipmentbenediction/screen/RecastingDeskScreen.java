package com.xiaohunao.equipmentbenediction.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.xiaohunao.equipmentbenediction.EquipmentBenediction;
import com.xiaohunao.equipmentbenediction.block_entity.RecastingDeskBlockEntity;
import com.xiaohunao.equipmentbenediction.block_entity.container.RecastingDeskContainerMenu;
import com.xiaohunao.equipmentbenediction.data.dao.QualityData;
import com.xiaohunao.equipmentbenediction.data.dao.RecastingRequirement;
import com.xiaohunao.equipmentbenediction.network.EquipmentQualityPacketHandler;
import com.xiaohunao.equipmentbenediction.network.message.QualitySyncMessage;
import com.xiaohunao.equipmentbenediction.registry.CapabilityRegistry;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class RecastingDeskScreen extends AbstractContainerScreen<RecastingDeskContainerMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(EquipmentBenediction.MOD_ID, "textures/gui/recasting_desk_gui.png");
    private static boolean isPressed = false;

    public RecastingDeskScreen(RecastingDeskContainerMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void renderBg(@NotNull PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        blit(pPoseStack, this.leftPos, this.topPos, 0.0F, 0.0F, 176, 166, 256, 256);
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        addRenderableWidget(new RecastingButton(leftPos + 80, topPos + 39, 16, 16, MutableComponent.create(new TranslatableContents("equipmentquality.recasting_desk_gui.recasting"))));
    }

    private class RecastingButton extends Button {
        public RecastingButton(int x, int y, int width, int height, Component message) {
            super(x, y, width, height, message, new RecastingButtonOnPress());
        }

        @Override
        public void renderButton(@NotNull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            if (isPressed)
                blit(matrixStack, x, y, 21, 167, 19, 19, 256, 256);
            else
                blit(matrixStack, x, y, 1, 167, 19, 19, 256, 256);
        }
    }

    public class RecastingButtonOnPress implements Button.OnPress {

        @Override
        public void onPress(@NotNull Button button) {
            if (menu.blockEntity instanceof RecastingDeskBlockEntity recastingDeskBlockEntity) {
                isPressed = true;
                ItemStackHandler itemHandler = recastingDeskBlockEntity.getItemHandler();
                ItemStack equipmentStack = itemHandler.getStackInSlot(0);
                ItemStack stack = itemHandler.getStackInSlot(1);
                equipmentStack.getCapability(CapabilityRegistry.QUALITY).ifPresent(cap -> {
                    QualityData qualityData = EquipmentBenediction.QUALITY_DATA.get(cap.getId());
                    for (RecastingRequirement recastingRequirement : qualityData.getRecastingRequirement()) {
                        boolean completeValid = recastingRequirement.isCompleteValid(stack);
                        boolean valid = qualityData.isValid(stack);
                        if (completeValid && valid) {
                            boolean hasQuality = cap.isHasQuality();
                            String id = EquipmentBenediction.QUALITY_DATA.getRandomEquipmentQualityData().getId();
                            EquipmentQualityPacketHandler.INSTANCE.sendToServer(new QualitySyncMessage(id, hasQuality));
                        }
                    }
                });
            }
            new Thread(() -> {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isPressed = false;
            }).start();
        }
    }
}
