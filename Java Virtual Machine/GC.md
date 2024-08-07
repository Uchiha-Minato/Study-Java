# JVM垃圾回收 - Garbage Collect

    1.如何判断对象可以回收
    2.垃圾回收算法
    3.分代垃圾回收
    4.垃圾回收器
    5.垃圾回收调优

*JVM GC过程：*

1.**先判断可以回收的对象**

    没有被GC ROOT直接或间接引用的对象可以被回收

2.**JVM将堆空间分为 新生代Young Gen，老年代Old Gen**

    其中新生代又分为 From,To,Eden区

3.**new出来的对象出生在Eden区，当E区被填满时触发Minor GC**

    Minor GC使用复制算法
    Eden区不被标记的对象复制到From区，然后将Eden区与To区剩下的对象全部删除
    下一次触发Minor GC时，Eden区与From区的对象重复上述复制操作到To区
    如此循环往复使得From区与To区交替使用
    
        优点：比直接把堆内存分两块复制利用率高
        From,To,Eden的大小比默认为 1:1:8

4.**如果对象在一次Minor GC中存活，则年龄+1。当年龄=15时，对象会被复制到Old区**

*大对象（如一个长度为几万的int数组）也会被直接存放在Old区*

5.**当Old区满时，触发Full GC**

    触发Old GC的同时一般会伴随着Minor GC
    触发Full GC时，整个Java程序将会暂停到GC结束
    主要使用 标记清除 或 标记整理 


## 1.如何判断对象可以回收

### 1.1 引用计数法（Python使用）

    一个对象被其他变量引用，就让这个对象的一个引用计数+1；
    如果某一个变量不在引用它，计数-1；
    计数为0时则回收。

    不足：若是有两个对象互相引用，那么这两个对象都不会被GC。

### 1.2 可达性分析算法（JVM使用）

*扫描堆中的对象，看是否能够沿着 以GC Root对象为起点的引用链找到该对象，若找不到，则代表可以回收。*

    GC Root: 可达性算法的起点

哪些对象可以作为GC Root？ 
<br>- 工具：Eclipse提供的Memory Analyzer(MAT)

    Java虚拟机运行时核心类引用的对象
    调用操作系统方法时（本地方法栈）引用的Java对象
    正在加锁的对象(synchroized) 
    活动线程中（虚拟机栈栈顶栈帧）所引用的对象

### 1.3 四种引用

    java.lang.ref.Reference<T>
    强引用，软引用，弱引用，虚引用，终结器引用

![reference](/Pictures/reference.png)

*实线箭头表示强引用，虚线箭头代表其他4种*

#### 强引用

平时用的 变量 = 对象 就是强引用。

    只要沿着GC Root的引用链能找到它，就不会被回收；
    当GC Root对它的强引用都断开时它就会被回收。

**只有所有的GC Root对象都不通过强引用引用该对象，才能被回收**

#### 软、弱引用

    java.lang.ref.SoftReference<T> //软引用类
    java.lang.ref.WeakReference<T> //弱引用类

当GC发生时，对象没有被直接的强引用所引用，那么它就有可能被回收。

- 软引用：GC触发后，内存依然不够，且软引用对象没有被强引用，就会把软引用的对象回收。

- 弱引用：只要发生了GC，不管内存够不够，都会将弱引用回收。

*软、弱引用都可以配合引用队列工作。*

    当引用的对象被释放掉后，软引用和弱引用本身也是一个对象，都会占用内存。
    若创建时分配了一个引用队列，在它所引用的对象被回收时，软弱引用本身就会进入到引用队列。
    需要进一步释放空间时，就遍历引用队列，找到并释放空间。

#### 虚引用 - *必须配合引用队列使用*

用于跟踪对象是否已被垃圾回收器回收。与弱引用和软引用不同，虚引用不会影响对象的生命周期，但可以在对象被回收后收到通知。

    java.lang.ref.PhantomReference<T> //虚引用类
    java.lang.ref.ReferenceQueue<T> //引用队列类

