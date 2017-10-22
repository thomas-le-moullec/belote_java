package com.jcoinche.model;

public class ProtoTask {
    public enum Protocol {WAIT, TAKECARD, GETASSET, PUTCARD}
    private Protocol task;
    private Board board;

    public ProtoTask() {
    }

    public ProtoTask (Protocol taskToDo, Board board) {
        this.task = taskToDo;
        this.board = board;
    }

    public Protocol getTask() {
        return this.task;
    }

    public void setTask(Protocol taskToDo) {
        this.task = taskToDo;
    }

    public Board getboard() {
        return this.board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
