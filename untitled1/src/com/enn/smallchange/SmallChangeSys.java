package com.enn.smallchange;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class SmallChangeSys {
    //1.显示菜单，并可以选择菜单

    public static void main(String[] args) {

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

        do {
            System.out.println("\n========零钱通菜单========");
            System.out.println("\t\t\t1 零钱通明细");
            System.out.println("\t\t\t2 收益入账");
            System.out.println("\t\t\t3 消费");
            System.out.println("\t\t\t4 退出");

            System.out.println("请选择（1-4）:");
            key = scanner.next();

            switch (key) {
                case "1":
                    System.out.println(details);
                    break;
                case "2":
                    System.out.println("收益入账金额:");
                    money = scanner.nextDouble();
                    if(money < 0) {
                        System.out.println("收入金额 需要 大于 0");
                        break;
                    }
                    balance += money;

                    date = new Date();//获取当前日期

                    details +="\n收益入账\t+" + money + "\t" + sdf.format(date) + "\t余额:" + balance;
                    break;
                case "3":
                    System.out.println("消费金额:");
                    money = scanner.nextDouble();
                    if(money < 0 || money > balance) {
                        System.out.println("消费金额 应在0-" + balance + "元");
                        break;
                    }
                    System.out.println("消费说明:");
                    note = scanner.next();
                    balance -= money;
                    details +="\n" + note + "\t-" + money + "\t" + sdf.format(date) + "\t余额:" + balance;
                    break;
                case "4":

                    String choice = "";
                    while (true) {
                        System.out.println("你确定要退出吗？ y/n");
                        choice = scanner.next();
                        if("y".equals(choice)||"n".equals(choice)) {
                            break;
                        }
                    }
                    if(choice.equals("y")) {
                        loop = false;
                    }

                    break;
                default:
                    System.out.println("选择有误，请重新选择");
            }

        }while(loop);

        System.out.println("======退出了零钱通项目======");
    }
}
