# JVM垃圾回收 - Garbage Collect

    1.如何判断对象可以回收
    2.垃圾回收算法
    3.分代垃圾回收
    4.垃圾回收器
    5.垃圾回收调优

*JVM GC过程：*

1.**先判断可以回收的对象**

    没有被GC ROOT直接或间接引用的对象可以被回收

2.**JVM将虚拟机栈分为 新生代Young Gen，老年代Old Gen**

    其中新生代又分为 S0,S1,E区

3.**new出来的对象出生在E区，当E区被填满时触发Minor GC**

    Minor GC使用复制算法，E区不被标记的对象复制到S0区，然后将E区与S1区剩下的对象全部删除，下一次触发Minor GC时，E区与S0区的对象重复上述复制操作到S1区，如此循环往复使得S0与S1区交替使用
        优点：比直接把栈内存分两块复制利用率高
        S0,S1,E的大小比默认为 1:1:8

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

    软引用：GC触发后，内存依然不够，且软引用对象没有被强引用，就会把软引用的对象回收。

    弱引用：只要发生了GC，不管内存够不够，都会将弱引用回收。

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

其中，新生代又进一步分为 *E(Eden)区 - 伊甸园* 、*From(S0)区* 、*To(S1)区*，S0与S1区都是幸存区

    对象首先分配在E区。
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

![parallelGc](../Pictures/UseParallelGC.png)

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

    Concurrent并发 CMS工作在老年代，标记+清除算法；
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

## 5.GC调优

..