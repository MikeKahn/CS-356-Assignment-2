package com.github.MikeKahn.core;

/**
 * Created by Michael on 10/24/2016.
 *
 */
public class Driver {

        private static final String[] positiveWords = {
                "good", "great", "beautiful", "excellent"
        };

        public static void main(String[] args) {
            Manager instance = Manager.getInstance(); //init the singleton

            instance.addPositiveWords(positiveWords);

            java.awt.EventQueue.invokeLater(() -> new AdminControlGUI().setVisible(true));


        }

}
