package com.enn.service;

import java.util.HashMap;
import java.util.Iterator;

/**
 * 该类用于管理和客户端通信的线程
 */
public class ManageClientThreads {
    private static HashMap<String, ServerConnetClientThread> hm = new HashMap<>();

    public static HashMap<String, ServerConnetClientThread> getHm() {
        return hm;
    }

    //添加线程对象到 hm集合
    public static void addClientThread(String userId, ServerConnetClientThread serverConnetClientThread) {
        hm.put(userId, serverConnetClientThread);
    }

    //根据userId 返回线程
    public static ServerConnetClientThread getServerConnetClientThread(String userId) {
        return hm.get(userId);
    }

    //从集合中，移除某个线程对象
    public static void removeServerConnectClientThread(String userId) {
        hm.remove(userId);
    }

    //编写方法，可以返回在线用户列表
    public static String getOnlineUser() {
        //遍历集合
        Iterator<String> iteger = hm.keySet().iterator();
        String onlineUserList = "";
        while (iteger.hasNext()) {
            onlineUserList += iteger.next().toString() + " ";
        }
        return onlineUserList;
    }
}
