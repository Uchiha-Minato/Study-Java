package com.lhy.dsp.conccommand;

import com.lhy.dsp.command.ICommand;
import com.lhy.dsp.receiver.MakeDir;

import java.util.ArrayList;
import java.util.List;

public class ConcreteCommand implements ICommand {

    private List<String> dirNameList;

    private MakeDir makeDir;

    public ConcreteCommand(MakeDir makeDir) {
        dirNameList = new ArrayList<>();
        this.makeDir = makeDir;
    }

    @Override
    public void execute(String name) {
        makeDir.createDir(name);
        dirNameList.add(name);
    }

    @Override
    public void undo() {
        if (!dirNameList.isEmpty()) {
            int m = dirNameList.size();
            String str = dirNameList.get(m - 1);
            makeDir.deleteDir(str);
            dirNameList.remove(m - 1);
        } else {
            System.out.println("没有需要撤销的操作");
        }
    }
}
