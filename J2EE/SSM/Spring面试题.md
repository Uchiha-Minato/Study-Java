# Spring面试题

## Spring概述

### 1. 什么是Spring？

Spring是一个轻量级的，JavaSE与JavaEE分层的一站式轻量级Java开发开源框架。

Spring最根本的使命是*解决企业级应用开发的复杂性，即简化Java开发。*

Spring有两个核心特性：DI和AOP。

为了降低Java开发的复杂性，Spring采取了4种关键策略：

- 基于实体类的轻量级（小，快，方便）和最小侵入性编程（侵入：反复修改代码）；

- 通过DI和面向接口实现松耦合；

- 基于切面和惯例进行声明式编程；

- 通过切面和模板减少样板式代码。

### 2. Spring框架的设计目标，设计理念，核心

**设计目标**：为开发者提供一个一站式轻量级应用开发平台

**设计理念**：
- 在JavaEE开发中，支持实体类（或者特指某些组件）和JavaBean开发方式，使应用面向接口开发；

- 通过Spring IoC实现依赖反转，将对象间的依赖关系交给IoC容器实现解耦。

**框架的核心**：IoC和AOP。

通过IoC容器管理实体类对象及其耦合关系；通过AOP以动态非侵入的方式增强服务。

### 3. Spring的优缺点？

**优点**

- 方便解耦，简化开发
- AOP面向切面编程
- 支持声明式事务
- 方便程序测试
- 方便集成各种优秀框架
- 降低JavaEE API的使用难度

**缺点**

- Spring原本是个很轻量级的框架，却给人感觉大而全
- Spring依赖反射，反射影响性能
- 使用门槛高，入门Spring需要较长时间

### 4. Spring的应用场景？

JavaEE企业级开发，包括SSH，SSM

价值：
- Spring是非侵入式框架，目标是是应用程序代码对框架依赖最小化；
- Spring提供一个一致的编程模型，使应用直接使用POJO开发，与运行环境隔离开来；
- Spring推动应用设计风格向 面向对象和面向接口开发 转变，提高了代码重用性。

### 5. Spring由哪些模块组成？

Spring大约有20个模块，由1300个不同的文件构成。

### 6. Spring框架中用到了哪些设计模式？

* 1. 工厂方法模式：BeanFactory，用于创建对象；
* 2. 单例模式、原型模式：Bean的scope，默认是singleton
* 3. 代理模式：AOP，用到了JDK动态代理和cglib字节码生成技术
* 4. 模板方法模式：用于解决代码重复的问题。后缀是Template的都是模板。
* 5. 观察者模式：发布-订阅模式。如ApplicationListener。

### 7. 详细讲一下核心容器（Spring Context应用上下文）模块

这是基本的Spring模块，提供spring框架的基础功能，BeanFactory是任何以spring为基础的应用的核心。

Spring Context提供了一个容器，通过xml配置文件或注解的方式，可以将各种组件（如Bean、Service、Controller等）注册到容器中，并管理它们的生命周期和依赖关系。

### 8. 使用Spring有哪些方式？

使用Spring有以下方式：

- 作为一个成熟的Spring Web应用程序；
- 作为第三方Web框架，使用Spring Frameworks中间层；
- 作为企业级Java Bean，它可以包装现有的POJO；
- 用于远程使用。 

***

## IoC

### 9. 什么是Spring IoC容器？

控制反转（IoC，Inversion of Control）把传统上由程序代码直接操作的对象的调用权交给容器，通过容器来实现对象组件的装配和管理。

IoC负责创建对象，管理对象（通过DI），装配对象，配置对象，并管理这些对象的生命周期。

### 10. IoC有什么作用

1) 解耦：通过DI的方式将对象之间的依赖关系从代码中解耦，开发人员只需要定义对象之间的依赖关系，不需要手动创建管理对象。

2) 管理对象生命周期：IoC容器负责管理对象的生命周期，包括对象的创建，初始化和销毁，开发人员只需要配置对象的生命周期相关的信息，容器会根据配置管理对象的创建和销毁

3) 提供依赖注入：IoC容器通过依赖注入的机制，自动将对象所需的依赖注入到对象中。开发人员只需定义对象的依赖关系，容器会自动解析和注入依赖，使对象能够正常运行。 

4) 提供AOP支持：IoC容器与AOP（Aspect-Oriented Programming，面向切面编程）紧密集成，提供了强大的AOP支持。通过AOP，开发人员可以将与业务逻辑无关的横切关注点（如日志记录、事务管理等）从业务代码中分离出来，提高代码的可维护性和重用性。

### 11. IoC的优点

- 松耦合：开发人员只需定义对象的依赖关系，无需关注对象的创建和管理。

- 易测试性：有IoC容器的存在，通过依赖注入，可以方便地替换依赖的测试对象。

