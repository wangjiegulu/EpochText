package com.wangjiegulu.epochtext.library;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/4.
 */
public interface SpanResolver<T> {
    /**
     * 匹配的正则表达式
     */
    String patternRegex();

    /**
     * 把匹配到的正则进行解析
     */
    @Nullable
    T resolveEntry(@NonNull String group) throws RuntimeException;

    /**
     * 把解析的数据进行装配
     */
    void assembly(@NonNull final WeakReference<TextView> textViewRef, @NonNull final Spannable spannable, @NonNull String group, @NonNull T entry, int groupStart, int groupEnd);
}
