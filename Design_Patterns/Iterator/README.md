# 说明

通过一个例子来描述迭代器模式所涉及的角色。

## 打印名字

我们将创建一个叙述导航方法的 Iterator 接口和一个返回迭代器的 Container 接口。实现了 Container 接口的实体类将负责实现 Iterator 接口。

演示类使用实体类 NamesRepository 来打印 NamesRepository 中存储为集合的 Names。

### 集合 Aggregate

- interface Container

### 具体集合、具体迭代器

- NameRepository
- 内部类 NameIterator

### 迭代器

- interface Iterator