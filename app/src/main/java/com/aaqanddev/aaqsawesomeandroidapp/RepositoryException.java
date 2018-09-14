package com.aaqanddev.aaqsawesomeandroidapp;

import Exception;
//Class Drawn almost directly from Adrian Hall's
//Repository demo, original Medium here:
// https://medium.com/@FizzyInTheHall/building-a-production-android-app-9-the-repository-pattern-fbae7dd398ce

public class RepositoryException extends Exception {
    public RepositoryException(String msg){
        super(msg);
    }

    public RepositoryException(String msg, Throwable cause){
        super(msg, cause);
    }
}
