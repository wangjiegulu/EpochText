package com.wangjiegulu.epochtext.library.markdown.list.unordered;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ScaleXSpan;
import android.util.Log;
import android.widget.TextView;

import com.wangjiegulu.epochtext.library.BaseSpanResolver;
import com.wangjiegulu.epochtext.library.spans.bullet.EpochBulletSpan;
import com.wangjiegulu.epochtext.library.util.SizeUtil;

import java.lang.ref.WeakReference;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/8.
 */
public class MarkdownUnorderedListSpanResolver extends BaseSpanResolver<String>{
    private static final String TAG = MarkdownUnorderedListSpanResolver.class.getSimpleName();

    @Override
    public String patternRegex() {
        return "(?:^|\\n)(\\-|\\*|\\+)\\s(.*)";
    }

    @Nullable
    @Override
    public String resolveEntry(@NonNull String group) throws RuntimeException {
        return group;
    }

    @Override
    public void assembly(@NonNull WeakReference<TextView> textViewRef, @NonNull Spannable spannable, @NonNull String group, @NonNull String entry, int groupStart, int groupEnd) {
        if(!isEditMode){
            int start = group.indexOf("- ");
            start = start < 0 ? group.indexOf("* ") : start;
            start = start < 0 ? group.indexOf("+ ") : start;
            if(start >= 0){
                Log.i(TAG, "groupEnd: " + groupEnd);

                TextView tv = textViewRef.get();
                if(null == tv){
                    return;
                }
                Context context = tv.getContext();
                Paint.FontMetricsInt fm = tv.getPaint().getFontMetricsInt();
                int lineSpace = tv.getLineHeight() - Math.abs(fm.ascent) - Math.abs(fm.descent) - Math.abs(fm.leading);

                spannable.setSpan(
                        new EpochBulletSpan(
                                (int)SizeUtil.dp2px(context, 20),
                                0xff000000,
                                (int)SizeUtil.dp2px(context, 3),
                                lineSpace
                        ),
                        groupStart + start, groupEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                spannable.setSpan(
                        new ScaleXSpan(0),
                        groupStart + start, groupStart + start + 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            }
        }

//        spannable.setSpan(new ForegroundColorSpan(0xffbbbbbb), groupStart, groupEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
    }
}
