package com.jcoinche.client;

import com.jcoinche.model.ProtoTask;

import java.util.TimerTask;

public class CustomTask extends TimerTask {

    private Client client;

    public CustomTask(Client client) {
        this.client = client;
        //Constructor
        System.out.println("RUNNING CUSTOM TASK\n");
    }

    public void run() {
        try {

            client.askForTask(client.getStompSession());
            // Your task process

        } catch (Exception ex) {

            System.out.println("error running thread " + ex.getMessage());
        }
    }
}