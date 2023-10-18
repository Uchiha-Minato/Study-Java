package com.lhy.dsp.repo;

import com.lhy.dsp.aggregate.Container;
import com.lhy.dsp.iterator.Iterator;

public class NameRepository implements Container {

    public String[] names = {"lhy", "lkc", "zj", "ghy"};
    @Override
    public Iterator getIterator() {
        return new NameIterator();
    }

    private class NameIterator implements Iterator {

        int index;
        @Override
        public boolean hasNext() {
            return index < names.length;
        }

        @Override
        public Object next() {
            return this.hasNext() ? names[index++] : null;
        }
    }
}
