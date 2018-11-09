package com.wangjiegulu.epochtext.library.markdown.bold;

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
 * Date: 2018/11/5.
 */
public class MarkdownBoldSpanResolver extends BaseSpanResolver<String>{
    @Override
    public String patternRegex() {
        return "(?<!\\*)\\*{2}[^\\*]+\\*{2}|(?<!_)_{2}[^_]+_{2}";
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
            spanStart = groupStart + 2;
            spanEnd = groupEnd - 2;
        } else {
            spannable.setSpan(new ScaleXSpan(0), groupStart, groupStart + 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            spannable.setSpan(new ScaleXSpan(0), groupEnd - 2, groupEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            spanStart = groupStart;
            spanEnd = groupEnd;
        }

        spannable.setSpan(new StyleSpan(Typeface.BOLD), spanStart, spanEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
    }
}
