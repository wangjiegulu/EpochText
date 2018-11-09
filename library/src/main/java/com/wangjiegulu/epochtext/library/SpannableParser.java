package com.wangjiegulu.epochtext.library;

import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.wangjiegulu.epochtext.library.exception.EpochTextException;
import com.wangjiegulu.epochtext.library.spans.longclick.LinkMovementMethodExt;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/4.
 */
public class SpannableParser implements TextWatcher {
    private static final String TAG = SpannableParser.class.getSimpleName();
    private WeakReference<TextView> textView;
    private List<BaseSpanResolver<?>> spanResolvers = new ArrayList<>();

    public <T> SpannableParser addSpanResolver(BaseSpanResolver<T> spanResolver) {
        spanResolvers.add(spanResolver);
        return this;
    }
    public SpannableParser addSpanResolvers(BaseSpanResolver<?>... spanResolver) {
        spanResolvers.addAll(Arrays.asList(spanResolver));
        return this;
    }
    public SpannableParser addSpanResolvers(List<BaseSpanResolver<?>> spanResolver) {
        spanResolvers.addAll(spanResolver);
        return this;
    }
    public SpannableParser setEditMode(boolean isEditMode){
        for (BaseSpanResolver<?> spanResolver : spanResolvers) {
            spanResolver.setEditMode(isEditMode);
        }
        return this;
    }

    public SpannableParser(TextView textView) {
        this.textView = new WeakReference<>(textView);
        this.textView.get().addTextChangedListener(this);

        this.textView.get().setMovementMethod(LinkMovementMethodExt.instance());
    }

    public void parse(Editable content) {
        TextView tv = textView.get();
        if(null == tv){
            return;
        }

        int selectionStart = tv.getSelectionStart();
        tv.setText(new SpannableString(content.toString()));
        if(tv instanceof EditText){
            ((EditText)tv).setSelection(selectionStart);
        }

        long start = System.currentTimeMillis();
        for (BaseSpanResolver<?> spanResolver : spanResolvers) {
            resolveSpan(tv.getEditableText(), spanResolver);
        }

        Log.i(TAG, "parse cost: " + (System.currentTimeMillis() - start));
    }

    private <T> void resolveSpan(Spannable content, BaseSpanResolver<T> spanResolver) {
        Log.i(TAG, "[resolveSpan] SpanResolver: " + spanResolver.getClass().getSimpleName());
        Pattern pattern = Pattern.compile(spanResolver.patternRegex());
        final Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            String groupStr = matcher.group();
            final int start = matcher.start();
            final int end = matcher.end();
            Log.i(TAG, "matcherStr: " + groupStr);
            Log.i(TAG, "matcher start: " + start);
            Log.i(TAG, "matcher end: " + end);
            if (null == textView.get()) {
                break;
            }
            T entry;
            try {
                entry = spanResolver.resolveEntry(groupStr);
            } catch (RuntimeException e) {
                throw new EpochTextException("Resolve entry failed ! match group: " + groupStr, e);
            }

            if (null == entry) {
                throw new EpochTextException("Resolved entry is null ! match group: " + groupStr);
            }
            spanResolver.assembly(textView, content, groupStr, entry, start, end);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // ignore
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        // ignore
    }

    private String oldContent;
    @Override
    public void afterTextChanged(Editable s) {
        Log.i(TAG, "afterTextChanged editable: " + s.hashCode());

        if(s.toString().equals(oldContent)){
            return;
        }
        oldContent = s.toString();
//        s.clearSpans();
        parse(s);
    }
}
