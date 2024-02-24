package com.enn.service;

import com.enn.qqcommon.Message;
import com.enn.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 该类对应的对象和某个客户端保持通信
 */
public class ServerConnetClientThread extends Thread {
    private Socket socket;
    private String userId;//连接到服务器端的用户id

    public ServerConnetClientThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }
    public Socket getSocket() {
        return socket;
    }
    @Override
    public void run() {//这里线程处于run状态，可以发送/接收消息
        while (true) {
            try {
                System.out.println("服务端和客户端" + userId + " 保持通信，读取数据...");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                //根据message类型，做相应的业务处理
                if (message.getMesType().equals(MessageType.MESSAGE_GET_ONLIEND_FRIEND)) {
                    System.out.println(message.getSender() + "要在线用户列表");
                    String onlineUser = ManageClientThreads.getOnlineUser();
                    //返回message 对象，返回客户端
                    Message message2 = new Message();
                    message2.setMesType(MessageType.MESSAGE_RET_ONLIEND_FRIEND);
                    message2.setContent(onlineUser);
                    message2.setGetter(message.getSender());
                    //返回给客户端
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message2);
                } else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {
                    //根据message获取getter id ，然后得到对应线程
                    ServerConnetClientThread serverConnetClientThread =
                            ManageClientThreads.getServerConnetClientThread(message.getGetter());
                    //再得到对应socket的对象输出流，将message对象转发给指定的客户端
                    ObjectOutputStream oos = new ObjectOutputStream(serverConnetClientThread.getSocket().getOutputStream());
                    oos.writeObject(message);//转发，如果客户不在线，可以保存到数据库，实现离线留言
                } else if (message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)) {
                    //需要遍 管理线程的集合
                    HashMap<String, ServerConnetClientThread> hm = ManageClientThreads.getHm();
                    Iterator<String> iterator = hm.keySet().iterator();
                    while(iterator.hasNext()) {
                        //取出在线用户Id
                        String onLineUserId = iterator.next().toString();

                        if(!onLineUserId.equals(message.getSender())) {//排除发消息的用户
                            //进行转发
                            ObjectOutputStream oos = new ObjectOutputStream(hm.get(onLineUserId).getSocket().getOutputStream());
                            oos.writeObject(message);
                        }
                    }
                } else if (message.getMesType().equals(MessageType.MESSAGE_FILE_MES)) {
                    //根据getter id 获取到对应的线程， 将message对象转发
                    ServerConnetClientThread serverConnetClientThread = ManageClientThreads.getServerConnetClientThread(message.getGetter());
                    ObjectOutputStream oos = new ObjectOutputStream(serverConnetClientThread.getSocket().getOutputStream());
                    //转发
                    oos.writeObject(message);
                } else if (message.getMesType().equals(MessageType.MESSAGE_CLIENT_EXIT)) {//客户端退出
                    System.out.println(message.getSender() + " 退出");
                    //将客户端对应线程，从集合中删除
                    ManageClientThreads.removeServerConnectClientThread(message.getSender());
                    socket.close();
                    //退出
                    break;
                } else {
                    System.out.println("其他类型的message");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
