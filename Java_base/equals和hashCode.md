# 为什么重写了equals()方法就一定要重写hashCode()方法

## 源码解析

### Object类 - 没有被重写过的equals()

    Java SE 17 & JDK 17 API
    package java.lang ↓
    public class Object ↓

```java
public boolean equals(Object obj) {
    return (this == obj);
}
```
由此可见，*没有经过重写的equals方法和'=='操作符是等价的*

**比较的都是obj引用指向的内存地址。**

### String类 - 重写过的equals()

    Java SE 17 & JDK 17 API
    package java.lang ↓
    public class String implements java.io.Serializable, 
        Comparable<String>, CharSequence,
               Constable, ConstantDesc ↓

```java
public boolean equals(Object anObject) {
    if (this == anObject) {
        return true;
    }
    return (anObject instanceof String aString)
        && (!COMPACT_STRINGS || this.coder == aString.coder)
        && StringLatin1.equals(value, aString.value);
}
```
判断流程：

1. 使用'=='比较内存地址，如果内存地址相同则返回true；

2. 如果内存地址不同，继续判断，同时满足以下三个条件则返回true：

* instanceof -> 左边的对象是右边类的实例；
* 不是紧凑的字符串 或 编码相同；
* 两个字符串的byte数组中的值一一对应相等。

*关于StringLatin1.equals()*

    Java SE 17 & JDK 17 API
    package java.lang ↓
    final class StringLatin1 ↓

```java
@IntrinsicCandidate
public static boolean equals(byte[] value, byte[] other) {
    if (value.length == other.length) {
        for (int i = 0; i < value.length; i++) {
            if (value[i] != other[i]) {
               return false;
            }
        }
        return true;
    }
    return false;
}
```
**就是用来比较byte[]的。相等则true，否则false。**

## equals和hashCode有什么关系？

首先，Java中任何对象都有一个native的hashCode()方法，继承自所有类的父类java.lang.Object。

    Java SE 17 & JDK 17 API
    package java.lang ↓
    public class Object ↓
    
    @IntrinsicCandidate
    public native int hashCode();

    返回对象的散列码值。支持此方法是为了使用哈希表，例如java.util.HashMap提供的哈希表。

当往这些集合类去添加元素时，就使用到hashCode()方法来判断元素是否存在。

*如果直接使用equals()比较内存地址，效率太低。*

所以一般是直接使用对象的hashCode值进行取模运算。

**对象加入哈希集合的步骤：**
* 如果table里没有此对象hashCode对应的值，则直接添加；
* 如果table里有此对象的hashCode值，则使用equals()比较内存地址：
* 若内存地址相同，则直接覆盖；
* 若内存地址不同，则散列到其他的地址。

这样一来大大提高了元素加入集合的效率。

**对象的hashCode值默认是JVM使用随机数生成的。**

**因此在数据量庞大的情况下，难免出现两个对象hashCode值相同的情况。**

这种情况在哈希表里体现为**哈希冲突。**

    解决方法一般有：
    链表、线性探测

*如果两个完全相同的对象（引用指向的内存地址相同），则它们的hashCode值也一定相同。*

## 回到题目

在没有重写这两个方法的前提下，若：

    x.equals(y)==true

则x与y的引用都指向了同一个对象（内存地址相同），则：

    (x.hashCode() == y.hashCode()) == true

假如重写了equals()而没有重写hashCode()，就会出现：

    x.equals(y) == true 但是
    (x.hashCode() == y.hashCode()) == false

此时这个类就无法与集合一起工作。

## 一种回答

如果只重写equals()而不重写hashCode()，就有可能导致equals结果为true但hashCode值不相同。

那么这个只重写了equals()的对象在使用散列集合(Hash集合)进行存储时就会出现问题。

因为散列集合使用对象的hashCode来计算存储位置。

此时若有两个完全相同的对象，但存储在散列集合中的位置不同，

则当我们需要根据这个对象去获取数据的时候就会出现问题。