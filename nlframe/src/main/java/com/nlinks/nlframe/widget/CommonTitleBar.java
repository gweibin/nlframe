package com.nlinks.nlframe.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.nlinks.nlframe.R;


/**
 * 项目名称：Minielectric
 * 类描述：通用标题栏
 * 创建时间：2018/7/4 10:39
 *
 * @author gweibin@linewell.com
 */
public class CommonTitleBar extends RelativeLayout {
    private ImageView ivLeft;
    private TextView btnRight, tvTitle;
    private CommonTitleBarSettings mSettings;
    private int titleBarHeight;

    public CommonTitleBar(Context context) {
        super(context);
    }

    public CommonTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CommonTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        titleBarHeight = h;
    }

    /**
     * 获取标题栏高度
     */
    public int getTitleBarHeight() {
        return this.titleBarHeight;
    }

    /**
     * 获取左边按钮
     */
    public ImageView getBtnLeft() {
        return ivLeft;
    }

    /**
     * 获取右边按钮
     */
    public TextView getBtnRight() {
        return btnRight;
    }

    /**
     * 设置标题背景颜色
     *
     * @param color 颜色值（#ff0000）
     */
    public void setTitleBgColor(String color) {
        setBackgroundColor(Color.parseColor(color));
    }

    /**
     * 获取标题控件
     */
    public TextView getTitleTv() {
        return tvTitle;
    }

    /**
     * 设置标题
     */
    public void setTitleText(String title) {
        tvTitle.setText(title);
    }

    /**
     * 设置标题
     */
    public void setTitleTextColor(int color) {
        tvTitle.setTextColor(color);
    }

    /**
     * 设置右边按钮的文本
     */
    public void setRightBtnText(String msg) {
        btnRight.setText(msg);
        btnRight.setVisibility(VISIBLE);
    }

    /**
     * 设置右边按钮的图片
     */
    public void setRightBtnSrc(int resouceId) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), resouceId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        btnRight.setCompoundDrawables(null, null, drawable, null);
    }

    /**
     * 设置右边按钮显示隐藏
     *
     * @param visible true：显示   false：隐藏
     */
    public void setRightBtnVisible(boolean visible) {
        btnRight.setVisibility(visible ? VISIBLE : GONE);
    }

    /**
     * 初始化
     */
    private void init(final Context context, AttributeSet attrs) {

        mSettings = new CommonTitleBarSettings(context, attrs);
        setGravity(Gravity.CENTER_VERTICAL);

        View view = LayoutInflater.from(context).inflate(R.layout.common_title_bar, this, true);
        ivLeft = view.findViewById(R.id.iv_title_left);
        btnRight = view.findViewById(R.id.tv_title_right);
        tvTitle = view.findViewById(R.id.tvTitle);

        //设置标题文字
        tvTitle.setText(mSettings.getTitleText());
        //设置标题颜色
        tvTitle.setTextColor(mSettings.getTitleTextColor());
        // 设置左边按钮的图片
        if (mSettings.getLeftDrawable() > 0) {
            ivLeft.setImageResource(mSettings.getLeftDrawable());
        }
        //左按钮默认打开，右按钮默认关闭
        ivLeft.setVisibility(mSettings.isLeftBtnVisible() ? VISIBLE : INVISIBLE);
        btnRight.setVisibility(mSettings.isRightBtnVisible() ? VISIBLE : INVISIBLE);
        //设置右边按钮的文字(文字图片二选一)
        if (mSettings.getRightBtnDrawable() > 0) {
            Drawable dRight = ContextCompat.getDrawable(context, mSettings.getRightBtnDrawable());
            dRight.setBounds(0, 0, dRight.getMinimumWidth(), dRight.getMinimumHeight());
            btnRight.setCompoundDrawables(dRight, null, null, null);
        } else {
            btnRight.setText(mSettings.getRightText());
        }
        btnRight.setTextColor(mSettings.getRightBtnTextColor());
    }

    /////////////////////////////以下解决标题栏问题//////////////////////////////////////////
    /*
     * 动态计算顶部padding
     */
    /*static void transparentPadding(Context context, View view, boolean ancestorFitsSystemWindow) {
        if (!ancestorFitsSystemWindow) {
            Resources resources = context.getResources();
            int barSize = getStatusBarSize(resources);
            if (barSize > 0) {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams != null) {
                    layoutParams.height += barSize;
                    view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + barSize,
                        view.getPaddingRight(), view.getPaddingBottom());
                }
            }
        }
    }*/

    /*
     * 获取statusBar大小
     */
    /*static int getStatusBarSize(Resources res) {
        int result = 0;
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }*/
}
