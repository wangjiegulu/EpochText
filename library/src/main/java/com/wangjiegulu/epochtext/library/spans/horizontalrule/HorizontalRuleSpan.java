package com.wangjiegulu.epochtext.library.spans.horizontalrule;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.style.ReplacementSpan;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/9.
 */
public class HorizontalRuleSpan extends ReplacementSpan{
    private static final String TAG = HorizontalRuleSpan.class.getSimpleName();
    private int width;
    private Paint linePaint;
    private int lineSpace;

    public HorizontalRuleSpan(int width, int color, float borderSize, int lineSpace) {
        this.width = width;
        this.lineSpace = lineSpace;
        linePaint = new Paint();
        linePaint.setColor(color);
        linePaint.setStrokeWidth(borderSize);
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, @Nullable Paint.FontMetricsInt fm) {
        return width;
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        int startY = top + (bottom - top) / 2 - lineSpace / 2;
        canvas.drawLine(0, startY, width, startY, linePaint);
    }
}
