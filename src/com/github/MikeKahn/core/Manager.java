package com.github.MikeKahn.core;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

/**
 * Created by Michael on 10/24/2016.
 * Singleton
 */
public class Manager {
    private static Manager ourInstance = new Manager();

    public static Manager getInstance() {
        return ourInstance;
    }

    private HashMap<String, User> userMap = new HashMap<>(); //map containing all users
    private HashMap<String, Node> groupMap = new HashMap<>(); //map contains all user groups, groups are a basic node as they simply have an id and children

    private Node root; //root user group, contains all users and user groups as children

    private Manager() {
        root = new Node("Root", null,null); //init root without parent or any children
        groupMap.put(root.id, root);
    }

    public Node getRoot() {
        return root;
    }
    public void setRoot(Node node) {
        root = node;
    }

    public User createUser(String id, Node parent) throws UserIDTakenException {
        if(userMap.containsKey(id)) { //user with id already exists
            throw new UserIDTakenException(id);
        } else {
            User user = new User(id, parent, null);
            userMap.put(id, user);
            parent.add(user);
            return user;
        }
    }

    public Node createGroup(String id, Node parent) throws UserIDTakenException {
        if(groupMap.containsKey(id)) { //user with id already exists
            throw new UserIDTakenException(id);
        } else {
            Node group = new Node(id, parent, null);
            groupMap.put(id, group);
            parent.add(group);
            return group;
        }
    }

    public int getTotalUserCount() {
        return userMap.size();
    }

    public int getTotalGroupCount() {
        return groupMap.size();
    }

    public int getTotalMessageCount() {
        int count = 0;
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(getRoot());
        while(!queue.isEmpty()) {
            Node n = queue.poll();
            if(n instanceof User) {

            } else {
                queue.addAll(n.getChildren());
            }
        }
        return 0;
    }

    public int getPercentPositive() {
        return 0;
    }

    public void push(User user, Message msg) {
        //TODO: update all open users feeds that follow this user
    }
}
