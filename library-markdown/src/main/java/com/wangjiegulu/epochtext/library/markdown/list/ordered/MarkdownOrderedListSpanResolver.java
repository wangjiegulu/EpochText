package com.wangjiegulu.epochtext.library.markdown.list.ordered;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.TextView;

import com.wangjiegulu.epochtext.library.BaseSpanResolver;

import java.lang.ref.WeakReference;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/8.
 */
public class MarkdownOrderedListSpanResolver extends BaseSpanResolver<String>{
    private static final String TAG = MarkdownOrderedListSpanResolver.class.getSimpleName();

    @Override
    public String patternRegex() {
        return "(?:^|\\n)[0123456789]+\\.\\s.*";
    }

    @Nullable
    @Override
    public String resolveEntry(@NonNull String group) throws RuntimeException {
        return group;
    }

    @Override
    public void assembly(@NonNull WeakReference<TextView> textViewRef, @NonNull Spannable spannable, @NonNull String group, @NonNull String entry, int groupStart, int groupEnd) {
        int end = group.indexOf(" ");
        if(end >= 0){
            Log.i(TAG, "groupEnd: " + groupEnd);

            spannable.setSpan(new StyleSpan(Typeface.BOLD), groupStart + (group.startsWith("\n") ? 0 : 1), groupStart + end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        }

    }
}
