package com.lxkj.dmhw.widget.banner;

import com.lxkj.dmhw.bean.HomePage;
import com.lxkj.dmhw.bean.self.ActivityBean;

import java.util.List;

/**
 * @author SoBan
 * @create 2017/4/13 11:20.
 */

public class ShareCardItem {

    private List<ActivityBean> dataList;

    public List<ActivityBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<ActivityBean> dataList) {
        this.dataList = dataList;
    }

    public static class ZSCardItem {
        private double todayIndex;
        private int amount;
        private int shopNum;
        private int memberNum;
        private String indexDate;

        public double getTodayIndex() {
            return todayIndex;
        }

        public void setTodayIndex(double todayIndex) {
            this.todayIndex = todayIndex;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getShopNum() {
            return shopNum;
        }

        public void setShopNum(int shopNum) {
            this.shopNum = shopNum;
        }

        public int getMemberNum() {
            return memberNum;
        }

        public void setMemberNum(int memberNum) {
            this.memberNum = memberNum;
        }

        public String getIndexDate() {
            return indexDate;
        }

        public void setIndexDate(String indexDate) {
            this.indexDate = indexDate;
        }
    }


}
