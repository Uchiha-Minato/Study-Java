# SQL - Structured Query Language

结构化查询语言。

*特性：*
- 一体化
- 高度非过程化
- 简洁
- 使用方式多样

## SQL的功能

- 数据查询: Select
- 数据定义

    Create Drop Alter

- 数据更改

    Update Delete Insert

- 数据控制

    Grant Revoke Deny

## 1.数据定义

|对象|创建|修改|删除|
|----|----|----|----|
|架构|create schema|----|drop schema|
|表|create table|alter table|drop table|
|视图|create view|alter view|drop view|
|索引|create index|alter index|drop index|

**定义架构** 语法：

    create schema [<架构名>] authorization <用户名>

例如 创建名为cr_lines的架构：

    create schema cr_lines authorization root

**定义表** 语法：

    create table <表名> (
        <列名> <数据类型> [列级完整性约束],
        {...}
        [,表级完整性约束]
    )

列级完整性约束有：
- not null: 非空
- default 常量表达式: 给定列的默认值
- unique: 限制列取值不重
- check(逻辑表达式): 限制列的取值范围
- primary key: 定义本列为主键
- foreign key: 定义本列为引用其他表的外键

例如 创建名为crlines的表

定义自增非空主键line_id，唯一非空line_name:

    create table crlines (
        line_id int not null auto_increment primary key,
        line_name varchar(25) not null unique
    )

**修改表** 语法：

    alter table <表名>
        [alter column <列名> <新数据类型>] 
        | [add <列名> <数据类型> [约束]]
        | [drop column <列名>]
        | [add [constraint <约束名>] 约束定义]
        | [drop [constrant] <约束名>]


## 2.数据操作

### 2.1 查询 - R ead - select

**查询语句基本结构 关键字 - select**

    select <目标列名序列> from <表名>
        [where <行选择条件>]
        [group by <分组依据列>]
        [having <组选择条件>]
        [order by <排序依据列>]

**含字符串常量**

    select line_name, '地区' from crlines

**指定列别名**

    [列名|表达式] [as] 列别名
    或 列别名 = [列名|表达式]

    select Sname 姓名, 2018-Sage 年份 from student

**消除结果集中的重复行 - distinct**

    select distinct Sno from SC

**查询满足条件的元组 - where字句**

|查询条件|谓词|
|---|---|
|比较运算符|=, >, >=, <, <=, !=|
|确定范围|[not] between and|
|确定集合|[not] in|
|字符匹配|like, not like|
|空值|is [not] null|
|多重条件|and, or|

例如

    select Sname from Student 
        where Sdept='计算机系'
    select Sname, Sage from Student 
        where Sage < 20
    select distinct Sno from SC 
        where Grade < 60
    select Sname, Sdept, Sage from student
        where Sage between 20 and 23
    
**确定集合 - in**

    列名 [not] in(常量1, 常量2,...)
    select Sname, Ssex from Student
        where Sdept in('信息管理系', '计算机系')
    等价于 where Sdept = '信息管理系' or Sdept = '计算机系'

**字符串匹配 - like** *模糊查询*

    列名 [not] like <匹配串> [escape <转义字符>]

匹配串追踪可以包含如下通配符:

- %(百分号)：匹配0个或多个字符
- _(下划线)：匹配一个字符
- []：匹配方括号中的任何一个字符
- [^]：不匹配方括号中的任何一个字符

若要比较的字符是连续的，则可以用连字符"-"表达。

例如，要匹配b,c,d,e中的任意一个字符，就可以使用[b-e]表达。

    select Sname, Sdept from Student
        where Sname like '张%'
    -- 查询姓 张李刘 的
    select * from Student
        where Sname like '[张李刘]%'
    -- 查询名字里第二个字是 “大” 或 “小” 的
    select * from Student
        where Sname like '_[大小]%'
    -- 查询学号尾号不是2、3、5的学生信息
    select * from Student
        where Sno like '%[^235]'

"转义字符"是任何一个有效的字符，在匹配串中也包含这个字符

当要查找的字符串正好含有通配符时使用

表明在转义字符**后面的那一个字符**将被视为普通字符而不是通配符。

    例如：
    -- 查找field1字段中包含字符"30%"的记录
    where field1 like '%30!%%' escape '!'
    -- 查找field1字段中包含下划线的记录
    where field1 like '%!_%' escape '!'

