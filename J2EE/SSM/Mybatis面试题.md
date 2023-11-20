# MyBatis面试题

## 1. 什么是MyBatis？

MyBatis是一个可以自定义SQL、存储过程和高级映射的，基于JDBC的DAO层框架。

在没有MyBatis之前，我们都用的JDBC来操作数据库。但JDBC有两大缺点：

- 除了SQL语句，其他的操作包括加载驱动、建立连接、释放连接等操作都是重复的；
- SQL语句以字符串的形式与Java代码放在一起，需要修改SQL语句时就相当于修改Java代码，违反了“开-闭原则”。

MyBatis的出现解决了上述问题。

- MyBatis底层封装了JDBC的所有连接操作；
- 将SQL代码写在xml文件中，实现与Java代码的解耦。

此外，MyBatis还具有一些其他的特性，如：

- 动态SQL；
- ResultMap映射；
- 一对一，一对多的映射等

能更方便地进行开发。

## 2. 说一下MyBatis的缓存。

首先，缓存是为了减少SQL与数据库的IO交互，提升查询效率。

MyBatis的缓存分为一级缓存和二级缓存。

**一级缓存：SqlSession级别。默认开启。**

作用范围是同一个SqlSession中，不同的SqlSession之间数据不共享。

**二级缓存：工厂级别。可以手动开启。**

多个SqlSession可以共用二级缓存。

开启：使用二级缓存的类实现Serializable接口，在映射文件中添加cache标签。

## 3. MyBatis如何进行分页？分页插件的原理是什么？

分页的三种方式：
- 使用RowBounds对象进行分页
- 直接编写SQL语句进行分页（limit关键字）
- 使用MyBatis分页插件

分页插件的原理：

实现MyBatis提供的接口，实现自定义插件，在插件的拦截方法内拦截待执行的SQL，然后重写SQL。
```
select * from student -> select * from student limit 0, 10
```

## 4. 简述MyBatis的插件运行原理，以及如何编写一个插件

1）插件运行原理

Mybatis在执行SQL语句之前，会先将SQL语句传递给拦截器链。

拦截器链中的每个拦截器都会对SQL语句进行处理，如果拦截器返回false，则会中断拦截器链，Mybatis将不会继续执行SQL语句。

拦截器链中的最后一个拦截器会将SQL语句传递给Mybatis进行执行。

2）编写插件

- 实现MyBatis的Interceptor接口并重写intercept()方法；
- 然后再给插件编写注解，指定要拦截哪一个接口的方法；
- 最后在配置文件中配置你编写的插件。
```
<plugins
	<plugin interceptor="com.mybatis_demo.plugin.MySecondPlugin"></plugin>
</plugins>
```

## 5. MyBatis动态SQL是做什么的？都有哪些动态SQL？简述一下动态SQL的执行原理。

1）MyBatis动态sql可以让我们在xml映射文件中以标签的形式编写动态sql，完成逻辑判断和动态拼接sql的功能。

2）9种动态SQL标签：
- if ：用于条件判断
- choose when otherwise ：用于条件选择
- trim ：用于去除生成的SQL语句中的多余部分
- where ：用于生成where子句
- set ：生成update子句
- foreach ：用于循环遍历集合或数组，生成对应的SQL语句

3）执行原理

- Mybatis在解析XML映射文件时，会将动态标签中的表达式转换为OGNL表达式；
- 在执行SQL语句时，会使用OGNL表达式来解析动态标签，并生成SQL语句；
- MyBatis将生成的SQL语句传递给数据库进行执行。

## 6. #{}和${}的区别

 #{}是预编译，相当于PreparedStatement的'?'；

 ${}是直接将参数复制一份加入sql，容易SQL注入。

## 7. 为什么说Mybatis是半自动ORM映射工具？它与全自动的区别在哪里？

Hibernate是全自动ORM映射工具。

使用Hibernate查询关联对象或者关联集合对象时，可以根据对象关系模型直接获取，所以他是全自动的。

而Mybatis在查询关联对象或关联集合对象时，需要手动编写sql和ResultMap。

