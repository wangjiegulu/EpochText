package com.wangjiegulu.epochtext.library.markdown.linecode;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.Spanned;
import android.widget.TextView;

import com.wangjiegulu.epochtext.library.BaseSpanResolver;
import com.wangjiegulu.epochtext.library.spans.inline.InlineCodeSpan;
import com.wangjiegulu.epochtext.library.util.SizeUtil;

import java.lang.ref.WeakReference;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/4.
 */
public class MarkdownInlineCodeSpanResolver extends BaseSpanResolver<String> {
    @Override
    public String patternRegex() {
        return "(?<!`)`{1}[^`]+?`(?!`)";
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
            spanStart = groupStart;
            spanEnd = groupEnd;
        }
        TextView tv = textViewRef.get();
        if(null == tv){
            return;
        }
        Context context = tv.getContext();
        Paint.FontMetricsInt fm = tv.getPaint().getFontMetricsInt();
        int height = Math.abs(fm.ascent) + Math.abs(fm.descent);
        spannable.setSpan(new InlineCodeSpan(
                height,
                group.substring(group.indexOf('`') + 1, group.lastIndexOf('`')),
                0xff999999,
                (int)tv.getTextSize(),
                0xffe1e1e1,
                (int)SizeUtil.dp2px(tv.getContext(), 5),
                (int)SizeUtil.dp2px(context, 4),
                (int)SizeUtil.dp2px(context, 8),
                (int)SizeUtil.dp2px(context, 4)
        ), spanStart, spanEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
    }
}
