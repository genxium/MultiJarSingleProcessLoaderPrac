package com.mycompany.hybrid;

public class HybridApp {
    final private static Object lock = new Object();
    /**
     * The "not working approach".
     */

    public static void main(String[] args) {
        /**
         * What if the "entry classpaths" of "arithmetics" and "stringhelper" were NOT shaded respectively, thus conflicted?
         *
         * For example, if both
         * - "com.mycompany.app:arithmetics" and
         * - "com.mycompany.app:stringhelper"
         * contained "`classpath/method` == `com.mycompany.app.App/main`" which we expect to use here in "com.mycompany.hybrid.HybridApp", then we might think of the following "ClassLoader trick" -- but it doesn't work.
         *
         * The following code snippet would fail in runtime when executing "`com.mycompany.app.App/main` of `arithmetics-1.0-SNAPSHOT.jar`" in 2 cases.
         * - If "com.mycompany.hybrid:hybrid" were built with dependencies "com.mycompany.app:arithmetics" and "com.mycompany.app:stringhelper", then the "shared SpringContext" would be contaminated by the conflicting '<bean id="lib" class="com.mycompany.app.Lib" />' from both of these 2 dependencies.
         * - Otherwise the runtime would lack "imported dependencies symbols for either `com.mycompany.app:arithmetics` or `com.mycompany.app:stringhelper`" to execute correctly.
         *
         * ```
          final String currentWorkingDir = System.getProperty("user.dir");
          System.out.println("currentWorkingDir == " + currentWorkingDir);
          class ArithmeticsAppRunnable implements Runnable {
              public void run() {
                  final List<String> appCpParts1 = Arrays.asList(currentWorkingDir, "arithmetics", "target", "arithmetics-1.0-SNAPSHOT.jar");
                  final List<String> appCpParts2 = Arrays.asList(currentWorkingDir, "..", "arithmetics", "target", "arithmetics-1.0-SNAPSHOT.jar");

                  try {
                      final URL appCp1 = new File(String.join("/", appCpParts1))
                              .toURI().toURL();
                      final URL appCp2 = new File(String.join("/", appCpParts2))
                              .toURI().toURL();
                      final URL[] urls = new URL[] { appCp1, appCp2 };
                      final URLClassLoader cl = new URLClassLoader(urls,
                              this.getClass().getClassLoader());
                      final Class<?> clazz = cl.loadClass("com.mycompany.app.App");
                      final Constructor ctor = clazz.getConstructor();
                      final Object clazzIns = ctor.newInstance();
                      final Object serverArgConverted[] = { args };
                      final Class sampleArgClass[] = { (new String[1]).getClass() };
                      final Method mainMethod = clazz.getDeclaredMethod("main", sampleArgClass);
                      mainMethod.invoke(null, serverArgConverted);
                  } catch (MalformedURLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                      e.printStackTrace();
                  }
              }
          }
          final ArithmeticsAppRunnable r1 = new ArithmeticsAppRunnable();
          final Thread t1 = new Thread(r1);
          t1.start();
         * ```
         */

        /**
         * The "thread" below can be replaced by any other "detached task", e.g. "coroutine" or "actor".
         */

        System.out.println("Hello hybrid!");
        class ArithmeticsAppRunnable implements Runnable {
            public void run() {
                com.mycompany.arithmetics.app.App.main(args);
            }
        }
        final ArithmeticsAppRunnable r1 = new ArithmeticsAppRunnable();
        final Thread t1 = new Thread(r1);
        t1.start();

        class StringhelperAppRunnable implements Runnable {
            public void run() {
                com.mycompany.stringhelper.app.App.main(args);
            }
        }
        final StringhelperAppRunnable r2 = new StringhelperAppRunnable();
        final Thread t2 = new Thread(r2);
        t2.start();
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
