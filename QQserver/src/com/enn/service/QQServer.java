package com.enn.service;

import com.enn.qqcommon.Message;
import com.enn.qqcommon.MessageType;
import com.enn.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 这是服务端，在监听9999 ，等待客户端连接，并保持通信
 */
public class QQServer {
    private ServerSocket ss= null;
    //创建一个集合，存放多个用户，如果是这些用户登入，就认为是合法
    // ConcurrentHashMap 处理线程安全，即线程同步处理，在多线程情况下是安全的
    //HashMap 没有处理线程安全
    private static ConcurrentHashMap<String, User> validUsers = new ConcurrentHashMap<>();
    static {//在静态代码块， 初始化 validUsers
        validUsers.put("100",new User("100","123456"));
        validUsers.put("200",new User("200","123456"));
        validUsers.put("300",new User("300","123456"));
    }
    //验证用户是否有效的方法
    private boolean checkUser(String userId,String passwd) {
        User user = validUsers.get(userId);
        if(user == null) {//说明userId没有存在ValidUsers 的key中
            return false;
        }
        if(!user.getPasswd().equals(passwd)) {//userId正确，但是密码错误
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        new QQServer();
    }
    public QQServer() {
        //注意：端口可以写在配置文件
        try {
            System.out.println("服务端在9999端口监听...");
            //启动推送新闻的线程
            new Thread(new SendNewsToAllService()).start();
            ss = new ServerSocket(9999);
            while(true) {//当和某个客户端连接后，会继续监听
                Socket socket = ss.accept();//如果没有客户端连接，就会阻塞在这里
                //得到socket关联的对象输入流
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                //得到socket关流的对象输出流
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                User u = (User)ois.readObject();//读取客户端发送的对象
                //创建一个Message对象，准备回复客户端
                Message message = new Message();
                if(checkUser(u.getUserId(),u.getPasswd())) {//登入通过
                    message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    //将message对象回复
                    oos.writeObject(message);
                    //创建一个线程，和客户端保持通信，该线程该持有socket对象
                    ServerConnetClientThread scct = new ServerConnetClientThread(socket, u.getUserId());
                    scct.start();
                    //把线程对象，放入到一个集合中，进行管理
                    ManageClientThreads.addClientThread(u.getUserId(), scct);
                }else {//登入失败
                    System.out.println("用户 id="+u.getUserId()+" pwd="+u.getPasswd()+" 验证失败");
                    message.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
                    oos.writeObject(message);
                    socket.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            //如果服务器退出while 说明服务器端不在监听
            try {
                ss.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
