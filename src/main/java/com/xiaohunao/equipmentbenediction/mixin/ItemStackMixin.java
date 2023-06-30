package com.xiaohunao.equipmentbenediction.mixin;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.xiaohunao.equipmentbenediction.EquipmentBenediction;
import com.xiaohunao.equipmentbenediction.data.dao.GlossaryData;
import com.xiaohunao.equipmentbenediction.data.dao.QualityData;
import com.xiaohunao.equipmentbenediction.registry.CapabilityRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(at = @At("RETURN"), method = "getAttributeModifiers", cancellable = true)
    private void modifyAttributeModifiers(EquipmentSlot slot, CallbackInfoReturnable<Multimap<Attribute, AttributeModifier>> info) {
        Multimap<Attribute, AttributeModifier> map = info.getReturnValue();
        LinkedListMultimap<Attribute, AttributeModifier> newMap = LinkedListMultimap.create();
        map.asMap().forEach((attribute, modifiers) -> {
            modifiers.forEach(modifier -> {
                newMap.put(attribute, modifier);
            });
        });

        ItemStack stack = (ItemStack) (Object) this;

        stack.getCapability(CapabilityRegistry.GLOSSARY).ifPresent(cap -> {
            List<String> glossaryIDList = cap.getGlossaryIDList();
            glossaryIDList.forEach(id -> {
                GlossaryData glossaryData = EquipmentBenediction.GLOSSARY_DATA.get(id);
                glossaryData.getAttribute().forEach(data -> {
                    if (data.checkSlots(slot)) {
                        Map<Attribute, AttributeModifier> attributeMap = data.getAttributeMap();
                        attributeMap.forEach(newMap::put);
                    }
                });
            });
        });


        info.setReturnValue(newMap);
    }

    @Inject(method = "getTooltipLines", at = @At("RETURN"), cancellable = true)
    private void addCustomTooltip(Player player, TooltipFlag flag, CallbackInfoReturnable<List<Component>> info) {
        List<Component> tooltip = info.getReturnValue();
        ItemStack stack = (ItemStack) (Object) this;
        addQuality(stack, tooltip);
        addGlossary(stack, tooltip);
        info.setReturnValue(tooltip);
    }

    private void addQuality(ItemStack stack, List<Component> toolTip) {
        stack.getCapability(CapabilityRegistry.QUALITY).ifPresent(cap -> {
            if (cap.isHasQuality()) {
                String id = cap.getId();
                QualityData qualityData = EquipmentBenediction.QUALITY_DATA.get(id);
                MutableComponent quality = new TranslatableComponent(EquipmentBenediction.MOD_ID +".quality." + qualityData.getId())
                        .withStyle(ChatFormatting.valueOf(qualityData.getColor().toUpperCase()));
                TranslatableComponent desc = new TranslatableComponent(EquipmentBenediction.MOD_ID +".quality.desc");
                MutableComponent append = desc.append(": ").append(quality);
                toolTip.add(1, append);
            }
        });
    }

    private void addGlossary(ItemStack stack, List<Component> toolTip) {
        stack.getCapability(CapabilityRegistry.GLOSSARY).ifPresent(cap -> {
            if (cap.isHasGlossary()) {
                List<String> glossaryIDList = cap.getGlossaryIDList();
                glossaryIDList.forEach(id -> {
                    GlossaryData glossaryData = EquipmentBenediction.GLOSSARY_DATA.get(id);
                    MutableComponent glossary = new TranslatableComponent(EquipmentBenediction.MOD_ID+ ".glossary." + glossaryData.getId())
                            .withStyle(ChatFormatting.valueOf(glossaryData.getColor().toUpperCase()));
                    toolTip.add(glossary);
                });
            }
        });
    }


}
