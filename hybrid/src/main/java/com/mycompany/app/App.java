package com.mycompany.app;

public class App
{
    final private static Object lock = new Object();

    public static void main( String[] args )
    {
        class ArithmeticsAppRunnable implements Runnable{
            public void run() {
                ArithmeticsApp.main(args);
            }
        }
        final ArithmeticsAppRunnable r1 = new ArithmeticsAppRunnable();
        final Thread t1 = new Thread(r1);
        class StringHelperAppRunnable implements Runnable{
            public void run() {
                StringHelperApp.main(args);
            }
        }
        final StringHelperAppRunnable r2 = new StringHelperAppRunnable();
        final Thread t2 = new Thread(r2);

        t1.start();
        t2.start();
        System.out.println( "Hello hybrid!");

        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
