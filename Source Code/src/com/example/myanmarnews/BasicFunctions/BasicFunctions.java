package com.example.myanmarnews.BasicFunctions;

import com.example.myanmarnews.MainActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * 
 * @author Fabio Ngo
 * Class store basic functions ( most-used functions)
 */
public class BasicFunctions {
	/*
	 * Resize Image View to the size
	 */
	public static void ResizeImageView(int width,int height, ImageView imageView){
		Drawable drawable = imageView.getDrawable();
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,width,height,true);
        imageView.setImageBitmap(scaledBitmap);
	}
	public static void ResizeImageView(int size, ImageView imageView){
		Drawable drawable = imageView.getDrawable();
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,size,size,true);
        imageView.setImageBitmap(scaledBitmap);
	}
	/**
	 * Convert DP to PX
	 */
	public static int dpToPx(int dp, Context context)
	{
	    float density = context.getResources().getDisplayMetrics().density;
	    return Math.round((float)dp * density);
	}
	
}
