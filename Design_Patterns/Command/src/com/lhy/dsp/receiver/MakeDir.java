package com.lhy.dsp.receiver;

import java.io.File;

public class MakeDir {
    public void createDir(String name) {
        File dir = new File(name);
        boolean a = dir.mkdir();
        if (a) {
            System.out.println("目录创建成功");
        } else {
            System.out.println("目录创建失败");
        }
    }

    public void deleteDir(String name) {
        File dir = new File(name);
        boolean a = dir.delete();
        if(a) {
            System.out.println("目录删除成功");
        } else {
            System.out.println("目录删除失败");
        }
    }
}
