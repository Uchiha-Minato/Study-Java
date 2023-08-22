# 说明

通过一个简单的问题讲述装饰模式及其核心角色。

## 增强小鸟的飞行功能。

假设系统中有一Bird抽象类，以及其一个子类Sparrow。

Sparrow类实现了Bird类的fly()方法，使得麻雀对象调用此方法可以连续飞行100米。

现在用户需要两只鸟（对象），无论是哪种鸟都可以，但必须能连续飞行150米和200米。

*使用装饰模式，无需修改系统代码，只需添加装饰即可。*

### 抽象组件 Component

- Bird 鸟抽象大类

### 具体组件 ConcreteComponent

- Sparrow 麻雀类

### 装饰 Decorator

- Decorator 装饰类

### 具体装饰 ConcreteDecorator

- ElectricWings 电子翅膀