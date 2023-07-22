package com.xiaohunao.equipmentbenediction.registry;


import com.xiaohunao.equipmentbenediction.EquipmentBenediction;
import com.xiaohunao.equipmentbenediction.capability.GlossaryCapability;
import com.xiaohunao.equipmentbenediction.capability.IGlossaryCapability;
import com.xiaohunao.equipmentbenediction.capability.IQualityCapability;
import com.xiaohunao.equipmentbenediction.capability.QualityCapability;
import com.xiaohunao.equipmentbenediction.data.QualityDataLoader;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;




@Mod.EventBusSubscriber
public class CapabilityRegistry {
    public static final Capability<IQualityCapability> QUALITY = CapabilityManager.get(new CapabilityToken<>() {
    });
    public static final Capability<IGlossaryCapability> GLOSSARY = CapabilityManager.get(new CapabilityToken<>() {
    });

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {
        event.register(IQualityCapability.class);
        event.register(IGlossaryCapability.class);
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesItemStack(AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack stack = event.getObject();
        if (QualityDataLoader.isValid(stack)) {
            event.addCapability(EquipmentBenediction.asResource("quality"), new QualityCapability());
            event.addCapability(EquipmentBenediction.asResource("glossary"), new GlossaryCapability());
        }
    }
}