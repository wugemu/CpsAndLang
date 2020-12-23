package com.lxkj.dmhw.bean.self;

import java.io.Serializable;

public class SucaiBean implements Serializable{
    private int materialCount;
    private SucaiDbean material;

    public int getMaterialCount() {
        return materialCount;
    }

    public void setMaterialCount(int materialCount) {
        this.materialCount = materialCount;
    }

    public SucaiDbean getMaterial() {
        return material;
    }

    public void setMaterial(SucaiDbean material) {
        this.material = material;
    }
}
