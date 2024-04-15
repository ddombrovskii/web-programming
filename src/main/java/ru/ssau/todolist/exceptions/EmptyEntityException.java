package ru.ssau.todolist.exceptions;

public class EmptyEntityException extends Exception {
    public EmptyEntityException(String message) {
        super(message);
    }
}
