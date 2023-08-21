package com.lhy.dsp.compositenode;

import com.lhy.dsp.component.MilitaryPerson;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MilitaryOfficer extends MilitaryPerson {

    protected List<MilitaryPerson> list;

    public MilitaryOfficer(String name, double salary) {
        super(name, salary);
        list = new LinkedList<>();
    }

    @Override
    public void add(MilitaryPerson person) {
        list.add(person);
    }

    @Override
    public void remove(MilitaryPerson person) {
        list.remove(person);
    }

    @Override
    public MilitaryPerson getChild(int index) {
        return list.get(index);
    }

    @Override
    public Iterator<MilitaryPerson> getAllChildren() {
        return list.iterator();
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

}
