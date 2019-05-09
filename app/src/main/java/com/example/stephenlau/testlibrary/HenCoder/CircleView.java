package com.example.stephenlau.testlibrary.HenCoder;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.example.stephenlau.testlibrary.R;

public class CircleView extends View {
    private int diameter;
    /**
     * 必须至少重写前两个构造函数
     *
     * @param context
     */
    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //AttributeSet attrs 就是我们在styles.xml文件中的<declare-styleable>标签
        //即属性集合的标签，在R文件中名称为R.styleable.name
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView);

        //第一个参数为属性集合里面的属性，R文件名称：R.styleable.属性集合名称_属性名称
        diameter = typedArray.getDimensionPixelSize(R.styleable.CircleView_diameter, 100);

        //最后记得将TypedArray对象回收
        typedArray.recycle();

    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //widthMeasureSpec和heightMeasureSpec包含宽和高的信息
        int widthMode = MeasureSpec.getMode(widthMeasureSpec); //UNSPECIFIED, AT_MOST or EXACTLY之一
//        match_parent--->EXACTLY
//        wrap_content--->AT_MOST
//        固定尺寸（如100dp）--->EXACTLY

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        setMeasuredDimension(diameter, diameter);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int r = getMeasuredWidth() / 2;
        int centerX = getLeft() + r;
        int centerY = getTop() + r;

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawCircle(centerX, centerY, r, paint);

    }
}
