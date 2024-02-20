package com.IO.Properties;

import org.junit.Test;

import java.io.*;
import java.util.Properties;

public class DogProperties {
    public static void main(String[] args) throws IOException {
        /**
         * (1) 编写Dog类（name,age,color） 创建一个dog对象，读取dog.properties
         * （2）将创建的Dog对象， 序列化到文件 dog。dat 文件
         */
        String filePath = "untitled1\\src\\dog.properties";
        Properties properties = new Properties();
        properties.load(new FileReader(filePath));
        String name = properties.get("name") + "";//Object -> String
        int age = Integer.parseInt(properties.get("age") + "");//Object ->int
        String color = properties.get("color") + "";//Object -> String

        Dog dog = new Dog(name, age, color);
        System.out.println("====dog对象信息====");
        System.out.println(dog);

        //将创建的Dog对象， 序列化到文件 dog.dat 文件
        String serFilePath = "d:\\java\\dog.txt";
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(serFilePath));
        oos.writeObject(dog);

        //关闭流
        oos.close();
        System.out.println("dog对象，序列化完成。。。");
    }

    //编写一个方法 反序列化
    @Test
    public void m1() throws IOException, ClassNotFoundException {
        String serFilePath = "d:\\java\\dog.txt";
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(serFilePath));
        Dog dog = (Dog) ois.readObject();

        System.out.println("===反序列化后 dog===");
        System.out.println(dog);
        ois.close();
    }
}

class Dog implements Serializable {
    private String name;
    private int age;
    private String color;

    public Dog(String name, int age, String color) {
        this.name = name;
        this.age = age;
        this.color = color;
    }


    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", color='" + color + '\'' +
                '}';
    }
}
