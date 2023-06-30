package com.xiaohunao.equipmentbenediction.registry;


import com.xiaohunao.equipmentbenediction.EquipmentBenediction;
import com.xiaohunao.equipmentbenediction.block_entity.RecastingDeskBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, EquipmentBenediction.MOD_ID);
    public static final RegistryObject<BlockEntityType<RecastingDeskBlockEntity>> RECASTING_DESK_BLOCK_ENTITY = TILE_ENTITIES.register("recasting_desk_block_entity", () -> BlockEntityType.Builder.of(RecastingDeskBlockEntity::new, BlockRegistry.RECASTING_DESK.get()).build(null));

    public static void register(IEventBus eventBus) {
        TILE_ENTITIES.register(eventBus);
    }
}
