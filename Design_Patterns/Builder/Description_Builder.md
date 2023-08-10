# 说明

用一个简单的例子说明建造者模式所涉及的各个角色。

## 创建容器。

容器含有**按钮**、**标签**和**文本框**等组件。

*不同的用户对容器的要求不同。*

如：
- 包含组件不同
- 组件的顺序不同

### 产品 Product

- PanelProduct 容器产品

### 抽象建造者 Abstract Builder

- IBuilder

### 具体生成器 Concrete Builder

- ConcreteBuilder1 
- ConcreteBuilder2

### 指挥者 Director

- Director

