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

### 1.数据定义

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
- default: 给定列的默认值
- unique: 限制列取值不重
- check: 限制列的取值范围
- primary key: 定义本列为主键
- foreign key: 定义本列为引用其他表的外键

例如 创建名为crlines的表

定义自增非空主键line_id，唯一非空line_name:

    create table crlines (
        line_id int not null auto_increment primary key,
        line_name varchar(25) not null unique
    )