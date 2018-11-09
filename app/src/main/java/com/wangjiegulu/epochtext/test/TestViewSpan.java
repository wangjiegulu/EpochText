package com.wangjiegulu.epochtext.test;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wangjiegulu.epochtext.library.spans.view.EpochViewSpan;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/9.
 */
public class TestViewSpan extends EpochViewSpan {
    private int fillWidth;
    public TestViewSpan(TextView parentTv, int lineHeight, int fillWidth) {
        super(parentTv, lineHeight);
        this.fillWidth = fillWidth;
    }

    @Override
    protected View createView() {
        final Context context = getContext();
        if(null == context){
            return null;
        }
        final TextView view = new TextView(context);
        view.setLayoutParams(new ViewGroup.LayoutParams(fillWidth, 600));
        view.setGravity(Gravity.CENTER);
        view.setText("hello world");
        view.setBackgroundColor(Color.GRAY);
        view.setTextColor(Color.BLACK);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setText(view.getText().toString() + "clicked");
                Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();

            }
        });





        return view;
    }

    @Override
    protected void measureView(View view) {
        measureView(
                View.MeasureSpec.makeMeasureSpec(fillWidth, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(600, View.MeasureSpec.EXACTLY)
        );
    }

}
