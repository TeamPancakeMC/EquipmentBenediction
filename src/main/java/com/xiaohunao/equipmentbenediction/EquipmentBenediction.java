package com.xiaohunao.equipmentbenediction;

import com.xiaohunao.equipmentbenediction.compat.curios.CuriosMarkEvent;
import com.xiaohunao.equipmentbenediction.data.GlossaryDataLoader;
import com.xiaohunao.equipmentbenediction.data.QualityDataLoader;
import com.xiaohunao.equipmentbenediction.event.ItemMarkEvent;
import com.xiaohunao.equipmentbenediction.registry.AttributesRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(EquipmentBenediction.MOD_ID)
public class EquipmentBenediction {
    public static final String MOD_ID = "equipmentbenediction";
    public EquipmentBenediction() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        AttributesRegister.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(this::onDataPackLoad);

        MinecraftForge.EVENT_BUS.register(new ItemMarkEvent());
        MinecraftForge.EVENT_BUS.register(new CuriosMarkEvent());

    }
    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    private void onDataPackLoad(AddReloadListenerEvent event) {
        event.addListener(new QualityDataLoader());
        event.addListener(new GlossaryDataLoader());
    }
}
