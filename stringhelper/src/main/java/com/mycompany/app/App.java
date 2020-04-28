package com.mycompany.app;

public class App
{
    final private static Object lock = new Object();
    public static void main( String[] args )
    {
        /**
         * When invoked from "com.mycompany.hybrid.HybridApp/main" together with "com.mycompany.arithmetics.app.App/main",
         * the following invocation by "Spring ApplicationContext" would fail due to contamination of '<bean id="lib" />'.
         *
         // final ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
         // final Lib libIns = (Lib) ctx.getBean("lib");
         // ctx.refresh();
         // ctx.start();
         */
        final Lib libIns = new Lib();
        final String reversedStr = libIns.reverse("foobar");
        System.out.println("Hello stringhelper! Reversed string result is " + reversedStr);
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
