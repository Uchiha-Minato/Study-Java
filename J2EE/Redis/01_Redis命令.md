# Redis

Redis是一个键值型非关系型数据库。

https://redis.io

## 存储数据结构

**基本类型：**

* String 字符串
* Hash 哈希表
* List 有序集合（链表）
* Set 无序集合
* SortedSet 可排序集合

**特殊类型：在基本类型的基础上做了一些处理**

* GEO 地理坐标
* Bitmap 
* HyperLog
* 等

## Redis通用命令

*通过help[command]可以查看一个命令的具体用法。*

- **KEYS** 查看符合模板的所有key
- **DEL** 删除指定key
- **EXISTS** 判断key是否存在
- **EXPIRE** 给一个key设置有效期，到期后会被自动删除
- **TTL** 查看一个key的剩余有效期

## String类型命令

Redis中最简单的存储类型。

根据字符串的格式不同，又可以分为三类：
* string 普通字符串
* int 整数类型，可以做自增、自减操作
* float 浮点类型

不管是哪种格式，底层都是byte[]。只不过编码方式不同。

- **SET** 添加或修改一个String类的键值对
- **GET** 根据key获取value
- **MSET** 批量添加key和value
- **MGET** 批量根据key获取value
- **INCR** 让一个int类型的key自增1
- **INCRBY** 让一个整形的key自增，可以指定步长
- **DECR** 自减1
- **INCRBYFLOAT** 让一个float的key根据指定的步长自增
- **SETNX** 添加一个String类型的字符串，前提是key不存在
- **SETEX** 添加一个String类型的字符串，并指定有效期

*SETNX = set key value nx,  SETEX = set key value ex*

### Key的层级格式

Redis的key允许由多个单词形成层级结构，单词之间用':'隔开。比如：

``````
    项目名:业务名:类型:id
``````

## Hash类型

也叫散列，其value是一个无序字典，类似于HashMap。

Hash结构：

    key, value ( field, value )

- **HSET key field value** 添加或修改一个hash类型key的field的值
- **HGET key field** 获取一个hash类型key的field的值
- **HMSET** 批量添加多个hash类型key的field
- **HMGET** 批量获取多个hash类型key的field
- **HGETALL** 获取一个hash类型key中所有的field和value
- **HKEYS** 获取一个hash类型key的所有field
- **HVALS** 获取一个hash类型key的所有value
- **HINCRBY** 让一个hash类型的key的字段值自增并指定步长
- **HSETNX** 添加一个hash类型key的field，前提是field不存在

## List类型

与LinkedList类似，可以看成是一个双向链表结构。

特征：
* 有序
* 元素可以重复
* 插入和删除快
* 查询速度一般

- **LPUSH key element ...** 向列表左侧插入一个或多个元素
- **LPOP key** 移除并返回列表左侧的第一个元素，没有则返回nil
- **RPUSH key element ...** 向列表右侧插入一个或多个元素
- **RPOP key** 移除并返回列表右侧的第一个元素，没有则返回nil
- **LRANGE key star end** 返回一段角标范围内的所有元素
- **BLPOP和BRPOP** B代表阻塞（Block），与LPOP和RPOP类似

    在没有元素时等待指定时间，而不是直接返回nil。

## Set类型

与HashSet类似，可以看成是value为null的HashMap。

因为也是一个Hash表，因此具备与HashSet类似的特征：
* 无序
* 元素不可重复
* 查找快
* 支持交集、并集、差集功能

- **SADD key member ...** 向set中添加一个或多个元素
- **SREM key member ...** 移除set中的指定元素
- **SCARD key** 返回set元素中的个数
- **SISMEMBER key member** 判断一个元素是否存在于set中
- **SMEMBERS** 获取set中的所有元素
- **SINTER key1 key2 ...** 求key1与key2的交集
- **SDIFF key1 key2 ...** 求key1与key2的差集
- **SUNION key1 key2 ...** 求key1与key2的并集

## SortedSet类型

是一个可排序的set集合，与Java中额TreeSet有些类似，但底层数据结构差别大

    TreeSet: 红黑树
    SortedSet: 跳表SkipList + hash表

SortedSet的每一个元素都带有一个score属性，可以基于score属性对元素排序。

特征：
* 可排序
* 元素不重复
* 查询速度快

- **ZADD key score member** 添加一个或多个元素到sorted set，如存在则更新其score值
- **ZREM key member** 删除sorted set中的一个指定元素
- **ZSCORE key member** 获取sorted set中指定元素的score值
- **ZRANK key member** 获取sorted set中指定元素的排名
- **ZCARD key** 获取sorted set中元素个数
- **ZCOUNT key min max** 统计score值在给定范围内的所有元素个数
- **ZINCRBY key increment member** 让sorted set中的指定元素按照指定步长自增
- **ZRANGE key min max** 按照score排序后，获取指定排名范围内的元素
- **ZRANGEBYSCORE key min max** 按照score排序后，获取指定score范围内的元素
- **ZDIFF ZINTER ZUNION** 求差集 交集 并集

*上述所有排序默认都是升序。如果要降序，则在命令的Z后面添加REV即可*
