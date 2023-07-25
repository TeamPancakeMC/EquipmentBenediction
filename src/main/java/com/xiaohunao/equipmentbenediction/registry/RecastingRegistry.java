package com.xiaohunao.equipmentbenediction.registry;

import com.xiaohunao.equipmentbenediction.EquipmentBenediction;
import com.xiaohunao.equipmentbenediction.recasting.RecastingDeskBlock;
import com.xiaohunao.equipmentbenediction.recasting.RecastingDeskBlockEntity;
import com.xiaohunao.equipmentbenediction.recasting.RecastingDeskContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecastingRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, EquipmentBenediction.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, EquipmentBenediction.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EquipmentBenediction.MOD_ID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.CONTAINERS, EquipmentBenediction.MOD_ID);

    public static final RegistryObject<MenuType<RecastingDeskContainerMenu>> RECASTING_DESK_MENU = MENUS.register("recasting_desk_menu", () -> IForgeMenuType.create(RecastingDeskContainerMenu::new));

    public static final RegistryObject<Block> RECASTING_DESK_BLOCK = BLOCKS.register("recasting_desk", RecastingDeskBlock::new);
    public static final RegistryObject<Item> RECASTING_DESK_ITEM = ITEMS.register("recasting_desk", () -> new BlockItem(RECASTING_DESK_BLOCK.get(), new Item.Properties().tab(EquipmentBenediction.EQUIPMENT_QUALITY_TAB)));

    public static final RegistryObject<BlockEntityType<RecastingDeskBlockEntity>> RECASTING_DESK_BLOCK_ENTITY = TILE_ENTITIES.register("recasting_desk_block_entity", () -> BlockEntityType.Builder.of(RecastingDeskBlockEntity::new, RECASTING_DESK_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        TILE_ENTITIES.register(eventBus);
        ITEMS.register(eventBus);
        MENUS.register(eventBus);
    }
}
