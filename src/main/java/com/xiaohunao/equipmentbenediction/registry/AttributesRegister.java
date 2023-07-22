package com.xiaohunao.equipmentbenediction.registry;

import com.xiaohunao.equipmentbenediction.EquipmentBenediction;
import com.xiaohunao.equipmentbenediction.attribute.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AttributesRegister {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, EquipmentBenediction.MOD_ID);

    public static final RegistryObject<Attribute> EXECUTE = ATTRIBUTES.register(ExecuteAttribute.NAME, ExecuteAttribute::new);
    public static final RegistryObject<Attribute> BOW_SPEED = ATTRIBUTES.register(BowSpeedAttribute.NAME, BowSpeedAttribute::new);
    public static final RegistryObject<Attribute> BOW_MOVEMENT_SPEED = ATTRIBUTES.register(BowMovementSpeedAttribute.NAME, BowMovementSpeedAttribute::new);
    public static final RegistryObject<Attribute> ABSORB_BLOOD = ATTRIBUTES.register(AbsorbBloodAttribute.NAME, AbsorbBloodAttribute::new);
    public static final RegistryObject<Attribute> SUMMON_LIGHTNING = ATTRIBUTES.register(SummonLightningAttribute.NAME, SummonLightningAttribute::new);
    public static final RegistryObject<Attribute> ATTACK_POISON = ATTRIBUTES.register(PoisonAttackAttribute.NAME, PoisonAttackAttribute::new);
    public static final RegistryObject<Attribute> FAR_DISTANCE_ATTACK = ATTRIBUTES.register(FarAttackAttribute.NAME, FarAttackAttribute::new);
    public static final RegistryObject<Attribute> NIGH_DISTANCE_ATTACK = ATTRIBUTES.register(NighAttackAttribute.NAME, NighAttackAttribute::new);
    public static final RegistryObject<Attribute> SLOWNESS_ATTACK = ATTRIBUTES.register(SlownessAttackAttribute.NAME, SlownessAttackAttribute::new);
    public static final RegistryObject<Attribute> WITHER_ATTACK = ATTRIBUTES.register(WitherAttackAttribute.NAME, WitherAttackAttribute::new);
    public static final RegistryObject<Attribute> LEVITATION_ATTACK = ATTRIBUTES.register(LevitationAttackAttribute.NAME, LevitationAttackAttribute::new);

    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }
}
