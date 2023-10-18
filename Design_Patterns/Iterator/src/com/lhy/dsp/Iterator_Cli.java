package com.lhy.dsp;

import com.lhy.dsp.iterator.Iterator;
import com.lhy.dsp.repo.NameRepository;

public class Iterator_Cli {
    public static void main(String[] args) {
        NameRepository repo = new NameRepository();

        for(Iterator iterator = repo.getIterator(); iterator.hasNext();) {
            String name = (String) iterator.next();
            System.out.println("Name : " + name);
        }
    }
}
