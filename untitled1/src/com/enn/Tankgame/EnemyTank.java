package com.enn.Tankgame;

import java.util.Vector;

public class EnemyTank extends Tank implements Runnable {
    Vector<Shot> shots = new Vector<>();
    //增加成员，EnemyTank 可以得到敌人坦克的Vector’
    Vector<EnemyTank> enemyTanks = new Vector<>();
    boolean isLive = true;

    public EnemyTank(int x, int y) {
        super(x, y);
    }

    //提供一个方法，可以将MyPanel 的成员设置到EnemyTank 的成员enemyTank
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    //编写方法，判断当前坦克，是否和enemyTank 中其他坦克发生重叠或碰撞
    public boolean isTouchEnemyTank() {
        //判断当前敌人
        switch (this.getDirect()) {
            case 0://上
                //让当前敌人坦克和其他所有敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //c从vector中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this) {
                        //如果敌人坦克向上/下
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //当前坦克 左上角坐标
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() <= enemyTank.getY() + 60
                                    && this.getY() >= enemyTank.getY()) {
                                return true;
                            }
                            //当前坦克 右上角坐标
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() <= enemyTank.getY() + 60
                                    && this.getY() >= enemyTank.getY()) {
                                return true;
                            }

                        }
                        //如果是 右/左
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //当前坦克 左上角坐标
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() <= enemyTank.getY() + 40
                                    && this.getY() >= enemyTank.getY()) {
                                return true;
                            }
                            //当前坦克 右上角坐标
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() <= enemyTank.getY() + 40
                                    && this.getY() >= enemyTank.getY()) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 1://右
                //让当前敌人坦克和其他所有敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //c从vector中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this) {
                        //如果敌人坦克向上/下
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //当前坦克 左下角坐标
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 60 <= enemyTank.getY() + 60
                                    && this.getY() + 60 >= enemyTank.getY()) {
                                return true;
                            }
                            //当前坦克 右下角坐标
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() + 60 <= enemyTank.getY() + 60
                                    && this.getY() + 60 >= enemyTank.getY()) {
                                return true;
                            }

                        }
                        //如果是 右/左
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //当前坦克 左下角坐标
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 60 <= enemyTank.getY() + 40
                                    && this.getY() + 60 >= enemyTank.getY()) {
                                return true;
                            }
                            //当前坦克 右下角坐标
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() + 60 <= enemyTank.getY() + 40
                                    && this.getY() + 60 >= enemyTank.getY()) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 2://下
                //让当前敌人坦克和其他所有敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //c从vector中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this) {
                        //如果敌人坦克向上/下
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //当前坦克 左上角坐标
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() <= enemyTank.getY() + 60
                                    && this.getY() >= enemyTank.getY()) {
                                return true;
                            }
                            //当前坦克 左下角坐标
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() + 40 <= enemyTank.getY() + 60
                                    && this.getY() + 40 >= enemyTank.getY()) {
                                return true;
                            }

                        }
                        //如果是 右/左
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //当前坦克 右上角坐标
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() <= enemyTank.getY() + 40
                                    && this.getY() >= enemyTank.getY()) {
                                return true;
                            }
                            //当前坦克 右下角坐标
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() + 40 <= enemyTank.getY() + 40
                                    && this.getY() + 40 >= enemyTank.getY()) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 3://左
                //让当前敌人坦克和其他所有敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //c从vector中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this) {
                        //如果敌人坦克向上/下
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //当前坦克 左上角坐标
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() <= enemyTank.getY() + 60
                                    && this.getY() >= enemyTank.getY()) {
                                return true;
                            }
                            //当前坦克 左下角坐标
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 40 <= enemyTank.getY() + 60
                                    && this.getY() + 40 >= enemyTank.getY()) {
                                return true;
                            }
                        }
                        //如果是 右/左
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //当前坦克 左上角坐标
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() <= enemyTank.getY() + 40
                                    && this.getY() >= enemyTank.getY()) {
                                return true;
                            }
                            //当前坦克  左下角坐标
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 40 <= enemyTank.getY() + 40
                                    && this.getY() + 40 >= enemyTank.getY()) {
                                return true;
                            }
                        }
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public void run() {
        while (true) {

            if (isLive && shots.size() < 10) {
                //判断坦克的方向，创建对应的子弹
                Shot s = null;
                switch (getDirect()) {
                    case 0:
                        s = new Shot(getX() + 20, getY(), 0);
                        break;
                    case 1:
                        s = new Shot(getX() + 60, getY() + 20, 1);
                        break;
                    case 2:
                        s = new Shot(getX() + 20, getY() + 60, 2);
                        break;
                    case 3:
                        s = new Shot(getX(), getY() + 20, 3);
                        break;
                }
                shots.add(s);
                new Thread(s).start();
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
            }
            //根据坦克的方向来继续移动
            switch (getDirect()) {
                case 0://上
                    for (int i = 0; i < 20; i++) {
                        if (getY() > 0 && !isTouchEnemyTank()) {
                            moveUp();
                        } else break;
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 1://右
                    for (int i = 0; i < 20; i++) {
                        if (getX() + 60 < 1000 && !isTouchEnemyTank()) {
                            moveRight();
                        } else break;
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 2://下
                    for (int i = 0; i < 20; i++) {
                        if (getY() + 60 < 700 && !isTouchEnemyTank()) {
                            moveDown();
                        } else break;
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 3://左
                    for (int i = 0; i < 30; i++) {
                        if (getX() > 0 && !isTouchEnemyTank()) {
                            moveLeft();
                        } else break;
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
            }
            //随机改变坦克方向
            setDirect((int) (Math.random() * 4));
            //考虑该线程什么时候结束
            if (!isLive) break;//退出线程
        }
    }
}
