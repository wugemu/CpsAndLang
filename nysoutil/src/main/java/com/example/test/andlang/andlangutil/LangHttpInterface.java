package com.example.test.andlang.andlangutil;

/**
 * Created by root on 18-3-13.
 */

public interface LangHttpInterface<T> {
    void success(T map);
    void empty();
    void error();
    void fail();
}
