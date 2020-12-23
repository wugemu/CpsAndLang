package com.lxkj.dmhw.model;

import com.example.test.andlang.andlangutil.BaseLangViewModel;
import com.lxkj.dmhw.bean.IncomeData;

import java.util.List;

public class CashModel extends BaseLangViewModel {
    private List<IncomeData> incomeDataList;
    private List<IncomeData> incomeDataDetialList;

    public List<IncomeData> getIncomeDataDetialList() {
        return incomeDataDetialList;
    }

    public void setIncomeDataDetialList(List<IncomeData> incomeDataDetialList) {
        this.incomeDataDetialList = incomeDataDetialList;
    }

    public List<IncomeData> getIncomeDataList() {
        return incomeDataList;
    }

    public void setIncomeDataList(List<IncomeData> incomeDataList) {
        this.incomeDataList = incomeDataList;
    }
}
