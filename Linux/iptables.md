# iptables防火墙

iptables自上而下有由：

- Tables表
- chain 链
- Rules 规则 

## iptables内建表（4种）

表用于提供特定功能。

### Filter 包过滤

iptables的默认表，有3种内建链：

- INPUT 

    处理来自外部的数据

- OUTPUT 

    处理向外发送的数据

- FORWARD 

    将数据转发到本机其他网卡上

### NAT 网络地址转换

- PREROUTING

    处理刚到本机并在路由转发前的数据包，通常用于DNAT。

    转换目的地IP：DestinationNAT

- POSTROUTING

    处理即将离开本机的数据包，通常用于SNAT。

    转换源IP：SourceNAT

- OUTPUT

    处理本机产生的数据包。

### Mangle 包重构

指定如何处理数据包，改变TCP头部的QoS。

链：

    PREROUTING OUTPUT FORWARD INPUT POSTROUTING

### Raw 数据跟踪修改

用于处理异常。

链：

    PREROUTING OUTPUT

**表之间的优先级：**

    Raw >> Mangle >> NAT >> Filter

## 链 Chain

数据包下来时的执行顺序。

1. INPUT——进来的数据包应用此规则链中的策略

2. OUTPUT——外出的数据包应用此规则链中的策略

3. FORWARD——转发数据包时应用此规则链中的策略

4. PREROUTING——对数据包作路由选择前应用此链中的规则
（有的数据包进来的时侯都先由这个链处理）

5. POSTROUTING——对数据包作路由选择后应用此链中的规则
（所有的数据包出去的时侯都先由这个链处理）

## Rules 规则

包含一个条件和一个目标（target）。

#### 目标（target）

- ACCEPT 

    放行

- DROP

    丢弃

- RETURN

    停止执行当前链中后续规则，并返回到调用链中

- REJECT

    拒绝

- QUEUE

    将数据包移交

- SNAT

    转换源IP，SourceNAT
    
    数据包将要出去时，修改源地址，目的地址不变，在本地建立NAT表项。

    在数据返回时，根据NAT表将目的地址数据改为数据发送时的源地址，并发送给主机。

    *用于解决内网机器共用一个公网地址上网的问题。*

- DNAT

    转换目的IP，DestinationNAT

    IP包刚到且经过本机路由之前，重新修改目标地址，源地址不变，在本地建立NAT表项。

    在数据返回时，将源地址修改为数据来时的目标地址，并发送给远程主机。

    *用于隐藏真实的后端地址。*

- REDIRECT

    是DNAT的一种特殊形式，将数据包转发到本地host上，无论ip头部目的地址是什么，方便在本机做端口转发。


## iptables选项说明

基本用法：

    iptables [-t 表] [选项] [链] [规则]

|选项|用法|说明|
|---|---|---|
|-A,--append|-A chain|向链中添加一条规则|
|-D,--delete|-D chain [rulenum]|删除规则|
|-I,--insert|-I chain [rulenum]|插入规则|
|-R,--replace|-R chain rulenum|替换规则|
|-L,--list|-L|列出所有规则|
|-F,--flush|-F [chain]|删除选中链的所有规则|
|-X,--delete-chain|-X [chain]|删除用户自定义链|
|-P,--policy|-P chain target|修改链的目标为target|
|-E,--rename-chain|-E oldname newname|修改链名|
|-4|-4|ipv4|
|-6|-6|ipv6|
|-p,--protocal|-p [protocal]|协议|
|-i,--in-interface|-i ens33|网络接口名|
|-j,--jump|-j target|规则的目标|
|-t,--table|-t table|指定表名|
|-v,--verbose|-v|详细输出|
|-n,--numeric|-n|将ip和端口以数值输出|
|-s,--source|-s address[CIDR]|数据包来源，网段或地址|
||-s !address[CIDR]|指定地址之外的地址|
|--dprot|--dport port|目标端口|
|--sport|--sport port|源端口|
|-d,--destination|-d address[CIDR]|目的地址|
|-m,--match|-m match|扩展匹配|

## 应用举例

### 禁ping

ping的原理就是发送icmp数据包。

    iptables -I INPUT -p icmp --icmp-type 8 -j DROP

