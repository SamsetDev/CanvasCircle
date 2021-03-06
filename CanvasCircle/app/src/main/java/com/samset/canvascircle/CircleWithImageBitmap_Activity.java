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

package com.samset.canvascircle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.samset.canvascircle.customview.CircularView;

public class CircleWithImageBitmap_Activity extends AppCompatActivity {

    private Bitmap bitmap,mybit;
    private CircularView compassView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_with_image_bitmap_);

        imageView= (ImageView) findViewById(R.id.img);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_image);
        compassView=new CircularView(this);
        mybit=compassView.getCircularBitmap(bitmap);

        imageView.setImageBitmap(mybit);
    }
}
