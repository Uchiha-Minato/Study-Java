package com.lhy.dsp.command;

public interface ICommand {
    void execute(String name);
    void undo();
}
