package com.nlinks.nlframe.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.nlinks.nlframe.R;


/**
 * 项目名称：Minielectric
 * 类描述：标题栏设置
 * 创建时间：2018/7/4 10:42
 *
 * @author gweibin@linewell.com
 */
public class CommonTitleBarSettings {
    private String titleText = "", leftText = "", rightText = "";
    private final int titleTextColor;
    private final boolean titleDrawableVisible;
    private final boolean leftBtnVisible, rightBtnVisible;
    private final int leftDrawable;
    private final int rightBtnTextColor;
    private final int rightBtnDrawable;

    CommonTitleBarSettings(Context context, AttributeSet attrs) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CommonTitleBar);

        titleText = attributes.getString(R.styleable.CommonTitleBar_title_text);
        titleTextColor = attributes.getColor(R.styleable.CommonTitleBar_title_text_color,
                context.getResources().getColor(R.color.textDark));
        titleDrawableVisible = attributes.getBoolean(R.styleable.CommonTitleBar_title_drawable_visible,
                false);
        leftBtnVisible = attributes.getBoolean(R.styleable.CommonTitleBar_left_button_visible,
                true);
        leftDrawable = attributes.getResourceId(R.styleable.CommonTitleBar_left_drawable,
                0);
        rightBtnVisible = attributes.getBoolean(R.styleable.CommonTitleBar_right_button_visible,
                false);
        rightText = attributes.getString(R.styleable.CommonTitleBar_right_button_text);

        rightBtnTextColor = attributes.getColor(R.styleable.CommonTitleBar_right_button_text_color,
                context.getResources().getColor(R.color.textGray));

        rightBtnDrawable = attributes.getResourceId(R.styleable.CommonTitleBar_right_button_drawable,
                0);

        attributes.recycle();
    }

    String getTitleText() {
        return titleText;
    }

    String getLeftText() {
        return leftText;
    }

    String getRightText() {
        return rightText;
    }

    int getTitleTextColor() {
        return titleTextColor;
    }

    boolean isLeftBtnVisible() {
        return leftBtnVisible;
    }

    boolean isRightBtnVisible() {
        return rightBtnVisible;
    }

    int getRightBtnTextColor() {
        return rightBtnTextColor;
    }

    int getRightBtnDrawable() {
        return rightBtnDrawable;
    }


    public int getLeftDrawable() {
        return leftDrawable;
    }
}