- 可维护性：通过IoC容器进行对象创建和依赖管理，当需要更改依赖关系时，只需调整容器的配置，无需修改大量代码。

- AOP支持：IoC与AOP紧密集成，提供了强大的AOP支持。AOP可以将与业务无关的横切关注点从业务代码中分离，提高了可维护性和重用性。

- 可扩展性：需要引入新组件或替换组件时，只需调整容器配置。

### 12. Spring IoC的实现机制

工厂方法模式 + 反射。

在工厂方法中使用反射创建对象实例。

### 13. Spring IoC支持哪些功能？

- 对象的创建和管理
- 依赖注入
- 配置管理
- 生命周期管理
- AOP支持
- 事件机制

### 14. BeanFactory和ApplicationContext有什么区别？ 

BeanFactory和ApplicationContext都是Spring容器。

*ApplicationContext是BeanFactory的孙接口。*

```
public interface ApplicationContext extends ..., BeanFactory, ...
```

**BeanFactory实例化bean的方式：懒汉式，用到才实例化；**

**ApplicationContext实例化bean的方式：饿汉式，启动时就全部实例化。**

ApplicationContext作为BeanFactory的派生，提供了更完整的框架功能：

- 继承MessageSource，支持国际化（支持各国家语言的切换）；
- 统一的资源文件访问方式；
- 提供在监听器中注册bean的事件（事件监听）；
- 同时加载多个配置文件；
- 载入多个（有继承关系的）上下文，使得每一个上下文都专注于一个特定的层次。

### 15. Spring如何设计容器的，BeanFactory和ApplicationContext的关系详解

### 16. ApplicationContext通常的实现是什么

- ClassPathXmlApplicationContext：从类路径中加载配置文件，适用于xml配置的应用程序。

- WebXmlApplicationContext：用于基于xml配置的web应用程序上下文，可以加载web应用程序上下文。

### 17. 什么是DI（依赖注入）

程序运行过程中，如果需要创建一个对象，无需在代码中new创建，而是依赖外部注入。

组件之间的依赖关系由容器进行管理。

### 18. DI的基本原则

应用组件应当专心实现业务功能，而不是进行依赖关系的查找和创建。

组件之间的依赖关系的管理和组件装配应当全权交给IoC容器。

### 19. DI有什么优势

- 查找定位操作与应用代码完全无关；
- 不依赖于容器的API，可以很容易地在任何容器以外使用应用对象；
- 不需要特殊的接口，绝大多数对象可以做到完全不必依赖容器。

### 20. 有哪些DI方式

### 21. 构造器依赖注入和Setter方法注入的区别

***

## Spring Beans

### 22. 什么是Spring Beans？

### 23. 一个Spring Bean的定义包含什么？

### 24. 如何给Spring容器提供配置元数据？Spring有几种配置方式？

### 25. Spring配置文件包含了哪些信息？

### 26. Spring基于xml注入bean的几种方式

### 27. 怎样定义类的作用域？

### 28. 解释Spring支持的几种bean的作用域

### 29. Spring框架中的单例bean是线程安全的吗？

### 30. Spring如何处理线程并发问题？

### 31. 说一下Bean的生命周期

### 32. 哪些是重要的bean生命周期方法？能否重载？

### 33. 什么是Spring的内部bean？什么是Spring inner beans？

### 34. 在Spring中如何注入一个Java集合？

### 35. 什么是bean自动装配？

### 36. 使用@Autowired自动注入的过程是什么

### 37. 自动装配有哪些局限性？

### 38. 你可以在Spring中注入一个null和一个空字符串吗？

****

## Spring注解

### 39. 什么是基于Java的Spring注解配置，给一些注解例子

### 40. 怎样开启注解装配？

### 41. @Component,@Controller,@Repository,@Service有什么区别

### 42. @Required 有什么用

### 43. @Autowired 有什么用

### 44. @Autowired和@Resource之间的区别

### 45. @Qualifier 有什么用

### 46. @RequestMapping 有什么用

****

## Spring数据访问

### 47. 解释对象/关系映射集成模块

### 48. Spring支持的事务管理类型，Spring事务实现方式有哪些？

### 49. Spring事务的实现方式和实现原理

### 50. 说一下Spring的事务传播行为

### 51. 说一下Spring的事务隔离

### 52. 你更倾向用哪种事务管理类型

***

## AOP

### 53. Spring AOP 和 AspectJ AOP有什么区别？AOP有哪些实现方式？

### 54. JDK动态代理和cglib动态代理有什么区别

### 55. 如何理解Spring的代理

### 56. 解释一下AOP里面的几个名词

### 57. Spring在运行时通知对象

### 58. Spring只支持方法级别的连接点

### 59. 在Spring AOP中，关注点和横切关注的区别是什么

### 60. 
