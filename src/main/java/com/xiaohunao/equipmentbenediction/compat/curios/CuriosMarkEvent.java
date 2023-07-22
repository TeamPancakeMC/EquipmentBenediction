package com.xiaohunao.equipmentbenediction.compat.curios;

import com.xiaohunao.equipmentbenediction.data.GlossaryDataLoader;
import com.xiaohunao.equipmentbenediction.registry.CapabilityRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.util.ICuriosHelper;

import java.util.List;
import java.util.Set;

public class CuriosMarkEvent {
    @SubscribeEvent
    public void addAttributeModifiers(ItemAttributeModifierEvent event) {
        ItemStack itemStack = event.getItemStack();
        itemStack.getCapability(CapabilityRegistry.GLOSSARY).ifPresent(cap ->{
            if (ModList.get().isLoaded("curios")) {
                addCurios(itemStack, cap.getGlossaryIDList());
            }
        });
    }

    private void addCurios(ItemStack itemStack, List<String> list) {
        ICuriosHelper curiosHelper = CuriosApi.getCuriosHelper();
        list.stream()
                .map(GlossaryDataLoader.GLOSSARY_DATA_MAP::get)
                .forEach(glossaryData -> {
                    Set<String> curioTags = curiosHelper.getCurioTags(itemStack.getItem());
                    glossaryData.getSlots().stream()
                            .filter(curioTags::contains)
                            .forEach(slotType -> glossaryData.getAttributeMap().forEach((attribute, attributeModifier)
                                    -> curiosHelper.addModifier(itemStack, attribute, attributeModifier.getName(), attributeModifier.getId(), attributeModifier.getAmount(), attributeModifier.getOperation(), slotType)));
                });
    }
}
