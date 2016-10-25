package com.github.MikeKahn.core;

/**
 * Created by Michael on 10/24/2016.
 */
public class IDTakenException extends Exception {

        IDTakenException(String id) {
            super("Id '" + id + "' is already taken.");
        }

}
