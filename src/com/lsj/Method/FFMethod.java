package com.lsj.Method;

import com.lsj.Memory.Memory;
import com.lsj.Memory.Program;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.*;
import java.util.Timer;

/**
 * Created by hdmi on 16-12-17.
 */
public class FFMethod extends Thread{
    int minMemorySize = 20;
    LinkedList<Memory> memoryList;
    List<Program> programList;
    List<Program> didprogramList;
    JTable memoryTable;
    JTable undoProgramTable;
    JTable didProgramTable;
    String[] memoryColumName = {"编号", "起始地址", "内存大小", "程序编号"};
    String[] undoProgramColumName = {"编号", "程序大小"};
    String[] didProgramColumName = {"编号", "程序大小"};
    List dropList=new ArrayList<>();


    public FFMethod(LinkedList<Memory> memoryList, List<Program> programList, JTable memoryTable, JTable undoProgramTable, JTable didProgramTable) {
        this.memoryList = memoryList;
        this.programList = programList;
        this.memoryTable = memoryTable;
        this.undoProgramTable = undoProgramTable;
        this.didProgramTable = didProgramTable;
    }

    @Override
    public void run() {
        methodRun();
        super.run();
    }

    public void methodRun() {
        new Runnable(){
            @Override
            public void run() {
                List<Program> didprogramList = new ArrayList<Program>();
                java.util.Timer timer = new Timer();
                while (programList.size() > 0) {
                System.out.print("programlistAllonce------ ");
                for (int i = 0; i < programList.size(); i++) {
                    System.out.print(i+" ");
                    Program program = programList.get(i);
                    for (int j = 0; j < memoryList.size(); j++) {

                        Memory memory = memoryList.get(j);
                        if (memory.getState() == 0) {
                            if (memory.getSize() > program.getSize()) {
                                //内存大小减去程序大小如果大于最小不可分内存，则切割一块内存出来
                                if (memory.getSize() - program.getSize() > minMemorySize) {
                                    Memory newMemory = new Memory();
                                    newMemory.setStart(memory.getStart() + program.getSize());
                                    newMemory.setSize(memory.getSize() - program.getSize());
                                    newMemory.setState(0);
                                    memory.setSize(program.getSize());
                                    memory.setState(program.getId());
                                    memoryList.add(j + 1, newMemory);
                                }else{
                                    memory.setState(program.getId());
                                }

                                programList.remove(i);

                                didprogramList.add(program);
                                dropList.add(i);
                                i--;
                                System.out.print("dropList.size---"+dropList.size()+"\n");
                                //programList.remove(i);
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                updateMemoryTable();
                                updateUndoProgramTable(programList,undoProgramTable);
                                updateUndoProgramTable(didprogramList,didProgramTable);


                                break;
                            }
                        }
                    }

                }
                System.out.print("memoryRestore++");

                for (int i=0;i<memoryList.size();i++){
                    if(i<memoryList.size()-1){
                        Memory nowMemory=memoryList.get(i);
                        Memory nextMemory=memoryList.get(i+1);
                        if(nowMemory.getStart()+nowMemory.getSize()==nextMemory.getStart()){
                            System.out.print(nowMemory.getStart()+nowMemory.getSize()==nextMemory.getStart());
                            nowMemory.setSize(nowMemory.getSize()+nextMemory.getSize());
                            nowMemory.setState(0);
                            memoryList.remove(i+1);
                            i--;
                        }
                        nowMemory.setState(0);
                    }
                }
                memoryList.get(memoryList.size()-1).setState(0);
                updateMemoryTable();
//                for (int i=0;i<dropList.size();i++){
//                    programList.remove(dropList.get(i));
//                    System.out.print("programList.size"+programList.size()+"\n");
//                }
//                updateUndoProgramTable(programList,undoProgramTable);
//                updateUndoProgramTable(didprogramList,didProgramTable);
//
               }

            }
        }.run();

       }



    public void updateMemoryTable() {
        Vector memorycolumName = new Vector();
        Vector data = new Vector();
        for (int i = 0; i < memoryColumName.length; i++) {
            memorycolumName.add(memoryColumName[i]);
        }

        for (int i = 0; i < memoryList.size(); i++) {
            Vector row = new Vector();
            row.add(i + 1);
            row.add(memoryList.get(i).getStart());
            row.add(memoryList.get(i).getSize());
            row.add(memoryList.get(i).getState());
            data.add(row);
        }

        TableModel model = new DefaultTableModel(data, memorycolumName);
        memoryTable.setModel(model);
    }

    public void updateUndoProgramTable(List<Program> programList,JTable table) {
        Vector columName = new Vector();
        Vector data = new Vector();
        for (int i = 0; i < undoProgramColumName.length; i++) {
            columName.add(undoProgramColumName[i]);
        }

        for (int i = 0; i < programList.size(); i++) {
            Vector row = new Vector();
            row.add(i + 1);
            row.add(programList.get(i).getSize());
            data.add(row);
        }

        TableModel model = new DefaultTableModel(data, columName);
        table.setModel(model);
    }
}
