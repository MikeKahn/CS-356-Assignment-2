package com.github.MikeKahn.core;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Michael on 10/25/2016.
 *
 */
public class Message {

    final String timeStamp;
    final String content;
    final String userID;

    public Message(User user, String content) {
        userID = user.id;
        timeStamp = (new Timestamp((new Date()).getTime())).toString();
        this.content = content.trim();
    }

    @Override
    public String toString() {
        return "[" + timeStamp + "][" + userID + "]" + content;
    }
}
