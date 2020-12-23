package com.lxkj.dmhw.view.inspectroom;


import com.lxkj.dmhw.R;

import java.util.ArrayList;
import java.util.Random;


public class GoodsInitUtile {

    private static int goodType1 = R.mipmap.live_like_1;
    private static int goodType2 = R.mipmap.live_like_2;
    private static int goodType3 = R.mipmap.live_like_3;
    private static int goodType4 = R.mipmap.live_like_4;
    private static int goodType5 = R.mipmap.live_like_5;
    private static int goodType6 = R.mipmap.live_like_6;
    private static int goodType7 = R.mipmap.live_like_7;
    private static int goodType8 = R.mipmap.live_like_8;
    private static int goodType9 = R.mipmap.live_like_9;
    private static int goodType10 = R.mipmap.live_like_10;
    private static int goodType11 = R.mipmap.live_like_11;
    private static int goodType12 = R.mipmap.live_like_12;
    private static int goodType13 = R.mipmap.live_like_13;

    private static int TYPECOUNT = 13;

    private static int[] randomImages={R.mipmap.live_like_1,R.mipmap.live_like_2, R.mipmap.live_like_3,R.mipmap.live_like_4,R.mipmap.live_like_5,
            R.mipmap.live_like_6,R.mipmap.live_like_7,R.mipmap.live_like_8,R.mipmap.live_like_9,R.mipmap.live_like_10,R.mipmap.live_like_11,
            R.mipmap.live_like_12,R.mipmap.live_like_13};

    /*
     *   不同用户的标识id
     */
    public static int fromGoodID(int _id) {
        int rint = (int) (_id % TYPECOUNT);
        return rint;
    }

    /*
     * 获取点赞的类型
     */
    public static int getGoodsType(int type) {
        int rType = goodType1;
        switch (type) {
            case 1:
                rType = goodType1;
                break;
            case 2:
                rType = goodType2;
                break;
            case 3:
                rType = goodType3;
                break;
            case 4:
                rType = goodType4;
                break;
            case 5:
                rType = goodType5;
                break;
            case 6:
                rType = goodType6;
                break;
            case 7:
                rType = goodType7;
                break;
            case 8:
                rType = goodType8;
                break;
            case 9:
                rType = goodType9;
                break;
            case 10:
                rType = goodType10;
                break;
            case 11:
                rType = goodType11;
                break;
            case 12:
                rType = goodType12;
                break;
            case 13:
                rType = goodType13;
                break;
        }
        return rType;
    }

    //获取随机的imageresource的个数 不超过3个
    public static ArrayList<Integer> getRandomImages(){
        Random random = new Random();
        int size=randomImages.length;
        ArrayList<Integer>tempImages=new ArrayList();
         int i=random.nextInt(3);
      while (tempImages.size()<=i){
         tempImages.add(randomImages[random.nextInt(size)]);
      }
      return tempImages;
    }
}