**空值查询 - is[not]null**

**多重条件查询 - and or**

**对查询结果进行排序 - order by子句**

    order by <列名> [asc|desc] [, <列名>...]

例如：

    select Sno, Grade from SC
        where Cno='C002' 
        order by Grade desc

**使用聚合函数汇总数据**

聚合函数是对一组值进行计算并返回一个统计结果。

聚合函数包括：
- count(*) 统计表中元组的个数
- count([distinct] <列名>) 统计列值个数
- sum(<列名>) 计算列值的和（必须是数值型列）
- avg(<列名>) 计算列值的平均值（必须是数值型列）
- max(<列名>) 得到列值的最大值
- min(<列名>) 最小值

*除了count(※)外，其他函数在计算过程中均忽略null值*

例如

    select count(*) from Student
    select count(distinct Sno) from SC
    select sum(Grade) from SC where Sno='2019011378'

**对数据进行分组统计 - group by子句**

    group by <分组依据列> [,...] [having <组提取条件>]

作用：可以控制计算的级别：全表还是一组
目的：细化计算函数的作用域

    --统计每门课程的选课人数，列出课程号和选课人数
    --先对查询结果按Cno的值分组，然后再对每一组使用聚合函数计算
    select Cno, count(Sno) from SC
        group by Cno
    
    select Sno 学号, count(*) 选课门数, avg(Grade) 平均成绩
        from SC group by Sno

注意事项：
- group by 子句中的分组依据必须是表中存在的列名
- 带有group by子句的select的查询列表中只能出现分组依据列和统计函数，因为分组后每个组只返回一行结果

**使用having子句**

用于对分组自身进行限制。类似于where，但不是用于单个记录。

    -- 查询选课门数超过3门的学生学号和选课门数
    -- 先执行group by子句对SC表数据按Sno进行分组
    再用count函数分别对每一组进行统计
    最后筛选出统计结果满足大于3的组 --
    select Sno, count(*) 选课门数 from SC
        group by Sno 
        having count(*) > 3
    
*说明*
- where子句用于筛选from子句中指定的数据源所产生的行数据
- group by子句用来对经where子句筛选后的结果数据进行分组
- having子句用于对分组后的数据进一步筛选

**多表连接查询 - join 一次查询同时涉及两个或以上的表**

**1.内连接**

    from 表1 [inner] join 表2 on <连接条件>
    <连接条件>：
    [<表名1.>][<列名1>] = [<表名2.>][<列名2>]
    表别名：
    <表名> [as] <别名>

*一旦使用别名，在当前语句结束前，都要使用别名作为表名*

例如

    select * from Student
        inner join SC on Student.Sno = SC.Sno
    --查询计算继续学生的修课情况，列出学生名字，所修课程号，成绩
    select Sname, Cno, Grade from Student 
        join SC on Student.Sno = SC.Sno
        where Sdept = '计算机系'
    -- 统计每个系的学生考试平均成绩
    select Sdept, avg(Grade) 平均成绩 from Student S
        join SC on S.Sno = SC.Sno
        group by Sdept

*自连接 - 特殊的内连接*

相互连接的表物理上是同一个表。

因此必须为两个表取别名，使之在逻辑上称为两个表。

    from 表1 as T1 join 表1 as T2

例如：

    --查询与刘晨在同一个系学习的学生姓名和所在的系
    select S2.Sname, S2.Sdept from Student S1 
        join Student S2 on S1.Sdept = S2.Sdept
        where S1.Sname = '刘晨' and S2.Sname != '刘晨'
    -- 查询与"数据结构"在同一个学期开设的课程的课程名和学期
    select C1.name, C1.Semester from Course C1
        join Course C2 on C1.Semester = C2.Semester
        where C2.Cname = '数据结构'

**2.外连接**

外连接会返回from子句中提到的至少一个表的所有行，<br>
只要这些行符合where或having搜索条件。

    left [outer] join
    right [outer] join

    -- 查询京广线所有车站的信息
    select station_name 车站名, distance 距离 from stations
	    left join crlines on stations.line_name = crlines.line_name
        where crlines.line_name='京广线'
        order by distance desc; 

    -- 查询计算机系没有选课的学生的姓名和性别
    select Sname, Ssex from Student S 
        left join SC on S.Sno = SC.Sno
        where Sdept = '计算机系' and SC.Sno is null

