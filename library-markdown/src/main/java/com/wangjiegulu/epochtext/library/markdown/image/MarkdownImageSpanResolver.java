package com.wangjiegulu.epochtext.library.markdown.image;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.wangjiegulu.epochtext.library.BaseSpanResolver;
import com.wangjiegulu.epochtext.library.spans.image.BottomAlignImageSpan;
import com.wangjiegulu.epochtext.library.spans.longclick.FullClickableSpan;
import com.wangjiegulu.epochtext.library.util.SizeUtil;

import java.lang.ref.WeakReference;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/4.
 */
public class MarkdownImageSpanResolver extends BaseSpanResolver<MarkdownImageEntry> {
    private static final String TAG = MarkdownImageSpanResolver.class.getSimpleName();

    @Override
    public String patternRegex() {
//        return "!\\[.*]\\(.*\\)";
        return "!\\[[^\\]]+\\]\\([^\\)]+\\)";
    }

    @Override
    @Nullable
    public MarkdownImageEntry resolveEntry(@NonNull String group) throws RuntimeException{
        return new MarkdownImageEntry(
                group.substring(group.indexOf("[") + 1, group.indexOf("]")),
                group.substring(group.indexOf("(") + 1, group.indexOf(")"))
        );
    }

    @Override
    public void assembly(@NonNull final WeakReference<TextView> textViewRef, @NonNull final Spannable spannable, @NonNull String group, @NonNull final MarkdownImageEntry entry, final int groupStart, final int groupEnd) {
        if(null == textViewRef.get()){
            return;
        }

        // TODO: 2018/11/8 wangjie
        Drawable placeHolder = new ColorDrawable(0xffe4e4e4);
        int width = (int) SizeUtil.dp2px(textViewRef.get().getContext(), 300);
        int height = width / 2;
        placeHolder.setBounds(0, 0, width, height);
        final BottomAlignImageSpan placeHolderSpan = new BottomAlignImageSpan(placeHolder, textViewRef.get().getLineHeight());
        spannable.setSpan(placeHolderSpan, groupStart, groupEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        Glide.with(textViewRef.get().getContext())
                .asDrawable()
                .load(entry.getUrl())
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        if(null == textViewRef.get()){
                            return;
                        }

                        int width = textViewRef.get().getWidth();
                        int height = width * resource.getIntrinsicHeight() / resource.getIntrinsicWidth();

                        resource.setBounds(0, 0, width, height);
                        // 删除placeHolder
                        spannable.removeSpan(placeHolderSpan);

//                        resource.setBounds(0, 0, 280, 280);
                        spannable.setSpan(new BottomAlignImageSpan(resource, textViewRef.get().getLineHeight()), groupStart, groupEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    }
                });

        spannable.setSpan(new FullClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(textViewRef.get().getContext(), entry.getDesc(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onLongClick(View widget) {
                Toast.makeText(textViewRef.get().getContext(), "long click: " + entry.getDesc(), Toast.LENGTH_SHORT).show();
            }
        }, groupStart, groupEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
    }

}
