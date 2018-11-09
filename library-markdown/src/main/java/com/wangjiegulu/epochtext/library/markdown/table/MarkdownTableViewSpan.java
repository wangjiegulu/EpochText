package com.wangjiegulu.epochtext.library.markdown.table;

import android.view.View;
import android.widget.TextView;

import com.wangjiegulu.epochtext.library.spans.view.EpochViewSpan;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/9.
 */
public class MarkdownTableViewSpan extends EpochViewSpan{

    public MarkdownTableViewSpan(TextView parentTv, int lineHeight) {
        super(parentTv, lineHeight);
    }

    @Override
    protected View createView() {
        return null;
    }

    @Override
    protected void measureView(View view) {

    }
}
