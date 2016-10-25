package com.github.MikeKahn.core;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by Michael on 10/24/2016.
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

    public AdminControlGUI() {
        //init all components
        setSize(600,480);
        setTitle("CS 356 Assignment 2 - Mini Twitter");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(panelParent); //add form to the frame
        NodeTreeModel model = new NodeTreeModel(Manager.getInstance().getRoot());
        nodeTree.setModel(model);
        nodeTree.addTreeSelectionListener(e -> {
            Node node = (Node)nodeTree.getLastSelectedPathComponent();
            if(node instanceof User) {
                lblUserID.setText("User ID: " + node.id);
                lblGroupID.setText("Group ID: " + node.parent.id);
            } else {
                lblUserID.setText("User ID: -");
                lblGroupID.setText("Group ID: " + node.id);
            }
        });

        redirectSystemStreams();

        showUserTotalButton.addActionListener(e -> System.out.println("Total user count: " + Manager.getInstance().getTotalUserCount()));
        showGroupTotalButton.addActionListener(e -> System.out.println("Total group count: " + Manager.getInstance().getTotalGroupCount()));
        showMessageTotalButton.addActionListener(e -> System.out.println("Total message count: " + Manager.getInstance().getTotalMessageCount()));
        showPositivePercentageButton.addActionListener(e -> System.out.println("Positive message percent: " + Manager.getInstance().getPercentPositive()));

        addUserButton.addActionListener(e -> {
            System.out.println("add user");
        });

        addGroupButton.addActionListener(e -> {
            System.out.println("add group");
        });

        openUserViewButton.addActionListener(e -> {
            if(nodeTree.getLastSelectedPathComponent() instanceof User) { //check if last selected item is a user
                //TODO: open user window
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
