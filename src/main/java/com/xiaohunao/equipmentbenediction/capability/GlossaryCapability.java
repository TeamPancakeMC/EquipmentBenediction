package com.xiaohunao.equipmentbenediction.capability;

import com.xiaohunao.equipmentbenediction.registry.CapabilityRegistry;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GlossaryCapability implements IGlossaryCapability, ICapabilitySerializable<CompoundTag>, ICapabilityProvider {
    private boolean isHasGlossary = true;
    private final List<String> glossaryIDList = new ArrayList<>();

    public boolean isHasGlossary() {
        return isHasGlossary;
    }

    @Override
    public void setHasGlossary(boolean hasGlossary) {
        isHasGlossary = hasGlossary;
    }

    @Override
    public List<String> getGlossaryIDList() {
        return glossaryIDList;
    }

    @Override
    public void addGlossary(String id) {
        this.glossaryIDList.add(id);
    }

    @Override
    public void clearGlossary() {
        this.glossaryIDList.clear();
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return CapabilityRegistry.GLOSSARY.orEmpty(cap, LazyOptional.of(() -> this));
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putBoolean("hasGlossary", this.isHasGlossary);
        ListTag tags = new ListTag();
        for (String id : this.glossaryIDList) {
            CompoundTag tag = new CompoundTag();
            tag.putString("id", id);
            tags.add(tag);
        }
        compoundTag.put("glossaryIDList", tags);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.isHasGlossary = nbt.getBoolean("hasGlossary");
        ListTag tags = nbt.getList("glossaryIDList", 10);
        for (int i = 0; i < tags.size(); i++) {
            CompoundTag tag = tags.getCompound(i);
            this.glossaryIDList.add(tag.getString("id"));
        }
    }
}
