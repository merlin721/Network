package lib.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.View;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2018/7/23
 * description   : xxxx描述
 */

public class TabView extends View {

    private final String tabName;
    private int textColor = Color.WHITE;
    private int mIconInsetLeft, mIconInsetTop, mIconInsetRight, mIconInsetBottom;
    private Paint tabNamePaint;
    private float textX;
    private float textY;
    private int backColor;
    private Paint backPaint;

    public TabView(Context context, int backColor, int texColor, String tabName) {
        super(context);
        this.backColor = backColor;
        this.textColor = texColor;
        this.tabName = tabName;
        init();
    }

    private void init() {
        tabNamePaint = new Paint();
        tabNamePaint.setTextSize(46);
        tabNamePaint.setColor(textColor);
        tabNamePaint.setAntiAlias(true);
        tabNamePaint.setFakeBoldText(true);
        tabNamePaint.setTextAlign(Paint.Align.CENTER);

        backPaint = new Paint();
        backPaint.setColor(backColor);
        backPaint.setAntiAlias(true);
        backPaint.setStyle(Paint.Style.FILL);

        int insetsDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10,
            getContext().getResources().getDisplayMetrics());
        mIconInsetLeft = mIconInsetTop = mIconInsetRight = mIconInsetBottom = insetsDp;
    }

    public int dp2px(final float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int px = dp2px(50);
        setMeasuredDimension(px, px);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, backPaint);
        canvas.drawText(tabName, getWidth() / 2,
            (getHeight() / 2 + tabNamePaint.getTextSize() / 2 - 10), tabNamePaint);
    }
}
