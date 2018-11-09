package com.wangjiegulu.epochtext.library.markdown;

import com.wangjiegulu.epochtext.library.exception.EpochTextException;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/4.
 */
@Deprecated
public class IllegalMarkdownSyntaxException extends EpochTextException{
    public IllegalMarkdownSyntaxException(String message) {
        super(message);
    }

    public IllegalMarkdownSyntaxException(String message, Throwable cause) {
        super(message, cause);
    }
}
