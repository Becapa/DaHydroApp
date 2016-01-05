package com.dahydroshop.android.dahydroapp.com.dahydroshop.android.dahydroapp.notdone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Mike on 11/25/2015.
 */
public class RoomView extends View {

    private Paint mLightPaint;
    private Paint mBackgroundPaint;
    private int rows;
    private int cols;
    private int height;
    private int width;
    private String side;
    private static final int sizeW = 60;
    private static final int sizeL = 120;
    private static final int sizeH = 30;

    public RoomView(Context context) {
        super(context);
    }

    public RoomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mLightPaint = new Paint();
        mLightPaint.setColor(Color.YELLOW);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(Color.LTGRAY);



    }
    public void setValues(int rows, int cols, int height,  int width, String side){
        this.rows = rows;
        this.cols = cols;
        this.height = height;
        this.width = width;
        this.side = side;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawPaint(mBackgroundPaint);
        if(side.equals("top")){

            float left = ((width*60)/(cols+1))-30;
            float right = ((width*60)/(cols+1))+30;
            for(int i = 0; i < cols; i++){
                float top = ((height*60)/(rows+1))-30;
                float bottom = ((height*60)/(rows+1))+30;
                for(int j = 0; j < rows; j++){
                    canvas.drawRect(left,top,right,bottom, mLightPaint);
                    top+=((height*60)/(rows+1));
                    bottom+=((height*60)/(rows+1));
                }
                left+=((width*60)/(cols+1));
                right+=((width*60)/(cols+1));
            }
        }else{
            float left = ((width*60)/(cols+1))-30;
            float right = ((width*60)/(cols+1))+30;
            for(int i = 0; i < cols; i++){
                float top = 50;
                float bottom = 80;
                canvas.drawRect(left,top,right,bottom, mLightPaint);
                left+=((width*60)/(cols+1));
                right+=((width*60)/(cols+1));
            }
        }
    }
}
