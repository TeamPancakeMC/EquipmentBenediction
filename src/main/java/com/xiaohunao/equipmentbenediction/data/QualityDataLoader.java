package com.xiaohunao.equipmentbenediction.data;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.xiaohunao.equipmentbenediction.data.dao.QualityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;

public class QualityDataLoader extends SimpleJsonResourceReloadListener {
    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();
    private Map<String, QualityData> qualityDataHashMap;

    public QualityDataLoader() {
        super(GSON, "quality");
    }

    @Override
    protected void apply(@NotNull Map<ResourceLocation, JsonElement> loader, @NotNull ResourceManager manager, @NotNull ProfilerFiller profiler) {
        Map<String, QualityData> qualityDataHashMap = Maps.newHashMap();
        for (Map.Entry<ResourceLocation, JsonElement> entry : loader.entrySet()) {
            try {
                QualityData qualityData = GSON.fromJson(entry.getValue(), QualityData.class);
                qualityDataHashMap.put(qualityData.getId(), qualityData);
            } catch (IllegalArgumentException | JsonParseException exception) {
                throw new JsonParseException("解析品质Json数据包错误" + entry.getKey(), exception);
            }
        }
        this.qualityDataHashMap = qualityDataHashMap;
    }

    public Map<String, QualityData> getQualityDataHashMap() {
        return qualityDataHashMap;
    }

    public boolean isValid(ItemStack stack) {
        return isValid(ForgeRegistries.ITEMS.getKey(stack.getItem()));
    }

    public boolean isValid(ResourceLocation id) {
        if (qualityDataHashMap == null) return false;
        for (Map.Entry<String, QualityData> entry : qualityDataHashMap.entrySet()) {
            QualityData data = entry.getValue();
            return data.isValid(id);
        }
        return false;
    }

    public QualityData getRandomEquipmentQualityData() {
        if (qualityDataHashMap == null) return null;
        double probabilitySum = 0.0;
        for (QualityData equipment : qualityDataHashMap.values()) {
            probabilitySum += equipment.getChance();
        }
        double random = Math.random() * probabilitySum;
        double sum = 0.0;
        for (Map.Entry<String, QualityData> entry : qualityDataHashMap.entrySet()) {
            sum += entry.getValue().getChance();
            if (random < sum) {
                return entry.getValue();
            }
        }
        return null;
    }

    public QualityData get(String id) {
        return qualityDataHashMap.get(id);
    }

    public Collection<? extends QualityData> getAll() {
        return qualityDataHashMap.values();
    }
}