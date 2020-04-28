package com.mycompany.app;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    final private static Object lock = new Object();

    public static void main(String[] args) {
        /**
         * When invoked from "com.mycompany.hybrid.HybridApp/main" together with "com.mycompany.stringhelper.app.App/main",
         * the following invocation by "Spring ApplicationContext" would fail due to contamination of '<bean id="lib" />'.
         *
         // final ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
         // final Lib libIns = (Lib) ctx.getBean("lib");
         // ctx.refresh();
         // ctx.start();
         */
        final Lib libIns = new Lib();
        final int plusResult = libIns.plus(1, 2);
        System.out.println("Hello arithmetics! Plus result is " + plusResult);
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
