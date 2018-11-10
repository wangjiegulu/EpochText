package com.wangjiegulu.epochtext.library.markdown.table;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wangjiegulu.epochtext.library.spans.view.EpochMatchViewSpan;
import com.wangjiegulu.epochtext.library.util.SizeUtil;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/9.
 */
public class MarkdownTableViewSpan extends EpochMatchViewSpan {
    private MarkdownTableEntry entry;
    private int lastLineEndIndex;

    public MarkdownTableViewSpan(TextView parentTv, int lineHeight, int matchWidth, MarkdownTableEntry entry, int lastLineEndIndex) {
        super(parentTv, lineHeight, matchWidth);
        this.entry = entry;
        this.lastLineEndIndex = lastLineEndIndex;
    }

    @Override
    protected boolean shouldDraw(int start, int end) {
        return end == lastLineEndIndex;
    }

    @Override
    protected View createView() {
        Context context = getContext();
        if(null == context){
            return null;
        }


        HorizontalScrollView scrollView = new HorizontalScrollView(context);
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(matchWidth, (int) SizeUtil.dp2px(context, 200)));

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        for(MarkdownTableEntry.MarkdownTableTh th : entry.ths){
            for(String td : th.tds){
                TextView tv = new TextView(context);
                tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                tv.setText(td);
                tv.setTextSize(20);
                linearLayout.addView(tv);
            }
        }

        scrollView.addView(linearLayout);

        return scrollView;
    }

    @Override
    protected void measureView(View view) {
        Context context = getContext();
        if(null == context){
            return;
        }
        measureView(
                View.MeasureSpec.makeMeasureSpec(matchWidth, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec((int) SizeUtil.dp2px(context, 200), View.MeasureSpec.EXACTLY)
        );
    }
}
