package com.wangjiegulu.epochtext.library.spans.longclick;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/4.
 */
public class LinkMovementMethodExt extends LinkMovementMethod {
    private static final String TAG = LinkMovementMethodExt.class.getSimpleName();

    public static MovementMethod instance() {
        if (instance == null)
            instance = new LinkMovementMethodExt();

        return instance;
    }

    private static LinkMovementMethodExt instance;

    private long lastClickTime;

    private static final long CLICK_DELAY = 500L;

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
//        Log.i(TAG, "onTouchEvent event: " + event);
        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            FullClickableSpan[] link = buffer.getSpans(off, off, FullClickableSpan.class);

            if (link.length != 0) {
                if (action == MotionEvent.ACTION_UP) {

                    if (System.currentTimeMillis() - lastClickTime < CLICK_DELAY) {
                        // click
                        link[0].onClick(widget);

                    } else {
                        // long click
                        link[0].onLongClick(widget);
                    }

                } else {
//                    Selection.setSelection(buffer,
//                    buffer.getSpanStart(link[0]),
//                    buffer.getSpanEnd(link[0]));
                    lastClickTime = System.currentTimeMillis();
                }

                return true;
            } else {
                Selection.removeSelection(buffer);
            }
        }
        return super.onTouchEvent(widget, buffer, event);
    }

}
