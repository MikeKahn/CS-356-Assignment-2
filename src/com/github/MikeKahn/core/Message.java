package com.github.MikeKahn.core;

/**
 * Created by Michael on 10/25/2016.
 */
public class Message {

    public final long timeStamp;
    public final String content;

    public Message(String content) {
        timeStamp = System.nanoTime();
        this.content = content;
    }

}
