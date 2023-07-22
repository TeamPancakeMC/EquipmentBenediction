package com.xiaohunao.equipmentbenediction.recasting;

import com.xiaohunao.equipmentbenediction.data.dao.ItemVerifier;

import java.util.List;

public class RecastingRequirement {
    private final int count;
    private final List<ItemVerifier> verifiers;

    public RecastingRequirement(int count, List<ItemVerifier> verifiers) {
        this.count = count;
        this.verifiers = verifiers;
    }

    public int getCount() {
        return count;
    }

    public List<ItemVerifier> getVerifiers() {
        return verifiers;
    }
}
