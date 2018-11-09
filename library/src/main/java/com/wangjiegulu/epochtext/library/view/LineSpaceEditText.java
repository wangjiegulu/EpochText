package com.wangjiegulu.epochtext.library.view;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.util.AttributeSet;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 *
 * Workaround for issue with Edittext: https://issuetracker.google.com/issues/37009353#comment5
 * Not working good
 *
 * https://github.com/hanks-zyh/LineHeightEditText
 *
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/8.
 */
public class LineSpaceEditText extends AppCompatEditText {
    private float mSpacingMult = 1f;
    private float mSpacingAdd = 0f;

    public LineSpaceEditText(Context context) {
        this(context, null);
    }

    public LineSpaceEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.editTextStyle);
    }

    public LineSpaceEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        getLineSpacingAddAndLineSpacingMult();
        listenTextChange();
    }


    private void getLineSpacingAddAndLineSpacingMult() {
        try {
            Field mSpacingAddField = TextView.class.getDeclaredField("mSpacingAdd");
            Field mSpacingMultField = TextView.class.getDeclaredField("mSpacingMult");
            mSpacingAddField.setAccessible(true);
            mSpacingMultField.setAccessible(true);
            mSpacingAdd = mSpacingAddField.getFloat(this);
            mSpacingMult = mSpacingMultField.getFloat(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listenTextChange() {
        addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setLineSpacing(0f, 1f);
                setLineSpacing(mSpacingAdd, mSpacingMult);
            }


            @Override
            public void afterTextChanged(Editable s) {
                // ignore
            }
        });
    }


}
