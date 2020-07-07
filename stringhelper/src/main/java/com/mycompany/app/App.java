package com.mycompany.app;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.management.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class App {
    final private static Object lock = new Object();

    public static void main(String[] args) {
        final InputStream inputStream = App.class.getResourceAsStream("/applicationContext-stringhelper.xml");
        final InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        final String content = new BufferedReader(streamReader).lines().collect(Collectors.joining("\n"));
        System.out.println("Hello stringhelper! The content of `applicationContext-stringhelper.xml` is \n" + content);

        final ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-stringhelper.xml");
        ctx.refresh();
        ctx.start();
        // System.out.println("Hello stringhelper! ctx.getId() is " + ctx.getId() + ", ctx.getBeanFactory().hashCode() is " + ctx.getBeanFactory().hashCode());
        final Lib libIns = (Lib) ctx.getBean("lib");

        final String reversedStr = libIns.reverse("foobar");
        System.out.println("Hello stringhelper! Reversed string result is " + reversedStr);

        // Making this "stringhelper" process/daemon an "MBeanServer", see https://docs.oracle.com/javase/tutorial/jmx/mbeans/standard.html for more information.
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = null; // "Package:type=ClassName"
        try {
            name = new ObjectName("com.mycompany.app:type=Lib");
            mbs.registerMBean(libIns, name);
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        } catch (NotCompliantMBeanException e) {
            e.printStackTrace();
        } catch (InstanceAlreadyExistsException e) {
            e.printStackTrace();
        } catch (MBeanRegistrationException e) {
            e.printStackTrace();
        }

        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
