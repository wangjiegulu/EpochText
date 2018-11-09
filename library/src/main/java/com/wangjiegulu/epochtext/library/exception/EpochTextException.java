package com.wangjiegulu.epochtext.library.exception;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/4.
 */
public class EpochTextException extends RuntimeException{
    public EpochTextException(String message) {
        super(message);
    }

    public EpochTextException(String message, Throwable cause) {
        super(message, cause);
    }
}
