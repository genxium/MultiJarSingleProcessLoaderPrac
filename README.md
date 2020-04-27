To package and execute an individual jar of "arithmetics", use
```
proj-root> ./shortcuts.sh package_every_individual_subproject && ./shortcuts.sh exec_arithmetics
```

To package and execute an individual jar of "stringhelper", use
```
proj-root> ./shortcuts.sh package_every_individual_subproject && ./shortcuts.sh exec_stringhelper
```

To package and execute the jar of "hybrid", use
```
proj-root> ./shortcuts.sh package_wrapped_hybrid && ./shortcuts.sh exec_hybrid
```
The module `hybrid` uses `shaded arithmetics-*.jar` and `shaded stringhelper-*.jar` as dependencies to resolve the "originally conflict entry classpath `com.mycompany.app.App`".
