package com.github.MikeKahn.core;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;

/**
 * Created by Michael on 10/24/2016.
 * Singleton
 */
class Manager {
    private static Manager ourInstance = new Manager();

    static Manager getInstance() {
        return ourInstance;
    }

    private HashMap<String, User> userMap = new HashMap<>(); //map containing all users
    private HashMap<String, Node> groupMap = new HashMap<>(); //map contains all user groups, groups are a basic node as they simply have an id and children

    private DefaultMutableTreeNode root; //root user group, contains all users and user groups as children

    private HashSet<String> activeUsers;

    private Manager() {
        Node rootNode = new Node("Root",null,null);
        activeUsers = new HashSet<>();
        root = new DefaultMutableTreeNode(rootNode); //init root without parent or any children
        rootNode.instance = root;
        groupMap.put(rootNode.id, rootNode);
    }

    DefaultMutableTreeNode getRoot() {
        return root;
    }
    /*void setRoot(DefaultMutableTreeNode node) {
        root = node;
    }*/

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

    Node getGroup(String id) {
        return (groupMap.containsKey(id)) ? groupMap.get(id) : null;
    }

    int getTotalUserCount() {
        return userMap.size();
    }

    int getTotalGroupCount() {
        return groupMap.size();
    }

    int getTotalMessageCount() {
        int count = 0;
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(getObject(getRoot()));
        while(!queue.isEmpty()) {
            Node n = queue.poll();
            if(n instanceof User) {
                count += ((User) n).getMessages().size();
            } else {
                queue.addAll(n.getChildren());
            }
        }
        return count;
    }

    int getPercentPositive() {
        return 0;
    }

    void push(User user, Message msg) {
        //TODO: update all open users feeds that follow this user
        for (String id: activeUsers) {
            System.out.println(id);
        }
    }
}
