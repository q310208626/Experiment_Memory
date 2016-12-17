package com.lsj.Memory;

import com.lsj.Method.BFMethod;
import com.lsj.Method.FFMethod;
import com.lsj.Method.NFMethod;
import com.lsj.Method.WFMethod;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * Created by hdmi on 16-12-11.
 */
public class Experiment_Memory extends JFrame {
    private LinkedList<Memory> memoryList;
    private List<Program> programList;

    private JPanel createPanel;
    private JButton memoryCreateButton;
    private JButton programCreateButton;

    private JPanel memoryPanel;
    private JLabel memoryLabel;
    private JTable memoryTable = null;
    private JScrollPane memoryScrollPane;
    TableModel memoryTableModel = null;
    String[] memoryColumName = {"编号", "起始地址", "内存大小", "程序编号"};

    private JPanel methodSelectPanel;
    private ButtonGroup methodSelectButtonGroup;
    private JRadioButton FFButton; //首次适应算法
    private JRadioButton NFButton;  //循环首次适应
    private JRadioButton BFButton;  //最佳适应算法
    private JRadioButton WFButton;  //最坏适应算法
    private JButton runMethodButton;

    private JPanel undoProgramPanel;
    private JLabel undoProgramLabel;
    JTable undoProgramTable = null;
    private JScrollPane undoProgramTableScrollPane;
    private TableModel undoProgramTableModel = null;
    String[] undoProgramColumName = {"编号", "程序大小"};

    private JPanel didProgramPanel;
    private JLabel didProgramLabel;
    JTable didProgramTable = null;
    private JScrollPane didProgramTableScrollPane;
    private TableModel didProgramTableModel = null;
    String[] didProgramColumName = {"编号", "程序大小"};

