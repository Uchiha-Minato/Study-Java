# 说明

通过一个简单的问题讲述如何使用组合模式。

## 描述连队的树形军士结构。

一个连队由1个连长，2个排长，6个班长和90个士兵组成，一共99人

连长直接指挥2个排长，
每个排长直接指挥3个班长，
每个班长直接指挥15个士兵。

连长的军饷每月5000，排长4000，班长2000，士兵1000

### 抽象组件 Component

- MilitaryPerson 军人抽象类

### Composite结点 Component Node

- MilitaryOfficer 军官类

### Leaf结点 Leaf Node

- MilitarySoldier 士兵类

