package com.github.MikeKahn.core;

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.EventListener;

/**
 * Created by Michael on 10/25/2016.
 */
public class NodeTreeModel implements TreeModel {

    private EventListenerList listenerList;

    public NodeTreeModel() {
        Manager.getInstance(); //ensure that manager has been init'd
        listenerList = new EventListenerList();
    }

    public NodeTreeModel(Node node) {
        Manager.getInstance().setRoot(node);
        listenerList = new EventListenerList();
    }

    @Override
    public Object getRoot() {
        return Manager.getInstance().getRoot();
    }

    @Override
    public Object getChild(Object parent, int index) {
        return ((Node)parent).getChild(index);
    }

    @Override
    public int getChildCount(Object parent) {
        return ((Node)parent).getChildCount();
    }

    @Override
    public boolean isLeaf(Object node) {
        return ((Node)node).getChildCount() == 0;
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {

    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        Node pnode = (Node)parent;
        Node cnode = (Node)child;
        for(int i = 0; i < pnode.getChildCount(); i++) {
            if(pnode.getChild(i).equals(cnode)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
        listenerList.add(TreeModelListener.class, l);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        listenerList.remove(TreeModelListener.class, l);
    }

    protected void fireTreeStructureChanged(Object oldRoot) {
        TreeModelEvent event = new TreeModelEvent(this, new Object[] { oldRoot });
        EventListener[] listeners = listenerList.getListeners(TreeModelListener.class);
        for (int i = 0; i < listeners.length; i++)
            ((TreeModelListener) listeners[i]).treeStructureChanged(event);
    }
}
