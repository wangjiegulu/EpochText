package com.wangjiegulu.epochtext.library;

import android.text.SpanWatcher;
import android.text.Spannable;

import com.wangjiegulu.epochtext.library.spans.view.EpochViewSpan;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/4.
 */
public abstract class BaseSpanResolver<T> implements SpanResolver<T>{
    /**
     * 是否为编辑模式
     */
    protected boolean isEditMode;

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
    }

    /**
     * Add an View Span, and watch span remove event.
     * @param spannable
     * @param span
     * @param start
     * @param end
     * @param flags
     */
    public void setEpochViewSpan(Spannable spannable, final EpochViewSpan span, final int start, final int end, final int flags){
        spannable.setSpan(span, start, end, flags);
        spannable.setSpan(new SpanWatcher() {
            @Override
            public void onSpanAdded(Spannable text, Object what, int start, int end) {

            }

            @Override
            public void onSpanRemoved(Spannable text, Object what, int start, int end) {
                if(what == span){
                    span.onSpanRemoved();
                }
            }

            @Override
            public void onSpanChanged(Spannable text, Object what, int ostart, int oend, int nstart, int nend) {

            }
        }, start, end, flags);
    }
}
