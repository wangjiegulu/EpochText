package com.wangjiegulu.epochtext.library.spans.quote;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.text.Layout;
import android.text.style.QuoteSpan;
import android.util.Log;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/8.
 */
public class EpochQuoteSpan extends QuoteSpan{
    private static final String TAG = EpochQuoteSpan.class.getSimpleName();
    private int mColor;
    private int stripeWidth;
    private int gapWidth;
    private int lineHeight;
    private int end;

    public EpochQuoteSpan(int stripeWidth, int gapWidth, int lineHeight, int end) {
        this.stripeWidth = stripeWidth;
        this.gapWidth = gapWidth;
        this.lineHeight = lineHeight;
        this.end = end;
    }

    public EpochQuoteSpan(int color, int stripeWidth, int gapWidth, int lineHeight, int end) {
        super(color);
        this.stripeWidth = stripeWidth;
        this.gapWidth = gapWidth;
        this.mColor = color;
        this.lineHeight = lineHeight;
        this.end = end;
    }

    public EpochQuoteSpan(Parcel src, int stripeWidth, int gapWidth, int lineHeight, int end) {
        super(src);
        this.stripeWidth = stripeWidth;
        this.gapWidth = gapWidth;
        this.lineHeight = lineHeight;
        this.end = end;
    }

    public int getLeadingMargin(boolean first) {
        return stripeWidth + gapWidth;
    }

    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir,
                                  int top, int baseline, int bottom,
                                  CharSequence text, int start, int end,
                                  boolean first, Layout layout) {
        Log.i(TAG, "end: " + end);
        Paint.Style style = p.getStyle();
        int color = p.getColor();

        p.setStyle(Paint.Style.FILL);
        p.setColor(mColor);

        Paint.FontMetricsInt fm = p.getFontMetricsInt();

        int lineSpace = this.end == end ? lineHeight - Math.abs(fm.ascent) - Math.abs(fm.descent) - Math.abs(fm.leading) : 0;

        c.drawRect(x, top, x + dir * stripeWidth, bottom - lineSpace, p);

        p.setStyle(style);
        p.setColor(color);
    }


}
