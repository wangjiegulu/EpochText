package com.wangjiegulu.epochtext.library.spans.longclick;

import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/4.
 */
public abstract class FullClickableSpan extends ClickableSpan{

    public abstract void onLongClick(View widget);
}
