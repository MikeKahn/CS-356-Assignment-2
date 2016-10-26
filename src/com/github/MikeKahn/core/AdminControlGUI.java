package com.github.MikeKahn.core;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by Michael on 10/24/2016.
 *
 */
public class AdminControlGUI extends JFrame {
    private JPanel panelParent;
    private JTree nodeTree;
    private JButton addUserButton;
    private JButton addGroupButton;
    private JButton openUserViewButton;
    private JButton showUserTotalButton;
    private JButton showPositivePercentageButton;
    private JButton showMessageTotalButton;
    private JButton showGroupTotalButton;
    private JLabel lblUserID;
    private JLabel lblGroupID;
    private JTextPane textPaneConsole;

    private String userID = "";
    private String groupID = "";

    private DefaultTreeModel model;

    AdminControlGUI() {
        //init all components
        setSize(600,480);
        setTitle("CS 356 Assignment 2 - Mini Twitter");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(panelParent); //add form to the frame
        model = new DefaultTreeModel(Manager.getInstance().getRoot());
        nodeTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        nodeTree.setModel(model);
        nodeTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)nodeTree.getLastSelectedPathComponent();
            Node instance = (Node)node.getUserObject();
            if(instance instanceof User) {
                userID = instance.id;
                groupID = instance.parentNode.id;
            } else {
                userID = "-";
                groupID = instance.id;
            }
            lblUserID.setText("User ID: " + userID);
            lblGroupID.setText("Group ID: " + groupID);
        });

        redirectSystemStreams();

        showUserTotalButton.addActionListener(e -> System.out.println("Total user count: " + Manager.getInstance().getTotalUserCount()));
        showGroupTotalButton.addActionListener(e -> System.out.println("Total group count: " + Manager.getInstance().getTotalGroupCount()));
        showMessageTotalButton.addActionListener(e -> System.out.println("Total message count: " + Manager.getInstance().getTotalMessageCount()));
        showPositivePercentageButton.addActionListener(e -> System.out.println("Positive message percent: " + Manager.getInstance().getPercentPositive()));

        addUserButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Create new user, enter user id below");
            if(input == null || input.trim().equals("")) return;
            if(Manager.getInstance().getGroup(groupID) != null) {
                try {
                    DefaultMutableTreeNode user = Manager.getInstance().createUser(input, Manager.getInstance().getGroup(groupID).instance);
                    model.insertNodeInto(user, (MutableTreeNode) user.getParent(), user.getParent().getIndex(user));
                    System.out.println("New user created with user id: " + input);
                } catch(IDTakenException ex) {
                    System.err.println(ex.getMessage());
                }
            } else {
                System.err.println("No group selected.");
            }
        });

        addGroupButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Create new group, enter group id below");
            if(input == null || input.trim().equals("")) return;
            if(Manager.getInstance().getGroup(groupID) != null) {
                try {
                    DefaultMutableTreeNode group = Manager.getInstance().createGroup(input, Manager.getInstance().getGroup(groupID).instance);
                    model.insertNodeInto(group, (MutableTreeNode)group.getParent(), group.getParent().getIndex(group));
                    System.out.println("New group created with group id: " + input);
                } catch(IDTakenException ex) {
                    System.err.println(ex.getMessage());
                }
            } else {
                System.err.println("No group selected");
            }
        });

        openUserViewButton.addActionListener(e -> {
            if(((DefaultMutableTreeNode)nodeTree.getLastSelectedPathComponent()).getUserObject() instanceof User) { //check if last selected item is a user
                User user = (User)((DefaultMutableTreeNode) nodeTree.getLastSelectedPathComponent()).getUserObject();
                if(user == null){
                    System.err.println("Error: No user selected.");
                    return;
                } //return if no user selected
                if(Manager.getInstance().userActive(user.id)) {
                    System.err.println("Error: User is already active.");
                    return;
                }
                UserControlGUI userControlGUI = new UserControlGUI(user);
                Manager.getInstance().setActive(user.id,userControlGUI.getModel());
                userControlGUI.setVisible(true);
            }
        });

    }

    //update text pane with output from console
    private void updateTextPane(final String text) {
        SwingUtilities.invokeLater(() -> {
            Document doc = textPaneConsole.getDocument();
            try {
                doc.insertString(doc.getLength(), text, null);
            } catch (BadLocationException e) {
                throw new RuntimeException(e);
            }
            textPaneConsole.setCaretPosition(doc.getLength() - 1);
        });
    }

    private void redirectSystemStreams() {
        OutputStream out = new OutputStream() {
            @Override
            public void write(final int b) throws IOException {
                updateTextPane(String.valueOf((char) b));
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                updateTextPane(new String(b, off, len));
            }

            @Override
            public void write(byte[] b) throws IOException {
                write(b, 0, b.length);
            }
        };

        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(out, true));
    }

}
