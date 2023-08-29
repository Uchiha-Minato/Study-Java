# 说明

通过一个简单的问题讲述享元模式所涉及的各个角色。

## 创建若干个圆。

ShapeFactory 有一个 Circle 的 HashMap，其中键名为 Circle 对象的颜色。

无论何时接收到请求，都会创建一个特定颜色的圆。

ShapeFactory 检查它的 HashMap 中的 circle 对象，如果找到 Circle 对象，则返回该对象，

否则将创建一个存储在 hashmap 中以备后续使用的新对象，并把该对象返回到客户端。

### 享元接口 Flyweight

- CircleShape

### 享元工厂 FlyweightFactory

- CircleFactory

### 具体享元 Concrete Flyweight

- Circle

