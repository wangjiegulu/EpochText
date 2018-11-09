package com.wangjiegulu.epochtext.library.markdown.italic;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ScaleXSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

import com.wangjiegulu.epochtext.library.BaseSpanResolver;

import java.lang.ref.WeakReference;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/4.
 */
public class MarkdownItalicSpanResolver extends BaseSpanResolver<String> {
    @Override
    public String patternRegex() {
//        return "(?<!\\*)\\*{1}[^\\*]+?\\*(?!\\*)|(?<!_)_{1}[^_]+?_(?!_)";
        return "(?<!\\*)\\*{1}[^\\*]+?\\*(?!\\*)";
    }

    @Nullable
    @Override
    public String resolveEntry(@NonNull String group) throws RuntimeException {
        return group;
    }

    @Override
    public void assembly(@NonNull WeakReference<TextView> textViewRef, @NonNull Spannable spannable, @NonNull String group, @NonNull String entry, int groupStart, int groupEnd) {
        int spanStart;
        int spanEnd;

        if(isEditMode){
            spanStart = groupStart + 1;
            spanEnd = groupEnd - 1;
        } else {
            spannable.setSpan(new ScaleXSpan(0), groupStart, groupStart + 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            spannable.setSpan(new ScaleXSpan(0), groupEnd - 1, groupEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            spanStart = groupStart;
            spanEnd = groupEnd;
        }

        spannable.setSpan(new StyleSpan(Typeface.ITALIC), spanStart, spanEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
    }
}
