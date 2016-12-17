package com.lsj.Memory;

/**
 * Created by hdmi on 16-12-11.
 */
public class Memory {
    //内存初始状态=0表示空闲
    private int state=0;
    private int size;
    private int start;  //起始地址

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
