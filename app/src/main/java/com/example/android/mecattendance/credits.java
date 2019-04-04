package com.example.android.mecattendance;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class credits extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(credits.this,Attendence.class));
        overridePendingTransition(
                0,
                R.anim.play_panel_close_background
        );
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        getSupportActionBar().setTitle("Developer Credits");
        Context mContext;
        Resources mResources;
        RelativeLayout mRelativeLayout;
        Button mBTNCircular;
        ImageView mImageView,ImageView2;


            // Get the application context
        mContext = getApplicationContext();

            // Get the Resources
        mResources = getResources();

            // Get the widgets reference from XML layout
        mImageView = (ImageView) findViewById(R.id.kolam);
        ImageView2 = (ImageView) findViewById(R.id.manu);


            // Get the bitmap from drawable resources
        final Bitmap srcBitmap = BitmapFactory.decodeResource(mResources, R.drawable.k2);
        final Bitmap bitmap = BitmapFactory.decodeResource(mResources,R.drawable.manu);

            // Display the bitmap in ImageView
        mImageView.setImageBitmap(srcBitmap);
        ImageView2.setImageBitmap(bitmap);

            // Set a click listener for circular Button widget
        Paint paint = new Paint();

        // Get source bitmap width and height
        int srcBitmapWidth = srcBitmap.getWidth();
        int srcBitmapHeight = srcBitmap.getHeight();
        int BitmapWidth = bitmap.getWidth();
        int BitmapHeight = bitmap.getHeight();

                /*
                    IMPORTANT NOTE : You should experiment with border and shadow width
                    to get better circular ImageView as you expected.
                    I am confused about those size.
                */
        // Define border and shadow width
        int borderWidth = 25;
        int shadowWidth = 10;

        // destination bitmap width
        int dstBitmapWidth = Math.min(srcBitmapWidth,srcBitmapHeight)+borderWidth*2;
        int dstbitmapWidth = Math.min(BitmapWidth,BitmapHeight)+borderWidth*2;
        //float radius = Math.min(srcBitmapWidth,srcBitmapHeight)/2;

        // Initializing a new bitmap to draw source bitmap, border and shadow
        Bitmap dstBitmap = Bitmap.createBitmap(dstBitmapWidth,dstBitmapWidth, Bitmap.Config.ARGB_8888);
        Bitmap dstBitmap2 = Bitmap.createBitmap(dstbitmapWidth,dstbitmapWidth, Bitmap.Config.ARGB_8888);

        // Initialize a new canvas
        Canvas canvas = new Canvas(dstBitmap);
        Canvas canvas1 = new Canvas(dstBitmap2);

        // Draw a solid color to canvas
        canvas.drawColor(Color.WHITE);
        canvas1.drawColor(Color.WHITE);

        // Draw the source bitmap to destination bitmap by keeping border and shadow spaces
        canvas.drawBitmap(srcBitmap, (dstBitmapWidth - srcBitmapWidth) / 2, (dstBitmapWidth - srcBitmapHeight) / 2, null);
        canvas1.drawBitmap(bitmap, (dstbitmapWidth - BitmapWidth) / 2, (dstbitmapWidth - BitmapHeight) / 2, null);

        // Use Paint to draw border
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderWidth * 2);
        paint.setColor(Color.WHITE);

        // Draw the border in destination bitmap
        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, canvas.getWidth() / 2, paint);
        canvas1.drawCircle(canvas1.getWidth() / 2, canvas1.getHeight() / 2, canvas1.getWidth() / 2, paint);

        // Use Paint to draw shadow
        paint.setColor(Color.LTGRAY);
        paint.setStrokeWidth(shadowWidth);

        // Draw the shadow on circular bitmap
        canvas.drawCircle(canvas.getWidth()/2,canvas.getHeight()/2,canvas.getWidth()/2,paint);
        canvas1.drawCircle(canvas1.getWidth()/2,canvas1.getHeight()/2,canvas1.getWidth()/2,paint);

                /*
                    RoundedBitmapDrawable
                        A Drawable that wraps a bitmap and can be drawn with rounded corners. You
                        can create a RoundedBitmapDrawable from a file path, an input stream, or
                        from a Bitmap object.
                */
        // Initialize a new RoundedBitmapDrawable object to make ImageView circular
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(mResources, dstBitmap);
        RoundedBitmapDrawable roundedbitmapDrawable = RoundedBitmapDrawableFactory.create(mResources, dstBitmap2);
                /*
                    setCircular(boolean circular)
                        Sets the image shape to circular.
                */
        // Make the ImageView image to a circular image
        roundedBitmapDrawable.setCircular(true);
        roundedbitmapDrawable.setCircular(true);

                /*
                    setAntiAlias(boolean aa)
                        Enables or disables anti-aliasing for this drawable.
                */
        roundedBitmapDrawable.setAntiAlias(true);
        roundedbitmapDrawable.setAntiAlias(true);

        // Set the ImageView image as drawable object
        mImageView.setImageDrawable(roundedBitmapDrawable);
        ImageView2.setImageDrawable(roundedbitmapDrawable);
    }
}

