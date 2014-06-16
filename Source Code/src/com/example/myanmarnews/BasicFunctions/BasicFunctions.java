package com.example.myanmarnews.BasicFunctions;

import java.util.Timer;
import java.util.TimerTask;

import com.example.myanmarnews.libs.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import com.example.myanmarnews.libs.actionbarpulltorefresh.library.Options;
import com.example.myanmarnews.libs.actionbarpulltorefresh.library.PullToRefreshLayout;
import com.example.myanmarnews.libs.actionbarpulltorefresh.library.listeners.OnRefreshListener;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
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
	    return Math.round(dp * density);
	}
	public static void IniPullToRefresh(final Activity activity, ViewGroup viewGroup, View view, final TimerTask timerTask, PullToRefreshLayout mPullToRefreshLayout){
		
		ActionBarPullToRefresh.from(activity)
		.insertLayoutInto(viewGroup) // We need to insert the PullToRefreshLayout into the Fragment's ViewGroup
		.theseChildrenArePullable(view) // We need to mark the ListView and it's Empty View as pullable This is because they are not direct children of the
		// ViewGroup
		.options(Options.create()
				.refreshingText("Fetching News...")
				.pullText("Pull me down to update!")
				.releaseText("Release to update!!!")
				.titleTextColor(android.R.color.black)
				.progressBarColor(android.R.color.holo_orange_light)
				.headerBackgroundColor(android.R.color.holo_blue_light)
				.progressBarStyle(Options.PROGRESS_BAR_STYLE_OUTSIDE)
				.build())
		.listener(new OnRefreshListener() { // We can now complete the setup as desired
			@Override
			public void onRefreshStarted(View view) {

				Timer timer = new Timer();
				TimerTask task = new TimerTask() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						timerTask.run();
					}
				};
				
				timer.schedule(task, 1000);
			}
		})
		.setup(mPullToRefreshLayout);
	}
}