**主要配合ByteBuffer使用**

    在虚引用的对象被GC时，虚引用本身进入引用队列
    间接地用一个线程（Reference Handler）调用虚引用Cleaner中的方法:
        Unsafe.freeMemory()来释放直接内存

#### 终结器引用 - *必须配合引用队列使用* ，不推荐

    java.lang.Object
        finallize()方法
    当引用的对象被GC时，系统创建终结器引用并加入引用队列
    后续会有一个处理引用队列的线程时不时查看队列
    
    若存在终结器引用，线程就会根据终结器引用找到对象，并调用其finallize()方法，真正释放空间。

实际上效率很低，因为那个线程优先级很低，导致finallize()方法迟迟不被调用。其次需要两次GC才能真正释放空间。

<br>

***举例：使用引用队列来释放软引用本身***

    //list -> SoftReference -> byte[] 软引用链
    List<SoftReference<byte[]>> list = new ArrayList<>();

    //引用队列
    ReferenceQueue<byte[]> queue = new ReferenceQueue<>();

    for(int i = 0; i < 5; i++) {
        //关联引用队列：在构造中添加队列作为参数
        SoftReference<byte[]> ref = new SoftReference<>(new byte[_4MB], queue);
        sout(ref.get);
        list.add(ref);
        sout(list.size);
    }

    Reference<? extends byte[]> poll = queue.poll();
    //遍历引用队列，删除软引用本身
    while(poll != null) {
        list.remove(poll);
        poll = queue.poll();
    }

<br>

## 2.垃圾回收算法

以下三种算法会分情况使用。

    标记清除 标记整理 复制

### 2.1 标记清除

把没有被引用的对象标记出来，然后清除

![Mark Sweep](/Pictures/标记清除.png)

    优点：速度快
    缺点：容易产生内存碎片

### 2.2 标记整理

在清理过程中把引用的对象向前移动，避免了产生内存碎片

![Mark Compact](/Pictures/标记整理.png)

    优点：没有内存碎片
    缺点：移动对象需要改变地址，效率较低
    
### 2.3 复制

步骤：

    1.把内存分为 *FROM* 和 *TO* 两块
    2.标记没有被引用的对象
    3.把被引用了的对象依次移入TO内存中
    4.清除FROM中所有被标记了的对象
    5.交换TO和FROM

![Copy](/Pictures/复制.png)

    优点：没有内存碎片
    缺点：占用双倍内存空间

## 3.分代垃圾回收

JVM将堆内存分为 *新生代Young Generation* 和 *老年代Old Generation*

其中，新生代又进一步分为 *Eden区 - 伊甸园* 、*From(S0)区* 、*To(S1)区*，From与To区都是幸存区

    对象首先分配在Eden区。
    新生代空间不足时，触发Minor GC（复制算法）；
    老年代空间不足时，触发Full GC（标记清除/标记整理）。

**Minor GC引发 stop the world，暂停其他用户的线程；**

    Minor GC暂停时间较短。

当对象寿命超过阈值时，会晋升至老年代，最大寿命是15(4bit,1111)

当老年代空间不足时，会先触发Minor GC，若空间仍然不足，则会触发Full GC

**Full GC引发 stop the world，整个Java程序都将暂停。**

    Full GC暂停时间较长。

如果Full GC过后，老年代空间仍然不够，则抛出*堆内存溢出*错误：

    java.lang.OutOfMemoryError: Java heap space

### 3.1 GC相关JVM参数

|含义|参数|
|------|------|
|堆初始大小|-Xms|
|堆最大大小|-Xmx 或 -XX:MaxHeapSize=size|
|新生代大小|-Xmn 或(-XX:NewSize=size + -XX:MaxNewSize=size)|
|幸存区比例(动态)|-XX:InitialSurvivorRatio=ratio 和 -XX:+UseAdaptiveSizePolicy|
|幸存区比例|-XX:SurvivorRatio=ratio|
|晋升阈值|-XX:MaxTenuringThreshold=threshold|
|晋升详情|-XX:+PrintTenuringDistribution|
|GC详情|-XX:+PrintGCDetails -verbose:GC|
|Full GC前Minor GC|-XX:+ScavengeBeforeFullGC|

### 3.2 GC分析 大对象直接晋升 堆内存溢出

