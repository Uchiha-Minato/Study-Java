# Helm Chart

Helm install charts into K8s.

**用于简化K8s应用部署，省去了一直apply的繁琐步骤。**

Helm Chart与K8s的关系相当于：

- yum .rpm 与 CentOS

各种云平台的模板应用就是Helm Chart。

## Helm

***本质上就是一个K8s包管理器。***

Helm在v3版本之后，直接通过`kubeconfig`连接`apiserver`，简化安全模块，降低使用难度

### 主要能力：

- 简化K8s应用部署
- 高度可配置
- 版本控制
- 模块化
- 应用程序库
- 插件系统

### 工作流程

![helm](./k8s_pic/Helm_workflow.png)

于是，Helm的基本使用步骤为

- 开发者首先创建并编辑chart的配置；
- 接着打包发布到Helm仓库；
- 管理员使用Helm命令从仓库下载依赖；
- Helm根据下载的依赖进行自动部署。

### 相关概念

|概念|描述|
|--|--|
|Charts|一个Helm包，包含了运行一个应用所需的镜像、依赖、资源定义等|
|Repository|仓库，存储Charts的地方|
|Release|Charts在K8s集群运行的一个实例，同一个Charts可以被多次安装，即为不同的Release|
|Value|Charts的参数，用于配置K8s对象|
|Template|使用Go模板语言自动生成K8s对象的定义文件|
|Namaspace|K8s中用于隔离资源的逻辑分区|

## Helm安装

首先上官网下载压缩包：
```
https://github.com/helm/helm/releases
```

比如下载下来Linux x64系统的压缩包，名为
`helm-v3.15.3-linux-amd64.tar.gz`

传入K8s Master机器的一个位置，解压：

```bash
tar -zxvf helm-v3.15.3-linux-amd64.tar.gz
```

解压后的目录名为`linux-amd64`，下面有三个文件：

```bash
[root@node ~]# ls -l linux-amd64/
total 51236
-rwxr-xr-x 1 1001 docker 52445336 Jul 10 15:29 helm
-rw-r--r-- 1 1001 docker    11373 Jul 10 15:34 LICENSE
-rw-r--r-- 1 1001 docker     3483 Jul 10 15:34 README.md
```

helm为二进制可执行文件。将这个文件拷到/usr/bin下

```bash
cp linux-amd64/helm /usr/bin
```

输入如下命令

```bash
helm help
```

如果输出helm的使用帮助，即为安装完成

配置命令自动补全：
```bash
yum install bash-completion

source <(helm completion bash)
echo "source <(helm completion bash)" >> ~/.bashrc
```

## Helm使用

### 配置Charts公共仓库

```bash
# 官方仓库
helm repo add stable https://charts.helm.sh/stable

# azure仓库
helm repo add stable http://mirror.azure.cn/kubernetes/charts
```

出现：`"stable" has been added to your repositories`即为添加成功，“stable”是稳定版镜像仓库

可以添加多个Charts仓库。

使用`helm repo list`查看已经添加成功的仓库。

更新仓库本地缓存

```bash
helm repo update

[root@node ~]# helm repo update
Hang tight while we grab the latest from your chart repositories...
...Successfully got an update from the "stable" chart repository
Update Complete. *Happy Helming!*
```

查找仓库，安装Chart（举例）

```bash 
helm search repo mongodb

helm install mongodb-helm stable/mongodb
```

### 指定需要连接的K8s集群

在使用 Helm 管理 Kubernetes 集群的时候，需要指定对应的 Kubernetes 集群。

Helm 不像 kubectl 会默认从本地 kubeconfig 配置文件读取当前的 Kubernetes 集群信息。

Helm在执行各种命令时，需要知道这些命令要操作的是哪一个Kubernetes集群。Helm没有默认的集群配置。

没有目标集群，Helm命令将无效。

```bash
export KUBECONFIG=/root/.kube/config
echo "KUBECONFIG=/root/.kube/config" >> /etc/profile
source /etc/profile
```

