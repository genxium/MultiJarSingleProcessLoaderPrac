package com.mycompany.app;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class App {
    final private static Object lock = new Object();

    public static void main(String[] args) {
        final InputStream inputStream = App.class.getResourceAsStream("/applicationContext.xml");
        final InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        final String content = new BufferedReader(streamReader).lines().collect(Collectors.joining("\n"));
        System.out.println("Hello arithmetics! The content of `applicationContext.xml` is \n" + content);

        final ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        ctx.refresh();
        ctx.start();
        System.out.println("Hello arithmetics! ctx.getId() is " + ctx.getId() + ", ctx.getBeanFactory().hashCode() is " + ctx.getBeanFactory().hashCode());

        final Lib libIns = (Lib) ctx.getBean("lib");

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
