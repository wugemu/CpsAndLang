package com.lxkj.dmhw.utils;

public class ButtonUtil2 {
    public static long btnId = -1L;
    private static long lastClickTime1;
    public static boolean isFastDoubleClick(long btn,long sleepTime) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime1;
        if (btnId != btn) {
            btnId = btn;
            lastClickTime1 = time;
            return false;
        } else if (0L < timeD && timeD < sleepTime) {
            return true;
        } else {
            lastClickTime1 = time;
            return false;
        }
    }
}
