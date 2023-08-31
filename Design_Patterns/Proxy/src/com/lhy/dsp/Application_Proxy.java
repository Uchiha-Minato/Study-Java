package com.lhy.dsp;

import com.lhy.dsp.proxy.Secretary;

public class Application_Proxy {
    public static void main(String[] args) {
        Secretary secretary = new Secretary();
        String back = secretary.hearPhone("你好，我要购买产品。");
        System.out.println(back);
        back = secretary.hearPhone("你好，贵公司卖什么产品。");
        System.out.println(back);
        back = secretary.hearPhone("我们是老朋友，请你吃饭。");
        System.out.println(back);
        back = secretary.hearPhone("cpdd");
        System.out.println(back);
        back = secretary.hearPhone("恐吓老板");
        System.out.println(back);
    }
}
