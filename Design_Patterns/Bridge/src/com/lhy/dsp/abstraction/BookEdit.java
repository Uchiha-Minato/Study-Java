package com.lhy.dsp.abstraction;

import com.lhy.dsp.implementor.IBookWriter;

public abstract class BookEdit {
    /**
     * 图书作者
     * */
    protected IBookWriter[] authors;
    /**
     * 系列丛书
     * */
    protected String[] seriesBookName;
    /**
     * 出版计划
     * @param bookName 书名数组
     * @param authorName 作者数组
     * */
    public abstract void planBook(String[] bookName, String[] authorName);
    /**
     * 发布图书
     * */
    public abstract void releaseBook();
}
