package com.IO.copy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopy {
    //完成文件拷贝
    //1.将文件读入到程序
    //2.将读取的文件内容 写入到指定的文件
    public static void main(String[] args) {
        String srcfilePath = "d:\\java\\tupian.jpg";
        String destfilePath = "d:\\java\\tupian2.jpg";
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;

        try {
            fileInputStream = new FileInputStream(srcfilePath);
            fileOutputStream = new FileOutputStream(destfilePath);
            //定义一个字节数组，提高读取效果
            byte[] buf = new byte[1024];
            int readLen = 0;
            while((readLen = fileInputStream.read(buf)) != -1) {
                fileOutputStream.write(buf,0,readLen);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {//关闭输入，输出流
            try {
                if(fileInputStream != null) {
                    fileInputStream.close();
                }
                if(fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
