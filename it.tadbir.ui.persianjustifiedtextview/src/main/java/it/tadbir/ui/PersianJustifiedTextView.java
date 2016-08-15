package it.tadbir.ui;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mohammad Shams on 8/14/2016.
 * Goal: Having a fast JustifiedTextView
 * <p>
 * Change Log: V1.0 8/14/2016
 * Class creation
 */

public class PersianJustifiedTextView extends TextView {
    private static final String TAG = "app:";
    private final String SPACE = " ";
    private String realText;
    private int realWidth;
    private int paddings = 0;
    private boolean justified = false;
    private Paint paint;


    public PersianJustifiedTextView(Context context) {
        super(context);
        init();
    }

    public PersianJustifiedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PersianJustifiedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (isInEditMode())
            return;


        //Log.d(TAG, "init Class");
        paddings = getPaddingLeft() + getPaddingRight();
        realText = super.getText().toString();


        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                realWidth = getWidth() - paddings;

                justify();
            }
        });
    }

    private void justify() {
        String tmp = realText;
        paint = this.getPaint();

        String[] words;
        String word;
        int wordCount;
        ArrayList<String> lineList = new ArrayList<String>();

        String[] breakedLines = tmp.split("\\n");
        int breakedLinesCount = breakedLines.length;

        for (int i = 0; i < breakedLinesCount; i++) {
            tmp = breakedLines[i];
            words = tmp.split("\\s");
            wordCount = words.length;
            lineList.clear();
            tmp = words[0];

            for (int j = 1; j < wordCount; j++) {
                word = words[j];

                if (paint.measureText(tmp + SPACE + word) <= realWidth) {
                    tmp = tmp + SPACE + word;
                } else {
                    tmp = spaceFill(tmp);
                    lineList.add(tmp);
                    tmp = word;
                }
            }

            lineList.add(tmp);
            breakedLines[i] = TextUtils.join(SPACE, lineList);
        }

        setText(TextUtils.join("\n", breakedLines));

        justified = true;
    }

    private String spaceFill(String str) {
        if (str.contains(SPACE)) {
            int pos = 0;
            String temp;

            while (true) {
                pos = str.indexOf(SPACE, pos);
                if (pos < 0)
                    pos = str.indexOf(SPACE, 0);

                temp = str.substring(0, pos) + SPACE + str.substring(pos, str.length());

                if (paint.measureText(temp) < realWidth) {
                    str = temp;
                } else {
                    break;
                }

                pos += 2;
                //if (pos >= tmp.length()) pos = 0;
            }
        }

        return str;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //prevent creation time
        if (justified)
            justify();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        realText = text.toString();
        super.setText(text, type);
    }

    @Override
    public CharSequence getText() {
        return realText;
    }

    @Override
    public void append(CharSequence text, int start, int end) {
        realText = realText + text.subSequence(start, end).toString();
        super.setText(realText);
    }

}
