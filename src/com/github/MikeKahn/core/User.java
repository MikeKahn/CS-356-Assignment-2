package com.github.MikeKahn.core;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Michael on 10/24/2016.
 *
 */
public class User extends Node {

    private HashSet<String> followings;
    private HashSet<String> followers;
    private HashSet<Message> messages;

    public User(String id, Node parent, ArrayList<Node> children) {
        super(id, parent, children);
    }

    public void follow(String id) { //subscribe given user to this user
        followers.add(id);
    }

    public void followOther(String id) { //subscribe this user to anothe user
        followings.add(id);
    }

    public void postMessage(String content) {
        Message msg = new Message(content);
        messages.add(msg);
        Manager.getInstance().push(this, msg);
    }

    public HashSet<String> getFollowings() {
        return followings;
    }

    public HashSet<String> getFollowers() {
        return followers;
    }

    public HashSet<Message> getMessages() {
        return messages;
    }
}
