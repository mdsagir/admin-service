package com.doctory.domain;

public record ResponseModel(String message) {
    public static ResponseModel of(String message) {
        return new ResponseModel(message);
    }
}
