package com.jcoinche.model;

public class ProtoTask {
    public enum Protocol {WAIT, TAKECARD, GETASSET, PUTCARD}
    private Protocol task;

    public ProtoTask() {
    }

    public ProtoTask (Protocol taskToDo) {
        this.task = taskToDo;
    }

    public Protocol getTask() {
        return this.task;
    }

    public void setTask(Protocol taskToDo) {
        this.task = taskToDo;
    }
}
