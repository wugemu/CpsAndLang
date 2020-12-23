package com.lxkj.dmhw.utils;


    public interface RiseNumberBase {
        void start();

         RiseNumberTextView withNumber(float number);

         RiseNumberTextView withNumber(float number, boolean flag);

         RiseNumberTextView withNumber(int number,int fnum);

        RiseNumberTextView withNumber(int number);

         RiseNumberTextView setDuration(long duration);

        RiseNumberTextView withNumber(float number,float fnum);


         void setOnEnd(RiseNumberTextView.EndListener callback);
}