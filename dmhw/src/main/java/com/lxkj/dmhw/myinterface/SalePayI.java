package com.lxkj.dmhw.myinterface;

public interface SalePayI {
    void sendCode();
    void commit(String captcha);
    void clickClose();
}
