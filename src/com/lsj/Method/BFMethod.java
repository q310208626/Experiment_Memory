package com.lsj.Method;

import com.lsj.Memory.Memory;
import com.lsj.Memory.Program;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.*;

/**
 * Created by hdmi on 16-12-17.
 */
public class BFMethod extends Thread {
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
    List dropList = new ArrayList<>();
    int suitMinMemort=200;
    int suitMinMemortindex=-1;

    public BFMethod(LinkedList<Memory> memoryList, List<Program> programList, JTable memoryTable, JTable undoProgramTable, JTable didProgramTable) {
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
        List<Program> didprogramList = new ArrayList<Program>();
        java.util.Timer timer = new java.util.Timer();
        while (programList.size() > 0) {
            System.out.print("programlistAllonce------ \n");
            for (int i = 0; i < programList.size(); i++) {
                System.out.print(i + " ");
                Program program = programList.get(i);
                suitMinMemortindex=-1;
                suitMinMemort=200;
                for (int j = 0; j < memoryList.size(); j++) {
                    System.out.print("memory:"+j+"\n");
                    Memory memory = memoryList.get(j);
                    if (memory.getState() == 0) {
                        if (memory.getSize() > program.getSize()) {
                            //查找最小合适内存
                            if(suitMinMemort>memory.getSize()){
                                suitMinMemort=memory.getSize();
                                suitMinMemortindex=j;
                            }

                        }
                    }
                }
                //内存大小减去程序大小如果大于最小不可分内存，则切割一块内存出来
                if(suitMinMemortindex!=-1){
                    System.out.print("suitMinMemortindex"+suitMinMemortindex+"\n");
                    Memory memory=memoryList.get(suitMinMemortindex);
                    if (memory.getSize() - program.getSize() > minMemorySize) {
                        Memory newMemory = new Memory();
                        newMemory.setStart(memory.getStart() + program.getSize());
                        newMemory.setSize(memory.getSize() - program.getSize());
                        newMemory.setState(0);
                        memory.setSize(program.getSize());
                        memory.setState(program.getId());
                        memoryList.add(suitMinMemortindex + 1, newMemory);
                    } else {
                        memory.setState(program.getId());
                    }

                    programList.remove(i);
                    didprogramList.add(program);
                    dropList.add(i);
                    i--;
                    System.out.print("dropList.size---" + dropList.size() + "\n");
                    //programList.remove(i);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    updateMemoryTable();
                    updateUndoProgramTable(programList, undoProgramTable);
                    updateUndoProgramTable(didprogramList, didProgramTable);

                }

            }
            System.out.print("memoryRestore++");

            for (int i = 0; i < memoryList.size(); i++) {
                if (i < memoryList.size() - 1) {
                    Memory nowMemory = memoryList.get(i);
                    Memory nextMemory = memoryList.get(i + 1);
                    if (nowMemory.getStart() + nowMemory.getSize() == nextMemory.getStart()) {
                        System.out.print(nowMemory.getStart() + nowMemory.getSize() == nextMemory.getStart());
                        nowMemory.setSize(nowMemory.getSize() + nextMemory.getSize());
                        nowMemory.setState(0);
                        memoryList.remove(i + 1);
                        i--;
                    }
                    nowMemory.setState(0);
                }
            }
            memoryList.get(memoryList.size() - 1).setState(0);
            updateMemoryTable();
        }
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

    public void updateUndoProgramTable(List<Program> programList, JTable table) {
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
