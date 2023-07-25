package com.xiaohunao.equipmentbenediction.mixin;

import com.xiaohunao.equipmentbenediction.data.GlossaryDataLoader;
import com.xiaohunao.equipmentbenediction.registry.CapabilityRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.util.ICuriosHelper;

import java.util.List;
import java.util.Set;

@Mixin(Item.class)
public class ItemMarkMixin {
    @Inject(method = "inventoryTick", at = @At(value = "HEAD"))
    public void addCurios(ItemStack stack, Level level, Entity entity, int slot, boolean selected, CallbackInfo ci) {
        if (!ModList.get().isLoaded("curios")) {
            return;
        }
        ICuriosHelper curiosHelper = CuriosApi.getCuriosHelper();
        stack.getCapability(CapabilityRegistry.GLOSSARY).ifPresent(glossaryCap -> {
            List<String> glossaryIDList = glossaryCap.getGlossaryIDList();
            glossaryIDList.stream()
                    .map(GlossaryDataLoader.GLOSSARY_DATA_MAP::get)
                    .flatMap(glossaryData -> glossaryData.getAttributeDataList().stream())
                    .forEach(data -> {
                        Set<String> curioTags = curiosHelper.getCurioTags(stack.getItem());
                        data.getSlots().stream()
                                .filter(curioTags::contains)
                                .forEach(slotType ->curiosHelper.addModifier(stack, data.getAttribute(), data.getAttributeModifier().getName(), data.getAttributeModifier().getId(), data.getAttributeModifier().getAmount(), data.getAttributeModifier().getOperation(), slotType));
                    });
        });
    }
}
