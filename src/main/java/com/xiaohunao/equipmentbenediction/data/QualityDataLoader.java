package com.xiaohunao.equipmentbenediction.data;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.xiaohunao.equipmentbenediction.data.dao.QualityData;
import com.xiaohunao.equipmentbenediction.util.JsonUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class QualityDataLoader extends SimpleJsonResourceReloadListener {
    public static Map<String, QualityData> QUALITY_DATA_MAP = Maps.newHashMap();

    public QualityDataLoader() {
        super(JsonUtil.GSON, "quality");
    }

    @Override
    protected void apply(@NotNull Map<ResourceLocation, JsonElement> jsonElementMap, @NotNull ResourceManager manager, @NotNull ProfilerFiller profiler) {
        jsonElementMap.forEach((resourceLocation, jsonElement) -> {
            try {
                QualityData qualityData = JsonUtil.GSON.fromJson(jsonElement, QualityData.class);
                QUALITY_DATA_MAP.put(qualityData.getId(), qualityData);
            } catch (Exception e) {
                throw new RuntimeException("解析词缀Json数据包错误" + resourceLocation, e);
            }
        });
    }

    public static boolean isValid(ItemStack stack) {
        return isValid(ForgeRegistries.ITEMS.getKey(stack.getItem()));
    }

    public static boolean isValid(ResourceLocation id) {
        if (QUALITY_DATA_MAP == null) return false;
        return QUALITY_DATA_MAP.values().stream().anyMatch(qualityData -> qualityData.isValid(id));
    }

    public static QualityData getRandomQuality() {
        if (QUALITY_DATA_MAP == null) return null;
        double probabilitySum = QUALITY_DATA_MAP.values().stream()
                .mapToDouble(QualityData::getChance)
                .sum();
        double random = Math.random() * probabilitySum;
        double[] sum = {0.0};
        return QUALITY_DATA_MAP.values().stream()
                .filter(qualityData -> {
                    sum[0] += qualityData.getChance();
                    return random < sum[0];
                })
                .findFirst()
                .orElse(null);
    }
}