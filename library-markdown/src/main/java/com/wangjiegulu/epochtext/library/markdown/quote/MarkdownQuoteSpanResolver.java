package com.wangjiegulu.epochtext.library.markdown.quote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ScaleXSpan;
import android.util.Log;
import android.widget.TextView;

import com.wangjiegulu.epochtext.library.BaseSpanResolver;
import com.wangjiegulu.epochtext.library.spans.quote.EpochQuoteSpan;

import java.lang.ref.WeakReference;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/8.
 */
public class MarkdownQuoteSpanResolver extends BaseSpanResolver<String>{
    private static final String TAG = MarkdownQuoteSpanResolver.class.getSimpleName();

    @Override
    public String patternRegex() {
        return "(?:^|\\n)(&gt;|>)\\s(.*)";
    }

    @Nullable
    @Override
    public String resolveEntry(@NonNull String group) throws RuntimeException {
        return group;
    }

    @Override
    public void assembly(@NonNull WeakReference<TextView> textViewRef, @NonNull Spannable spannable, @NonNull String group, @NonNull String entry, int groupStart, int groupEnd) {
        if(!isEditMode){
            int start = group.indexOf("> ");
            if(start >= 0){
                Log.i(TAG, "groupEnd: " + groupEnd);
                spannable.setSpan(
                        new EpochQuoteSpan(0xffdddddd, 15, 40, textViewRef.get().getLineHeight(), groupEnd),
                        groupStart + start, groupEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                spannable.setSpan(
                        new ScaleXSpan(0),
                        groupStart + start, groupStart + start + 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            }
        }

        spannable.setSpan(new ForegroundColorSpan(0xffbbbbbb), groupStart, groupEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
    }
}
