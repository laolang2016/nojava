图标颜色

默认: `#9f9f9f`
激活: `#4772fa`

build windows

```
cmake -S . -B build/gnu-debug -DCMAKE_BUILD_TYPE=Debug -DCMAKE_PREFIX_PATH="D:/program/qt/6.5.3/mingw_64" -G "MinGW Makefiles"
cmake -S . -B build/gnu-release -DCMAKE_BUILD_TYPE=Release -DCMAKE_PREFIX_PATH="D:/program/qt/6.5.3/mingw_64" -G "MinGW Makefiles"
```

build linux