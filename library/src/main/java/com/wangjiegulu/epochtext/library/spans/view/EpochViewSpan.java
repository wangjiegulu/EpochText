package com.wangjiegulu.epochtext.library.spans.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.style.ReplacementSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wangjiegulu.epochtext.library.exception.EpochTextException;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/9.
 */
public abstract class EpochViewSpan extends ReplacementSpan {
    private static final String TAG = EpochViewSpan.class.getSimpleName();
    private TextView parentTv;
    private View view;
    private int lineHeight;

    private int viewMeasuredWidth;
    private int viewMeasuredHeight;

    private View.OnAttachStateChangeListener onAttachStateChangeListener;

    public EpochViewSpan(final TextView parentTv, int lineHeight) {
        this.parentTv = parentTv;
        this.lineHeight = lineHeight;
        onAttachStateChangeListener = new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                // ignore
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                onParentTvDetachedFromWindow();
                resetParentTv();
            }
        };
        parentTv.addOnAttachStateChangeListener(onAttachStateChangeListener);
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, @Nullable Paint.FontMetricsInt fm) {
        Log.i(TAG, "getSize...");
        if(null == view){
            view = createView();
            if(null != view){
                FrameLayout frameLayout = getParentFrameLayout();
                if(null != frameLayout){
                    frameLayout.addView(view);
                }

                measureView(view);
            }
        }

        if (fm != null) {
            fm.ascent = -viewMeasuredHeight;
            fm.descent = 0;

            fm.top = fm.ascent;
            fm.bottom = 0;
        }
        return viewMeasuredWidth;
    }

    protected void measureView(int widthMeasureSpec, int heightMeasureSpec){
        if(null != view){
            view.measure(widthMeasureSpec, heightMeasureSpec);
            viewMeasuredWidth = view.getMeasuredWidth();
            viewMeasuredHeight = view.getMeasuredHeight();
        }
        Log.i(TAG, "Measure viewMeasuredWidth: " + viewMeasuredWidth + ", viewMeasuredHeight: " + viewMeasuredHeight);
        Log.i(TAG, "Measure viewWidth: " + view.getWidth() + ", viewHeight: " + view.getHeight());
    }

    protected abstract View createView();

    protected abstract void measureView(View view);

    protected void onParentTvDetachedFromWindow(){

    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        if(null == view){
            return;
        }
        Log.i(TAG, "Draw viewMeasuredWidth: " + viewMeasuredWidth + ", viewMeasuredHeight: " + viewMeasuredHeight);
        Log.i(TAG, "Draw viewWidth: " + view.getWidth() + ", viewHeight: " + view.getHeight());
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        int lineSpace = lineHeight - Math.abs(fm.ascent) - Math.abs(fm.descent) - Math.abs(fm.leading);
        int transY = bottom - view.getHeight() - lineSpace;

        view.setX((int)x);
        view.setY(transY);

        // 占位
        paint.setColor(Color.BLUE);
        canvas.drawRect(new Rect((int)x, transY, (int)x + view.getWidth(), transY + view.getHeight()), paint);
    }

    @Nullable
    public View getView() {
        return view;
    }

    @Nullable
    public TextView getParentTv() {
        return parentTv;
    }

    @Nullable
    public Context getContext(){
        return null != parentTv ? parentTv.getContext() : null;
    }

    private void resetParentTv(){
        parentTv = null;
    }

    public void onSpanRemoved() {
        Log.i(TAG, "onSpanRemoved...");
        if(null != parentTv){
            parentTv.removeOnAttachStateChangeListener(onAttachStateChangeListener);
        }

        FrameLayout frameLayout = getParentFrameLayout();
        if(null != view && null != frameLayout){
            frameLayout.removeView(view);
            view = null;
        }
    }

    @Nullable
    private FrameLayout getParentFrameLayout(){
        if(null == parentTv){
            return null;
        }
        ViewParent viewParent = parentTv.getParent();
        if(null == viewParent || !(viewParent instanceof FrameLayout)){
            throw new EpochTextException("FrameLayout required wrapper to EditText / TextView.");
        }

        return (FrameLayout) viewParent;
    }
}
