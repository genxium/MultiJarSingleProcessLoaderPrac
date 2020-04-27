package com.mycompany.hybrid;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;

public class HybridApp {
    final private static Object lock = new Object();

    public static void main(String[] args) {
        /**
         * We couldn't invoke "com.mycompany.app.HybridApp" of "arithmetics" and "stringhelper" directly by the "classpath/method" specifier due to the obvious conflict.
         * The approach here makes use of the difference of their "artifactId"s.
         */

        /**
         * The "thread" below can be replaced by any other "detached task", e.g. "coroutine" or "actor".
         */
        class ArithmeticsAppRunnable implements Runnable {
            public void run() {
                final String currentWorkingDir = System.getProperty("user.dir");
                final List<String> arithmeticsLocationParts = Arrays.asList(currentWorkingDir, "snapshot-repo", "com", "mycompany", "app", "arithmetics", "1.0-SNAPSHOT", "arithmetics-1.0-20200427.051409-8.jar");
                final String arithmeticsJarLocation = "file:///" + String.join(File.pathSeparator, arithmeticsLocationParts);

                try {
                    final URL jarUrl = new URL(arithmeticsJarLocation);
                    final URLClassLoader clzLoader = URLClassLoader.newInstance(new URL[]{jarUrl});
                    final Class<?> clazz = clzLoader.loadClass("com.mycompany.app.App");
                    final Class sampleArgClass[] = { (new String[1]).getClass() };
                    final Method mainMethod = clazz.getDeclaredMethod("main", sampleArgClass);
                    mainMethod.invoke(null, args);
                } catch (MalformedURLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        final ArithmeticsAppRunnable r1 = new ArithmeticsAppRunnable();
        final Thread t1 = new Thread(r1);
        t1.start();

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
