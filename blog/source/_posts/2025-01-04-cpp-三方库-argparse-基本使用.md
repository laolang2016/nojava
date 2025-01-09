---
title: C++ 三方库 argparse 基本使用
date: 2025-01-04 19:12:09
categories:
- 编译原理
tags:
- 编译原理
- C++
- 命令行参数解析
---

# 参考资料

[https://github.com/p-ranav/argparse](https://github.com/p-ranav/argparse)

# 亮点

* 单头文件
* 需要 **`C++ 17`**
* MIT 协议

# 使用说明

只需要包含 `argparse.hpp` 文件即可

```c++
#include <argparse/argparse.hpp>
```

开始解析命令行之前, 需要创建一个 `ArgumentParser` 实例

```c++
argparse::ArgumentParser program("程序名称");
```

**注意:** `ArgumentParser` 构造函数的第二个可选参数可以指定程序的版本, 例如:

```c++
argparse::ArgumentParser program("graver", "0.0.1");
```

**注意:** `ArgumentParser` 构造函数的第三个可选参数可以设置默认参数. 例如:

```c++
argparse::ArgumentParser program("graver", "0.0.1", argparse::default_arguments::help, false);
```

可参考下面的 **默认参数** 一节

可以通过调用 `.add_argument(...)` 添加命令行参数. 可以添加多个参数名称, 例如:

```c++
program.add_argument("-v", "--verbose")
```

`Argparse` 支持多种参数类型, 包括位置参数、可选参数和复合参数. 下面开始展示如何配置每种参数

# 位置参数

## 代码

> `scan<'i', int>()` 可以将输入转换为十进制的数字, 具体使用参考下面的 **转换为数字类型** 一节

```c++
#include <argparse/argparse.hpp>

int main(int argc, char* argv[]) {
    argparse::ArgumentParser program("graver");

    program.add_argument("square").help("输出数字的平方").scan<'i', int>();

    try {
        program.parse_args(argc, argv);
    } catch (const std::exception& err) {
        std::cerr << err.what() << std::endl;
        std::cerr << program;
        return 1;
    }

    auto input = program.get<int>("square");
    std::cout << (input * input) << std::endl;

    return 0;
}
```

## 运行效果

```
laolang@laolang-mint:bin$ ./graver -h
Usage: graver [--help] [--version] square

Positional arguments:
  square         输出数字的平方 

Optional arguments:
  -h, --help     shows help message and exits 
  -v, --version  prints version information and exits 
laolang@laolang-mint:bin$ ./graver 1
1
laolang@laolang-mint:bin$ ./graver 2
4
laolang@laolang-mint:bin$ ./graver 5
25
laolang@laolang-mint:bin$ 
```

## 解释

* `add_argument()` 用于指定命令行参数名称
* 命令行参数时字符串
* `program.get<T>(key)` 可以获取参数值并指定泛型 `T`

# 可选参数

可选参数一 `-` 或 `--` 开头, 例如 `-v` 或者 `--input`. 可选参数可以出现在任何位置

**代码**

```c++
// SPDX-License-Identifier: MIT

#include <argparse/argparse.hpp>

int main(int argc, char *argv[]) {
    argparse::ArgumentParser program("graver");

    program.add_argument("square").help("输出数字的平方").scan<'i', int>();

    program.add_argument("--verbose").help("输出详细信息").default_value(false).implicit_value(true);

    try {
        program.parse_args(argc, argv);
    } catch (const std::exception &err) {
        std::cerr << err.what() << std::endl;
        std::cerr << program;
        return 1;
    }

    int input = program.get<int>("square");

    if (program["--verbose"] == true) {
        std::cout << input << " 的平方是 " << (input * input) << std::endl;
    } else {
        std::cout << (input * input) << std::endl;
    }
}
```

**运行效果**

```
laolang@laolang-mint:bin$ ./graver -h
Usage: graver [--help] [--version] [--verbose] square

Positional arguments:
  square         输出数字的平方 

Optional arguments:
  -h, --help     shows help message and exits 
  -v, --version  prints version information and exits 
  --verbose      输出详细信息 
laolang@laolang-mint:bin$ ./graver 5
25
laolang@laolang-mint:bin$ ./graver 5 --verbose
5 的平方是 25
laolang@laolang-mint:bin$ 
```

**解释**

* `--verbose` 是一个可选参数, 所以不指定 `--verbose` 参数时并不会报错. 
* `.default_value(false)` 指定了用户不指定该参数时的默认值
* `implicit_value(true)` 指定此选项是一个标志参数, 当提供了此参数未指定值时, 此参数值为 `true`. 实际上与下面的代码表现一致
    ```
    program.add_argument("--verbose").help("输出详细信息").flag();
    ```

## 标志

上面的 `default_value(false).implicit_value(true)` 的简写方式就是 `flag()`

## 必选参数

对于可选参数, 可以使用 `required()` 指定. 如果没有提供必选参数, 则程序会异常退出.

### 代码

```c++
// SPDX-License-Identifier: MIT

#include <argparse/argparse.hpp>

int main(int argc, char *argv[]) {
    argparse::ArgumentParser program("graver");

    program.add_argument("-o", "--output").required().help("指定输出文件");

    try {
        program.parse_args(argc, argv);
    } catch (const std::exception &err) {
        std::cerr << err.what() << std::endl;
        std::cerr << program;
        return 1;
    }

    std::cout << "输出文件是:" << program.get("-o") << "\n";
}
```

### 输出

```
laolang@laolang-mint:bin$ ./graver -h
Usage: graver [--help] [--version] --output VAR

Optional arguments:
  -h, --help     shows help message and exits 
  -v, --version  prints version information and exits 
  -o, --output   指定输出文件 [required]
laolang@laolang-mint:bin$ ./graver
-o: required.
Usage: graver [--help] [--version] --output VAR

Optional arguments:
  -h, --help     shows help message and exits 
  -v, --version  prints version information and exits 
  -o, --output   指定输出文件 [required]
laolang@laolang-mint:bin$ echo $?
1
laolang@laolang-mint:bin$ ./graver -o one.txt
输出文件是:one.txt
laolang@laolang-mint:bin$ echo $?
0
laolang@laolang-mint:bin$ 
```

## 访问没有默认值的可选参数

如果需要访问没有提供默认值的参数, 可以使用如下方式

### 代码

```c++
// SPDX-License-Identifier: MIT

#include <argparse/argparse.hpp>

int main(int argc, char *argv[]) {
    argparse::ArgumentParser program("graver");

    program.add_argument("--output").help("输出文件");

    try {
        program.parse_args(argc, argv);
    } catch (const std::exception &err) {
        std::cerr << err.what() << std::endl;
        std::cerr << program;
        return 1;
    }

    std::string outputFile;

    auto outputOptional = program.present<std::string>("--output");

    if (outputOptional.has_value()) {
        outputFile = *outputOptional;
        std::cout << "用户指定 --output 参数为:" << outputFile << std::endl;
    } else {
        std::cout << "用户未提供 --output 参数, 输出文件为 a.out" << std::endl;
        outputFile = "a.out";
    }

    std::cout << "输出文件:" << outputFile << std::endl;

    return 0;
}
```

### 输出

```
laolang@laolang-mint:bin$ ./graver 
用户未提供 --output 参数, 输出文件为 a.out
输出文件:a.out
laolang@laolang-mint:bin$ ./graver --output hello.exe
用户指定 --output 参数为:hello.exe
输出文件:hello.exe
laolang@laolang-mint:bin$ 
```


### 解释

`present<T>(key)` 方法返回的是 `std::optional<T>`

## 用户是否指定过参数值

可以使用 `.is_used()` 判断用户是否为使用 `.default_value` 指定了默认值的命令行参数指定了自定义参数值, 哪怕用户指定的值和程序指定的默认值一致

### 代码

```c++
// SPDX-License-Identifier: MIT

#include <argparse/argparse.hpp>

int main(int argc, char *argv[]) {
    argparse::ArgumentParser program("graver");

    program.add_argument("--output").default_value("a.out").help("输出文件");

    try {
        program.parse_args(argc, argv);
    } catch (const std::exception &err) {
        std::cerr << err.what() << std::endl;
        std::cerr << program;
        return 1;
    }

    auto outputFile   = program.get<std::string>("--output");
    auto outputIsUsed = program.is_used("--output");

    if (outputIsUsed) {
        std::cout << "用户指定 --output 参数为:" << outputFile << std::endl;
    } else {
        std::cout << "用户未提供 --output 参数, 输出文件为 a.out" << std::endl;
    }

    std::cout << "输出文件:" << outputFile << std::endl;

    return 0;
}
```

### 输出

```
laolang@laolang-mint:bin$ ./graver 
用户未提供 --output 参数, 输出文件为 a.out
输出文件:a.out
laolang@laolang-mint:bin$ ./graver --output a.out
用户指定 --output 参数为:a.out
输出文件:a.out
laolang@laolang-mint:bin$ ./graver --output hello.exe
用户指定 --output 参数为:hello.exe
输出文件:hello.exe
laolang@laolang-mint:bin$ 
```


## 可重复可选参数

使用 `append()` 方法指定参数可重复

### 代码

```c++
// SPDX-License-Identifier: MIT

#include <argparse/argparse.hpp>
#include <vector>

int main(int argc, char *argv[]) {
    argparse::ArgumentParser program("graver");

    program.add_argument("--input").default_value(std::vector<std::string>({"main.txt"})).append().help("输入文件");

    try {
        program.parse_args(argc, argv);
    } catch (const std::exception &err) {
        std::cerr << err.what() << std::endl;
        std::cerr << program;
        return 1;
    }

    auto inputFiles = program.get<std::vector<std::string>>("--input");
    if (inputFiles.empty()) {
        std::cout << "没有输入文件" << std::endl;
    } else {
        std::cout << "输入文件列表:" << std::endl;
        for (const auto &f : inputFiles) {
            std::cout << f << std::endl;
        }
    }

    return 0;
}
```

### 输出

```
laolang@laolang-mint:bin$ ./graver --input a.txt --input b.txt
输入文件列表:
a.txt
b.txt
laolang@laolang-mint:bin$ ./graver
输入文件列表:
main.txt
laolang@laolang-mint:bin$ 
```

### 解释

**注意:** 此时 `.default_value` 需要一个明确的模板参数匹配 `get` 类型

## Repeating an argument to increase a value

A common pattern is to repeat an argument to indicate a greater value.

```cpp
int verbosity = 0;
program.add_argument("-V", "--verbose")
  .action([&](const auto &) { ++verbosity; })
  .append()
  .default_value(false)
  .implicit_value(true)
  .nargs(0);

program.parse_args(argc, argv);    // Example: ./main -VVVV

std::cout << "verbose level: " << verbosity << std::endl;    // verbose level: 4
```

## 互斥参数组

可以使用 `program.add_mutually_exclusive_group(required = false)` 创建一个互斥参数组, `argparse` 将保证命令行中只有一个互斥组中的参数

### 代码

```c++
// SPDX-License-Identifier: MIT

#include <argparse/argparse.hpp>

int main(int argc, char *argv[]) {
    argparse::ArgumentParser program("graver");

    program.add_argument("--first");
    program.add_argument("--second");

    auto &group = program.add_mutually_exclusive_group();
    group.add_argument("--first");
    group.add_argument("--second");

    try {
        program.parse_args(argc, argv);
    } catch (const std::exception &err) {
        std::cerr << err.what() << std::endl;
        std::cerr << program;
        return 1;
    }

    return 0;
}
```

### 输出

```
laolang@laolang-mint:bin$ ./graver --first 1 --second 2
Argument '--second VAR' not allowed with '--first VAR'
Usage: graver [--help] [--version] [--first VAR] [--second VAR] [[--first VAR]|[--second VAR]]

Optional arguments:
  -h, --help     shows help message and exits 
  -v, --version  prints version information and exits 
  --first        
  --second       
  --first        
  --second       
laolang@laolang-mint:bin$ 
```

### 译注

建议自行控制参数的分组. 例如对于如下程序

```c++
// SPDX-License-Identifier: MIT

#include <argparse/argparse.hpp>

int main(int argc, char *argv[]) {
    argparse::ArgumentParser program("graver");

    program.add_argument("--first").flag();
    program.add_argument("--second").flag();

    auto &group = program.add_mutually_exclusive_group();
    group.add_argument("--first");
    group.add_argument("--second");

    try {
        program.parse_args(argc, argv);
    } catch (const std::exception &err) {
        std::cerr << err.what() << std::endl;
        std::cerr << program;
        return 1;
    }

    return 0;
}
```

运行会报错

```
laolang@laolang-mint:bin$ ./graver --first --second
Too few arguments for '--first'.
Usage: graver [--help] [--version] [--first] [--second] [[--first VAR]|[--second VAR]]

Optional arguments:
  -h, --help     shows help message and exits 
  -v, --version  prints version information and exits 
  --first        
  --second       
  --first        
  --second       
laolang@laolang-mint:bin$ 
```

# Storing values into variables

It is possible to bind arguments to a variable storing their value, as an
alternative to explicitly calling ``program.get<T>(arg_name)`` or ``program[arg_name]``

This is currently implementeted for variables of type ``bool`` (this also
implicitly calls ``flag()``), ``int``, ``double``, ``std::string``,
``std::vector<std::string>`` and ``std::vector<int>``.
If the argument is not specified in the command
line, the default value (if set) is set into the variable.

```cpp
bool flagvar = false;
program.add_argument("--flagvar").store_into(flagvar);

int intvar = 0;
program.add_argument("--intvar").store_into(intvar);

double doublevar = 0;
program.add_argument("--doublevar").store_into(doublevar);

std::string strvar;
program.add_argument("--strvar").store_into(strvar);

std::vector<std::string> strvar_repeated;
program.add_argument("--strvar-repeated").append().store_into(strvar_repeated);

std::vector<std::string> strvar_multi_valued;
program.add_argument("--strvar-multi-valued").nargs(2).store_into(strvar_multi_valued);

std::vector<int> intvar_repeated;
program.add_argument("--intvar-repeated").append().store_into(intvar_repeated);

std::vector<int> intvar_multi_valued;
program.add_argument("--intvar-multi-valued").nargs(2).store_into(intvar_multi_valued);
```

# Negative Numbers

Optional arguments start with ```-```. Can ```argparse``` handle negative numbers? The answer is yes!

```cpp
argparse::ArgumentParser program;

program.add_argument("integer")
  .help("Input number")
  .scan<'i', int>();

program.add_argument("floats")
  .help("Vector of floats")
  .nargs(4)
  .scan<'g', float>();

try {
  program.parse_args(argc, argv);
}
catch (const std::exception& err) {
  std::cerr << err.what() << std::endl;
  std::cerr << program;
  std::exit(1);
}

// Some code to print arguments
```

```console
foo@bar:/home/dev/$ ./main -5 -1.1 -3.1415 -3.1e2 -4.51329E3
integer : -5
floats  : -1.1 -3.1415 -310 -4513.29
```

As you can see here, ```argparse``` supports negative integers, negative floats and scientific notation.


# 结合使用位置参数和可选参数

## 代码

```c++
argparse::ArgumentParser program("main");

program.add_argument("square")
  .help("display the square of a given number")
  .scan<'i', int>();

program.add_argument("--verbose")
  .default_value(false)
  .implicit_value(true);

try {
  program.parse_args(argc, argv);
}
catch (const std::exception& err) {
  std::cerr << err.what() << std::endl;
  std::cerr << program;
  std::exit(1);
}

int input = program.get<int>("square");

if (program["--verbose"] == true) {
  std::cout << "The square of " << input << " is " << (input * input) << std::endl;
}
else {
  std::cout << (input * input) << std::endl;
}
```

## 效果

```console
foo@bar:/home/dev/$ ./main 4
16

foo@bar:/home/dev/$ ./main 4 --verbose
The square of 4 is 16

foo@bar:/home/dev/$ ./main --verbose 4
The square of 4 is 16
```

# Printing Help

`std::cout << program` prints a help message, including the program usage and information about the arguments registered with the `ArgumentParser`. For the previous example, here's the default help message:

```
foo@bar:/home/dev/$ ./main --help
Usage: main [-h] [--verbose] square

Positional arguments:
  square       	display the square of a given number

Optional arguments:
  -h, --help   	shows help message and exits
  -v, --version	prints version information and exits
  --verbose
```

You may also get the help message in string via `program.help().str()`.

## Adding a description and an epilog to help

`ArgumentParser::add_description` will add text before the detailed argument
information. `ArgumentParser::add_epilog` will add text after all other help output.

```cpp
#include <argparse/argparse.hpp>

int main(int argc, char *argv[]) {
  argparse::ArgumentParser program("main");
  program.add_argument("thing").help("Thing to use.").metavar("THING");
  program.add_argument("--member").help("The alias for the member to pass to.").metavar("ALIAS");
  program.add_argument("--verbose").default_value(false).implicit_value(true);

  program.add_description("Forward a thing to the next member.");
  program.add_epilog("Possible things include betingalw, chiz, and res.");

  program.parse_args(argc, argv);

  std::cout << program << std::endl;
}
```

```console
Usage: main [-h] [--member ALIAS] [--verbose] THING

Forward a thing to the next member.

Positional arguments:
  THING         	Thing to use.

Optional arguments:
  -h, --help    	shows help message and exits
  -v, --version 	prints version information and exits
  --member ALIAS	The alias for the member to pass to.
  --verbose

Possible things include betingalw, chiz, and res.
```


# 接收多个值的参数

一般来讲, `ArgumentParser` 将命令行参数与参数值一一对应. `nargs` 可以将多个参数值与命令行参数对应起来

## 代码

```c++
// SPDX-License-Identifier: MIT

#include <argparse/argparse.hpp>
#include <vector>

int main(int argc, char *argv[]) {
    argparse::ArgumentParser program("graver");

    program.add_argument("--input").nargs(argparse::nargs_pattern::at_least_one).help("输入文件");

    try {
        program.parse_args(argc, argv);
    } catch (const std::exception &err) {
        std::cerr << err.what() << std::endl;
        std::cerr << program;
        return 1;
    }

    auto inputFiles = program.get<std::vector<std::string>>("--input");
    if (inputFiles.empty()) {
        std::cout << "没有输入文件" << std::endl;
    } else {
        std::cout << "输入文件列表:" << std::endl;
        for (const auto &f : inputFiles) {
            std::cout << f << std::endl;
        }
    }

    return 0;
}
```

## 输出

```
laolang@laolang-mint:bin$ ./graver --input
Too few arguments for '--input'.
Usage: graver [--help] [--version] [--input VAR...]

Optional arguments:
  -h, --help     shows help message and exits 
  -v, --version  prints version information and exits 
  --input        输入文件 [nargs: 1 or more] 
laolang@laolang-mint:bin$ ./graver --input a.txt b.txt
输入文件列表:
a.txt
b.txt
laolang@laolang-mint:bin$ 
```

## 注意

可以使用如下形式指定参数值数量

```c++
nargs(3) // 3 个
nargs(1, 3) // 1 到 3 个
nargs(argparse::nargs_pattern::any) // 任意多个, 包含 0 个
nargs(argparse::nargs_pattern::at_least_one) // 至少一个
nargs(argparse::nargs_pattern::optional) // "?" in Python. This accepts an argument optionally.
```

# Compound Arguments

# Converting to Numeric Types

# Default Arguments

# Gathering Remaining Arguments

# Parent Parsers

# Subcommands

# Getting Argument and Subparser Instances

# Parse Known Args

# Hidden argument and alias

# ArgumentParser in bool Context

# Custom Prefix Characters

# Custom Assignment Characters
