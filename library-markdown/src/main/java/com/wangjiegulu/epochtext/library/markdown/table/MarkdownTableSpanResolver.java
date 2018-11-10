package com.wangjiegulu.epochtext.library.markdown.table;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ScaleXSpan;
import android.util.Log;
import android.widget.TextView;

import com.wangjiegulu.epochtext.library.BaseSpanResolver;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/9.
 */
public class MarkdownTableSpanResolver extends BaseSpanResolver<MarkdownTableEntry> {
    private static final String TAG = MarkdownTableSpanResolver.class.getSimpleName();

    @Override
    public String patternRegex() {
        return "(?:^|\\n)(\\|)(.*)\\n(\\|:?-{3,}:?)+\\|(\\n\\|.*)+";
    }

    @Nullable
    @Override
    public MarkdownTableEntry resolveEntry(@NonNull String group) throws RuntimeException {
        String source = group.trim();
        String[] lines = source.split("\n");
        if (lines.length < 3) { // 至少3行才能构成表格
            return null;
        }

        List<MarkdownTableEntry.MarkdownTableTh> thList = new ArrayList<>();
        // th
        String[] ths = lines[0].split("\\|");
        for (String thStr : ths) {
            if (thStr.length() <= 0) {
                continue;
            }
            MarkdownTableEntry.MarkdownTableTh th = new MarkdownTableEntry.MarkdownTableTh(thStr);
            thList.add(th);
        }
        // alignment
        String[] thAligns = lines[1].split("\\|");
        int thIndex = 0;
        for (String thAlignStr : thAligns) {
            if (thAlignStr.length() <= 0) {
                continue;
            }
            boolean startsWith = thAlignStr.startsWith(":");
            boolean endsWith = thAlignStr.endsWith(":");
            int alignment;
            if (startsWith && endsWith) {
                alignment = MarkdownTableEntry.MarkdownTableTh.ALIGNMENT_CENTER;
            } else if (startsWith) {
                alignment = MarkdownTableEntry.MarkdownTableTh.ALIGNMENT_LEFT;
            } else if (endsWith) {
                alignment = MarkdownTableEntry.MarkdownTableTh.ALIGNMENT_RIGHT;
            } else {
                alignment = MarkdownTableEntry.MarkdownTableTh.ALIGNMENT_LEFT;
            }

            thList.get(thIndex).alignment = alignment;
            thIndex++;
        }

        // content
        for(int i = 2; i < lines.length; i++){
            String[] contentStrs = lines[i].split("\\|");
            int j = 0;
            for(String contentStr : contentStrs){
                if(contentStr.length() <= 0){
                    continue;
                }
                thList.get(j).tds.add(contentStr);
                j++;
            }
        }

        MarkdownTableEntry entry = new MarkdownTableEntry(thList);
        Log.i(TAG, "entry: " + entry);
        return entry;
    }

    @Override
    public void assembly(@NonNull WeakReference<TextView> textViewRef, @NonNull Spannable spannable, @NonNull String group, @NonNull MarkdownTableEntry entry, int groupStart, int groupEnd) {
        if (!isEditMode) {
            // TODO: 2018/11/9 wangjie
            TextView tv = textViewRef.get();
            if(null == tv){
                return;
            }
            setEpochViewSpan(spannable, new MarkdownTableViewSpan(tv, tv.getLineHeight(), tv.getWidth(), entry), groupStart, groupStart + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            setEpochSpan(spannable, new ScaleXSpan(0), groupStart + 1, groupEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        }
    }
}
