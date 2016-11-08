package com.github.MikeKahn.core;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.tree.*;
import java.awt.*;
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

    private String userID = "";
    private String groupID = "";

    private DefaultTreeModel model;

    AdminControlGUI() {
        //init all components
        setSize(600, 480);
        setTitle("CS 356 Assignment 2 - Mini Twitter");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(panelParent); //add form to the frame
        model = new DefaultTreeModel(Manager.getInstance().getRoot());
        nodeTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        nodeTree.setModel(model);
        nodeTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) nodeTree.getLastSelectedPathComponent();
            Node instance = (Node) node.getUserObject();
            if (instance instanceof User) {
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
        showPositivePercentageButton.addActionListener(e -> System.out.println("Positive message percent: " + Manager.getInstance().getPercentPositive() + "%"));

        addUserButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Create new user, enter user id below");
            if (input == null || input.trim().equals("")) return;
            if (Manager.getInstance().getGroup(groupID) != null) {
                try {
                    DefaultMutableTreeNode user = Manager.getInstance().createUser(input, Manager.getInstance().getGroup(groupID).instance);
                    model.insertNodeInto(user, (MutableTreeNode) user.getParent(), user.getParent().getIndex(user));
                    nodeTree.scrollPathToVisible(new TreePath(user.getPath()));
                    System.out.println("New user created with user id: " + input);
                } catch (IDTakenException ex) {
                    System.err.println(ex.getMessage());
                }
            } else {
                System.err.println("No group selected.");
            }
        });

        addGroupButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Create new group, enter group id below");
            if (input == null || input.trim().equals("")) return;
            if (Manager.getInstance().getGroup(groupID) != null) {
                try {
                    DefaultMutableTreeNode group = Manager.getInstance().createGroup(input, Manager.getInstance().getGroup(groupID).instance);
                    model.insertNodeInto(group, (MutableTreeNode) group.getParent(), group.getParent().getIndex(group));
                    System.out.println("New group created with group id: " + input);
                } catch (IDTakenException ex) {
                    System.err.println(ex.getMessage());
                }
            } else {
                System.err.println("No group selected");
            }
        });

        openUserViewButton.addActionListener(e -> {
            if (nodeTree.getLastSelectedPathComponent() != null) { //check if no item is selected
                if (((DefaultMutableTreeNode) nodeTree.getLastSelectedPathComponent()).getUserObject() instanceof User) { //check if last selected item is a user
                    User user = (User) ((DefaultMutableTreeNode) nodeTree.getLastSelectedPathComponent()).getUserObject();
                    if (user == null) {
                        System.err.println("Error: No user selected.");
                        return;
                    } //return if no user selected
                    if (Manager.getInstance().userActive(user.id)) {
                        System.err.println("Error: User is already active.");
                        return;
                    }
                    UserControlGUI userControlGUI = new UserControlGUI(user);
                    Manager.getInstance().setActive(user.id, userControlGUI.getModel());
                    userControlGUI.setVisible(true);
                }
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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelParent = new JPanel();
        panelParent.setLayout(new BorderLayout(0, 0));
        panelParent.putClientProperty("html.disable", Boolean.FALSE);
        final JSplitPane splitPane1 = new JSplitPane();
        splitPane1.setDividerLocation(180);
        splitPane1.setDividerSize(5);
        splitPane1.setResizeWeight(0.5);
        panelParent.add(splitPane1, BorderLayout.CENTER);
        final JScrollPane scrollPane1 = new JScrollPane();
        splitPane1.setLeftComponent(scrollPane1);
        nodeTree = new JTree();
        nodeTree.setBackground(new Color(-9737365));
        scrollPane1.setViewportView(nodeTree);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(8, 5, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-9737365));
        splitPane1.setRightComponent(panel1);
        showUserTotalButton = new JButton();
        showUserTotalButton.setBackground(new Color(-10855846));
        showUserTotalButton.setText("Show User Total");
        panel1.add(showUserTotalButton, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        showPositivePercentageButton = new JButton();
        showPositivePercentageButton.setBackground(new Color(-10855846));
        showPositivePercentageButton.setText("Show Positive Percentage");
        panel1.add(showPositivePercentageButton, new com.intellij.uiDesigner.core.GridConstraints(7, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        showMessageTotalButton = new JButton();
        showMessageTotalButton.setBackground(new Color(-10855846));
        showMessageTotalButton.setText("Show Message Total");
        panel1.add(showMessageTotalButton, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        showGroupTotalButton = new JButton();
        showGroupTotalButton.setBackground(new Color(-10855846));
        showGroupTotalButton.setText("Show Group Total");
        panel1.add(showGroupTotalButton, new com.intellij.uiDesigner.core.GridConstraints(6, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblUserID = new JLabel();
        lblUserID.setFont(new Font(lblUserID.getFont().getName(), lblUserID.getFont().getStyle(), 18));
        lblUserID.setText("User ID:");
        panel1.add(lblUserID, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblGroupID = new JLabel();
        lblGroupID.setFont(new Font(lblGroupID.getFont().getName(), lblGroupID.getFont().getStyle(), 18));
        lblGroupID.setText("Group ID: ");
        panel1.add(lblGroupID, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addUserButton = new JButton();
        addUserButton.setBackground(new Color(-10855846));
        addUserButton.setText("Add User");
        panel1.add(addUserButton, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addGroupButton = new JButton();
        addGroupButton.setBackground(new Color(-10855846));
        addGroupButton.setText("Add Group");
        panel1.add(addGroupButton, new com.intellij.uiDesigner.core.GridConstraints(2, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        openUserViewButton = new JButton();
        openUserViewButton.setBackground(new Color(-10855846));
        openUserViewButton.setText("Open User View");
        panel1.add(openUserViewButton, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setFont(new Font(label1.getFont().getName(), label1.getFont().getStyle(), 16));
        label1.setHorizontalAlignment(0);
        label1.setHorizontalTextPosition(0);
        label1.setText("Console");
        panel1.add(label1, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        panel1.add(scrollPane2, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        textPaneConsole = new JTextPane();
        textPaneConsole.setEditable(false);
        scrollPane2.setViewportView(textPaneConsole);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelParent;
    }
}
