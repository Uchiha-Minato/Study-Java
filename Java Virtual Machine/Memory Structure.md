# JVM内存结构

    1.PC Register 程序计数器
    2.JVM Stacks 虚拟机栈
    3.Native Method Stack 本地方法栈
    4.Heap 堆
    5.Method Area 方法区

## 1.程序计数器 - Program Counter Register(寄存器)

**作用：记住下一条JVM指令的执行地址**

    PC是Java对物理硬件的屏蔽和抽象 
    PC在物理上通过寄存器实现

特点： 
    
    1.线程私有 - 一个PC属于一个线程
    2.不存在内存溢出（Java中唯一）

## 2.虚拟机栈 - JVM Stacks

    栈 - 后进先出 线性结构
    **虚拟机栈 - 每个线程运行需要的内存空间**
    栈帧 - 栈内元素
    虚拟机栈栈帧 - 每个方法调用时需要的内存

**每个线程只能有一个活动栈帧**，对应着当前正在执行的方法，**在本线程JVM栈栈顶**

每当一个新的线程被创建，JVM都会分配一个虚拟机栈。

![jvmstacks](https://img-blog.csdnimg.cn/20200720140238838.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3BlYW51dHd6aw==,size_16,color_FFFFFF,t_70)

JVM Stacks以栈帧为单位来保存线程运行状态。

*栈帧保存的部分数据：*

    1.方法的局部变量表
    2.操作数栈
    3.动态链接
    4.方法返回地址等信息。

### 问题分析

    Q1.垃圾回收是否涉及栈内存
    A1:不涉及。JVM GC机制只回收堆内存中不再被引用的对象，而栈内存中是一次次方法调用所产生的内存，其随着方法结束调用而自动释放。

    Q2.栈内存分配越大越好吗
    A2.不是。栈内存（线程运行时需要的内存）分配越大线程数量越少（物理内存大小一定）。

    Q3.方法内局部变量是否线程安全
    A3.安全。因为局部变量线程私有。若是类似static共享变量 则需要考虑线程安全。
    若是变量引用了对象并逃离了方法的作用范围（作为参数传入或是作为返回值返回）就不再线程安全。

![threadOwn](/Pictures/局部变量线程私有.png)

### 栈内存溢出

有几种情况

    1.栈帧过多导致栈内存溢出（典型为递归调用）
    2.栈帧过大导致栈内存溢出（不容易出现）

**抛出异常 - java.lang.StackOverflowError**

### 线程诊断

案例1：cpu占用过多

    Linux下
    1.先用top定位哪个进程对cpu占用高
    2.ps H -eo [pid,tid,%cpu] | grep [pid]
    3.jstack 进程id（JVM的工具）返回线程的HEX编号，进一步定位到问题代码的源码行数

案例2：程序运行很长时间没有结果（线程死锁）

<br>

## 3.本地方法栈 - Native Method Stacks

**JVM在调用本地方法时 给这些方法提供的内存空间**

    本地方法 - 不是由Java代码编写的方法
    如使用C/C++编写的访问操作系统的方法
    java.lang.Object中的
    public native int hashCode()方法

## 4.堆 - Heap

**通过new创建的对象都会使用堆内存**

特点

    1.线程共享，需要考虑线程安全
    2.有GC（垃圾回收）机制

### 堆内存溢出

**抛出错误 - java.lang.OutOfMemoryError: Java heap space**

### 堆内存诊断

    1.jps工具 - 查看当前系统中有哪些Java进程
        jps返回进程pid和进程名
    2.jmap工具 - 查看堆内存占用情况
        jmap -heap [pid]
    3.jconsole工具 - 图形界面多功能监测工具
![jconsole](/Pictures/jconsole.png)

## 5.方法区 - Method Area

JVM规范中对方法区的定义：

    1.是所有Java虚拟机线程共享的区域，存储了与类结构相关的信息（成员方法，构造器，成员变量，运行时常量池等）
    2.方法区在虚拟机启动时被创建，逻辑上是堆的组成部分（不同JVM厂商实现逻辑不同）
    3.方法区也会导致内存溢出错误
        PermGen永久代
        Metaspace元空间 - 使用系统物理内存
![jvmMA](/Pictures/jvm方法区组成.png)

### 方法区内存溢出

**JDK7以前** 永久代内存溢出，抛出

    java.lang.OutOfMemoryError: PermGen space

**JDK8之后** 元空间内存溢出，抛出

    java.lang.OutOfMemoryError: Metaspace

实际情况中 spring或mybatis中运用字节码的动态生成技术，会产生大量的类，使用不当容易造成方法区内存溢出

### 运行时常量池 - Runtime Constant Pool

**Java二进制字节码 *.class文件*** 包括

    类基本信息 常量池 类方法定义（包含虚拟机指令）

**常量池** 保存在*.class文件中 就是一张表，虚拟机指令根据这张常量表找到要执行的类名、方法名、参数类型、字面量等信息

**运行时常量池** 当该类被加载，该类的常量池信息就会放入运行时常量池中，并把里面的符号地址变成真实物理地址

<br>

### StringTable 串池 - hashtable结构（数组+链表），不能扩容

    jdk1.6之前，串池是常量池（永久代）的一部分；
    jdk1.8之后，串池是堆的一部分。
    
    将串池移到堆中，以大幅提高垃圾回收的效率。
    因为永久代垃圾回收效率太低。

下面是一个例子：

    public class Test5 {
        public static void main(String[] args) {
            String s1 = "a";
            String s2 = "b";
            String s3 = "ab";
            String s4 = s1 + s2;

            System.out.println( s3 == s4); //false
        }
    }

打开Terminal，使用javap -v Test5.class反编译字节码文件，找到常量池和本地变量表：

**常量池Constant Pool**
![constantpool](/Pictures/constantPool.png)

**本地变量表LocalVariableTable**
![localVariableTable](/Pictures/localVariableTable.png)

反编译后的主方法中：
![main](/Pictures/mainTest5.png)

其中为Java字节码，部分代码含义如下：
<table>
    <tr> 
        <th colspan="3">上图部分代码含义</th>
    </tr>
    <tr>
        <td>代码</td>
        <td>含义</td>
        <td>备注</td>
    </tr>
    <tr>
        <td>ldc</td>
        <td>根据后面的编号（如#7）到常量池中查找并加载信息</td>
        <td>信息可以是常量或是对象的引用等</td>
    </tr>
    <tr>
        <td>astore_1</td>
        <td>把加载好的信息存入Slot为1的局部变量，保存到表中</td>
        <td></td>
    </tr>
    <tr>
        <td>aload_1</td>
        <td>从局部变量表中加载Slot为1的变量</td>
        <td></td>
    </tr>
    <tr>
        <td>invokedynamic</td>
        <td>调用动态方法</td>
        <td></td>
    </tr>
    <tr>
        <td>getstatic</td>
        <td>获得静态方法</td>
        <td></td>
    </tr>
</table>

    本机Java环境为JDK 15.0.2
    在JDK 9之前，使用运算符"+"合并字符串变量，底层使用的是StringBuilder的append()方法，合并完成之后再toString();

    在JDK 9之后，使用运算符"+"合并字符串变量，底层使用如下方法：
    动态方法所在类为
        java.lang.invoke.StringConcatFactory
    方法名为（使用常量进行合并）
        static CallSite makeConcatWithConstants(...)
    返回值为
        一个CallSite，其目标可用于执行字符串连接，并使用给定的方法描述的动态连接参数
    具体合并过程对应上图中字节码编号的9，10，11，16

由此可见，**作为final的String类**，已经没有了变量与常量的区别，只要创建了就是常量，底层统一使用makeConcatWithConstants()方法合并。
    
### StringTable特性

1.常量池中的字符串仅仅是符号，第一次用到时才是对象;

2.利用串池的机制，可以避免重复创建字符串对象;

3.可以使用intern()方法，主动将串池中还没有的字符串对象放入串池；

    Java SE 15 & JDK 15 API
    java.lang.Object ⬇
        java.lang.String
    public String intern()
    返回字符串对象的规范表示。
    结果：与此字符串具有相同内容的字符串，但保证来自唯一串池。

    JVM中最初为空的串池由String类私有维护。

    此方法被调用时，如果串池中已经包含了一个由equals(Object)方法确定的与此对象相等的字符串，则返回池中已有的字符串；否则将这个字符串对象(String Object)加入串池中，并返回这个对象的引用。

    由此可见，对于任意两个字符串s和t，s.intern() == t.intern()结果是true，当且仅当s.equals(t)为true。

4.StringTable会发生垃圾回收。只会在堆内存紧张时触发。

### StringTable调优

如果系统中字符串常量非常多时，可以适当增大StringTable的大小以获得更高的效率。

虚拟机参数：   

    --XX：StringTableSize=1009(最小桶个数) 
    --XX:+PrintStringTableStatistics //打印串池信息到控制台

如果字符串个数非常多，并且有大量重复数据时，可以先使用intern()方法将字符串加入串池，节约堆内存的使用。

## 6.直接内存 - Direct Memory

*此部分不属于JVM内存管理，而属于系统内存。*

    常见于NIO操作时，用于数据缓冲区；
    分配回收成本较高，但读写性能高；
    不受JVM内存回收管理。

 <br>...

 






