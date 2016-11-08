package com.github.MikeKahn.core;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Michael on 10/25/2016.
 *
 */
public class UserControlGUI extends JFrame {


    private JPanel panelParent;
    private JButton followButton;
    private JTextField fieldTweet;
    private JButton postButton;
    private JTextField fieldUserID;
    private JList<String> listFollowing;
    private JList<String> listFeed;

    private DefaultListModel<String> msgModel;

    UserControlGUI(User user) {
        setTitle("User Control - " + user.id);
        setSize(600,480);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Manager.getInstance().setActive(user.id,null); //disable the user
                e.getWindow().dispose();
            }
        });
        add(panelParent);

        listFollowing.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        DefaultListModel<String> model = new DefaultListModel<>();
        user.getFollowings().forEach(model::addElement);
        listFollowing.setModel(model);

        listFeed.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        msgModel = new DefaultListModel<>();
        for(Message m:user.getFeed()) {
            msgModel.addElement(m.toString());
        }
        listFeed.setModel(msgModel);

        fieldUserID.addActionListener(e -> { //enter is pressed
            String id = fieldUserID.getText().trim();
            if(!id.isEmpty()) { //check if user ID field is empty
                user.follow(id,model);
                fieldUserID.setText(""); //clear input
            }
        });

        followButton.addActionListener(e -> {
            String id = fieldUserID.getText().trim();
            if(!id.isEmpty()) { //check if user ID field is empty
                user.follow(id, model);
                fieldUserID.setText(""); //clear input
            }
        });

        fieldTweet.addActionListener(e -> { //enter is pressed
            if(!fieldTweet.getText().isEmpty()) {
                user.postMessage(fieldTweet.getText());
                fieldTweet.setText(""); //clear input
            }
        });

        postButton.addActionListener(e -> {
            if(!fieldTweet.getText().isEmpty()) {
                user.postMessage(fieldTweet.getText());
                fieldTweet.setText(""); //clear input
            }
        });
    }

    DefaultListModel<String> getModel() {
        return msgModel;
    }
}
