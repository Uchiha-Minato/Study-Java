# Service

应用基础知识 - https://developer.android.google.cn/guide/components/fundamentals?hl=zh-cn

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

5.在类中定义变量实现这个接口，具体实现：

    private ServiceConnection conn = new ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //服务连接时的回调方法
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            //服务断开时的回调方法
        }
    }

其中，第一个方法中的第二个参数是 IBinder类型的，其实就是需要绑定的那个Service

Service在被绑定时，一开始重写的onBind()方法就会被调用，这个方法的返回值类型就是IBinder。

6.在Service类中创建内部类MyBinder继承Binder类，Binder类实现了IBinder接口。具体实现：

    public class MyBindService extends Service {
        ...
        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return new MyBinder();
        }
        ...
        public class MyBinder extends Binder {
            private MyBindService mBindService;
            public MyBinder(MyBinderService bindService) {
                mbindService = bindService;
            }

            public void method() {
                ...
            }
        }
    }

当onBind()方法被调用时，一个MyBinder类的对象就被返回到了onServiceConnect(Activity中的)方法那里

于是就可以通过这个对象实例来调用MyBinder中的方法了。

7.在Activity中接收上述MyBinder对象，在实现方法中接收时cast

    private MyBindService.MyBinder mBinder = null;
    private ServiceConnection conn = new ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //服务连接时的回调方法
            mBinder = (MYBindService.MyBinder)service;
            mBinder.method();//调用Service中的方法
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            //服务断开时的回调方法
        }
    }

通过上述所有步骤，就完成了绑定服务的连接与使用。