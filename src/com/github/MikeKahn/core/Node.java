package com.github.MikeKahn.core;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Michael on 10/24/2016.
 */
public class Node {

    public Node parent;
    public ArrayList<Node> children;
    public final String id;

    public Node(String id, Node parent, ArrayList<Node> children) {
        this.id = id;
        this.parent = parent;
        this.children = new ArrayList<>();
        if(children != null && !children.isEmpty()) {
            this.children.addAll(children);
        }
    }

    public void add(Node child) {
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

    public Node getChild(int i) {
        if(i < getChildCount()) {
            return children.get(i);
        }
        return null;
    }

    public int getChildCount() {
        return children.size();
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return id.equals(((Node)object).id);
    }

    @Override
    public String toString() {
        return id;
    }
}
