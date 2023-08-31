package com.lhy.dsp.realsubject;

import com.lhy.dsp.subject.IEmployee;

public class Boss implements IEmployee {
    @Override
    public String hearPhone(String s) {
        if (s.contains("买")||s.contains("卖")) {
            return "好的，等以后约时间面谈。";
        } else if (s.contains("吃饭")) {
            return "好的，按时来。";
        } else {
            return "之后再联系，会议马上开始了。";
        }
    }
}