**限制结果集行数 - top**

语法：

    top n [percent] [with ties]

解释及注意事项：
- top n 取查询结果的前n行
- top n percent 取查询结果的前n%行
- with ties 包括并列的结果
- top谓词写在select单词的后面
- 如果使用with ties，则必须使用order by子句排序

    -- 查询年龄最大的三个学生的姓名，年龄，所在系，包含并列
    select top 3 with ties Sname, Sage, Sdept from Student
        order by Sage desc

**多分支case表达式**

- 可以根据条件列表的值返回多个可能结果中的一个
- 可以用在任何允许使用表达式的地方
- 不是完整的T-SQL语句，不能单独执行

*简单case表达式*

    case 测试表达式
        when 简单表达式1 then 结果表达式1
        when 简单表达式2 then 结果表达式2
        ...
        [else 结果表达式n+1]
    end

*搜索case表达式 - 简单表达式n->布尔表达式*

**将查询结果保存到新表 - into**

语法：

    select 查询列表序列 into [#][#]<新表名> from 数据源...

- 一个“#”代表局部临时表
- 两个“##”代表全局临时表
- 临时表存放在内存中

例如：

    --将计算机系学生的学号、姓名、性别、年龄永久保存到Student_CS表中
    select Sno, Sname, Ssex, Sage into Student_CS
        from Student where Sdept = '计算机系'

可以对新表进行CURD操作。

**子查询**

通常出现在外层查询的where子句或having子句中，与运算符一起构成查询条件。

通常有如下形式：

    where 列名 [not] in (子查询)
    where 列名 比较运算符 (子查询)
    where exist (子查询)

*使用子查询进行基于集合的测试*

子查询的结果往往是一个集合，in就是在这个集合中进行操作

    where 表达式 [not] in (子查询)

例如

    -- 查询与“刘晨”在同一个系学习的学生
    select Sno, Sname, Sdept from Student
        where Sdept in (
            select Sdept from Student where Sname='刘晨'
        ) and Sname != '刘晨'

*使用子查询进行比较测试*

通过比较运算符，将一个表达式的值与子查询返回的值进行比较。

    where 列名 比较运算符 (子查询)

- 要求子查询必须是返回单值的查询语句

    --查询选了"C004"号课程且成绩高于此课程的平均成绩的学生的学号
    select Sno, Grade from SC
        where Cno = 'C004' and Grade > (
            select avg(Grade) from Student
            where Cno = 'C004' 
        )
    
*带exists谓词的子查询*

exists代表存在量词'∃'

    where [not] exist (子查询)

- 不反回查询的数据，只产生逻辑真值和假值

### 2.2 插入数据 - C reate - insert

语法：

    insert into <表名> [(<列表名>)] values (值表)

- 当值表有缺省时，必须列出列名

例如

    insert into crlines(line_name) value('京广线');

### 2.3 更新 - U - update

语法：

    update <表名> set <列名>= {表达式|default|null} [,...n]
    [from <条件表名> [,...n]] 
    [where <更新条件>]

例如：

    update crlines set id2='1' 
        where line_name='Beijing_HKWKowloog';

    --将计算机系全体学生的成绩加五分
    update SC set Grade = Grade + 5
        where Sno in (
            select Sno from Student where Sdept='计算机系'
        )
    或
    update SC set Grade = Grade + 5 from SC 
        join Student on SC.Sno = Student.Sno
        where Sdept = '计算机系'

### 2.4 删除 - D - Delete

语法：

    delete [from] <表名>
    [from <条件表名>[,...n]]
    [where <删除条件>]

### 小结：
- 不能将对统计后的结果进行筛选的条件写在where子句中

    应该写在having子句中。

- 不能将列值与统计结果值进行比较的条件写在where子句中

    应该用子查询实现。
    where Sage > avg(Sage) 不行
    where Sage > (select avg(Sage) from Student)
    
- 对行的过滤一般用where，对组的过滤一般用having

- 当查询目标来自多个表时，必须用join多表连接实现

- 子查询语句中的列不能用在外层查询中

- 使用自连接时，必须为表取别名

- 带否定条件的查询一般用子查询实现not in或not exists

- 当使用top时，一般都使用order by子句与它配合

