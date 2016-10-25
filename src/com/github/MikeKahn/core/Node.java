package com.github.MikeKahn.core;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
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
        return id;
    }

   /* @Override
    public TreeNode getChildAt(int childIndex) {
        return (childIndex > -1 && childIndex < getChildCount()) ? children.get(childIndex) : null;
    }

    @Override
    public int getChildCount() {
        return children.size();
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public int getIndex(TreeNode node) {
        for (int i = 0; i < getChildCount(); i++) {
            if(node.equals(children.get(i))) {
                return 0;
            }
        }
        return -1;
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return (getChildCount() == 0);
    }

    @Override
    public Enumeration children() {
        return Collections.enumeration(children);
    }

    @Override
    public void insert(MutableTreeNode child, int index) {
        children.add(index, (Node)child);
    }

    @Override
    public void remove(int index) {

    }

    @Override
    public void remove(MutableTreeNode node) {

    }

    @Override
    public void setUserObject(Object object) {

    }

    @Override
    public void removeFromParent() {
        parent.remove(this);
    }

    @Override
    public void setParent(MutableTreeNode newParent) {
        parent = (Node)newParent;
    }*/
}
