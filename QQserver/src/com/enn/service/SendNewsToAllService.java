package com.enn.service;

import com.enn.qqcommon.Message;
import com.enn.qqcommon.MessageType;
import com.enn.utils.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class SendNewsToAllService implements Runnable {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void run() {

        while (true) {
            System.out.println("请输入服务器要推送的新闻/消息[输入exit 表示退出推送服务]");
            String news = Utility.readString(100);
            if("exit".equals(news)) break;
            //构建一个消息，群发消息
            Message message = new Message();
            message.setSender("服务器");
            message.setMesType(MessageType.MESSAGE_TO_ALL_MES);
            message.setContent(news);
            message.setSendTime(new Date().toString());
            System.out.println("服务器推送消息给所有人 说: " + news);

            //遍历当前所有线程， 得到socket， 发送消息
            HashMap<String, ServerConnetClientThread> hm = ManageClientThreads.getHm();
            Iterator<String> iterator = hm.keySet().iterator();
            while (iterator.hasNext()) {
                String onLineUserId = iterator.next().toString();
                try {
                    ServerConnetClientThread serverConnetClientThread = hm.get(onLineUserId);
                    ObjectOutputStream oos = new ObjectOutputStream(serverConnetClientThread.getSocket().getOutputStream());
                    oos.writeObject(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
