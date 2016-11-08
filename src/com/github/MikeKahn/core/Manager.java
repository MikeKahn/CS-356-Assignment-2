package com.github.MikeKahn.core;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Michael on 10/24/2016.
 *
 */
class Manager {
    private static Manager ourInstance = new Manager();

    static Manager getInstance() {
        return ourInstance;
    }

    private HashMap<String, User> userMap = new HashMap<>(); //map containing all users
    private HashMap<String, Node> groupMap = new HashMap<>(); //map contains all user groups, groups are a basic node as they simply have an id and children

    private DefaultMutableTreeNode root; //root user group, contains all users and user groups as children

    private HashMap<String, DefaultListModel> activeUsers;

    private HashSet<String> positiveWords;

    private int posMsgCount;
    private int totalMsgCount;

    private Manager() {
        Node rootNode = new Node("Root",null,null);
        activeUsers = new HashMap<>();
        root = new DefaultMutableTreeNode(rootNode); //init root without parent or any children
        rootNode.instance = root;
        groupMap.put(rootNode.id, rootNode);
        positiveWords = new HashSet<>();
        posMsgCount = 0;
        totalMsgCount = 0;
    }

    DefaultMutableTreeNode getRoot() {
        return root;
    }

    private Node getObject(DefaultMutableTreeNode node) {
        return (Node)node.getUserObject();
    }

    DefaultMutableTreeNode createUser(String id, DefaultMutableTreeNode parent) throws IDTakenException {
        if(userMap.containsKey(id)) { //user with id already exists
            throw new IDTakenException(id);
        } else {
            User user = new User(id, parent, null);
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(user);
            user.setInstance(node);
            userMap.put(id, user);
            parent.add(node);
            return node;
        }
    }

    DefaultMutableTreeNode createGroup(String id, DefaultMutableTreeNode parent) throws IDTakenException {
        if(groupMap.containsKey(id)) { //user with id already exists
            throw new IDTakenException(id);
        } else {
            Node group = new Node(id, parent, null);
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(group);
            group.setInstance(node);
            groupMap.put(id, group);
            parent.add(node);
            return node;
        }
    }

    boolean userActive(String userID) {
        return activeUsers.containsKey(userID);
    }

    void setActive(String userID, DefaultListModel model) {
        if(userActive(userID)) { //disable if active
            activeUsers.remove(userID);
        } else {
            activeUsers.put(userID, model); //enable if inactive
        }
    }

    Node getGroup(String id) {
        return (groupMap.containsKey(id)) ? groupMap.get(id) : null;
    }

    User getUser(String id) {return (userMap.containsKey(id)) ? userMap.get(id) : null;}

    boolean userExists(String id) {
        return userMap.containsKey(id);
    }

    void addPositiveWords(String[] words) {
        positiveWords.addAll(Arrays.asList(words));
    }

    int getTotalUserCount() {
        return userMap.size();
    }

    int getTotalGroupCount() {
        return groupMap.size();
    }

    int getTotalMessageCount() {
        return totalMsgCount;
    }

    //return pos msg count divided by total as a double resulting in fraction, multiply 100 for percent
    int getPercentPositive() {
        return (int)((((double) posMsgCount)/ totalMsgCount) * 100);
    }

    DefaultListModel getActiveUser(String id) {
        return (userActive(id)) ? activeUsers.get(id) : null;
    }

    void push(User user, Message msg) {
        totalMsgCount++; //inc message count
        String[] split = msg.content.split("\\s+"); //split msg by empty space into array
        for(String s: split) {
            if(positiveWords.contains(s)) { //check if word is positive, if so inc
                posMsgCount++;
            }
        }
        activeUsers.keySet().stream().filter(user::isFollower).forEachOrdered(id -> {
            activeUsers.get(id).addElement(msg);
            userMap.get(id).receiveMessage(msg);
        });
        activeUsers.get(user.id).addElement(msg); //user should see there own post
    }
}
