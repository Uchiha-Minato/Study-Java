package com.lhy.dsp.concreteimplementor;

import com.lhy.dsp.implementor.IBookWriter;

public class Author implements IBookWriter {

    protected String name;

    public Author(String name) {
        this.name = name;
    }

    @Override
    public void startWriteBook(String bookName) {
        System.out.println(name + "编著了：" + bookName);
    }

    @Override
    public String getName() {
        return name;
    }
}
