# Linux常用命令

环境：Ubuntu 20.04 LTS / CentOS 8 Stream

## Linux命令的基本格式

    command 参数1 参数2 ...

*命令区分大小写。*

## 常用基本命令

|解释|命令|举例|
|----|----|----|
|帮助|man|man date|
|查看日期|date|date|
|显示日历|cal|cal 1998|
|显示大字|banner|banner "ABCD"|
|计算器|bc|bc|
|修改密码|passwd|passwd|
|查看谁在使用|who|who|
|清除屏幕|clear|clear|

### 注销

    $ exit 或 直接按键Ctrl+d

### 切换超级用户

    $ su

*Ubuntu20.04 LTS系统第一次进入root用户需要设置密码。*

    $ sudo passwd root

退出root用户：

    # exit

## Linux文件系统

Linux操作系统的文件系统分为两大类：
- 根文件系统 the root file system
- 附加文件系统

### 文件类型

- 一般文件 ordinary

    文本文件 二进制文件

- 目录文件 directory
- 特殊文件 special
- 符号链接文件 symbolic links

|符号|文件类型|
|---|------|
|-|普通文件|
|d|目录文件|
|b|块设备文件|
|c|字符设备文件|
|p|管道设备|
|l|符号链接文件|

例如：

![drwxr](./pic_linux/drwxr.png)

### 切换路径

    $ cd [相对路径][绝对路径]

根目录和用户目录：

    $ cd / 切换到根目录
    $ cd ~ 切换到用户目录 相当于 cd /home/minato

切换到当前/上级目录：

    $ cd . 切换到当前目录
    $ cd .. 切换到当前项目的父目录

## 文件存取权限

|权限|普通文件的存取权限|目录的存取权限|
|----|----|----|
|r|可读|可读文件名|
|w|可写|能建立和删除文件，可以改变文件名|
|x|可执行|能使用该目录下的文件(如cd命令)搜索文件|

有三种类型的用户可以存取文件：

- owner

    文件的拥有者。

- group

    文件所在的工作组。

- other

    其他用户。

每种类型的用户都有rwx三种存取权限。

### 修改存取权限

    $ chmod [用户类型=权限] [文件名] 
    例如
    chmod u=rwx,g=rx,o=rx abc.txt

    $ chmod [权限数] [文件名]
    例如
    chmod 755 abc.txt

    $ chmod [用户类型][+/-][权限] [文件名]
    例如
    chmod u-x, g+w abc

*对于abc文件，去除 用户执行 的权限，加入 组可写 的权限。*

    $ chmod a[+/-][权限] [文件名]
    例如
    chmod a+r abc

*对于abc文件，给所有用户添加读的权限。*

### Linux文件系统常用命令

|解释|命令|举例|
|----|----|----|
|显示当前目录|pwd|pwd|
|改变目录|cd|cd /usr|
|进入/home|cd|cd|
|创建目录|mkdir|mkdir abc|
|删除空目录|rmdir|rmdir abc|
|删除目录及其内容|rm -r|rm -r abc|
|显示目录内容|ls|ls -l(文件长列表)|
||ls|ls -a(所有类型文件)|
|显示文本文件内容|cat|cat test1.c|
|分屏显示文本内容|more|more test2.c|
|拷贝文件|cp|cp file1 file2|
|移动（重命名）文件|mv|mv test1.c abc.txt|
|删除文件|rm|rm abc.txt|

