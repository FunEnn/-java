package com.enn.Tankgame;

import java.util.Vector;

/**
 * 自己的坦克
 */
public class Hero extends Tank {
    //定义一个shot对象，表示一个射击（线程）
    Shot shot = null;
    Vector<Shot> shots = new Vector<>();

    public Hero(int x, int y) {
        super(x, y);
    }

    public void shotEnemyTank() {
        //控制子弹数量
//        if(shots.size() == 5) {
//            return;
//        }
        //根据Hero对象的位置和方向来创建Shot
        switch (getDirect()) {
            case 0://上
                shot = new Shot(getX() + 20, getY(), 0);
                break;
            case 1://右
                shot = new Shot(getX() + 60, getY() + 20, 1);
                break;
            case 2://下
                shot = new Shot(getX() + 20, getY() + 60, 2);
                break;
            case 3://左
                shot = new Shot(getX(), getY() + 20, 3);
                break;
        }
        //启动射击线程
        shots.add(shot);
        new Thread(shot).start();
    }
}
