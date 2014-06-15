package com.example.myanmarnews;

import com.example.myanmarnews.BasicFunctions.BasicFunctions;

import imageLoader.ImageLoader;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayWebPageActivity extends Activity {

	private ViewPager viewPager;
	private ActionBar actionBar;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.display_web_page);
	    //Variable for layout of activity
	    TextView page_title = (TextView) findViewById(R.id.page_title);
	    TextView page_public_date = (TextView) findViewById(R.id.page_public_date);
	    ImageView page_image = (ImageView) findViewById(R.id.page_image);
	    TextView page_content = (TextView) findViewById(R.id.page_content);
	    Button go_to_website = (Button) findViewById(R.id.go_to_website_button);
	   // String page_image_link;
	    
	    //Get information from fragment
	    final Intent i = getIntent();
	    
	    Log.d("DEBUG", i.getStringExtra("page_img_link"));
	    page_title.setText(i.getStringExtra("page_title"));
	    page_public_date.setText(i.getStringExtra("page_public_date"));
//	    page_image_link = i.getStringExtra("page_img_link");
	    page_content.setText(i.getStringExtra("description"));
	    //Downlaod Image from URL IMAGE

		ImageLoader imgLoader = new ImageLoader(this);
		// Loader image - will be shown before loading image
        int loader = R.drawable.image_not_found;
         
		imgLoader.DisplayImage(i.getStringExtra("page_img_link"), loader, page_image);
	    //page_title.setText("aaaa");
		//BasicFunctions.ResizeImageView(, height, imageView);
		//Config the
		//go_to_website.setP
		go_to_website.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse( i.getStringExtra("page_link") ) );

			    startActivity( browse );
				
			}
		});
		
		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
		//Set Description
		// Initilization
//		viewPager = (ViewPager) findViewById(R.id.pager);
//		actionBar = getActionBar();
//		//mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
//
//		//viewPager.setAdapter(mAdapter);
//		actionBar.setHomeButtonEnabled(false);
//		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);		

	    
	    
	    
	    // TODO Auto-generated method stub
	}
//	@Override
//	public void onTabReselected(Tab tab, FragmentTransaction ft) {
//	}
//
//	@Override
//	public void onTabSelected(Tab tab, FragmentTransaction ft) {
//		// on tab selected
//		// show respected fragment view
//		viewPager.setCurrentItem(tab.getPosition());
//	}
//
//	@Override
//	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
//	}

}
