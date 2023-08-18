# 说明

用一个简单的例子描述如何使用桥接模式。

## 编辑与作者。

出版社的编辑负责策划图书，

并遴选作者完成图书的编著，然后根据图书的印张确定图书的价格。

作者负责完成图书的编著工作。

### 抽象角色 Abstraction

- BookEdit 编辑抽象类

### 实现者 Implementor

- IBookWriter 作者接口

### 细化抽象 Refined Abstraction

- TUPBookEdit 细化编辑的工作

### 具体实现者 Concrete Implementor

- Author 作者