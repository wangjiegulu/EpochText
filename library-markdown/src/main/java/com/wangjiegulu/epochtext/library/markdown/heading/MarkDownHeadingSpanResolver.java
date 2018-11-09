package com.wangjiegulu.epochtext.library.markdown.heading;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
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
public class MarkDownHeadingSpanResolver extends BaseSpanResolver<MarkDownHeadingEntry> {
    private static final String TAG = MarkDownHeadingSpanResolver.class.getSimpleName();

    @Override
    public String patternRegex() {
        return "\\#{1,6}(?!\\#)\\s.*?($|\\n)";
    }

    @Nullable
    @Override
    public MarkDownHeadingEntry resolveEntry(@NonNull String group) throws RuntimeException {
//        int symbolCount = group.length() - group.replace("#", "").length();
        int symbolCount = group.indexOf(" ");
        return new MarkDownHeadingEntry(symbolCount, (float) (1.0f + Math.max(0, (6 - symbolCount)) * 0.1));
    }

    @Override
    public void assembly(@NonNull WeakReference<TextView> textViewRef, @NonNull Spannable spannable, @NonNull String group, @NonNull MarkDownHeadingEntry entry, int groupStart, int groupEnd) {
        int spanStart;
        int spanEnd;

        if(isEditMode){
            spanStart = groupStart;
            spanEnd = groupEnd;
        } else {
            int symbolCount = entry.getSymbolCount();
            spannable.setSpan(new ScaleXSpan(0), groupStart, groupStart + symbolCount + 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            spanStart = groupStart + symbolCount;
            spanEnd = groupEnd;
        }

        spannable.setSpan(new RelativeSizeSpan(entry.getFontScaleRate()), spanStart, spanEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannable.setSpan(new StyleSpan(Typeface.BOLD), spanStart, spanEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

    }
}
