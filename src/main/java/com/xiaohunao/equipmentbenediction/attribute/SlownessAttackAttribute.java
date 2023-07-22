package com.xiaohunao.equipmentbenediction.attribute;

import com.xiaohunao.equipmentbenediction.registry.AttributesRegister;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SlownessAttackAttribute extends BaseEffectAttribute{
    public static final String NAME = "generic.slowness_attack";
    public SlownessAttackAttribute() {
        super(NAME);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    Attribute getAttribute() {
        return AttributesRegister.SLOWNESS_ATTACK.get();
    }

    @Override
    MobEffect getMobEffect() {
        return MobEffects.MOVEMENT_SLOWDOWN;
    }

    @Override
    int getDuration() {
        return 60;
    }

    @Override
    int getLevel() {
        return 1;
    }

    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event) {
        super.onLivingAttack(event);
    }
}
