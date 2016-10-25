package com.github.MikeKahn.core;

/**
 * Created by Michael on 10/24/2016.
 */
public class Driver {
        public static void main(String[] args) {
            Manager instance = Manager.getInstance(); //init the singleton

            try {
                instance.createUser("1", instance.getRoot());
                instance.createUser("2", instance.getRoot());
                instance.createUser("3", instance.getRoot());
                instance.createUser("4", instance.getRoot());
                Node n = instance.createGroup("derp", instance.getRoot());
                instance.createUser("5", n);
                instance.createUser("6", n);
                instance.createUser("7", n);
                instance.createUser("8", n);
            } catch (UserIDTakenException e) {
                e.printStackTrace();
            }
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new AdminControlGUI().setVisible(true);
                }
            });


        }

}
