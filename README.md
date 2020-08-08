To package and execute an individual jar of "arithmetics", use
```
proj-root> ./shortcuts.sh exec_arithmetics
```

To package and execute an individual jar of "stringhelper", use
```
proj-root> ./shortcuts.sh exec_stringhelper
```

To package and execute the jar of "hybrid", use
```
proj-root> ./shortcuts.sh package_wrapped_hybrid && ./shortcuts.sh exec_hybrid
```
The module `hybrid` uses `shaded arithmetics-*.jar` and `shaded stringhelper-*.jar` as dependencies to resolve the "originally conflict entry classpath `com.mycompany.app.App`".

# About the "packaging" of "stringhelper"
Module "stringhelper" is deliberately lacking the use of "maven-jar-plugin" configured and thus NOT supposed to be packaged.

# About the incomplete example of "JMX"
This example is not meant to be complete about "JMX", though there's a minor mention of "MBeanServer". 

To walk a little out of scope, the "MBeanServer" is just part of a "JMXAgent"(originally specified in [JSR003](https://jcp.org/aboutJava/communityprocess/mrel/jsr003/index4.html)). Note that "class MBeanServer" is by default a "local-connector implement of MBeanServerConnection(irrelevant to TCP/IP)", if "remote-connector" is needed we should pair a "ConnectorServer(capable of using TCP/IP)" with it, i.e. "MBeanServer+ConnectorServer". See [JSR160](https://jcp.org/en/jsr/detail?id=160) for more information of "JMX remoting".
