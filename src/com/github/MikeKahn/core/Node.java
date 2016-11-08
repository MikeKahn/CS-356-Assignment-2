package com.github.MikeKahn.core;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.*;

/**
 * Created by Michael on 10/24/2016.
 *
 */
class Node {

    DefaultMutableTreeNode parent;
    DefaultMutableTreeNode instance;
    Node parentNode;
    private ArrayList<Node> children;
    final String id;

    Node(String id, DefaultMutableTreeNode parent, ArrayList<Node> children) {
        this.id = id;
        this.parent = parent;
        if (parent != null) {
            this.parentNode = (Node)parent.getUserObject();
        }
        this.children = new ArrayList<>();
        if(children != null && !children.isEmpty()) {
            this.children.addAll(children);
        }
    }

    void setInstance(DefaultMutableTreeNode node) {
        instance = node;
    }

    void add(Node child) {
        children.add(child);
    }

    public void add(Node ... children) {
        if(children.length > 0) {
            this.children.addAll(Arrays.asList(children));
        }
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Node && id.equals(((Node) object).id);
    }

    @Override
    public String toString() {
        return ((this instanceof User) ? "u" : "g") + ":" + id;
    }
}
