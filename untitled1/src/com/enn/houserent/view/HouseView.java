package com.enn.houserent.view;

import com.enn.houserent.domain.House;
import com.enn.houserent.service.HouseService;
import com.enn.houserent.utils.Utility;

/**
 * 1.显示界面
 * 2.接收用户输入
 * 3.调用HouseService完成对房屋信息的各种操作
 */
public class HouseView {
    private boolean loop = true;//控制显示菜单
    private char key = ' ';//接收用户输入
    private HouseService houseService = new HouseService(10);//设置数组大小


    //根据id修改房屋信息
    public void update() {
        System.out.println("\n===========修改房屋信息===========");
        System.out.println("请输入要修改的房屋id（-1退出）:");
        int updateId = Utility.readInt();
        if (updateId == -1) {
            return;
        }
        //返回的是引用类型
        House house = houseService.findById(updateId);
        if (house == null) {
            System.out.println("==========编号不存在=========");
            return;
        }
        System.out.println("姓名(" + house.getName() + "):");
        String name = Utility.readString(8, "");
        if (!"".equals(name)) {//修改
            house.setName(name);
        }
        System.out.println("电话(" + house.getPhone() + "):");
        String phone = Utility.readString(12);
        if (!"".equals(phone)) {//修改
            house.setPhone(phone);
        }
        System.out.println("地址(" + house.getAddress() + "):");
        String address = Utility.readString(18);
        if (!"".equals(address)) {//修改
            house.setAddress(address);
        }
        System.out.println("租金(" + house.getRent() + "):");
        int rent = Utility.readInt();
        if (rent != -1) {//修改
            house.setRent(rent);
        }
        System.out.println("状态(" + house.getName() + "):");
        String state = Utility.readString(3, "");
        if (!"".equals(state)) {//修改
            house.setState(state);
        }
        System.out.println("==========修改房屋信息成功==========");
    }

    //根据id查找房屋信息
    public void findHouse() {
        System.out.println("\n===========查找房屋信息===========");
        System.out.println("请输入要查找的房屋id:");
        int findId = Utility.readInt();
        //调用方法
        House house = houseService.findById(findId);
        if (house != null) {
            System.out.println(house);
        } else {
            System.out.println("=========查找房屋信息id不存在========");
        }
    }

    //编写delHouse（）接收输入的id，调用Service 的 del方法
    public void delHouse() {
        System.out.println("\n===========删除房屋信息===========");
        System.out.println("请输入待删除房屋编号（-1退出）:");
        int delId = Utility.readInt();
        if (delId == -1) {
            System.out.println("============放弃删除房屋信息==========");
            return;
        }
        //该方法本身就有循环判断的逻辑
        char choice = Utility.readConfirmSelection();
        if (choice == 'Y') {
            if (houseService.del(delId)) {
                System.out.println("============删除房屋信息成功==========");
            } else {
                System.out.println("========房屋编号不存在，删除失败=========");
            }
        } else {
            System.out.println("============放弃删除房屋信息==========");
        }
    }

    //接收输入，创建House对象，调用add方法
    public void addHouse() {
        System.out.println("\n===========添加房屋信息===========");
        System.out.println("姓名：");
        String name = Utility.readString(8);
        System.out.println("电话：");
        String phone = Utility.readString(12);
        System.out.println("地址：");
        String address = Utility.readString(16);
        System.out.println("月租：");
        int rent = Utility.readInt();
        System.out.println("状态：");
        String state = Utility.readString(3);
        //创建一个新的House对象
        House newHouse = new House(0, name, phone, address, rent, state);
        if (houseService.add(newHouse)) {
            System.out.println("\n===========添加房屋成功===========");
        } else {
            System.out.println("\n===========添加房屋失败===========");
        }
    }

    //显示房屋列表
    public void listHouse() {
        System.out.println("\n============房屋列表============");
        System.out.println("编号\t\t房主\t\t电话\t\t地址\t\t月租\t\t状态");
        House[] houses = houseService.list();//得到所有房屋信息
        for (int i = 0; i < houses.length; i++) {
            if (houses[i] == null) {
                break;
            }
            System.out.println(houses[i]);
        }
        System.out.println("\n==========房屋列表显示完毕==========");
    }

    //显示主菜单
    public void mainMenu() {
        do {
            System.out.println("\n==========房屋出租系统菜单==========");
            System.out.println("\t\t\t1 新 增 房 源");
            System.out.println("\t\t\t2 查 找 房 源");
            System.out.println("\t\t\t3 删 除 房 屋 信 息");
            System.out.println("\t\t\t4 修 改 房 屋 信 息");
            System.out.println("\t\t\t5 房 屋 列 表");
            System.out.println("\t\t\t6 退      出");
            System.out.println("请输入你的选择（1-6）:");
            key = Utility.readChar();

            switch (key) {
                case '1':
                    this.addHouse();
                    break;
                case '2':
                    System.out.println("编号\t\t房主\t\t电话\t\t地址\t\t月租\t\t状态");
                    this.findHouse();
                    break;
                case '3':
                    this.delHouse();
                    break;
                case '4':
                    this.update();
                    break;
                case '5':
                    this.listHouse();
                    break;
                case '6':
                    System.out.println("新增");
                    loop = false;
                    break;
            }
        } while (loop);
    }
}
