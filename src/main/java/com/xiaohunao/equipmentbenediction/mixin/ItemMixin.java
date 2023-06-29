package com.xiaohunao.equipmentbenediction.mixin;

import com.xiaohunao.equipmentbenediction.EquipmentBenediction;
import com.xiaohunao.equipmentbenediction.data.dao.GlossaryData;
import com.xiaohunao.equipmentbenediction.data.dao.QualityData;
import com.xiaohunao.equipmentbenediction.registry.CapabilityRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(method = "inventoryTick", at = @At(value = "HEAD"))
    public void mark(ItemStack stack, Level level, Entity entity, int slot, boolean selected, CallbackInfo ci) {
        if (level.isClientSide() || !EquipmentBenediction.QUALITY_DATA.isValid(stack)) return;
        stack.getCapability(CapabilityRegistry.QUALITY).ifPresent(qualityCap -> {
            if (qualityCap.isHasQuality()) return;
            QualityData data = EquipmentBenediction.QUALITY_DATA.getRandomEquipmentQualityData();
            qualityCap.setHasQuality(true);
            qualityCap.setId(data.getId());
        });

        List<GlossaryData> filteredGlossary = EquipmentBenediction.GLOSSARY_DATA.getFilteredGlossary(stack);
        stack.getCapability(CapabilityRegistry.GLOSSARY).ifPresent(glossaryCap -> {
            if (glossaryCap.isHasGlossary()) return;
            glossaryCap.setHasGlossary(true);
            filteredGlossary.forEach(glossaryData -> glossaryCap.addGlossaryID(glossaryData.getId()));
        });
    }
}