# Redis的Java客户端

常用的有三种：
* Jedis 
    
    以Redis命令作为方法名称，学习成本较低
    但是Jedis实例线程不安全，多线程环境下需要基于连接池使用

* Lettuce （Spring默认兼容）

    Lettuce是基于Netty实现（高性能的网络编程框架）
    支持同步、异步和响应式编程方式，线程安全
    支持Redis的哨兵模式、集群模式和管道模式

* Redisson

    是一个基于Redis实现的分布式、可伸缩的Java数据结构集合。
    包含了诸如Map、Queue、Lock、Semaphore、AtomicLong等功能

## Jedis使用步骤

### 1. 引入依赖

maven：

```
    <dependency>
        <groupId>redis.clients</groupId>
        <artifactId>jedis</artifactId>
        <version>4.3.1</version>
    </dependency>
```

Gradle:
```
    repositories {
        mavenCentral()
    }
    //...
    dependencies {
        implementation 'redis.clients:jedis:4.3.1'
        //...
    }
```

### 2. 创建Jedis对象，建立连接

```
    private Jedis jedis;

    //jedis = new Jedis(String host, int port);
    jedis = new Jedis("192.168.43.225", 6380);
    jedis.auth("123456"); //设置登录密码
    jedis.select(0); //选择0号数据库
```

### 3. 开始操作

*方法名就是命令*

如：

    jedis.hset("user:1", "name", "Jack");
    jedis.hset("user:1", "age", "21");
    Map<String, String> map = jedis.hgetAll("user:1");

### 4. 释放连接

    jedis.close();