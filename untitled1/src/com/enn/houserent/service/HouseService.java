package com.enn.houserent.service;

import com.enn.houserent.domain.House;

/**
 * 定义House[],保存House对象
 * 1.响应HouseView的调用
 * 2.完成（增删改查c[create]r[read]u[update]d[delete]）
 */
public class HouseService {
    private House[] houses;//保存House对象
    private int houseNums = 1;//记录有多少个房屋信息
    private int idCount = 1;
    public HouseService(int size) {
        houses = new House[size];//指定数组大小

        houses[0]=new House(1,"1","1","a",2,"未");
    }

    //find方法
    public House findById(int findId) {
        for(int i = 0; i < houseNums; i++){
            if(findId == houses[i].getId()) {
                return houses[i];
            }
        }
        return null;
    }

    //del方法
    public boolean del(int delId) {
        //应先找到删除的房屋信息对应下标
        int index = -1;
        for(int i = 0; i < houseNums; i++){
            if(delId == houses[i].getId()) {
                index = i;
            }
        }

        if(index == -1) {
            return false;
        }
        for(int i = index; i < houseNums - 1; i++){
            houses[i] = houses[i+1];
        }
        houses[--houseNums]=null;
        return true;
    }
    public boolean add(House newHouse) {
        if(houseNums == houses.length) {
            System.out.println("不能再添加了");
            return false;
        }
        houses[houseNums++] = newHouse;
        newHouse.setId(++idCount);
        return true;
    }
    //list方法，返回houses
    public House[] list() {
        return houses;
    }
}
