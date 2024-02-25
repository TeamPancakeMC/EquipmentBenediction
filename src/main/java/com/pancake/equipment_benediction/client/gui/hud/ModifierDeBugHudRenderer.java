package com.pancake.equipment_benediction.client.gui.hud;

import com.pancake.equipment_benediction.EquipmentBenediction;
import com.pancake.equipment_benediction.api.IModifier;
import com.pancake.equipment_benediction.common.init.ModGameRules;
import com.pancake.equipment_benediction.common.modifier.ModifierHelper;
import com.pancake.equipment_benediction.common.modifier.ModifierInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;


public class ModifierDeBugHudRenderer {
    static Minecraft minecraft = Minecraft.getInstance();

    public static IGuiOverlay MODIFIER_DEBUG_HUD = (forgeGui, guiGraphics, partialTicks, screenWidth, screenHeight) -> {
        render(forgeGui, guiGraphics, screenWidth, screenHeight, minecraft.player);
    };

    public static void render(ForgeGui forgeGui, GuiGraphics guiGraphics, int width, int height, LocalPlayer player) {
        GameRules gameRules = player.level().getGameRules();
        if (gameRules.getRule(ModGameRules.DE_BUG_HUD).get()) {
            return;
        }

        ListTag modifierTags = ModifierHelper.getPlayerModifierListTag(player);
        modifierTags.forEach((tag) -> {
            ModifierInstance.CODEC.parse(NbtOps.INSTANCE, tag)
                    .resultOrPartial(EquipmentBenediction.LOGGER::error)
                    .ifPresent((instance) -> {
                        IModifier modifier = instance.getModifier();
                        int amplifier = instance.getAmplifier();
                        guiGraphics.drawString(minecraft.font, modifier.getDisplayName(), 0, 0, 0xFFFFFF);
                        guiGraphics.drawString(minecraft.font, "Amplifier: " + amplifier, 200, 0, 0xFFFFFF);
                    });
        });
    }

    public static void registerOverlay(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.FOOD_LEVEL.id(), "modifier_debug", MODIFIER_DEBUG_HUD);
    }
}