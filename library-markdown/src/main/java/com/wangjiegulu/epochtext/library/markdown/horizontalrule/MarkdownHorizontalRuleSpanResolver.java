package com.wangjiegulu.epochtext.library.markdown.horizontalrule;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.Spanned;
import android.widget.TextView;

import com.wangjiegulu.epochtext.library.BaseSpanResolver;
import com.wangjiegulu.epochtext.library.spans.horizontalrule.HorizontalRuleSpan;
import com.wangjiegulu.epochtext.library.util.SizeUtil;

import java.lang.ref.WeakReference;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/9.
 */
public class MarkdownHorizontalRuleSpanResolver extends BaseSpanResolver<String>{
    @Override
    public String patternRegex() {
        return "(?:^|\\n)(\\-{3,}|\\*{3,}|\\_{3,})($|\\n)";
    }

    @Nullable
    @Override
    public String resolveEntry(@NonNull String group) throws RuntimeException {
        return group;
    }

    @Override
    public void assembly(@NonNull WeakReference<TextView> textViewRef, @NonNull Spannable spannable, @NonNull String group, @NonNull String entry, int groupStart, int groupEnd) {
        if(!isEditMode){
            TextView tv = textViewRef.get();
            if(null == tv){
                return;
            }
            Context context = tv.getContext();
            Paint.FontMetricsInt fm = tv.getPaint().getFontMetricsInt();
            int lineSpace = tv.getLineHeight() - Math.abs(fm.ascent) - Math.abs(fm.descent) - Math.abs(fm.leading);
            spannable.setSpan(
                    new HorizontalRuleSpan(tv.getWidth(), 0xffcccccc, SizeUtil.dp2px(context, .7F), lineSpace),
                    groupStart, groupEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        }
    }
}
