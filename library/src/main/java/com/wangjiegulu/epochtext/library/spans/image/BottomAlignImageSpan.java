package com.wangjiegulu.epochtext.library.spans.image;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/4.
 */
public class BottomAlignImageSpan extends ImageSpan{
    private static final String TAG = BottomAlignImageSpan.class.getSimpleName();
    private int lineHeight;
    public BottomAlignImageSpan(Drawable d, int lineHeight) {
        super(d);
        this.lineHeight = lineHeight;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        Drawable b = getDrawable();
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
//        int transY = (y + fm.descent + y + fm.ascent) / 2 - b.getBounds().bottom / 2;
//        int transY = 0;

        int lineSpace = lineHeight - Math.abs(fm.ascent) - Math.abs(fm.descent) - Math.abs(fm.leading);
        int transY = bottom - b.getBounds().bottom - lineSpace;

        canvas.save();
        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
    }

}
