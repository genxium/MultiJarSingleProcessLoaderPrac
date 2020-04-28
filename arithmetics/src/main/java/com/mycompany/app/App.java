package com.mycompany.app;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class App {
    final private static Object lock = new Object();

    public static void main(String[] args) {
        /**
         * [WARNING]
         *
         * The "configLocation" for constructing "ClassPathXmlApplicationContext" should be distinct from module to module.
         * If we had both "arithmetics-*.jar!applicationContext.xml" and "stringhelper-*.jar!applicationContext.xml" added
         * to "runtime-classpath of `hybrid-*.jar`", then only 1 "applicationContext.xml" will be effective, due to that
         * the "filepath under runtime-classpath of `hybrid-*.jar`" is constrained to be unique by JVM.
         *
         * -- YFLu, 2020-04-28
         */
        final InputStream inputStream = App.class.getResourceAsStream("/applicationContext-arithmetics.xml");
        final InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        final String content = new BufferedReader(streamReader).lines().collect(Collectors.joining("\n"));
        System.out.println("Hello arithmetics! The content of `applicationContext-arithmetics.xml` is \n" + content);

        final ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-arithmetics.xml");
        ctx.refresh();
        ctx.start();
        // System.out.println("Hello arithmetics! ctx.getId() is " + ctx.getId() + ", ctx.getBeanFactory().hashCode() is " + ctx.getBeanFactory().hashCode());
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
