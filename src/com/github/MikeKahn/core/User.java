package com.github.MikeKahn.core;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
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

    public User(String id, DefaultMutableTreeNode parent, ArrayList<Node> children) {
        super(id, parent, children);
        followings = new HashSet<>();
        followers = new HashSet<>();
        messages = new HashSet<>();
    }

    void follow(String id, DefaultListModel model) { //subscribe given user to this user
        if(this.id.equals(id)) { //cant follow self
            System.err.println("Error: Cannot follow self.");
        } else if(followings.contains(id)) { //check if already following
            System.err.println("Error: User " + this.id + " is already following " + id + ".");
        } else if(!Manager.getInstance().userExists(id)) { //check if the user doesnt exist
            System.err.println("Error: No user by the id " + id + " was found.");
        } else {
            followings.add(id);
            User u = Manager.getInstance().getUser(id);
            u.addFollower(this.id);
            model.addElement(id);
        }
    }

    void addFollower(String id) {
        followers.add(id);
    }

    void postMessage(String content) {
        Message msg = new Message(content);
        messages.add(msg);
        Manager.getInstance().push(this, msg);
    }

    HashSet<String> getFollowings() {
        return followings;
    }

    public HashSet<String> getFollowers() {
        return followers;
    }

    HashSet<Message> getMessages() {
        return messages;
    }

    boolean isFollower(String id) {
        return followers.contains(id);
    }
}
