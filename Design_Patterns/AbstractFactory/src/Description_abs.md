# 说明

通过一个简单的问题讲述抽象工厂模式所涉及的各个角色。

## 建立一个系统，该系统可以为用户提供

**西服套装** **牛仔套装**

套装包括*上衣和裤子。*

### 抽象产品 Abstract Product

- Clothes 衣服抽象类
- Trousers 裤子抽象类

### 具体产品 Concrete Product

- SuitClothes 西服
- CowboyClothes 牛仔上衣
- SuitTrousers 西裤
- CowboyTrousers 牛仔裤

### 抽象工厂 Abstract Factory

- ClothesFactory 定义方法返回上衣和裤子实例

### 具体工厂 Concrete Factory

- BeijingFactory 北京服装厂
- ShanghaiFactory 上海服装厂
