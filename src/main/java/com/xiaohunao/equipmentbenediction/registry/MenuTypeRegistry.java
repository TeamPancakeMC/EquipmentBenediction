package com.xiaohunao.equipmentbenediction.registry;


import com.xiaohunao.equipmentbenediction.EquipmentBenediction;
import com.xiaohunao.equipmentbenediction.block_entity.container.RecastingDeskContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuTypeRegistry {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.CONTAINERS, EquipmentBenediction.MOD_ID);

    public static final RegistryObject<MenuType<RecastingDeskContainerMenu>> RECASTING_DESK = MENUS.register("recasting_desk_menu", () -> IForgeMenuType.create(RecastingDeskContainerMenu::new));




    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
