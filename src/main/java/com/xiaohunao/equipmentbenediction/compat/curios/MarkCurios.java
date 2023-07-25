package com.xiaohunao.equipmentbenediction.compat.curios;

import com.xiaohunao.equipmentbenediction.data.GlossaryDataLoader;
import com.xiaohunao.equipmentbenediction.registry.CapabilityRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.util.ICuriosHelper;

import java.util.List;
import java.util.Set;

public class MarkCurios {
    @SubscribeEvent
    public void Mark(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        Level level = player.level;
        if (level.isClientSide) {
            return;
        }
        NonNullList<ItemStack> items = player.inventoryMenu.getItems();
        ICuriosHelper curiosHelper = CuriosApi.getCuriosHelper();

        for (ItemStack stack : items) {
            Set<String> curioTags = curiosHelper.getCurioTags(stack.getItem());
            if (curioTags.isEmpty()) {
                continue;
            }
            stack.getCapability(CapabilityRegistry.GLOSSARY).ifPresent(glossaryCap -> {
                List<String> glossaryIDList = glossaryCap.getGlossaryIDList();
                glossaryIDList.stream()
                        .map(GlossaryDataLoader.GLOSSARY_DATA_MAP::get)
                        .flatMap(glossaryData -> glossaryData.getAttributeDataList().stream())
                        .forEach(data -> data.getSlots().stream()
                                .filter(curioTags::contains)
                                .forEach(slotType -> curiosHelper.addModifier(stack, data.getAttribute(), data.getAttributeModifier().getName(), data.getAttributeModifier().getId(), data.getAttributeModifier().getAmount(), data.getAttributeModifier().getOperation(), slotType)));
            });
        }

    }
}
