package com.lhy.dsp.component;

import java.util.Iterator;

public abstract class MilitaryPerson {

    protected String name;
    protected double salary;

    public MilitaryPerson(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    /**
     * 添加人员
     * */
    public abstract void add(MilitaryPerson person);
    /**
     * 删除人员
     * */
    public abstract void remove(MilitaryPerson person);
    /**
     * 获得指定的孩子结点
     * */
    public abstract MilitaryPerson getChild(int index);
    /**
     * 获得所有孩子结点
     * */
    public abstract Iterator<MilitaryPerson> getAllChildren();
    /**
     * 判断是否为叶子结点
     * */
    public abstract boolean isLeaf();
    /**
     * 计算军饷
     * */
    public double getSalary() {
        return salary;
    };
    /**
     * 设置军饷
     * */
    public void setSalary(double salary) {
        this.salary = salary;
    }
}
