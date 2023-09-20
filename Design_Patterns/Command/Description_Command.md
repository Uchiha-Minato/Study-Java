# 说明

通过一个简单的例子来描述命令模式中所涉及的各个角色

以及undo能撤销execute方法的执行效果。

## 请求者请求在硬盘上建立目录，请求成功后还可以撤销请求。

### 命令接口 Command

- ICommand

### 具体命令 ConcreteCommand

- ConcreteCommand

### 请求者 Invoker

- RequestMakedir

### 接收者 Receiver

- MakeDir