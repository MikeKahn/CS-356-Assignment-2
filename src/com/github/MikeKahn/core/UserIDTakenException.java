package com.github.MikeKahn.core;

/**
 * Created by Michael on 10/24/2016.
 */
public class UserIDTakenException extends Exception {

        public UserIDTakenException(String id) {
            super("User id '" + id + "' is already taken.");
        }

}