    public Experiment_Memory() {
        Container con = this.getContentPane();
        this.setVisible(true);
        this.setSize(1000, 500);
        con.setLayout(new GridBagLayout());

        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 0;
        c1.gridy = 0;
        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 1;
        c2.gridy = 0;
        c2.gridwidth = 2;
        GridBagConstraints c3 = new GridBagConstraints();
        c3.gridx = 0;
        c3.gridy = 1;
        GridBagConstraints c4 = new GridBagConstraints();
        c4.gridx = 1;
        c4.gridy = 1;
        GridBagConstraints c5 = new GridBagConstraints();
        c5.gridx = 2;
        c5.gridy = 1;
        GridBagConstraints c6 = new GridBagConstraints();
        c6.gridx = 2;
        c6.gridy = 0;

        createPanel = new JPanel();
        memoryCreateButton = new JButton("内存创建");
        programCreateButton = new JButton("程序创建");
        memoryCreateButton.addActionListener(new MemoryCreateListener());
        programCreateButton.addActionListener(new ProgramCreateListener());
        createPanel.add(memoryCreateButton);
        createPanel.add(programCreateButton);


        methodSelectPanel = new JPanel();
        methodSelectButtonGroup = new ButtonGroup();
        FFButton = new JRadioButton("首次适应");
        NFButton = new JRadioButton("循环首次适应");
        BFButton = new JRadioButton("最佳适应");
        WFButton = new JRadioButton("最坏适应");
        runMethodButton = new JButton("运行");
        methodSelectButtonGroup.add(FFButton);
        methodSelectButtonGroup.add(NFButton);
        methodSelectButtonGroup.add(BFButton);
        methodSelectButtonGroup.add(WFButton);
        FFButton.setSelected(true);
        runMethodButton.addActionListener(new MethodRunListener());
        methodSelectPanel.add(FFButton);
        methodSelectPanel.add(NFButton);
        methodSelectPanel.add(BFButton);
        methodSelectPanel.add(WFButton);
        methodSelectPanel.add(runMethodButton);

        //内存表格
        memoryPanel = new JPanel();
        memoryLabel = new JLabel("内存");
        memoryTable = new JTable(new Object[][]{}, memoryColumName) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        memoryTableModel = new DefaultTableModel();
        memoryTable.setPreferredScrollableViewportSize(new Dimension(300, 400));
        memoryScrollPane = new JScrollPane(memoryTable);
        memoryScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        memoryPanel.add(memoryLabel);
        memoryPanel.add(memoryScrollPane);

        //未进入内存程序表格
        undoProgramPanel = new JPanel();
        undoProgramLabel = new JLabel("未做程序");
        undoProgramTable = new JTable(new Object[][]{}, undoProgramColumName) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        undoProgramTableModel = new DefaultTableModel();
        undoProgramTable.setPreferredScrollableViewportSize(new Dimension(160, 400));
        undoProgramTableScrollPane = new JScrollPane(undoProgramTable);
        undoProgramTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        undoProgramPanel.add(undoProgramLabel);
        undoProgramPanel.add(undoProgramTableScrollPane);

        //已进入内存程序表格
        didProgramPanel = new JPanel();
        didProgramLabel = new JLabel("已做程序");
        didProgramTable = new JTable(new Object[][]{}, didProgramColumName) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        didProgramTableModel = new DefaultTableModel();
        didProgramTable.setPreferredScrollableViewportSize(new Dimension(160, 400));
        didProgramTableScrollPane = new JScrollPane(didProgramTable);
        didProgramTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        didProgramPanel.add(didProgramLabel);
        didProgramPanel.add(didProgramTableScrollPane);

        //设置屏幕在中间
        Toolkit kit = Toolkit.getDefaultToolkit();    // 定义工具包
        Dimension screenSize = kit.getScreenSize();   // 获取屏幕的尺寸
        int screenWidth = screenSize.width / 2;         // 获取屏幕的宽
        int screenHeight = screenSize.height / 2;       // 获取屏幕的高
        int height = this.getHeight();
        int width = this.getWidth();
        this.setLocation(screenWidth - width / 2, screenHeight - height / 2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        con.add(methodSelectPanel, c1);
        con.add(createPanel, c2);
        con.add(memoryPanel, c3);
        con.add(undoProgramPanel, c4);
        con.add(didProgramPanel, c5);


    }

    public class MemoryCreateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            MemoryList.createList();
            if (MemoryList.getList() != null) {
                memoryList = MemoryList.getList();
            }
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

            memoryTableModel = new DefaultTableModel(data, memorycolumName);
            memoryTable.setModel(memoryTableModel);

        }
    }

    public class ProgramCreateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ProgramList.createList();
            if (ProgramList.getList() != null) {
                programList = ProgramList.getList();
            }
            Vector undoProgramcolumName = new Vector();
            Vector data = new Vector();
            for (int i = 0; i < undoProgramColumName.length; i++) {
                undoProgramcolumName.add(undoProgramColumName[i]);
            }

            for (int i = 0; i < programList.size(); i++) {
                Vector row = new Vector();
                row.add(programList.get(i).getId());
                row.add(programList.get(i).getSize());
                data.add(row);
            }

            undoProgramTableModel = new DefaultTableModel(data, undoProgramcolumName);
            undoProgramTable.setModel(undoProgramTableModel);

        }
    }

    public class MethodRunListener implements ActionListener {
        LinkedList<Memory> listenerMemoryList = new LinkedList<Memory>();
        List<Program> listenerProgramList = new ArrayList<Program>();

        @Override
        public void actionPerformed(ActionEvent e) {
            listenerMemoryList.clear();
            listenerProgramList.clear();

            if (memoryList != null && programList != null) {
                listenerMemoryList.addAll(memoryList);
                listenerProgramList.addAll(programList);
                System.out.print("startRunMthod!!!!!!!!!!");
                if (FFButton.isSelected()) {
                    new FFMethod(listenerMemoryList, listenerProgramList, memoryTable, undoProgramTable, didProgramTable).start();
                } else if (NFButton.isSelected()) {
                    new NFMethod(listenerMemoryList, listenerProgramList, memoryTable, undoProgramTable, didProgramTable).start();
                } else if (BFButton.isSelected()) {
                    new BFMethod(listenerMemoryList, listenerProgramList, memoryTable, undoProgramTable, didProgramTable).start();
                } else if (WFButton.isSelected()) {
                    new WFMethod(listenerMemoryList, listenerProgramList, memoryTable, undoProgramTable, didProgramTable).start();
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Experiment_Memory();
            }
        });
    }
}
