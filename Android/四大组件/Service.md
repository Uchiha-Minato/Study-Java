# Service

Service - 服务，是一种运行时用户不可见的活动

*可以理解成没有布局的Activity*

典型场景：

    音乐后台播放、后台下载

**Notice：**

    Service不同于子线程，Service是运行在主线程中的，因此不能进行耗时操作。

## Service的创建

步骤：

1.新建一个自己的Service类继承系统的Service类

    android.app.Service

2.重写其中的几个重要方法：

    onCreate() onBind() onStartCommand() onDestroy()

3.在AndroidManifest.xml中注册这个服务

    <application...>
        <service android:name=".ExampleService"/>
        ...
    </application>

4.启动Service

通过Intent显式启动。

    public void startService(View view) {
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }

## Service的类型

*按启动方式分：*

**绑定式bindService**

    绑定服务后，服务就跟启动者（Activity）的生命周期绑定在一起了，如果启动者销毁，服务也跟着销毁；
    由于是绑定状态，启动者可以和Service进行通信。

**非绑定式startService**

    一旦服务开启就和启动者无关了，即使启动者退出，Service依然在后台运行；
    启动者无法调用Service里的方法。

绑定式Service创建：

1.创建一个自己的Service类，继承android.app.Service

    public class MyBindService extends Service {...}

2.重写几个重要方法

3.在Manifest中注册服务

    小技巧：在MyBindService类中，把光标放在类名上
    按ALT + Enter即可一键注册

4.通过Intent显示启动

    public void bindService(View view) {
        Intent intent = new Intent(this, MyBindService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

其中bindService方法有三个参数。源码解释如下：

    android.content ⬇
    public abstract class Context ⬇
        public abstract boolean bindService(
            Intent service,
            ServiceConnection conn,
            int flags
        );
    连接到一个应用服务，如果需要则创建。它定义了一个你的应用程序与服务的依赖。给定的参数conn将在创建服务对象时接收该服务对象，并被告知该服务对象是否死亡或重新启动。只有在调用上下文存在的情况下，系统才会认为服务是必需的。
    参数：
    service - 标识要连接的服务，必须明确指定组件名称；
    conn - 在服务启动或停止时接收消息。必须是一个合法且非空的ServiceConnection对象；
    flags - 绑定操作的选项。

ServiceConnection是一个接口，用于访问服务。
