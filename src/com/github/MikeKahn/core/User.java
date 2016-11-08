package com.github.MikeKahn.core;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Created by Michael on 10/24/2016.
 *
 */
public class User extends Node {

    private HashSet<String> followings;
    private HashSet<String> followers;
    private PriorityQueue<Message> feed;
    private PriorityQueue<Message> posts;

    public User(String id, DefaultMutableTreeNode parent, ArrayList<Node> children) {
        super(id, parent, children);
        followings = new HashSet<>();
        followers = new HashSet<>();
        Comparator comparator = (o1, o2) -> ((Message)o1).timeStamp.compareTo(((Message)o2).timeStamp); //make sure feed are sorted by time posted
        feed = new PriorityQueue<>(comparator);
        posts = new PriorityQueue<>(comparator);
    }

    void follow(String id, DefaultListModel model) { //subscribe given user to this user
        if(this.id.equals(id)) { //cant follow self
            System.err.println("Error: Cannot follow self.");
        } else if(followings.contains(id)) { //check if already following
            System.err.println("Error: User " + this.id + " is already following " + id + ".");
        } else if(!Manager.getInstance().userExists(id)) { //check if the user doesn't exist
            System.err.println("Error: No user by the id " + id + " was found.");
        } else {
            followings.add(id);
            User u = Manager.getInstance().getUser(id);
            u.addFollower(this.id);
            //add all messages from followed user
            u.getPosts().forEach(this::receiveMessage);
            //refresh model view
            DefaultListModel userModel = Manager.getInstance().getActiveUser(this.id);
            userModel.clear(); //clear current view
            System.out.println(feed);
            for(Message m:feed) { //fill view with feed
                userModel.addElement(m.toString());
            }
            model.addElement(id);
        }
    }

    void addFollower(String id) {
        followers.add(id);
    }

    void postMessage(String content) {
        Message msg = new Message(this, content);
        feed.add(msg);
        posts.add(msg);
        Manager.getInstance().push(this, msg);
    }

    void receiveMessage(Message msg) {
        feed.add(msg);
    }

    HashSet<String> getFollowings() {
        return followings;
    }

    public HashSet<String> getFollowers() {
        return followers;
    }

    PriorityQueue<Message> getPosts() {
        return posts;
    }

    PriorityQueue<Message> getFeed() {
        return feed;
    }

    boolean isFollower(String id) {
        return followers.contains(id);
    }
}
