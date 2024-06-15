package com.github.xiaojiu.exceptions;

public class parametersExceptions extends RuntimeException {
    public parametersExceptions(String message) {
        super(message);
    }

    public parametersExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}
