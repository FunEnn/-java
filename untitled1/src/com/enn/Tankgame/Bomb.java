package com.enn.Tankgame;

public class Bomb {
    int x, y;
    int life = 9;//炸弹生命周期
    boolean isLive = true;//是否存活

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //减少生命值
    public void lifeDown() {
        if (life > 0)
            life--;
        else
            isLive = false;
    }
}
