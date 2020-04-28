package com.nlinks.nlframe.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.nlinks.nlframe.R;


/**
 * 项目名称：Minielectric
 * 类描述：弧形View
 * 创建时间：2018/7/5 9:54
 *
 * @author gweibin@linewell.com
 */
public class PerfectArcView extends View {
    private Paint mPaint;
    private Bitmap mBitmap;
    private RectF mRect = new RectF();
    private Point mCircleCenter;
    private float mRadius;
    private int mStartColor;
    private int mEndColor;
    private float mProportion;
    private LinearGradient mLinearGradient;

    public PerfectArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        readAttr(attrs);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mCircleCenter = new Point();
    }

    private void readAttr(AttributeSet set) {
        TypedArray typedArray = getContext().obtainStyledAttributes(set, R.styleable.PerfectArcView);
        mStartColor = typedArray.getColor(R.styleable.PerfectArcView_arc_startColor, getResources().getColor(R.color.brandColor));
        mEndColor = typedArray.getColor(R.styleable.PerfectArcView_arc_endColor, getResources().getColor(R.color.brand_transparent));
        mProportion = typedArray.getFloat(R.styleable.PerfectArcView_arc_proportion, (float) 1.5);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int height = getHeight();
        int width = getWidth();
        // 矩形
        mRect.left = 0;
        mRect.top = 0;
        mRect.right = width;
        mRect.bottom = height;
        mRadius = width;
        mLinearGradient = new LinearGradient(0, height / 2, width, height / 2, mStartColor, mEndColor, Shader.TileMode.MIRROR);
        // 圆心坐标
        mCircleCenter.x = width / 2;
        mCircleCenter.y = (int) (height - mRadius);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int layerId = canvas.saveLayer(0, 0, canvasWidth, canvasHeight, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawCircle(mCircleCenter.x, mCircleCenter.y, mRadius, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //绘制渐变色
        mPaint.setShader(mLinearGradient);
        canvas.drawRect(mRect, mPaint);
        mPaint.setXfermode(null);
    }
}
