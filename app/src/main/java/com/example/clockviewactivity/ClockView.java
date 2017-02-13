package com.example.clockviewactivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xiaolong.wei on 2017/2/13.
 */

public class ClockView extends View {

    private Paint mPaint;
    private int radius;
    //转过的角度
    private int mDegree;
    private String upNum;
    private String downNum;
    private int minuteHandLength;
    private int hourHandLength;
    private int secondHandLength;

    private int hour;
    private int minute;
    private int second;

    public ClockView(Context context) {
        this(context,null);
    }

    public ClockView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        radius = 500;
        minuteHandLength = 380;
        hourHandLength = 300;
        secondHandLength = 420;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(30);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画表
        //1、画圓
        canvas.drawCircle(radius , radius  ,radius,mPaint);
        //2、画刻度
        for (int i = 0 ; i < 360 / 6; i ++ ) {

            canvas.save();

            canvas.rotate( 3 * i,radius,radius);
            mDegree = 3 * i;

            if (mDegree % 30 == 0) {//画长刻度
                canvas.drawLine(radius, 0, radius, 60, mPaint);
                canvas.drawLine(radius, 2 * radius, radius, 2 * radius - 60, mPaint);
            }else {//画短刻度
                canvas.drawLine(radius,0,radius,30,mPaint);
                canvas.drawLine(radius,2 * radius, radius, 2 * radius - 30 , mPaint);
            }
            canvas.restore();
        }

        //3、画数字
        int newRadius = radius - 100;
        Rect r = new Rect();
        String s = "";
        for (int j = 0 ; j < 12 ; j ++ ){
            if (j == 0){
                s = "12";
            }else {
                s = j + "";
            }
            mPaint.getTextBounds(s,0,s.length(),r);
            canvas.drawText(s,radius - r.width() / 2 + (int)(newRadius * Math.sin(30 * j * Math.PI / 180)),100 + (int)(newRadius * Math.sin(30 * j * Math.PI / 180) * Math.tan(15 * j * Math.PI / 180)) , mPaint);
        }
        //4、画表心
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(radius,radius,10,mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        //5、画表针
        canvas.drawLine(radius,radius,radius + ((int) (Math.sin(75 * Math.PI / 180) * secondHandLength)), radius - ((int) (Math.cos(75 * Math.PI / 180) * secondHandLength)),mPaint);
        mPaint.setStrokeWidth(5);
        canvas.drawLine(radius,radius,radius,radius - minuteHandLength,mPaint);//分针
        mPaint.setStrokeWidth(10);
        canvas.drawLine(radius,radius, radius + ((int) (Math.sin(30 * Math.PI / 180) * hourHandLength)), radius - ((int) (Math.cos(30 * Math.PI / 180) * hourHandLength)),mPaint);//时针
    }

    public void setTime(){

    }

    public void startTime(){
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST){
            width = radius * 2;
        }

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (heightMode == MeasureSpec.AT_MOST){
            height = radius * 2;
        }

        setMeasuredDimension(width,height);

    }
}
