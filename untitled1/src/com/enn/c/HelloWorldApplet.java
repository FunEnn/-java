package com.enn.c;


/*
 * @author Enn
 * @version 1.0
 *
 **/

import java.applet.Applet;
import java.awt.*;

public class HelloWorldApplet extends Applet
{
    private String name;

    public HelloWorldApplet(String name) throws HeadlessException {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {

        System.out.println();
    }
}