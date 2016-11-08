package com.github.MikeKahn.core;

/**
 * Created by Michael on 10/24/2016.
 *
 */
class IDTakenException extends Exception {

        IDTakenException(String id) {
            super("Id '" + id + "' is already taken.");
        }

}
