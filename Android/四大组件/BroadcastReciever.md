# BroadcastReciever 广播接收器

广播接收器，本质上是一个系统级的监听器。

与*设计模式*中的**观察者(发布-订阅)模式**基本相同。

对于Android系统广播，主要包括两方面：

- **广播者**

    可以是系统软件，也可以是用户的应用程序。

- **接收者**

    当广播者因为某些事件发生而发出广播后，系统会根据<intent-filter../>元素来筛选感兴趣的广播。
    如果感兴趣并实现了接收器BroadcaseReciever，接收到广播之后就会在onReceive()方法中进行处理。

**onReceive()方法中不能编写耗时操作。**

    超过10s会被Android系统认为无响应，触发ANR
    Application No Response

## 使用步骤

1.将需要广播的消息封装到Intent中，然后通过调用
    
    public abstract class Context中的  
    sendBroadcast()
    sendOrderedBroadcast()
    sendStickBroadcast()
    
等方法将Intent广播出去；

2.注册接收器。有两种方式：
- **静态注册 - AndroidManifest.xml**

        <receiver android:name=".broadcastreceiver.MySMSReceiver"
            android:enabled="true" android:exported="true">
            <intent-filter>
                <action android:name="android.provider.Telephony.SMS_RECEIVED"/>
            </intent-filter>
        </receiver>

- **上下文动态注册**

        @SuppressLint("UnspecifiedRegisterReceiverFlag")
        private void regReceiver() {
            receiver = new MyReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.intent.action.MY_BROADCAST");
            registerReceiver(receiver, filter);
            Toast.makeText(this, "广播接收器已注册", Toast.LENGTH_SHORT).show();
        }

3.接收者通过IntentFilter(过滤器)来检查收到的Intent。

如果感兴趣，则调用onReceive()方法来接收和处理消息。

    @Override
    public void onReceive(Context context, Intent intent) {
        String strMsg = intent.getStringExtra("broadcastMsg");
        Toast.makeText(context, strMsg, Toast.LENGTH_SHORT).show();
    }