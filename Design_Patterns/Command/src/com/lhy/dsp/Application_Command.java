package com.lhy.dsp;

import com.lhy.dsp.command.ICommand;
import com.lhy.dsp.conccommand.ConcreteCommand;
import com.lhy.dsp.invoker.RequestMakedir;
import com.lhy.dsp.receiver.MakeDir;

public class Application_Command {
    public static void main(String[] args) {
        MakeDir makeDir = new MakeDir();
        ICommand command = new ConcreteCommand(makeDir);
        RequestMakedir askMakedir = new RequestMakedir();
        askMakedir.setCommand(command);
        askMakedir.startExecuteCommand("hahaha1");
        askMakedir.startExecuteCommand("abc2");
        askMakedir.startExecuteCommand("niubi");
        askMakedir.undoCommand();
        askMakedir.undoCommand();
    }
}
