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

1. 比较内存地址，如果内存地址相同则返回true；

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

