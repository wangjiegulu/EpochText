package com.wangjiegulu.epochtext.library.spans.view;

import android.widget.TextView;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/10.
 */
public abstract class EpochMatchViewSpan extends EpochViewSpan{
    protected int matchWidth;

    public EpochMatchViewSpan(TextView parentTv, int lineHeight, int matchWidth) {
        super(parentTv, lineHeight);
        this.matchWidth = matchWidth;
    }

}
