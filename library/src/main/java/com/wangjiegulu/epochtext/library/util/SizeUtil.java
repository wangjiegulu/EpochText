package com.wangjiegulu.epochtext.library.util;

import android.content.Context;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/8.
 */
public class SizeUtil {
    public static float dp2px(Context context, float dpValue){
        return (dpValue * context.getResources().getDisplayMetrics().density + 0.5f);
    }
}
