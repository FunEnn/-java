package com.enn.Tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

/**
 * 坦克大战的绘图区域
 */
//为了监听键盘事件
//为了让Panel 不停重绘子弹，需要将MyPanel 实现 Runnable， 当作一个线程使用
public class MyPanel extends JPanel implements KeyListener, Runnable {
    //定义我的坦克
    Hero hero = null;
    //定义敌人坦克
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //定义一个存放Node 对象的Vector，用于恢复敌人坦克的坐标和方向
    Vector<Node> nodes = new Vector<>();
    //定义一个vector用于存放炸弹
    //当子弹击中坦克后，加入一个Bomb对象到bombs
    Vector<Bomb> bombs = new Vector<>();
    int enemyTanksSize = 3;

    //定义三张炸弹图片
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;


    public MyPanel(String key) {
        //先判断记录是否存在
        File file = new File(Recorder.getRecordFile());
        if(file.exists()) {
            nodes = Recorder.getNodesAndEnemyTankRec();
        }else {
            System.out.println("文件不存在，只能开启新的游戏");
            key = "1";
        }
        //将MyPanel对象的 enemyTanks 设置给 Record
        Recorder.setEnemyTanks(enemyTanks);

        //初始化自己坦克
        hero = new Hero(500, 100);

        switch (key) {
            case "1":
                //初始化敌人坦克
                for (int i = 0; i < enemyTanksSize; i++) {
                    EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
                    //将enemyTanks设置给 enemyTank
                    enemyTank.setEnemyTanks(enemyTanks);
                    //设置方向
                    enemyTank.setDirect(2);
                    //启动敌人坦克
                    new Thread(enemyTank).start();
                    //给敌人坦克 加入一颗子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    enemyTank.shots.add(shot);
                    //启动
                    new Thread(shot).start();
                    //加入
                    enemyTanks.add(enemyTank);
                }
                break;
            case "2"://继续上局游戏
                //初始化敌人坦克
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());
                    //将enemyTanks设置给 enemyTank
                    enemyTank.setEnemyTanks(enemyTanks);
                    //设置方向
                    enemyTank.setDirect(node.getDirect());
                    //启动敌人坦克
                    new Thread(enemyTank).start();
                    //给敌人坦克 加入一颗子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    enemyTank.shots.add(shot);
                    //启动
                    new Thread(shot).start();
                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                System.out.println("你的输入有误...");
                break;
        }


        //出生化图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb1.png"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb2.png"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb3.png"));
        //播放音乐
        new AePlayWave("untitled1\\src\\222.wav").start();
    }

    //编写方法，显示我方击毁敌方坦克的信息
    public void showInfo(Graphics g) {
        //画出玩家总成绩
        g.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);

        g.drawString("累积击毁敌方坦克", 1020, 30);
        drawTank(1020, 60, g, 0, 1);
        g.setColor(Color.BLACK);
        g.drawString(Recorder.getAllEnemyTankNum() + "", 1080, 100);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);//填充矩形，默认黑色
        showInfo(g);
        //画出坦克——封装方法
        if (hero != null && hero.isLive) {
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 0);
        }
        //画出子弹
