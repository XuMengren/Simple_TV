package com.example.simpletv.Tools;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/***
    *创建时间：2021/2/2 6:25 PM
    *作者：xyd
    *描述：设置Taylayout 下标的宽度
    *参数：
    *返回值(Y/N):
*/
public class IndicatorDrawable extends Drawable {

    private Paint mPaint;
    private float indicatorLeft;
    private float indicatorRight;
    private int indicatorWidth;
    private float indicatorHeight;

    public IndicatorDrawable() {
        this(0);
    }

    public IndicatorDrawable(int indicatorWidth) {
        mPaint = new Paint();
        this.indicatorWidth = indicatorWidth;
    }


    @Override
    public void draw(@NonNull Canvas canvas) {
        // 获取Drawable的真实边界，这个在调用draw之前TabLayout已经设置完毕
        indicatorLeft = getBounds().left;
        indicatorRight = getBounds().right;
        indicatorHeight = getBounds().bottom - getBounds().top;
        // 圆角半径
        float radius = indicatorHeight / 2f;
        // 默认充满
        if (indicatorWidth == 0) {
            indicatorWidth = (int) (indicatorRight - indicatorLeft);
        }
        // 指示器绘制中心
        float indicatorCenter = (indicatorRight + indicatorLeft) / 2f;
        if (indicatorLeft >= 0 && indicatorRight > indicatorLeft
                && indicatorWidth <= indicatorRight - indicatorLeft) {
            RectF rectF = new RectF(indicatorCenter - indicatorWidth / 2f, getBounds().top,
                    indicatorCenter + indicatorWidth / 2f, getBounds().bottom);
            canvas.drawRoundRect(rectF, radius, radius, mPaint);
        }
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public void setColorFilter(@ColorInt int color, @NonNull PorterDuff.Mode mode) {
        super.setColorFilter(color, mode);
        // 获取TabLayout传入的画笔颜色
        mPaint.setColor(color);
    }

    @Override
    public void setTint(int tintColor) {
        super.setTint(tintColor);
        // 获取TabLayout传入的画笔颜色
        mPaint.setColor(tintColor);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}

