package com.xiaohunao.equipmentbenediction.data;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.xiaohunao.equipmentbenediction.data.dao.GlossaryData;
import com.xiaohunao.equipmentbenediction.data.dao.QualityData;
import com.xiaohunao.equipmentbenediction.registry.CapabilityRegistry;
import com.xiaohunao.equipmentbenediction.util.JsonUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GlossaryDataLoader extends SimpleJsonResourceReloadListener {

    public static Map<String, GlossaryData> GLOSSARY_DATA_MAP = Maps.newHashMap();

    public GlossaryDataLoader() {
        super(JsonUtil.GSON, "glossary");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsonElementMap, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller filler) {
        jsonElementMap.forEach((resourceLocation, jsonElement) -> {
            try {
                GlossaryData glossaryData = JsonUtil.GSON.fromJson(jsonElement, GlossaryData.class);
                GLOSSARY_DATA_MAP.put(glossaryData.getId(),glossaryData);
            }catch (Exception e){
                throw new RuntimeException("解析词缀Json数据包错误" + resourceLocation, e);
            }
        });
    }
    public static boolean isValid(ItemStack stack) {
        return isValid(ForgeRegistries.ITEMS.getKey(stack.getItem()));
    }

    public static boolean isValid(ResourceLocation id) {
        if (GLOSSARY_DATA_MAP == null) return false;
        return GLOSSARY_DATA_MAP.values().stream().anyMatch(glossaryData -> glossaryData.isValid(id));
    }

    public static List<GlossaryData> getFilteredGlossary(ItemStack stack) {
        return stack.getCapability(CapabilityRegistry.QUALITY)
                .map(qualityCap -> {
                    String id = qualityCap.getId();
                    QualityData qualityData = QualityDataLoader.QUALITY_DATA_MAP.get(id);

                    int count = qualityData.getCount();
                    int level = qualityData.getLevel();

                    return Stream.generate(GlossaryDataLoader::getRandomGlossaryData)
                            .distinct()
                            .limit(count)
                            .filter(glossaryData -> glossaryData.getQuality_level() <= level)
                            .filter(glossaryData -> glossaryData.isValid(ForgeRegistries.ITEMS.getKey(stack.getItem())))
                            .collect(Collectors.toList());
                })
                .orElse(Collections.emptyList());
    }

    public static GlossaryData getRandomGlossaryData() {
        List<GlossaryData> glossaryDataList = new ArrayList<>(GLOSSARY_DATA_MAP.values());
        double totalWeight = glossaryDataList.stream()
                .mapToDouble(GlossaryData::getChance)
                .sum();
        double randomWeight = Math.random() * totalWeight;

        double cumulativeWeight = 0;
        for (GlossaryData glossaryData : glossaryDataList) {
            cumulativeWeight += glossaryData.getChance();
            if (randomWeight < cumulativeWeight) {
                return glossaryData;
            }
        }
        throw new IllegalStateException("No GlossaryData found");
    }
}