# 设计模式 Design Pattern

一共有23种设计模式，它们代表了解决常见问题最佳的实践。

通常被有经验的面向对象的软件开发人员所采用。

设计模式是软件开发人员在软件开发过程中面临的一般问题的解决方案。

提出者：GOF（Gang of Four）四人帮

    Erich Gamma
    Richard Helm
    Ralph Johnson
    John Vlissides 

设计模式主要是基于以下OOP原则：

- 1.依赖倒置原则 DIP

    设计一个类时，不应该让类面向具体的类，而是面向抽象类或接口。抽象不依赖细节，细节应该依赖抽象。

- 2.开-闭原则**

    设计应当对功能扩展开放，对代码修改关闭。

- 3.合成复用原则

    继承关系最大的弱点是打破了封装，子类能够访问父类的实现细节，子类与父类之间紧密耦合，子类缺乏独立性，从而影响了子类的可维护性。

    因此，在设计中应避开继承关系的缺点，充分使用对象组合的优点。

- 4.单一职责原则 - 高内聚，低耦合

    如果类中的方法是一组相关的行为，则称该类是高内聚的，反之称为低内聚的。

    所谓低耦合就是尽量不要让一个类含有太多的其它类的实例的引用，以避免修改系统的其中一部分会影响到其它部分。

- 5.里氏代换原则 LSP

    继承必须确保父类所拥有的性质在子类中依然成立。

- 6.接口隔离原则 ISP

    一个类对另外一个类的依赖是建立在最小的接口上。使用多个专门的接口比使用单一的总接口要好。

- 7.迪米特法则 LoD - 最少知识原则 LKP

    一个对象应当对其他对象有尽可能少的了解。

<br>
*23种设计模式可以根据目的分成三大类：*

## 1.创建型模式 Creational

这些设计模式提供了一种在创建对象的同时隐藏创建逻辑 的方式，**而不是使用new直接实例化对象。**

这使得程序在判断针对某个给定实例需要创建哪些对象时更加灵活。

*创建型模式 包括*

- 工厂方法模式 Factory Method 
- 抽象工厂模式 Abstract Factory 
- 单例模式 Singleton 
- 建造者模式 Builder 
- 原型模式 Prototype 

## 2.结构型模式 Structural

这些模式关注对象之间的组合和关系。

旨在解决如何构建灵活且可复用的类和对象结构。

*结构型模式 包括*

- 适配器模式 Adapter 
- 桥接模式 Bridge 
- 过滤器模式* Filter、Criteria
- 组合模式 Composite 
- 装饰器模式 Decorator 
- 外观模式 Facade 
- 享元模式 Flyweight 
- 代理模式 Proxy 

## 3.行为型模式 Behavioral

这类模式关注对象之间的通信和交互。

旨在解决对象之间的责任分配和算法的封装。

*行为型模式 包括*

- 责任链模式 Chain of Responsibility 
- 命令模式 Command 
- 解释器模式 Interpreter 
- 迭代器模式 Iterator 
- 中介者模式 Mediator 
- 备忘录模式 Memento 
- 观察者模式 Observer
- 状态模式 State 
- 策略模式 Strategy
- 模板模式 Template
- 访问者模式 Visitor
- 空对象模式* Null Object

<br>此外，还有

## 4. J2EE模式

这些模式特别关注表示层（View），由Sun Java Center鉴定。

*J2EE模式 包括*

- MVC模式
- 业务代表模式 Business Delegate
- 组合实体模式 Composite Entity
- 数据访问对象模式 Data Access Object(DAO)
- 前端控制模式 Front Controller
- 拦截过滤器模式 Intercepting Filter
- 服务定位器模式 Service Locator
- 传输对象模式 Transfer Object