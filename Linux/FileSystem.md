# Linux文件系统

Linux操作系统的文件系统分为两大类：
- 根文件系统 the root file system
- 附加文件系统

## 文件类型

- 一般文件 ordinary

    文本文件 二进制文件

- 目录文件 directory
- 特殊文件 special
- 符号链接文件 symbolic links

|符号|文件类型|
|---|------|
|-|普通文件（Regular file）|
|d|目录文件（directory）|
|b|块设备文件（block device）|
|c|字符设备文件（character）|
|p|管道设备（FIFO）|
|l|符号链接文件（Symbol link）|
|s|套接字（socket）|

*使用`ls -l`查看目录，输出的第一列字符就是文件类型。*

### 软链接 & 硬链接

#### 软链接

*软链接即符号链接Symbol link。*

创建：`ln -s [指向的路径] [软链接名称]`

```
[root@test test]# ln -s /dev/sda1 devlink
[root@test test]# ls -l
[root@test test]# lrwxrwxrwx. 1 root root 9 Aug 29 11:20 devlink -> /dev/sda1
```

最前面的'l'表示这是一个符号链接。

性质：

- 软链接既对目录生效也对文件生效；
- 进入这个链接就等于进入了被链接的目录；
- **软链接文件的inode信息与原文件不同**；
- 删除原文件，软链接不被删除，但是链接失效。

#### 硬链接 Hard link

创建：`ln -n [指向的文件] [硬链接名称]`

```
[root@test test]# ln -n abc cde
[root@test test]# ls -l
[root@test test]#
total ...
716713 -rw-r--r--. 1 root root 29 Sep  6 10:19 abc
716713 -rw-r--r--. 1 root root 29 Sep  6 10:19 cde
```

性质：
- 硬链接仅对文件生效；
- 相当于完全复制了一个文件，inode完全相同；
- 删除链接文件或原文件，对另一个文件完全没有影响。

## ls命令

基本使用方式

    ls [options] [directory]

|选项（不全）|说明|
|---|---|
|-l|显示长列表，详细信息|
|-a,--all|显示全部文件，包括隐藏|
|-A|跟-a差不多，不显示`.`和`..`|
|-d|仅列出本目录`.`|
|-G|在长列表中，不输出组名|
|-h,--human-readable|在长列表中将文件大小以KMG的方式打印|
|-si|配合`-h`一起使用，输出数据大小进制为1000|
|-i,--inode|打印每个文件的索引节点inode|
|-s,--size|打印在块中为每个文件所分配的空间|
|-r,--reverse|倒序输出（按照文件首字母ASCII排序）|
|-R,--recursive|递归地列出所有的子目录|
|-t|按照时间顺序打印，新的在前面|


### 文件名颜色的含义

使用ls命令查看文件时，命令行会根据不同的文件类型输出不同的颜色。

![dircolor](./pic_linux/dircolor.png)

参数说明：`ls -ahl`

- -a = --all 所有类型文件
- -h = --human-readable 人可读，把文件大小写成K、M、G等
- -l (long)长列表信息

|颜色|说明|
|---|---|
|正蓝色|目录|
|白色|普通文件|
|绿色|可执行文件|
|红色|压缩文件|
|浅蓝色|链接文件|
|黄色|设备文件|
|灰色|其他文件|
|红底白字|用户粘滞位文件`u+s`|
|红底闪烁白字|符号链接指向的的不存在的文件|
|黄底黑字|组粘滞位文件`g+s`|
|蓝底白字|其他用户粘滞位文件，且无其他可写性|
|绿底蓝字|其他可写性目录`o+w`，没有粘滞位|
|绿底黑字|具有粘滞位和其他可写性的目录`+t,o+w`|
|黑底黄字|管道文件|
|粗体品红字|套接字文件/door?（不知道什么东西）|
|粗体黑底黄字|[块，字符]设备驱动程序|
|粗体黑底红字|符号链接到不存在的文件，或不可启动的文件|
|红底黑字|具有功能的文件|

### 粘滞位

是一种特殊的权限位，用于防止文件或目录被非所有者删除。

这个概念在目录（尤其是/tmp目录）中非常有用，因为它可以防止普通用户删除或移动其他用户的文件。


## 根目录结构

使用以下命令进入根目录：

    cd /

![rootpath](./pic_linux/rootpath.png)

