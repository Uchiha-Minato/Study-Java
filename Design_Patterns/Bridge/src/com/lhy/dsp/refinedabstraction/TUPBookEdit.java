package com.lhy.dsp.refinedabstraction;

import com.lhy.dsp.concreteimplementor.Author;
import com.lhy.dsp.abstraction.BookEdit;
import com.lhy.dsp.implementor.IBookWriter;

public class TUPBookEdit extends BookEdit {
    @Override
    public void planBook(String[] bookName, String[] authorName) {
        //细化工作
        seriesBookName = bookName;
        authors = new IBookWriter[seriesBookName.length];
        for (int i = 0; i < seriesBookName.length; i++) {
            authors[i] = new Author(authorName[i]);
            authors[i].startWriteBook(seriesBookName[i]);
        }
    }

    @Override
    public void releaseBook() {
        System.out.println("图书有关信息：");
        for (int i = 0; i < seriesBookName.length; i++) {
            System.out.printf("书名：%s - 作者：%s\n",seriesBookName[i], authors[i].getName());
        }
    }
}