//        if (hero.shot != null && hero.shot.isLive == true) {
//            g.fill3DRect(hero.shot.x, hero.shot.y, 3, 3, false);
//        }
        //将hero子弹集合 shots取出
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if (shot != null && hero.shot.isLive == true) {
                g.draw3DRect(shot.x, shot.y, 3, 3, false);
            } else
                hero.shots.remove(shot);
        }
        //如果bombs 集合中右对象，就画出
        for (int i = 0; i < bombs.size(); i++) {
            //取出炸弹
            Bomb bomb = bombs.get(i);
            //根据当前这个bomb对象的life值去画出对应的图片
            if (bomb.life > 6) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            }
            bomb.lifeDown();
            //如果bomb life 为0 ，就从bombs集合中删除
            if (bomb.life == 0) bombs.remove(bomb);
        }
        //画出敌人坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            //从vector中取出坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            if (enemyTank.isLive) {//当敌人坦克是存活的，才画出坦克
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 1);
                //画出enemyTank 所有子弹
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    //取出子弹
                    Shot shot = enemyTank.shots.get(j);
                    //绘制
                    if (shot.isLive) {
                        g.draw3DRect(shot.x, shot.y, 3, 3, false);
                    } else {
                        //从Vector 移除
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }
    }
    //编写方法，画出坦克

    /**
     * @param x
     * @param y
     * @param g      画笔
     * @param direct 坦克方向
     * @param type   坦克类型
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        switch (type) {
            case 0://自己的坦克
                g.setColor(Color.cyan);
                break;
            case 1://敌人的坦克
                g.setColor(Color.yellow);
                break;
        }
        //根据坦克方向，来绘制对应坦克
        //direct 0:向上 1:向右 2:向下 3:向左
        switch (direct) {
            case 0://向上
                g.fill3DRect(x, y, 10, 60, false);//坦克左轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//坦克右轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//坦克盖子
                g.fillOval(x + 10, y + 20, 20, 20);//坦克圆盖子
                g.drawLine(x + 20, y + 30, x + 20, y);//炮筒
                break;
            case 1://向右
                g.fill3DRect(x, y, 60, 10, false);//坦克上边轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//坦克下边轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//坦克盖子
                g.fillOval(x + 20, y + 10, 20, 20);//坦克圆盖子
                g.drawLine(x + 30, y + 20, x + 60, y + 20);//炮筒
                break;
            case 2://向下
                g.fill3DRect(x, y, 10, 60, false);//坦克左轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//坦克右轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//坦克盖子
                g.fillOval(x + 10, y + 20, 20, 20);//坦克圆盖子
                g.drawLine(x + 20, y + 30, x + 20, y + 60);//炮筒
                break;
            case 3://向左
                g.fill3DRect(x, y, 60, 10, false);//坦克上边轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//坦克下边轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//坦克盖子
                g.fillOval(x + 20, y + 10, 20, 20);//坦克圆盖子
                g.drawLine(x + 30, y + 20, x, y + 20);//炮筒
                break;
            default:

        }
    }

    //编写方法，判断子弹是否击中敌人
    public void hitEnemyTank() {
        //遍历我们的子弹
        for (int j = 0; j < hero.shots.size(); j++) {
            Shot shot = hero.shots.get(j);
            //判断是否击中坦克
            if (hero.shot != null && hero.shot.isLive) {//当子弹存在时
                //遍历敌人所有坦克
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    hitTank(hero.shot, enemyTank);
                }
            }
        }
    }

    public void hitTank(Shot s, Tank enemyTank) {
        //判断s 击中坦克
        switch (enemyTank.getDirect()) {
            case 0:
            case 2:
                if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 40
                        && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 60) {
                    s.isLive = false;
                    enemyTank.isLive = false;
                    //当子弹击中后就将 enemyTank从Vector中拿掉
                    enemyTanks.remove(enemyTank);
                    //我方击毁坦克后增加
                    if (enemyTank instanceof EnemyTank) {
                        Recorder.addEnemyTankNum();
                    }
                    //创建Bomb对象，加入到bombs集合
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                }
                break;
            case 1:
            case 3:
                if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 60
                        && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 40) {
                    s.isLive = false;
                    enemyTank.isLive = false;
                    //当子弹击中后就将 enemyTank从Vector中拿掉
                    enemyTanks.remove(enemyTank);
                    if (enemyTank instanceof EnemyTank) {
                        Recorder.addEnemyTankNum();
                    }
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                }
        }

    }

    //编写方法，判断敌人坦克是否击中我方坦克
    public void hitHero() {
        //遍历敌人坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            //遍历enemyTank 对象所有的子弹
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                //再取出子弹
                Shot shot = enemyTank.shots.get(j);
                //判断1是否击中我方坦克
                if (hero.isLive && shot.isLive) {
                    hitTank(shot, hero);
                }
            }
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    //处理 键按下的情况
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {//按下W
            hero.setDirect(0);
            //修改坦克坐标
            if (hero.getY() > 0) {
                hero.moveUp();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) {//按下D
            hero.setDirect(1);
            if (hero.getX() + 70 < 1000) {
                hero.moveRight();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {//按下S
            hero.setDirect(2);
            if (hero.getY() + 90 < 750) {
                hero.moveDown();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {//按下A
            hero.setDirect(3);
            if (hero.getX() > 0) {
                hero.moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_J) {//按下J发射子弹

            //判断子弹是否销毁
//            if (hero.shot == null || !hero.shot.isLive) {
            hero.shotEnemyTank();
//            }
        }
        //面板重绘
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {//重绘区域
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            hitEnemyTank();
            hitHero();
            this.repaint();
        }
    }
}
