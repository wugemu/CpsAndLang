package com.lxkj.dmhw.bean.self;

import java.io.Serializable;
import java.util.List;

public class WarehouseBean implements Serializable {
    private List<GoodSku> shopCarDtos;
    private String warehouseId;
    private String warehouseName;
    private int ifNoDeliveryAran;//是否是偏远地区不发货 -1-不支持的配送地址 -2 因路途遥远需额外加收运费 0 正常发货
    private String noDeliveryAranTips;//不发货或者路途遥远提示语

    public int getIfNoDeliveryAran() {
        return ifNoDeliveryAran;
    }

    public void setIfNoDeliveryAran(int ifNoDeliveryAran) {
        this.ifNoDeliveryAran = ifNoDeliveryAran;
    }

    public String getNoDeliveryAranTips() {
        return noDeliveryAranTips;
    }

    public void setNoDeliveryAranTips(String noDeliveryAranTips) {
        this.noDeliveryAranTips = noDeliveryAranTips;
    }

    public List<GoodSku> getShopCarDtos() {
        return shopCarDtos;
    }

    public void setShopCarDtos(List<GoodSku> shopCarDtos) {
        this.shopCarDtos = shopCarDtos;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
}
