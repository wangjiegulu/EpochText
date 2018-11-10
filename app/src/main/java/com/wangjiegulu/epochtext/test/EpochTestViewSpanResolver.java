package com.wangjiegulu.epochtext.test;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.Spanned;
import android.widget.TextView;

import com.wangjiegulu.epochtext.library.BaseSpanResolver;
import com.wangjiegulu.epochtext.library.spans.view.EpochViewSpan;

import java.lang.ref.WeakReference;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/9.
 */
public class EpochTestViewSpanResolver extends BaseSpanResolver<String> {
    @Override
    public String patternRegex() {
        return "\\{EPOCH_VIDEO\\}";
    }

    @Nullable
    @Override
    public String resolveEntry(@NonNull String group) throws RuntimeException {
        return group;
    }

    @Override
    public void assembly(@NonNull WeakReference<TextView> textViewRef, @NonNull final Spannable spannable, @NonNull String group, @NonNull String entry, int groupStart, int groupEnd) {
        if (!isEditMode) {
            TextView tv = textViewRef.get();
            if (null == tv) {
                return;
            }
            final EpochViewSpan viewSpan = new TestViewSpan(textViewRef.get(), tv.getLineHeight(), tv.getWidth());
            setEpochSpan(spannable,
                    viewSpan,
                    groupStart, groupEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

//            textViewRef.get().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    spannable.removeSpan(viewSpan);
//                }
//            }, 5000);
        }

    }
}
