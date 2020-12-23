package com.lxkj.dmhw.bean.self;

import java.io.Serializable;
import java.util.List;

/**
 * Created By lhp
 * on 2019/4/26
 */
public class Multiple implements Serializable {
    private String specificationTitle;
    private List<MultipleSku> propertiesList;

    public String getSpecificationTitle() {
        return specificationTitle;
    }

    public void setSpecificationTitle(String specificationTitle) {
        this.specificationTitle = specificationTitle;
    }

    public List<MultipleSku> getPropertiesList() {
        return propertiesList;
    }

    public void setPropertiesList(List<MultipleSku> propertiesList) {
        this.propertiesList = propertiesList;
    }
}