*大对象*（如10M的byte[]）在**老年代空间足够**且**新生代空间肯定不够**的情况下会直接晋升至老年代。

示例：
    
    设置堆空间大小为20M，from区和to区不自动变化
    -Xmx20M -Xms20M -Xmn10M -XX:+UseSerialGC -verbose:gc -XX:+PrintGCDetails 

    往ArrayList集合中添加一个8MB的byte数组

运行结果如下图所示：

![bigobj_oom](../Pictures/大对象oom.png)

    可以看到，老年代tenured generation
    占用率直接提升至80%，且没有触发GC。

***注意：在别的线程中Java堆内存溢出，不会使主线程停止。***

测试类TestJVM1:

    public class TestJVM1 {
        prsf int _8MB = 8*1024*1024;
        psvm(String[] args) throws InterruptedExeption{
            new Thread(()->{
                ArrayList<byte[]> list=new A..<>();
                list.add(_8MB);
                list.add(_8MB);   
            }).start();

            sout("Sleep...");
            Thread.sleep(2000L);
        }
    }

在上述测试类中，新创建了一个匿名线程使得堆内存溢出。

运行结果：

![oom](../Pictures/OutOfMemErr_OOM.png)

    新开线程中报错：Java堆内存溢出
    但主线程并没有因此暂停，随后还进行了一次GC。

**一个线程内的OutOfMemoryError不会导致整个Java主线程意外结束。**

## 4.垃圾回收器

### 4.1 串行

- 单线程
- 适用于堆内存较小的情况

![serialGC](../Pictures/UseSerialGC.png)

**-XX:+UseSerialGC = Serial + SerialOld**

- Serial: 工作在新生代，用的复制算法
- SerialOld: 工作在老年代，用的标记+整理算法

*都是单线程工作，使用算法有所不同。*

### 4.2 吞吐量优先 - 并行

**多个GC线程并行执行，但不允许用户线程执行。**

- 多线程，适合堆内存较大的场景
- 适合多核cpu
- 让单位时间内GC时stop the world时间最短

![parallelGC](../Pictures/UseParallelGC.png)

VM参数：

- -XX:+UseParalleGC ~ -XX:+UseParallelOldGC
  
    随便开启一个，另一个都绑定开启

- -XX:+UseAdaptiveSizePolicy
 
    采用自适应大小调整策略（Eden from to）

- -XX:GCTimeRatio=ratio
  
    目标1：调整GC时间与总时间的比例（一般设置为19）

- -XX:MaxGCPauseMillis=ms
  
    目标2：设置最大暂停毫秒数（默认200ms）
    
&emsp;**目标1与目标2是对立的目标。**

- -XX:ParallelGCThreads=n
    
    控制ParallelGC时的线程数

### 4.3 响应时间优先 - 并发

**用户线程与GC线程并发执行。**

- 多线程，适合堆内存较大的场景
- 适合多核cpu
- 让单次GC时stop the world的时间尽可能短
- *会对整个程序的吞吐量造成一定影响*

![cmsGC](../Pictures/UseCMSGC.png)

VM参数：

- -XX:+UseConcMarkSweepGC ~ -XX:+UseParNewGC ~ SerialOld

    Concurrent并发 
    
    CMS工作在老年代，标记+清除算法；
    
    ParNewGC 工作在新生代，复制算法。
    
    当并发失败时，CMS退化到串行垃圾回收的SerialOld
    做一次串行整理，减少内存碎片后CMS才能继续工作。

&emsp;*由于内存碎片问题，一旦发生并发失败，GC时间会一下子飙升。（CMS最大的问题）*

&emsp;在初始标记时，线程数受到下面两个参数的影响:

- -XX:ParallelGCThreads=n ~ -XX:ConcGCThreads=threads
    
    并行GC线程数，一般与CPU核数一样
    并发GC线程数，一般是并行线程数的1/4

- -XX:CMSInitiatingOccupancyFraction=percent

    控制触发CMS GC的内存占比
    当老年代内存占用到达设置值时，进行一次GC
    主要用于处理新产生的浮动垃圾。

