package com.wangjiegulu.epochtext.library.markdown.image;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2018/11/4.
 */
public class MarkdownImageEntry {
    private String desc;
    private String url;

    public MarkdownImageEntry(String desc, String url) {
        this.desc = desc;
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "MarkdownImageEntry{" +
                "desc='" + desc + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
