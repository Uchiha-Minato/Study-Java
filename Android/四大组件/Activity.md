# Activity

应用基础知识 - Activity

https://developer.android.google.cn/guide/components/activities/intro-activities?hl=zh-cn

## Activity的概念

一个应用程序可能会按功能的不同涉及多个Activity。移动应用与桌面应用相比，特殊之处在于：

*用户与应用的互动经常以不确定的形式开始。*

例如：从照片应用直接进入发送电子邮件的界面。

当一个应用调用另一个应用时，调用的是另一个应用的一个Activity，而不是整个应用。通过这种方式，Activity充当了应用与用户互动的入口点。

Activity提供窗口供应用在其中绘制界面。这个窗口通常会填满整个屏幕，也有可能作为浮窗悬浮着。

虽然应用中的各个 Activity 协同工作形成统一的用户体验，但每个 Activity 与其他 Activity 之间只存在松散的关联，应用内不同 Activity 之间的依赖关系通常很小。

事实上，Activity 经常会启动属于其他应用的 Activity。

## 配置清单文件 AndroidManifest.xml

使用前需要声明。必要元素是 android:name

    <manifest ...>
        <application ...>
            <activity android:name=".MainActivity"/>
            ...
        </application...>
        ...
    </manifest>

### Intent过滤器

一个非常强大的功能。

可以显示启动Activity，也可以根据隐式请求启动Activity。

*显式请求：*
- 在GMail应用中启动“发送电子邮件Activity”

*隐式请求：*
- 在任何能够完成此工作的 Activity 中启动‘发送电子邮件’屏幕

使用：

在<activity>元素中声明<intent-filter>属性。此元素的定义包括：
- <action> 向Intent过滤器添加操作，需要至少一个。
- <category> 向Intent过滤器添加类别名称。
- <data> 向Intent过滤器添加数据规范。

这些元素组合在一起，可以指定Activity能够响应的Intent类型。

例如：
配置一个发送文本数据并接收其他 Activity 的文本数据发送请求的 Activity

    <activity android:name=".ExpActivity" android:icon=...>
        <intent-filter>
            <action android:name="android.intent.action.SEND"/>
            <category android:name="android.intent.category.DEFAULT"/>
            <data android:mimeType="text/plain"/>
        </intent-filter>
    </activity>

Java代码：

    Intent sendIntent = new Intent();
    sendIntent.setAction(Intent.ACTION_SEND);
    sendIntent.setType("text/plain");
    sendIntent.putExtra(Intent.EXTRA_TEXT, textMsg);
    startActivity(sendIntent);

具体参数和常量查看源码：

    Android API 33 ext-5  android.content.Intent

## Activity Lifecycle 生命周期

而 Android 系统与编程范式启动的main()方法不同，它会调用与其生命周期特定阶段相对应的特定回调方法来启动 Activity 实例中的代码。

- **onCreate() - 必须实现**

此回调方法会在系统创建Activity时触发。这时应该初始化Activity的基础组件，如创建视图并绑定数据。

最重要的是需要调用 **setContentView()**方法来定义界面布局。

onCreate()完成后，下一个回调将是onStart()。

- **onStart()**

onCreate()结束后，Activity将进入**已启动**状态，并对用户可见。

此回调包含Activity进入前台前最后的准备工作。

- **onResume()**

系统会在Activity开始与用户互动之前调用这个回调。

此时Activity在Activity栈的栈顶，并会捕获所有的用户输入。

onResume()后总是跟着onPause()回调。

- **onPause()**

当Activity失去焦点并进入"已暂停"状态时，系统就会调用这个方法。

调用则代表用户正在离开该Activity，Activity很快将进入“已停止”状态。

如果用户希望界面继续更新，则处于“已暂停”状态的Activity也可以继续更新界面。

onPause()执行完毕后，下一个回调是onStop()或onResume()。

- **onStop()**

当Activity对用户*不再可见*时，系统会调用onStop()。

不再可见有多种情况：
- Activity被销毁
- 有新的Activity启动
- 现有的Activity正在进入“已恢复”状态并覆盖了已停止的Activity

下一个回调将是onRestart()或onDestroy()。

- **onRestart()**

处于“已停止”状态的Activity即将重启时，系统就会调用onRestart()。

这个回调后面总是跟着onStrat()。

- **onDestroy()**

系统会在销毁Activity前调用此回调。

通常，实现onDestroy()是为了确保在销毁时释放所有占用的资源。

![activity](../pic_android/Lifecycle_activity.png)

### Activity栈

后进先出，栈顶的那个Activity处于活动状态，其他的处于暂停或停止状态。

按返回键时，将栈顶的Activity弹栈。

