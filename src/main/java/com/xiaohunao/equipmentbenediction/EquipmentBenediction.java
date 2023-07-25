package com.xiaohunao.equipmentbenediction;



import com.xiaohunao.equipmentbenediction.compat.curios.MarkCurios;
import com.xiaohunao.equipmentbenediction.data.GlossaryDataLoader;
import com.xiaohunao.equipmentbenediction.data.QualityDataLoader;
import com.xiaohunao.equipmentbenediction.event.ItemMarkEvent;
import com.xiaohunao.equipmentbenediction.network.EquipmentQualityPacketHandler;
import com.xiaohunao.equipmentbenediction.recasting.RecastingDeskScreen;
import com.xiaohunao.equipmentbenediction.registry.AttributesRegister;
import com.xiaohunao.equipmentbenediction.registry.RecastingRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;


@Mod(EquipmentBenediction.MOD_ID)
public class EquipmentBenediction {
    public static final String MOD_ID = "equipmentbenediction";

    public EquipmentBenediction() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        RecastingRegistry.register(eventBus);
        AttributesRegister.register(eventBus);

        MinecraftForge.EVENT_BUS.register(new ItemMarkEvent());
        if (ModList.get().isLoaded(CuriosApi.MODID)){
            eventBus.addListener(this::enqueueIMC);
            MinecraftForge.EVENT_BUS.register(new MarkCurios());
        }

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(this::onDataPackLoad);

    }

    public static final CreativeModeTab EQUIPMENT_QUALITY_TAB = new CreativeModeTab("equipmentquality") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(RecastingRegistry.RECASTING_DESK_ITEM.get());
        }
    };
    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    private void onDataPackLoad(AddReloadListenerEvent event) {
        event.addListener(new QualityDataLoader());
        event.addListener(new GlossaryDataLoader());
    }


    public void clientSetup(final FMLClientSetupEvent event) {
        MenuScreens.register(RecastingRegistry.RECASTING_DESK_MENU.get(), RecastingDeskScreen::new);
    }

    private void setup(final FMLCommonSetupEvent event) {
        EquipmentQualityPacketHandler.init();
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HEAD.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.NECKLACE.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BELT.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HANDS.getMessageBuilder().build());
    }
}
