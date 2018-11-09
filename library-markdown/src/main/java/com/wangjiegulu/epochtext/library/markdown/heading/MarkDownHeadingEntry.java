package com.wangjiegulu.epochtext.library.markdown.heading;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/5.
 */
public class MarkDownHeadingEntry {
    private int symbolCount;
    private float fontScaleRate;

    public MarkDownHeadingEntry(int symbolCount, float fontScaleRate) {
        this.symbolCount = symbolCount;
        this.fontScaleRate = fontScaleRate;
    }

    public int getSymbolCount() {
        return symbolCount;
    }

    public void setSymbolCount(int symbolCount) {
        this.symbolCount = symbolCount;
    }

    public float getFontScaleRate() {
        return fontScaleRate;
    }

    public void setFontScaleRate(float fontScaleRate) {
        this.fontScaleRate = fontScaleRate;
    }

    @Override
    public String toString() {
        return "MarkDownHeadingEntry{" +
                "symbolCount=" + symbolCount +
                ", fontScaleRate=" + fontScaleRate +
                '}';
    }
}
