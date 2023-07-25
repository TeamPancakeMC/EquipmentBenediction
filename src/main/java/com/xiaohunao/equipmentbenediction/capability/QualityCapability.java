package com.xiaohunao.equipmentbenediction.capability;

import com.xiaohunao.equipmentbenediction.registry.CapabilityRegistry;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class QualityCapability implements IQualityCapability, ICapabilitySerializable<CompoundTag>, ICapabilityProvider {

    private boolean hasQuality = true;
    private String id = "";

    public boolean isHasQuality() {
        return hasQuality;
    }

    public void setHasQuality(boolean hasQuality) {
        this.hasQuality = hasQuality;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return CapabilityRegistry.QUALITY.orEmpty(cap, LazyOptional.of(() -> this));
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putBoolean("hasQuality", hasQuality);
        compoundTag.putString("id", id);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.hasQuality = nbt.getBoolean("hasQuality");
        this.id = nbt.getString("id");
    }
}