|文件|类型|说明|
|--|--|--|
|bin(ary)|软链接，链接到/usr/bin|存放系统命令的目录|
|boot|目录|系统启动目录，保存如内核文件和启动引导程序等|
|dev(ice)|目录|保存设备文件|
|etc(etera)|目录|保存配置文件，默认使用rpm安装的应用的配置文件都在这|
|home|目录|普通用户家目录，普通用户`cd ~`就到这|
|lib(rary)|软链接，链接到/usr/lib|保存系统调用的函数库|
|lib64|软链接，链接到/usr/lib64|64位系统调用函数库|
|media|目录|可卸载媒介挂载目录，如U盘和光盘|
|mnt(mount)|目录|挂载目录|
|opt|目录|第三方安装软件保存的位置|
|proc(esses)|虚拟文件系统（大小为0）|主要保存进程状态，系统内核等，存储在内存，重启后消失（重置）|
|root|目录|超级用户的家目录，超级用户`cd ~`到这|
|run|目录|用于存储系统运行时的临时文件和只在该时段可用的文件，例如进程ID文件和锁文件|
|sbin|软链接，链接到/usr/sbin|存放系统命令的目录，只有超级用户可以执行|
|srv(service)|目录|服务数据目录|
|sys|虚拟文件系统|主要保存内核信息|
|tmp(temp)|临时目录|保存临时文件，重启后一般不保留|
|usr(unix shared resources)|目录|系统软件资源目录|
|var|目录|动态数据保存位置。主要保存缓存，日志及软件运行产生的文件|

## 一些重要文件（目录）的说明

### /usr

#### /usr/bin /usr/sbin

    非必须的普通用户可执行命令，sbin是root权限才能使用

#### /usr/include

    标准头文件

#### /usr/lib

    bin和sbin的库文件

#### /usr/src

    内核源码

### /etc

#### /etc/fstab

存放开机之后自动挂载的目录

通过引用，可访问的文件系统维护在 `/dev/disk` 下。

![fstab](./pic_linux/fstab.png)

从左到右:

- filesystem

    设备文件全路径

- dir

    挂载点

- type

    文件系统类型

- options

    选项。比如：

    ro：只读；rw：读写；async：异步

- dump

    是否备份

    0为忽略，1为备份

- pass

    是否检查磁盘
    
    0：不检查 1：根目录（默认检查） 2：其他需要检查的目录

#### /etc/yum.repos.d

    CentOS RHEL的yum源保存位置


## lsblk - 列出块设备信息，list block device

lsblk命令默认将所有的块设备（特别是RAM硬盘）以树形结构列出。

![lsblk](./pic_linux/lsblk.png)

基本用法：

    lsblk [options] [device...]

输出表头解读：

- **NAME 块设备的名称**
    
    第一列一般是磁盘(sd)或光盘(sr)

    还有虚拟磁盘(vda)，IDE硬盘(hda)
    
    按照磁盘顺序，sd[a...],sr[0...]

    第二列一般是是分区名称，比如上图sda1是引导分区，sda2是数据分区

    第三列一般是是逻辑卷，比如centos-root

- **MAJ（主设备号）:MIN（次设备号）** 

    主设备号通常用于标识设备类型。

    所有的硬盘一般为8，光盘一般为11.

    次设备号用于区分同一类型的不同设备。

- **RM 可移动**

    0为不可移动，1为可移动。

- **SIZE 大小**

    块设备的大小。默认格式为human-readable

- **RO 只读(ReadOnly)**

    1为只读块设备。

- **TYPE 类型**

    disk - 硬盘
    part - 分区
    lvm - 逻辑卷管理(Logical Volume Manager)
    rom - 光盘
    vda - 虚拟磁盘
    hda - IDE硬盘

- **MOUNTPOINT 挂载点**

    比如逻辑卷centos-root就挂载到了根目录'/'下。

选项说明

|选项（不全）|说明|
|---|---|
|-a,--all|打印所有设备|
|-b,--bytes|将设备空间大小以byte数字打印|
|-d,--nodeps|简洁打印，只打印第一列设备|
|-e <list>,--exclude <list>|按主机号排除设备|
|-f,--fs|打印文件系统详细信息|
|-p,--paths|打印设备全路径|
|-P,--pairs|以 键="值" 的格式打印|



