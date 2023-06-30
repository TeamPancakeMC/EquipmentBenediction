package com.xiaohunao.equipmentbenediction.registry;

import com.xiaohunao.equipmentbenediction.attribute.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AttributesRegister {
    public static BaseAttribute EXECUTE, BOW_SPEED,BOW_MOVEMENT_SPEED,ABSORB_BLOOD,SUMMON_LIGHTNING,ATTACK_POISON,
    FAR_DISTANCE_ATTACK,NIGH_DISTANCE_ATTACK,SLOWNESS_ATTACK,WITHER_ATTACK,LEVITATION_ATTACK;

    @SubscribeEvent
    public static void registerAttributes(final RegistryEvent.Register<Attribute> event) {
        event.getRegistry().registerAll(
                EXECUTE = new ExecuteAttribute(),
                BOW_SPEED = new BowSpeedAttribute(),
                BOW_MOVEMENT_SPEED = new BowMovementSpeedAttribute(),
                ABSORB_BLOOD = new AbsorbBloodAttribute(),
                SUMMON_LIGHTNING = new SummonLightningAttribute(),
                ATTACK_POISON = new PoisonAttackAttribute(),
                FAR_DISTANCE_ATTACK = new DistanceAttackAttribute.Far(),
                NIGH_DISTANCE_ATTACK = new DistanceAttackAttribute.Nigh(),
                SLOWNESS_ATTACK = new SlownessAttackAttribute(),
                WITHER_ATTACK = new WitherAttackAttribute(),
                LEVITATION_ATTACK = new LevitationAttackAttribute()
        );
    }
}