## 8. MyBatis是否支持延迟加载？如果支持，他的实现原理是什么？

1）Mybatis仅支持association关联对象和collection关联集合对象的延迟加载。

- association：一对一
- collection：一对多

启用延迟加载：lazyLoadingEnabled=true|false

2）原理：
- 使用cglib创建目标对象的代理对象
- 当调用目标方法时，进入拦截器方法。

    如调用a.getB().getName()，拦截器invoke()方法发现a.getB()结果为null，
    
    此时会单独发送事先保存好的查询关联B对象的SQL，把B对象查询上来（b），然后调用a.setB(b)设置B对象的值为b，于是a.getB()就不为空了。
    
    这时候再接着完成a.getB().getName()。

## 11. 简述MyBatis的xml映射文件和MyBatis内部数据结构之间的映射关系？

* 1.MyBatis将所有的xml配置信息都封装到Configuration对象内部；
* 2.在xml文件中，parameterMap标签会被解析为ParameterMap对象，其每个子元素会被解析为ParameterMapping对象。resultMap标签会被解析为ResultMap对象，其每个子元素会被解析为ResultMapping对象；
* 3.每个select、insert、update、delete标签均会被解析为MappedStatement对象，标签内的sql会被解析为BoundSql对象。

## 12. 什么是MyBatis接口绑定，有什么好处？

接口映射就是在MyBatis中定义任意接口，然后把接口里面的方法和SQL语句绑定。

使用时直接调用接口方法就可以，这样比起原来SqlSession提供的方法，有更多的选择和设置。

## 13. 接口绑定有几种实现方式，分别如何实现？

两种实现方式：
- 方法上写注解
- xml中写SQL语句，要指定xml文件中的namespace为接口全限定类名

## 14. 如何选择？

SQL语句简单时使用注解，SQL复杂时使用xml，更多使用xml。

## 15. MyBatis实现一对一有几种方式？

有两种方式：

1）联合查询

几个表联合查询，只查询一次，通过在resultMap里的 association结点 配置一对一的类即可；

2）嵌套查询

先查一个表，根据这个表里的结果的外键id，再去另外一个表里查询数据，也是通过association配置，但另外一个表的查询通过select属性配置。

## 16. MyBatis执行一对一、一对多的关联查询的实现方式及区别

MyBatis不仅可以一对一，一对多，还可以多对一，多对多。

多对一查询其实就是一对一，多对多查询其实就是一对多；

只需要把selectOne()修改为selectList()即可；

关联对象查询有两种实现方式：
- 单独发送一个sql去查询关联对象，赋给主对象，然后返回主对象；
- 嵌套查询是使用join查询，一部分列是A对象的属性值，另一部分是关联对象B的属性值，好处是只发送一个sql就可以把主对象和其关联对象查出来。

## 17. MyBatis里动态sql如何设定？

动态sql一般通过if结点来实现。

但是如果要写得完整，就必须配合where，trim结点。

* where：判断包含节点是否有内容，有内容就插入where，否则不插入；
* trim：判断如果动态语句是以and或or开始，那么自动把这个and或or取消掉。

## 18. MyBatis是如何将sql执行结果封装为目标对象并返回的？都有哪些形式？

有两种方式：

* 1.使用resultMap标签，逐一定义列名和对象属性名之间的映射关系
* 2.使用sql列的别名功能，将列别名自定义为对象属性名。列名不区分大小写，Mybatis会忽略列名大小写。

有了映射关系，Mybatis通过反射创建对象，同时使用反射给对象逐一赋值并返回。

找不到映射关系的属性是无法完成赋值的。

## 19.xml映射文件中，除了常见的curd标签，还有哪些标签

resultMap parameterMap sql include selectKey

其中sql为sql片段标签，通过include标签引入sql片段，selectKey为不支持自增的主键生成策略标签。

动态sql的9个标签
if choose where when otherwise trim set bind foreach

## 20.21. 实体类字段名和表名不一样，如何实现映射；模糊查询like语句怎么写

1）sql语句中自定义别名 2）resultMap映射

1）Java中写通配符，mybatis通过#{}赋值 2）在Sql中拼接通配符

