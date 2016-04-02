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

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.samset.canvascircle.customview.CompassView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
   private Button btnCanvas,btnBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBitmap= (Button) findViewById(R.id.btn_canvasbitmap);
        btnCanvas= (Button) findViewById(R.id.btn_canvas);
        btnCanvas.setOnClickListener(this);
        btnBitmap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v==btnCanvas)
        {
            startActivity(new Intent(MainActivity.this,CircleWithCanvas.class));
        }else {
            startActivity(new Intent(MainActivity.this,CircleWithImageBitmap_Activity.class));
        }
    }
}
