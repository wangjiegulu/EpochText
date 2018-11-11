package com.wangjiegulu.epochtext;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.wangjiegulu.epochtext.library.BaseSpanResolver;
import com.wangjiegulu.epochtext.library.SpannableParser;
import com.wangjiegulu.epochtext.library.markdown.bold.MarkdownBoldSpanResolver;
import com.wangjiegulu.epochtext.library.markdown.heading.MarkDownHeadingSpanResolver;
import com.wangjiegulu.epochtext.library.markdown.horizontalrule.MarkdownHorizontalRuleSpanResolver;
import com.wangjiegulu.epochtext.library.markdown.image.MarkdownImageSpanResolver;
import com.wangjiegulu.epochtext.library.markdown.italic.MarkdownItalicSpanResolver;
import com.wangjiegulu.epochtext.library.markdown.linecode.MarkdownInlineCodeSpanResolver;
import com.wangjiegulu.epochtext.library.markdown.link.MarkdownLinkSpanResolver;
import com.wangjiegulu.epochtext.library.markdown.list.ordered.MarkdownOrderedListSpanResolver;
import com.wangjiegulu.epochtext.library.markdown.list.unordered.MarkdownUnorderedListSpanResolver;
import com.wangjiegulu.epochtext.library.markdown.quote.MarkdownQuoteSpanResolver;
import com.wangjiegulu.epochtext.library.markdown.strike.MarkdownStrikeSpanResolver;
import com.wangjiegulu.epochtext.library.markdown.table.MarkdownTableSpanResolver;
import com.wangjiegulu.epochtext.test.EpochTestViewSpanResolver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private SpannableParser spannableParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText contentEt = findViewById(R.id.activity_main_content_et);

        /*
         * all supported markdown resolvers
         */
        List<BaseSpanResolver<?>> markdownResolvers = Arrays.asList(
                new MarkdownImageSpanResolver(),
                new MarkdownLinkSpanResolver(),
                new MarkdownItalicSpanResolver(),
                new MarkdownBoldSpanResolver(),
                new MarkDownHeadingSpanResolver(),
                new MarkdownStrikeSpanResolver(),
                new MarkdownQuoteSpanResolver(),
                new MarkdownInlineCodeSpanResolver(),
                new MarkdownUnorderedListSpanResolver(),
//                new MarkdownUnorderedSubListSpanResolver(),
                new MarkdownOrderedListSpanResolver(),
                new MarkdownHorizontalRuleSpanResolver(),
                new MarkdownTableSpanResolver(),

                new EpochTestViewSpanResolver()
        );

        spannableParser = new SpannableParser(contentEt);
        spannableParser
                .addSpanResolvers(markdownResolvers)
                .setEditMode(true);

        contentEt.post(new Runnable() {
            @Override
            public void run() {
                final String s = getFromAssets("md_test.md");
                Log.i(TAG, "raw content: " + s);
                contentEt.setText(s);
            }
        });

    }

    public void onToggleClick(View view){
        spannableParser.toggleMode();
    }

    private String getFromAssets(String fileName){
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line;
            StringBuilder result= new StringBuilder();
            while(null != (line = bufReader.readLine())){
                result.append(line).append('\n');
            }
            return result.toString();
        } catch (Exception e) {
            Log.e(TAG, "getFromAssets", e);
        }
        return "";
    }
}
