package com.lhy.dsp.leafnode;

import com.lhy.dsp.component.MilitaryPerson;

import java.util.Iterator;

public class MilitarySoldier extends MilitaryPerson {
    public MilitarySoldier(String name, double salary) {
        super(name, salary);
    }

    @Override
    public void add(MilitaryPerson person) {}

    @Override
    public void remove(MilitaryPerson person) {}

    @Override
    public MilitaryPerson getChild(int index) {
        return null;
    }

    @Override
    public Iterator<MilitaryPerson> getAllChildren() {
        return null;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }
}
