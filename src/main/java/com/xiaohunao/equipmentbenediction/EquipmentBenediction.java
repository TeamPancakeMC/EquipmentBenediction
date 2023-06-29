package com.xiaohunao.equipmentbenediction;

import com.xiaohunao.equipmentbenediction.attribute.LevitationAttackAttribute;
import com.xiaohunao.equipmentbenediction.attribute.PoisonAttackAttribute;
import com.xiaohunao.equipmentbenediction.attribute.SlownessAttackAttribute;
import com.xiaohunao.equipmentbenediction.attribute.WitherAttackAttribute;
import com.xiaohunao.equipmentbenediction.data.GlossaryDataLoader;
import com.xiaohunao.equipmentbenediction.data.QualityDataLoader;
import com.xiaohunao.equipmentbenediction.network.EquipmentQualityPacketHandler;
import com.xiaohunao.equipmentbenediction.registry.*;
import com.xiaohunao.equipmentbenediction.screen.RecastingDeskScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;

@Mod(EquipmentBenediction.MOD_ID)
public class EquipmentBenediction {
    public static final String MOD_ID = "equipmentbenediction";
    public static final QualityDataLoader QUALITY_DATA = new QualityDataLoader();
    public static final GlossaryDataLoader GLOSSARY_DATA = new GlossaryDataLoader();

    public EquipmentBenediction() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        AttributesRegister.register(eventBus);
        BlockRegistry.register(eventBus);
        ItemRegistry.register(eventBus);
        BlockEntityRegistry.register(eventBus);
        MenuTypeRegistry.register(eventBus);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(this::onDataPackLoad);
        onRegisterEvent();
    }

    public static final CreativeModeTab EQUIPMENT_QUALITY_TAB = new CreativeModeTab(MOD_ID) {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(BlockRegistry.RECASTING_DESK.get());
        }
    };

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    private void onDataPackLoad(AddReloadListenerEvent event) {
        event.addListener(QUALITY_DATA);
        event.addListener(GLOSSARY_DATA);
    }
    private void onRegisterEvent() {
        MinecraftForge.EVENT_BUS.register(new PoisonAttackAttribute());
        MinecraftForge.EVENT_BUS.register(new SlownessAttackAttribute());
        MinecraftForge.EVENT_BUS.register(new WitherAttackAttribute());
        MinecraftForge.EVENT_BUS.register(new LevitationAttackAttribute());
    }
    public void clientSetup(final FMLClientSetupEvent event) {
        MenuScreens.register(MenuTypeRegistry.RECASTING_DESK.get(), RecastingDeskScreen::new);
    }

    private void setup(final FMLCommonSetupEvent event) {
        EquipmentQualityPacketHandler.init();
    }
}
