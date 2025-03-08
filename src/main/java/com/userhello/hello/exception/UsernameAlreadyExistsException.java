package com.userhello.hello.exception;

public class UsernameAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L; // برای حذف هشدار اضافه شد

    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
