package com.enn.Tankgame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

public class TankGame01 extends JFrame {
    //定义MyPanel
    MyPanel mp = null;
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        TankGame01 tankGame01 = new TankGame01();
    }
    public TankGame01() {
        System.out.println("请输入选择 1: 新游戏 2: 继续上局");
        String key = scanner.next();
        mp = new MyPanel(key);
        //将mp放入到 Thread，并启动
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);//面板
        this.setSize(1250,750);
        this.addKeyListener(mp);//让JFrame 监听mp的键盘事件
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //在 JFrame 中增加相应关闭窗口的处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecord();
                System.exit(0);
            }
        });
    }
}

