package com.mycompany.app;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StringhelperApp
{
    final private static Object lock = new Object();
    public static void main( String[] args )
    {
        final ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        final StringhelperLib libIns = (StringhelperLib) ctx.getBean("lib");
        final String reversedStr = libIns.reverse("foobar");
        System.out.println("Hello stringhelper! Reversed string result is " + reversedStr);
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
