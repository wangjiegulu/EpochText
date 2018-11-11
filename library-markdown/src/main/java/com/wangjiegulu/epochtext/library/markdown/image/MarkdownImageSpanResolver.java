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
    public void assembly(@NonNull final WeakReference<TextView> textViewRef, @NonNull Spannable spannable, @NonNull String group, @NonNull final MarkdownImageEntry entry, final int groupStart, final int groupEnd) {
        TextView tv = textViewRef.get();
        if(null == tv){
            return;
        }
        if(isEditMode){
            return;
        }

        // TODO: 2018/11/8 wangjie
        Drawable placeHolder = new ColorDrawable(0xffe4e4e4);
//        int width = (int) SizeUtil.dp2px(textViewRef.get().getContext(), 300);
        int width = tv.getWidth();
        int height = width / 2;
        placeHolder.setBounds(0, 0, width, height);
        final BottomAlignImageSpan placeHolderSpan = new BottomAlignImageSpan(placeHolder, tv.getLineHeight());
        spannable.setSpan(placeHolderSpan, groupStart, groupEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        Glide.with(tv.getContext())
                .asDrawable()
                .load(entry.getUrl())
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        final TextView tv = textViewRef.get();
                        if(null == tv){
                            return;
                        }

                        // 删除placeHolder
                        Spannable newSpannable = tv.getEditableText();
                        newSpannable.removeSpan(placeHolderSpan);

                        int width = tv.getWidth();
                        int height = width * resource.getIntrinsicHeight() / resource.getIntrinsicWidth();
//                        int height = width / 2;
                        resource.setBounds(0, 0, width, height);
//                        resource.setBounds(0, 0, 280, 280);
                        newSpannable.setSpan(new BottomAlignImageSpan(resource, tv.getLineHeight()), groupStart, groupEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                        // Refresh content due to image span changed.
                        tv.setText(newSpannable, TextView.BufferType.SPANNABLE);
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
