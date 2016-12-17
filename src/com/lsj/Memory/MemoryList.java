package com.lsj.Memory;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by hdmi on 16-12-11.
 */
public class MemoryList {
    public static LinkedList<Memory> memoryList=new LinkedList<Memory>();
    public static Random rand=new Random();
    public static int adress=0;

    //创建空闲内存分区5个
    public static void createList(){
        memoryList.clear();
        adress=0;
        for (int i=0;i<5;i++){
            Memory memory=new Memory();
            memory.setSize(rand.nextInt(80)+20);
            memory.setStart(rand.nextInt(5)+adress);
            memory.setState(0);
            adress=memory.getStart()+memory.getSize();
            memoryList.add(memory);
        }
    }

    public static LinkedList getList(){
        return memoryList;
    }
}
