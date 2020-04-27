package com.mycompany.app;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ArithmeticsApp {
    final private static Object lock = new Object();
    public static void main(String[] args) {
        final ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        final ArithmeticsLib libIns = (ArithmeticsLib) ctx.getBean("lib");
        final int plusResult = libIns.plus(1, 2);
        System.out.println("Hello arithmetics! Plus result is " + plusResult);
        ctx.refresh();
        ctx.start();
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