## 22. 通常一个xml映射文件，都会写一个DAO与之对应。说一下DAO是否可以重载，以及其运行原理。

Mybatis映射的DAO方法不能重载。

因为通过DAO寻找xml对应sql的时候使用 全限定类名+方法名 的保存和寻找策略。

接口工作原理为jdk动态代理，运行时会为DAO生成代理对象，代理对象会拦截接口方法，去执行对应的sql返回数据。

## 23. 映射文件中，如果A标签通过include引用了B标签的内容，B标签能否定义在A标签的后面？

虽然xml文件按顺序解析，但标签定义依然可以写在任何地方。

解析A标签时，发现B不存在，Mybatis会先将A标签标记为未解析状态，然后继续解析剩下的标签。

等所有标签解析完毕，再去解析A标签，此时B标签也存在了。

## 24. 不同的xml映射文件，id是否可以重复？

如果配置了namespace，id就可以重复。

namespace+id 是作为 Map< String, MappedStatement >的key使用，如果没有namespace，就剩下id，id重复自然就会导致数据互相覆盖。

## 25. MyBatis如何执行批处理？

使用BatchExecutor（批处理执行器）完成批处理。

## 26. 有哪些Executor？

有三种基本的：SimpleExecutor、ReuseExecutor、BatchExecutor。

- SimpleExecutor: 每执行一次update或select，就开启一个Statement对象，用完立刻关闭Statement对象。
- ReuseExecutor：执行update或select，以sql为key查找Statement对象，存在就使用，不存在就创建。用完后不关闭，而是放置于Map中
- BatchExecutor：完成批处理。

## 27. MyBatis如何指定使用哪一种执行器？

两种方式：

- 在配置文件中，可以指定默认的ExecutorType执行器类型
- 也可以手动给DefaultSqlSessionFactory创建SqlSession的方法传递ExecutorType类型参数。

## 29. MyBatis是否可以映射Enum类？

不仅可以映射Enum类，Mybatis可以映射任何对象到表的一列上。

映射方式为自定义一个TypeHandler，实现TypeHandler的setParameter()和getResult()接口方法。

TypeHandler有两个作用：

* 1.完成javaType至jdbcType的转化 -> setParameter()；
* 2.完成jdbcType至javaType的转化 -> getResult()。

体现为setParameter()和getResult()方法，分别代表设置sql问号占位符参数和获取列查询结果。

## 30. 如何获取自动生成的（主）键值？

配置文件中设置usegeneratedkeys为true。

## 31. 在mapper中如何传递多个参数？

- 直接在方法中传递，然后用#{0}，#{1}获取；
- 使用@Param注解，可以直接在xml文件中通过#{name}获取。

## 32. resultType resultMap的区别

1）类名和数据库相同时，可以直接设置resultType参数为实体类名

2）若不同，则需要设置resultMap将结果名字和实体类名进行转换。

## 33. 使用mapper接口调用时都有哪些要求？

* 1.namespace指定为mapper接口的全限定类名；

* 2.Mapper接口方法名和xml文件中定义的id相同；

* 3.Mapper接口方法输入参数类型与parameterType类型一致；

* 4.Mapper返回参数类型与resultType类型一致。

## 34. MyBatis相比IBatis比较大的几个改进是什么？

- 有接口绑定，包括 注解绑定sql 和 xml绑定sql；

- 动态sql由原来的节点配置变成了OGNL表达式；

- 在一对一，一对多时引入了association，在一对多的时候引入了collection结点，不过都是在resultMap里配置。

## 35. IBatis和MyBatis核心处理类分别叫什么？

- IBatis：SqlMapClient
- MyBatis：SqlSession

## 36. IBatis和MyBatis在细节上的不同有哪些？

* 1.在sql里面变量命名由原来的 #变量# 变成了 #{变量}
* 2.原来的 $变量$ 变成了 ${变量}
* 3.原来在sql节点里的class都换名字，叫type
* 4.原来的queryForObject queryForList变成了 selectOne selectList
* 5.原来的别名设置在映射文件中，现在在核心配置文件中。