- -XX:+CMSScavengeBeforeReMark

    在重新标记之前，对新生代进行一次GC

### 4.4 G1 - Garbage First

JDK 9 弃用了CMS垃圾回收器，改用G1作为默认垃圾回收器。

适用场景
- 同时注重吞吐量和低延迟，默认的暂停目标是200ms
- 超大堆内存，会将堆划分为多个大小相等的区域（Region 大约是1248M）

    每个区域都可以独立作为Eden、From、To、新生代、老年代。

- 整体上是 **标记+整理** 算法，*区域之间是复制算法* 。

VM参数：
- -XX:+UseG1GC
- -XX:G1HeapRegionSize=size
- -XX:MaxGCPauseMillis=time

**(1) G1 GC阶段**

![g1gc](../Pictures/G1GC.png)

**(2) Young Collection 新生代**

![g1gc](../Pictures/G1_Young.png)

当伊甸园满时，把幸存对象复制进幸存区。

![g1gc](../Pictures/G1_Young2.png)

幸存区的对象够年龄的，晋升到老年代；不够年龄的接着复制。

**(3) Young Collection + Concurrent Marking(并发标记)**

- 在Young GC 时会进行GC Root的初始标记；
- 老年代占用堆空间比例达到阈值时，进行并发标记。

    -XX:InitiatingHeapOccupancyPercent=percent (defalut=45%)

![g1gc](../Pictures/G1_YCCM1.png)

**(4) Mixed Collection 混合收集**

会对伊甸园、幸存区、老年代进行全面地垃圾回收。

![g1gc](../Pictures/G1_Mixed1.png)

- 最终标记（Remark）会STW
- 拷贝存活（Evacuation）会STW

G1会根据VM参数：**-XX:MaxGCPauseMillis=ms 最大暂停时间**有选择地回收。

    找回收价值最大的回收。
    对于老年代区域，优先回收垃圾最多的区域
    以达到最大暂停时间的目标。

**(5) Full GC**

当G1老年代内存不足时，垃圾回收分两种情况。

如果回收速度 > 新产生垃圾的速度，不叫Full GC，还是并发垃圾收集。

如果回收速度 < 新产生垃圾的速度，并发收集失败，退化为串行收集。

*这时就叫Full GC。跟CMS很像。*

**(6) Young Collection跨代引用 - 老年代引用新生代**

![g1gc](../Pictures/G1_incomingRef.png)

卡表与Remembered Set。

一个卡的大小大约是512KB。

引用了新生代的卡区称为脏卡 dirty card

在引用变更时通过post-write barrier + dirty card queue

concurrent refinement threads 更新 Remembered Set

**(7) Remark 重新标记**

为了防止引用变更造成不必要的损失。因此要对对象的引用做进一步的检查。

当对象的引用发生变化时，JVM会给它添加一个*写屏障*，并把这个对象加入队列中。

    pre-write barrier 写屏障 + satb_mark_queue

等到整个并发标记结束，对队列中的对象进行重新检查标记。

**(8) JDK 8u20字符串去重**

- 优点：节省大量内存
- 缺点：略微多占用了cpu时间，新生代回收时间略微增加。

-XX:+UseStringDeduplication

    String s1 = new String("hello"); //char[]{'h','e','l','l','o'}
    String s2 = new String("hello"); //char[]{'h','e','l','l','o'}

将所有新分配的字符串放入一个队列；

当新生代回收时，G1并发检查是否有字符串重复。

若值一样，则让它们引用同一个char数组。

*与String.intern()不同，intern()关注的是字符串对象，而字符串去重关注的是char数组。*

...<br>

## 5.GC调优

- 掌握GC相关的JVM参数，会基本的空间调整

    查看虚拟机运行参数：
    "JDK安装路径\bin\java" -XX:+PrintFlagsFinal -version | findstr "GC"

- 掌握相关工具

    可视化 命令行等

- 调优跟应用，环境有关，处处不一样

    主要是一个经验

### 5.1 调优领域

- 内存
- 锁竞争
- cpu占用
- io

### 5.2 确定目标

- 【低延迟】还是【高吞吐量】，选择合适的回收器
- CMS，G1，ZGC
- ParallelGC

