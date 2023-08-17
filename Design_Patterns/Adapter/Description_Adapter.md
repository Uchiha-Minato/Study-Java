# 说明

用一个简单的例子说明适配器模式所涉及的各个角色。

## 适配不同种电器的插座

某用户有*一台洗衣机*和*一台电视机*

电视机 -> 两项插头；洗衣机 -> 三项插头

**现在用户想用新买的三相插座来使用洗衣机和电视机。**

即要为电视机创建适配器。

### 目标 Target

- ThreeElectricOutlet 三孔输出插座

### 被适配者 Adaptee

- TwoElectricOutlet 双孔输出插座

### 适配器 Adapter

- ThreeElectricAdapter 三转二适配器
