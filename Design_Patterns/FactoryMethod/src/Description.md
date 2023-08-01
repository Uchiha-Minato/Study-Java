# 说明

通过一个简单的问题讲述工厂方法模式。

## 圆珠笔与笔芯。

**用户希望自己的圆珠笔能使用不同颜色的笔芯。**

### 抽象产品

- PenCore 笔芯

### 具体产品

- RedPenCore
- BluePenCore
- BlackPenCore

### 抽象工厂

- CoreCreator

### 具体工厂

- RedCoreCreator
- BlueCoreCreator
- BlackCoreCreator