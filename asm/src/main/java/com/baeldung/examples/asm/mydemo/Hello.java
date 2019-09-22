package com.baeldung.examples.asm.mydemo;

/**
 * Created by shucheng on 2019-9-22 上午 10:01
 */
public class Hello {

    @Add
    public int add(int x, int y) {
        return x + y;
    }

    public void add() {}
}
