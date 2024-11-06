# Go程序结构

## 命名

变量名必须以**Unicode字母**或**下划线**开头，后面可以跟任意数量的字母、数字或下划线，区分大小写。

### 关键字

不能用于定义变量。包括：
```
break default func interface select

case defer go map struct

chan else goto package switch

const fallthrough if range type

continue for import return var
```

预定义名字：

- 内建常量
```
true false iota nil
```

- 内建类型
```
int int8 int16 int32 int64 

unit uint8 uint16 uint32 uint64 uintptr

float32 float64 complex128 complex64

bool byte rune string error 
```

- 内建函数
```
make len cap new append copy close delete

complex real imag panic recover
```

## 声明

主要有4种类型的声明语句：

- var：变量
- const：常量
- type：类型
- func：函数实体对象

比如：
```Go
// Boiling prints the boiling point of water.
package main

import "fmt"

const boilingF = 212.0

func main() {
    var f = boilingF
    var c = (f - 32) * 5 / 9
    fmt.Printf("boiling point = %g°F or %g°C\n", f, c)
    // Output:
    // boiling point = 212°F or 100°C
}
```

## 变量

基本声明：

```Go
var 变量名 变量类型 = 表达式
```

其中 **变量类型** 或者 **表达式** 两部分可以省略其中的一个。

- 类型被省略，会自动根据表达式判断类型
- 表达式被省略，将用0值初始化

可以在一个声明语句中同时声明一组变量，或用一组初始化表达式声明并初始化一组变量。

如果省略每个变量的类型，将可以声明多个类型不同的变量（类型由初始化表达式推导）

```Go
var i, j, k int                 // int, int, int
var b, f, s = true, 2.3, "four" // bool, float64, string
```

一组变量也可以通过调用一个函数，由函数返回的多个返回值初始化。

```go
var f, err = os.Open(name) // os.Open returns a file and an error
```

### 局部变量

在函数内部，称为简短变量声明语句，基本声明方式：

```go
名字 := 表达式
```

变量类型由表达式推导。

```go
freq := rand.Float64() * 3.0
```

**`:=`是一个声明语句，而`=`是一个赋值语句。**

*特别地，简短变量声明并不全都是新变量。*

如果有一些已经在相同的词法域声明过了，那么简短变量声明语句对这些已经声明过的变量就只有赋值行为了。

```go
in, err := os.Open(infile)

out, err := os.Create(outfile)
```
简短变量声明语句中必须**至少有声明一个新的变量**。

```go
f, err := os.Open(infile)

f, err := os.Create(outfile) // 编译出错：没有新变量
```

### 指针

一个指针的值是另一个变量在内存中的地址。

不是每一个值都有地址，但对于每一个变量必然有对应的内存地址。

```go
x := 1
p := &x         // p为 *int型指针
fmt.Println(*p) // "1"
*p = 2          // 等效于 x = 2
fmt.Println(x)  // "2"
```

对于聚合型变量的每个成员，其中的每个字段或元素都可以被`&`操作符取地址。

### 语法糖：new()函数

new()函数是内建函数。使用方式：

```go
变量 := new(类型)
```
表达式new(T)将创建一个T类型的匿名变量，初始化为T类型的零值，然后*返回变量地址*，返回的指针类型为*T。



