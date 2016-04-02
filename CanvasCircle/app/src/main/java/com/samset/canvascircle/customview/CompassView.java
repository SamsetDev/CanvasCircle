/*
 * Copyright (C) 2016 by Samset
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.samset.canvascircle.customview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

import com.samset.canvascircle.R;

public class CompassView extends View {

    private Paint markerPaint;
    private Paint circlePaint;
    private Paint textPaint;
    private int textHeight;
    private float bearing;

    public void setBearing(float _bearing) {
        bearing = _bearing;
        sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED);
    }

    public float getBearing() {
        return bearing;
    }

    public CompassView(Context context) {
        super(context);
        initCompassView();
    }

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCompassView();
    }

    public CompassView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCompassView();
    }

    private void initCompassView() {

        setFocusable(true);
        Resources resources = this.getResources();

        // Set all component to draw circle
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(resources.getColor(R.color.colorPrimaryDark));
        circlePaint.setStrokeWidth(3);
        circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        // Set all component to draw text outer
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(resources.getColor(R.color.colorPrimary));
        textPaint.setTextSize((float) 50);// Setting here to display the font size.
        textPaint.setStyle(Paint.Style.STROKE);

        textHeight = (int) textPaint.measureText("yY");
        markerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        markerPaint.setColor(resources.getColor(R.color.colorAccent));

        Log.e("TextHeight"," Height "+ textHeight);
        setWillNotDraw(false);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Compass is a filled as much space circle, to set the measurement by setting the minimum boundary, height or width.
        int measuredWidth = measure(widthMeasureSpec);
        int measuredHeight = measure(heightMeasureSpec);

        int d = Math.min(measuredWidth, measuredHeight);
        setMeasuredDimension(d, d);
    }

    private int measure(int measureSpec) {
        int result = 0;
        // The decoding of the description of the measurement
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.UNSPECIFIED) {
            // If you do not specify a default boundaries, the return value is 200
            result = 200;
        } else {
            // Because you want to fill the space available, so always returns the entire available boundary
            result = specSize;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Find the control center, and the minimum edge length as a stored radius compass.
        int mMeasuredWidth = getMeasuredWidth();
        int mMeasuredHeight = getMeasuredHeight();
        int px = mMeasuredWidth / 2;
        int py = mMeasuredHeight / 2;
        int radius = Math.min(px, py);

        // Use the drawCircle method to draw
        canvas.drawCircle(px, py, radius - 60, circlePaint);
        Log.e("Radius ", " ---" + radius);
        Log.e("Radius ", " -px -->" + px + "-- py--> " + py);

        canvas.save();
        canvas.rotate(-bearing, px, py);
        canvas.restore();

        //Draw circle text
        for (int i = 1; i < 9; i++) {

            String angle = String.valueOf(i + 1);

            float angleTextWidth = textPaint.measureText(angle);
            int angleTextX = (int) (px - angleTextWidth / 2);
            int angleTextY = py - radius + (int) angleTextWidth + 8;//+10  adjust radius

            textPaint.setColor(getContext().getResources().getColor(R.color.colorAccent));
            canvas.drawText(String.valueOf(i), angleTextX, angleTextY, textPaint);
            canvas.rotate(45, px, py);
            canvas.save();

        }

        canvas.restore();
    }


    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        super.dispatchPopulateAccessibilityEvent(event);
        if (isShown()) {
            String bearingStr = String.valueOf(bearing);
            if (bearingStr.length() > AccessibilityEvent.MAX_TEXT_LENGTH)
                bearingStr = bearingStr.substring(0,
                        AccessibilityEvent.MAX_TEXT_LENGTH);

            event.getText().add(bearingStr);
            return true;
        }
        return false;
    }

    public static Bitmap getCircularBitmap(Bitmap bitmap)
    {
        Bitmap output;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        float r = 0;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            r = bitmap.getHeight() / 2;
        } else {
            r = bitmap.getWidth() / 2;
        }

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(r, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
}