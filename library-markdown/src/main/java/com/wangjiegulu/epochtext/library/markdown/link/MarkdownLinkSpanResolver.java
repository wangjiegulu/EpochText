package com.wangjiegulu.epochtext.library.markdown.link;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ScaleXSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wangjiegulu.epochtext.library.BaseSpanResolver;
import com.wangjiegulu.epochtext.library.spans.longclick.FullClickableSpan;

import java.lang.ref.WeakReference;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/4.
 */
public class MarkdownLinkSpanResolver extends BaseSpanResolver<MarkdownLinkEntry> {
    @Override
    public String patternRegex() {
        return "(?<!\\!)\\[.*?\\]\\(.*?\\)";
    }

    @Nullable
    @Override
    public MarkdownLinkEntry resolveEntry(@NonNull String group) throws RuntimeException {
        return new MarkdownLinkEntry(
                group.substring(group.indexOf("[") + 1, group.indexOf("]")),
                group.substring(group.indexOf("(") + 1, group.indexOf(")"))
        );
    }

    @Override
    public void assembly(@NonNull final WeakReference<TextView> textViewRef, @NonNull Spannable spannable, @NonNull String group, @NonNull final MarkdownLinkEntry entry, int groupStart, int groupEnd) {

        int titleStart = groupStart + group.indexOf("[");
        int titleEnd = groupStart + group.indexOf("]");

        int clickableStart;
        int clickableEnd;

        if (isEditMode) {
            clickableStart = groupStart;
            clickableEnd = groupEnd;
        } else { // 非编辑模式则隐藏连接中括号, 小括号, 链接地址
            spannable.setSpan(new ScaleXSpan(0), titleStart, titleStart + 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            spannable.setSpan(new ScaleXSpan(0), titleEnd, groupEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            clickableStart = titleStart + 1;
            clickableEnd = titleEnd;
        }

        spannable.setSpan(new FullClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
                ds.setColor(0xFF63D4D4);
                ds.bgColor = Color.TRANSPARENT;
            }

            @Override
            public void onLongClick(View widget) {
                // ignore
            }

            @Override
            public void onClick(View widget) {
                Toast.makeText(textViewRef.get().getContext(), entry.getLink(), Toast.LENGTH_SHORT).show();
            }
        }, clickableStart, clickableEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);


    }
}
