package com.mycompany.hybrid;
import com.mycompany.app.ArithmeticsApp;
import com.mycompany.app.StringhelperApp;

public class HybridApp {
    final private static Object lock = new Object();

    public static void main(String[] args) {
        /**
         * The "entry classpath" of "arithmetics" and "stringhelper" shouldn't conflict!
         *
         * For example, if both
         * - "com.mycompany.app:arithmetics" and
         * - "com.mycompany.app:stringhelper"
         * contains "`classpath/method` == `com.mycompany.app.ArithmeticsApp/main`" that we expect to use in "com.mycompany.hybrid.HybridApp", then
         * it'd be better that we just refactor the conflicting "classpath/method"s, rather than using "ClassLoader tricks".
         *
         */

        /**
         * The "thread" below can be replaced by any other "detached task", e.g. "coroutine" or "actor".
         */
        class ArithmeticsAppRunnable implements Runnable {
            public void run() {
                ArithmeticsApp.main(args);
            }
        }
        final ArithmeticsAppRunnable r1 = new ArithmeticsAppRunnable();

        class StringhelperAppRunnable implements Runnable {
            public void run() {
                StringhelperApp.main(args);
            }
        }
        final StringhelperAppRunnable r2 = new StringhelperAppRunnable();

        final Thread t2 = new Thread(r2);
        t2.start();

        System.out.println("Hello hybrid!");

        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
