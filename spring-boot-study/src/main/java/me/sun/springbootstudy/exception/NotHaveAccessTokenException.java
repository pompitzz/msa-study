package me.sun.springbootstudy.exception;

public class NotHaveAccessTokenException extends RuntimeException {
    public NotHaveAccessTokenException(String message) {
        super(message);
    }
}
