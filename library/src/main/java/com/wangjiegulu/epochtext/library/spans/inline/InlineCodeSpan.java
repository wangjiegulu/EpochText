package com.wangjiegulu.epochtext.library.spans.inline;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.ReplacementSpan;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/9.
 */
public class InlineCodeSpan extends ReplacementSpan {
    private static final String TAG = InlineCodeSpan.class.getSimpleName();
    private int spanHeight;
    private String text;
    private int textColor;
    private int textSize;
    private int bgColor;
    private int bgRadius;
    private int drawWidth;
    private int spanWidth;
    private int vPadding;
    private int hPadding;
    private int hMargin;

    private Paint bgPaint = new Paint();
    private TextPaint textPaint = new TextPaint();

    public InlineCodeSpan(int height,
                          String text,
                          int textColor,
                          int textSize,
                          int bgColor,
                          int bgRadius,
                          int vPadding,
                          int hPadding,
                          int hMargin) {
        this.spanHeight = height + vPadding * 2;
        this.text = text;
        this.textColor = textColor;
        this.textSize = textSize;
        this.bgColor = bgColor;
        this.bgRadius = bgRadius;
        this.vPadding = vPadding;
        this.hPadding = hPadding;
        this.hMargin = hMargin;

        drawWidth = (int) calcBgWidth();
        spanWidth = drawWidth + hMargin * 2;
    }

    private float calcBgWidth() {
        // 文字宽度 + padding
        Rect textRect = new Rect();
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        paint.getTextBounds(text, 0, text.length(), textRect);
        return textRect.width() + hPadding * 2;
    }

    /**
     * 设置宽度，宽度=背景宽度+右边距
     */
    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return spanWidth;
    }

    /**
     *
     * @param text   完整文本
     * @param start  setSpan里设置的start
     * @param end    setSpan里设置的start
     * @param top    当前span所在行的上方y
     * @param y      y其实就是metric里baseline的位置
     * @param bottom 当前span所在行的下方y(包含了行间距)，会和下一行的top重合
     * @param paint  使用此span的画笔
     */
    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        // 画背景
        bgPaint.setColor(bgColor);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setAntiAlias(true);

        Paint.FontMetrics metrics = paint.getFontMetrics();
        float textHeight = metrics.descent - metrics.ascent;
        // 算出背景开始画的y坐标
        float bgStartY = y + (textHeight - spanHeight) / 2 + metrics.ascent;

        float startTop = top - vPadding;
        float drawStartX = x + hMargin;
        // 画背景
        RectF bgRect = new RectF(drawStartX, startTop, drawStartX + drawWidth, startTop + spanHeight);
        canvas.drawRoundRect(bgRect, bgRadius, bgRadius, bgPaint);

        // 把字画在背景中间
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);  // 这个只针对x有效
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float textRectHeight = fontMetrics.bottom - fontMetrics.top;
        canvas.drawText(this.text, drawStartX + drawWidth / 2, bgStartY + (spanHeight - textRectHeight) / 2 - fontMetrics.top, textPaint);
    }
}
