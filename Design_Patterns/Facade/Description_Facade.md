# 说明

通过一个简单的子系统讲述外观模式所涉及的各个角色。

## 报社系统

### 子系统 Subsystem

有三个类：
- CheckWord 
    
    负责检查广告内容含有的字符个数。
- Charge
   
    负责计算费用。
- TypeSetting
    
    负责对广告进行排版。

### 外观类 Facade

- ClientServerFacade 

  用户用这个类在报纸上登上广告。