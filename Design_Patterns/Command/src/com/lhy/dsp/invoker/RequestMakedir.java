package com.lhy.dsp.invoker;

import com.lhy.dsp.command.ICommand;

public class RequestMakedir {
    private ICommand command;

    public void setCommand(ICommand command) {
        this.command = command;
    }

    public void startExecuteCommand(String name) {
        command.execute(name);
    }

    public void undoCommand() {
        command.undo();
    }
}
