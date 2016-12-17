package com.lsj.Memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by hdmi on 16-12-11.
 */
public class ProgramList {
    public static List<Program> programList=new ArrayList<Program>();

    public static Random rand=new Random();

    //创建空程序数j个
    public static void createList(){
        programList.clear();
        for (int i=0;i<15;i++){
            Program program=new Program();
            program.setSize(rand.nextInt(40)+10);
            program.setId(i+1);
            programList.add(program);
        }
    }

    public static List<Program> getList() {
        return programList;
    }
}
