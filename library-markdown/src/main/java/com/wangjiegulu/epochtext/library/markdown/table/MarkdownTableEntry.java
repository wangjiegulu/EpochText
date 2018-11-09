package com.wangjiegulu.epochtext.library.markdown.table;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/9.
 */
public class MarkdownTableEntry {
    public List<MarkdownTableTh> ths;

    public MarkdownTableEntry(List<MarkdownTableTh> ths) {
        this.ths = ths;
    }

    @Override
    public String toString() {
        return "MarkdownTableEntry{" +
                "ths=" + ths +
                '}';
    }

    static class MarkdownTableTh {
        public static final int ALIGNMENT_LEFT = 0x12;
        public static final int ALIGNMENT_CENTER = 0x13;
        public static final int ALIGNMENT_RIGHT = 0x14;

        public String name;
        public int alignment;
        public List<String> tds = new ArrayList<>();

        public MarkdownTableTh(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "MarkdownTableTh{" +
                    "name='" + name + '\'' +
                    ", alignment=" + alignment +
                    ", tds=" + tds +
                    '}';
        }
    }

}
