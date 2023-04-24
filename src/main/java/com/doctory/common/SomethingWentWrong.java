package com.doctory.common;

public class SomethingWentWrong extends RuntimeException {
    public SomethingWentWrong(String message) {
        super(message);
    }
}