*不同版本的JDK默认使用的垃圾回收器不同。*

- JDK8 之前：-XX:+UseParallelGC
- JDK9 之后：-XX:+UseG1GC

*JDK 17.0.6默认G1GC。*

查看默认垃圾回收器的cmd命令：

    定位到jdk安装目录/bin
    java.exe -XX:+PrintCommandLineFlags -version

![g1gc](../Pictures/jdk17UseG1GC.png)

### 5.3 最快的GC是不发生GC

查看Full GC前后的内存占用，考虑几个问题

*1. 数据是不是太多？*

如：使用jdbc查询：

    resultSet = pstmt.executeQuery("select * from 大表");

这样查，会把大量的数据放进Java内存，有再大的堆内存都不够用。

此时应该在sql语句中添加限制：limit n

*2. 数据表示是否太臃肿？*

- 对象图 查了许多用不上的数据
- 对象大小

    最小的Object 16字节 
    Integer包装类大约24字节 int基本类型4字节
    考虑对象瘦身

*3. 是否存在内存泄露？*

经典错误：静态map，光添加不删除，容易造成OutOfMemoryError

    static Map map = ...

需要保存长时间存活的对象，有许多方法，如：

- 软、弱引用
- 第三方缓存实现，如EhCache、Redis

### 5.4 新生代调优

**新生代的特点：**

- *所有的new操作的内存分配非常廉价*

&emsp;- *TLAB thread-local allocation buffer 线程局部分配缓冲区*

    分配对象时，会先检查TLAB缓冲区中有没有可用空间。
    如果有，会优先分配到TLAB中。
    主要是线程安全问题。

    每个线程用自己私有的伊甸园内存来进行对象的分配。
    使得多个线程同时创建对象时也不会产生内存干扰。

- *死亡对象的回收代价是0*

    因为所有的垃圾回收器使用的都是复制算法。

- *大部分对象用过即死*

- *Minor GC的时间远远低于Full GC（相差大约1-2个数量级）*

最有效的方式：直接把新生代内存调大 **但不是越大越好**。

虚拟机参数：-Xmn

官方解释：

*设置初始和最大的新生代堆内存大小(in bytes)。垃圾回收在这个区域的执行*
*远比在其他区域频繁。如果新生代内存太小，则Minor GC将会执行得很频繁。*

*如果新生代内存太大，则只会发生Full GC，那将会占用更多的时间完成。*

*Oracle建议保持新生代内存大于总堆大小的25%-50%。*

理想情况：**新生代能容纳所有【并发量\*(请求-响应)】的数据**

- 幸存区（From、To）大到能保留【当前活跃对象+需要晋升对象】

    当幸存区太小时，JVM会动态调整晋升年龄。
    一个对象可能刚一进到老年代就失去了引用，
    那么这个对象将会到Full GC时才被回收。

- 晋升阈值配置得当，让长时间存活对象尽快晋升

    JVM参数：
    -XX:MaxTenuringThreshold=threshold 调整最大晋升阈值
    -XX:+PrintTenuringDistribution 把幸存区中不同年龄对象占用的空间打印

### 5.5 老年代调优

以CMS为例

- CMS的老年代内存越大越好

    避免浮动垃圾造成并发失败

- 先尝试不做调优，如果没有Full GC，则系统运行就很ok了

    即使发生了，也要先尝试调优新生代。

- 观察发生Full GC时的老年代内存占用，将老年代内存预设调大1/4 ~ 1/3

    VM参数：
    -XX:CMSInitiatingOccupancyFraction=percent

### GC调优案例

1. Full GC和Minor GC频繁

    主要原因：空间太小。
    
    解决方法：想办法增大新生代空间和幸存区空间。

2. 请求高峰期发生Full GC，单次暂停时间特别长（CMS）

    主要原因：重新标记耗时过长（扫描老年代和新生代的所有对象）
    
    解决方案：在重新标记之前先对新生代进行一次垃圾回收，
    使得重新标记阶段扫描的对象少很多。
    VM参数：-XX:+CMSScavengeBeforeRemark

