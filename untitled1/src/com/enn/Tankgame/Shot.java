package com.enn.Tankgame;

/**
 * 射击子弹
 */
public class Shot implements Runnable {
    int x;
    int y;
    int direct = 0;//子弹方向
    int speed = 5;//速度
    boolean isLive = true;

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    @Override
    public void run() {
        while (true) {
            //子弹休眠
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            switch (direct) {
                case 0://上
                    y -= speed;
                    break;
                case 1://右
                    x += speed;
                    break;
                case 2://下
                    y += speed;
                    break;
                case 3://左
                    x -= speed;
                    break;
            }
            //当子弹到边界时 或 碰到敌人坦克时，就销毁
            if (!(x >= 0 && x <= 1000 && y >= 0 && y <= 750 && isLive)) {
                isLive = false;
                break;
            }
        }
    }
}
