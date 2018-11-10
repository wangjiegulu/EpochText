package com.wangjiegulu.epochtext.library.markdown.table;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wangjiegulu.epochtext.library.spans.view.EpochMatchViewSpan;
import com.wangjiegulu.epochtext.library.util.SizeUtil;
import com.wangjiegulu.library.markdown.R;

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

    /**
     * Only draw for last line.
     */
    @Override
    protected boolean shouldDraw(int start, int end) {
        return end == lastLineEndIndex;
    }

    @Override
    protected View createView() {
        Context context = getContext();
        if (null == context) {
            return null;
        }

        // TODO: 2018/11/10 wangjie  optimize

        HorizontalScrollView scrollView = new HorizontalScrollView(context);
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(matchWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        LinearLayout rowTh = new LinearLayout(context);
        rowTh.setOrientation(LinearLayout.HORIZONTAL);
        rowTh.setBackgroundColor(0xffd1d1d1);
        for (MarkdownTableEntry.MarkdownTableTh th : entry.ths) {
            TextView tv = new TextView(context);
            tv.setBackgroundResource(R.drawable.shape_markdown_table_border);
            int padding = (int) SizeUtil.dp2px(context, 4);
            tv.setPadding(padding, padding, padding, padding);
            tv.setTextSize(18);
            tv.setText(th.name);
            tv.getPaint().setFakeBoldText(true);
            tv.setLayoutParams(new ViewGroup.LayoutParams((int) (matchWidth / 3.5), ViewGroup.LayoutParams.MATCH_PARENT));
            int gravity;
            switch (th.alignment) {
                case MarkdownTableEntry.MarkdownTableTh.ALIGNMENT_LEFT:
                    gravity = Gravity.START | Gravity.CENTER_VERTICAL;
                    break;
                case MarkdownTableEntry.MarkdownTableTh.ALIGNMENT_CENTER:
                    gravity = Gravity.CENTER;
                    break;
                case MarkdownTableEntry.MarkdownTableTh.ALIGNMENT_RIGHT:
                    gravity = Gravity.END | Gravity.CENTER_VERTICAL;
                    break;
                default:
                    gravity = Gravity.START | Gravity.CENTER_VERTICAL;
            }
            tv.setGravity(gravity);
            rowTh.addView(tv);
        }

        linearLayout.addView(rowTh);

        int rowCount = entry.ths.get(0).tds.size();
        for (int i = 0; i < rowCount; i++){
            LinearLayout rowTd = new LinearLayout(context);
            if(i % 2 != 0){
                rowTd.setBackgroundColor(0xffe1e1e1);
            }
            for(MarkdownTableEntry.MarkdownTableTh th : entry.ths){
                TextView tv = new TextView(context);
                tv.setBackgroundResource(R.drawable.shape_markdown_table_border);
                int padding = (int) SizeUtil.dp2px(context, 4);
                tv.setPadding(padding, padding, padding, padding);
                tv.setTextSize(16);
                tv.setText(th.tds.get(i));
                tv.setLayoutParams(new ViewGroup.LayoutParams((int) (matchWidth / 3.5), ViewGroup.LayoutParams.MATCH_PARENT));
                int gravity;
                switch (th.alignment) {
                    case MarkdownTableEntry.MarkdownTableTh.ALIGNMENT_LEFT:
                        gravity = Gravity.START | Gravity.CENTER_VERTICAL;
                        break;
                    case MarkdownTableEntry.MarkdownTableTh.ALIGNMENT_CENTER:
                        gravity = Gravity.CENTER;
                        break;
                    case MarkdownTableEntry.MarkdownTableTh.ALIGNMENT_RIGHT:
                        gravity = Gravity.END | Gravity.CENTER_VERTICAL;
                        break;
                    default:
                        gravity = Gravity.START | Gravity.CENTER_VERTICAL;
                }
                tv.setGravity(gravity);
                rowTd.addView(tv);
            }
            linearLayout.addView(rowTd);
        }

        scrollView.addView(linearLayout);

        return scrollView;
    }

    @Override
    protected void measureView(View view) {
        Context context = getContext();
        if (null == context) {
            return;
        }
        measureView(
                View.MeasureSpec.makeMeasureSpec(matchWidth, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        );
    }
}
