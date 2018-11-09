package com.wangjiegulu.epochtext.library.spans.bullet;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Parcel;
import android.text.Layout;
import android.text.Spanned;
import android.text.style.BulletSpan;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/9.
 */
public class EpochBulletSpan extends BulletSpan{
    private int color;
    private int gapWidth;
    private int bulletRadius;
    private int lineSpace;

    private static Path sBulletPath = null;

    public EpochBulletSpan(int gapWidth, int color, int bulletRadius, int lineSpace) {
        super(gapWidth, color);
        this.color = color;
        this.gapWidth = gapWidth;
        this.bulletRadius = bulletRadius;
        this.lineSpace = lineSpace;
    }

    public EpochBulletSpan(Parcel src, int color, int gapWidth, int bulletRadius, int lineSpace) {
        super(src);
        this.color = color;
        this.gapWidth = gapWidth;
        this.bulletRadius = bulletRadius;
        this.lineSpace = lineSpace;
    }


    @Override
    public int getLeadingMargin(boolean first) {
        return 2 * bulletRadius + gapWidth;
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir,
                                  int top, int baseline, int bottom,
                                  CharSequence text, int start, int end,
                                  boolean first, Layout l) {
        if (((Spanned) text).getSpanStart(this) == start) {
            Paint.Style style = p.getStyle();
            int oldColor = p.getColor();
            p.setColor(color);

            p.setStyle(Paint.Style.FILL);

            if (c.isHardwareAccelerated()) {
                if (sBulletPath == null) {
                    sBulletPath = new Path();
                    // Bullet is slightly better to avoid aliasing artifacts on mdpi devices.
                    sBulletPath.addCircle(0.0f, 0.0f, 1.2f * bulletRadius, Path.Direction.CW);
                }

                c.save();
                c.translate(x + dir * bulletRadius * 2, (top + bottom) / 2.0f - lineSpace / 2.0f);
                c.drawPath(sBulletPath, p);
                c.restore();
            } else {
                c.drawCircle(x + dir * bulletRadius * 2, (top + bottom) / 2.0f - lineSpace / 2.0f, bulletRadius, p);
            }

            p.setColor(oldColor);

            p.setStyle(style);
        }
    }

}
