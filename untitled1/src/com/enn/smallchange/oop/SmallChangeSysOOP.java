package com.enn.smallchange.oop;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * 完成零钱通各个功能的类
 */
public class SmallChangeSysOOP {
    //定义相关变量
    boolean loop = true;
    Scanner scanner = new Scanner(System.in);
    String key ="";

    //2.完成零钱通明细
    //（1）把收益入账和消费，保存到数组 （2）使用对象 （3）可以使用String拼接
    String details = "--------零钱通明细--------";

    //3.完成收益入账
    double money = 0;
    double balance = 0;
    Date date = null;//表示日期
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");//用于日期格式化

    //4.消费
    String note ="";

    //显示菜单
    public void mainMenu() {
        do {
            System.out.println("\n========零钱通菜单(OOP)========");
            System.out.println("\t\t\t1 零钱通明细");
            System.out.println("\t\t\t2 收益入账");
            System.out.println("\t\t\t3 消费");
            System.out.println("\t\t\t4 退出");

            System.out.println("请选择（1-4）:");
            key = scanner.next();

            switch (key) {
                case "1":
                    this.detail();
                    break;
                case "2":
                    this.income();
                    break;
                case "3":
                    this.pay();
                    break;
                case "4":
                    this.exit();
                    break;
                default:
                    System.out.println("选择有误，请重新选择");
            }

        }while(loop);

    }
    //完成零钱通明细
    public void  detail() {
        System.out.println(details);
    }
    //完成收益入账
    public void income() {
        System.out.println("收益入账金额:");
        money = scanner.nextDouble();
        if(money < 0) {
            System.out.println("收入金额 需要 大于 0");
            return;
        }
        balance += money;

        date = new Date();//获取当前日期

        details +="\n收益入账\t+" + money + "\t" + sdf.format(date) + "\t余额:" + balance;
    }
    public  void pay() {
        System.out.println("消费金额:");
        money = scanner.nextDouble();
        if(money < 0 || money > balance) {
            System.out.println("消费金额 应在0-" + balance + "元");
            return;
        }
        System.out.println("消费说明:");
        note = scanner.next();
        balance -= money;
        details += "\n" + note + "\t-" + money + "\t" + sdf.format(date) + "\t余额:" + balance;
    }
    public void exit() {

        String choice;
        do {
            System.out.println("你确定要退出吗？ y/n");
            choice = scanner.next();
        } while (!"y".equals(choice) && !"n".equals(choice));
        if(choice.equals("y")) {
            loop = false;
        }

    }

}
