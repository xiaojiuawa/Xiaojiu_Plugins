package com.github.xiaojiu.xiaojiuMain.exceptions;

public class uncancelException extends RuntimeException {
    public uncancelException(String message){
        super(message);
    }

    public uncancelException(String message,Throwable cause){
        super(message, cause);
    }
}
