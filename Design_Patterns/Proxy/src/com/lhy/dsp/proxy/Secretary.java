package com.lhy.dsp.proxy;

import com.lhy.dsp.realsubject.Boss;
import com.lhy.dsp.subject.IEmployee;

public class Secretary implements IEmployee {

    private final Boss boss;

    public Secretary() {
        this.boss = new Boss();
    }

    @Override
    public String hearPhone(String s) {
        if(!(s.contains("恐吓")||s.contains("脏话"))) {
            String back = boss.hearPhone(s);
            return "我们老板说：" + back;
        }
        return "我们老板说：不接你的电话。";
    }
}
