# 内存结构

    1.PC Register 程序计数器
    2.JVM Stacks 虚拟机栈
    3.Native Method Stack 本地方法栈
    4.Heap 堆
    5.Method Area 方法区

## 程序计数器 - Program Counter Register(寄存器)

作用：记住下一条JVM指令的执行地址

    PC是Java对物理硬件的屏蔽和抽象 
    PC在物理上通过寄存器实现

特点： 
    
    1.线程私有 - 一个PC属于一个线程
    2.不存在内存溢出（Java中唯一）

## 虚拟机栈 - JVM Stacks

