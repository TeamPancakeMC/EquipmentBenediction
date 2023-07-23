package com.xiaohunao.equipmentbenediction.event;

import com.xiaohunao.equipmentbenediction.EquipmentBenediction;
import com.xiaohunao.equipmentbenediction.capability.IGlossaryCapability;
import com.xiaohunao.equipmentbenediction.capability.IQualityCapability;
import com.xiaohunao.equipmentbenediction.data.GlossaryDataLoader;
import com.xiaohunao.equipmentbenediction.data.QualityDataLoader;
import com.xiaohunao.equipmentbenediction.data.dao.GlossaryData;
import com.xiaohunao.equipmentbenediction.data.dao.QualityData;
import com.xiaohunao.equipmentbenediction.registry.CapabilityRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;


public class ItemMarkEvent {
    @SubscribeEvent
    public void Mark(TickEvent.PlayerTickEvent event) {
        InventoryMenu inventoryMenu = event.player.inventoryMenu;
        NonNullList<ItemStack> items = inventoryMenu.getItems();


        items.stream()
                .filter(itemStack -> itemStack.getCapability(CapabilityRegistry.QUALITY).isPresent())
                .map(itemStack -> itemStack.getCapability(CapabilityRegistry.QUALITY).orElseThrow(null))
                .filter(IQualityCapability::isHasQuality)
                .forEach(cap -> {
                    QualityData quality = QualityDataLoader.getRandomQuality();
                    if (quality != null) {
                        cap.setHasQuality(false);
                        cap.setId(quality.getId());
                    }
                });
        var context = new Object() {
            int count = -1;
        };
        items.stream()
                .filter(itemStack -> {
                    context.count++;
                    return itemStack.getCapability(CapabilityRegistry.GLOSSARY).isPresent();
                })
                .map(itemStack -> itemStack.getCapability(CapabilityRegistry.GLOSSARY).orElseThrow(null))
                .filter(IGlossaryCapability::isHasGlossary)
                .forEach(cap -> {
                    List<GlossaryData> filteredGlossary = GlossaryDataLoader.getFilteredGlossary(items.get(context.count));
                    cap.setHasGlossary(false);
                    filteredGlossary.forEach(glossaryData -> cap.addGlossary(glossaryData.getId()));
                });
    }

    @SubscribeEvent
    public void addAttributeModifiers(ItemAttributeModifierEvent event) {
        ItemStack itemStack = event.getItemStack();
        itemStack.getCapability(CapabilityRegistry.GLOSSARY).ifPresent(cap ->
                cap.getGlossaryIDList().stream()
                .map(GlossaryDataLoader.GLOSSARY_DATA_MAP::get)
                .flatMap(glossaryData -> glossaryData.getAttributeDataList().stream())
                        .filter(attributeData -> attributeData.getSlots().contains(event.getSlotType().getName()))
                        .forEach(attributeData -> event.addModifier(attributeData.getAttribute(), attributeData.getAttributeModifier())));
    }

    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        List<Component> toolTip = event.getToolTip();

        addQuality(itemStack, toolTip);
        addGlossary(itemStack, toolTip);
    }

    private void addQuality(ItemStack stack, List<Component> toolTip) {
        stack.getCapability(CapabilityRegistry.QUALITY).ifPresent(cap ->{
            if (!cap.isHasQuality()) {
                QualityData qualityData = QualityDataLoader.QUALITY_DATA_MAP.get(cap.getId());
                MutableComponent quality = Component.translatable(EquipmentBenediction.MOD_ID + ".quality." + qualityData.getId())
                        .withStyle(qualityData.getColor());
                MutableComponent desc = Component.translatable(EquipmentBenediction.MOD_ID + ".quality.desc")
                        .append(": ").append(quality);
                toolTip.add(1, desc);
            }
        });
    }


    private void addGlossary(ItemStack stack, List<Component> toolTip) {
        stack.getCapability(CapabilityRegistry.GLOSSARY).ifPresent(cap -> {
            if (!cap.isHasGlossary()) {
                List<String> glossaryIDList = cap.getGlossaryIDList();
                glossaryIDList.forEach(id -> {
                    GlossaryData glossaryData = GlossaryDataLoader.GLOSSARY_DATA_MAP.get(id);
                    MutableComponent glossary = Component.translatable(EquipmentBenediction.MOD_ID + ".glossary." + glossaryData.getId())
                            .withStyle(glossaryData.getColor());
                    toolTip.add(glossary);
                });
            }
        });
    }
}