icmptype

- 0 回显应答
- 8 回显请求

### 对特定机器开放端口

比如说对`192.168.1.1`开放tcp 22端口

    iptables -A INPUT -p tcp --dport 22 -s 192.168.1.1 -j ACCEPT

对处于`192.168.130.0/24`网段下的所有机器开放tcp 8080端口

    iptables -A INPUT -p tcp --dport 8080 -s 192.168.130.0/24 -j ACCEPT

对处于`192.168.130.0/24`网段下的所有机器开放多端口：

    iptables -A INPUT -p tcp -m multiport --dport 22,80,2222 -s 192.168.130.0/24 -j ACCEPT

### 允许转发

    iptables -P FORWARD ACCEPT

### 删除规则

删除上面配置的开放22端口：

    iptables -D INPUT -p tcp --dport 22 -s 192.168.1.1 -j ACCEPT

清空指定链上的所有规则：

    iptables -F chain

删除指定链，需链中没有任何规则且没被其他链引用

    iptables -X chain

<br>

# ipset - 管理员IP集合工具

*Administration tool for IP sets*

    [root@test ~]# which ipset
    /usr/sbin/ipset

对一个ipset设置规则，整个ipset里的ip都对这个规则生效。

基本用法：

    ipset [选项] 命令 [命令选项]

## 命令/选项说明

|命令/选项|用法|说明|
|--|--|--|
|n, create|create/n setname typename [create options]|创建一个以setname和指定类型标识的集|
|add|add setname add-entry [add options]|添加一个网段或IP到指定ipset|
|del|del setname del-entry [del options]|从指定ipset中删除一个IP或网段|
|test|test setname test-entry [test options]|查看ip或网段是否在指定ipset中|
|x, destroy|destroy/x setname|删除指定ipset，如果没给参数则删除所有|
|||假如ipset有引用，则不会删除任何ipset|
|list|list [setname] [options]|列出指定集合的头数据和项，如果没有给出，则列出所有集合的头数据和项|
|save|save [setname]|将给定的集合保存，如果没有指定集合，则将所有集合保存为restore可以读取的格式。选项-file可以用来指定文件名而不是标准输出
|||-file的保存方式与Redis持久化的AOF类似|
|restore|restore [-file]|恢复以save命令产生的已保存的session|
|flush|flush [setname]|清除指定集合内的所有网段和IP，未指定则对所有现有集合生效|
|e, rename|rename/e setname-from setname-to|给指定ipset改名|
|w, swap|swap/w setname1 setname2|交换两个集合里的所有字段|
||||
|-!, -exist||当要创建完全相同的集合、添加已经添加的条目或删除丢失的条目时，忽略错误|
|-o, -output|{plain / save / xml}|选择输出的格式|
|-q, -quiet||忽略所有输出，如果命令没有执行成功则还是会报错|
|-n, -name||只列出集合名|
|-t, -terse||列出集合头部和集合名|
|-f, -file|-f/-file filename|指定文件名|

## 集合类型（typename）说明

|类型|举例|说明|
|--|--|--|
|--|ipset n test1 [typename]|--|
|--|ipset add test1|--|
|bitmap||比特表存储|
|hash||哈希存储|
|bitmap:ip|192.168.1.1|--|
|bitmap:ip,mac|192.168.1.1,01:02:03:04:05:06|--|
|bitmap:port|80|--|
|hash:ip|192.168.1.1|--|
|hash:mac|01:02:03:04:05:06|--|
|hash:ip,mac|192.168.1.1,01:02:03:04:05:06|--|
|hash:net|192.168.1/24|--|
|hash:net,net|192.168.1/24,192.168.2/24|地址范围生效|
|hash:ip,port|192.168.1.1,tcp:443|--|
|hash:net,port|192.168.1/26,8080|--|
|hash:ip,port,ip|192.168.1.1,80,10.0.0.1|--|
|hash:ip,port,net|192.168.1.1,80,192.168.2/24|--|
|hash:ip,mark|192.168.1.1,0x63|mark是对ip的标记值|
|hash:net,port,net|192.168.1/24,80,192.168.2/24|--|
|hash:net,iface| 192.168.1.0/24,ens33|iface是要走的网卡|

