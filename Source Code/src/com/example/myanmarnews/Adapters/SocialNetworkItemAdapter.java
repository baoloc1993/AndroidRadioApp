package com.example.myanmarnews.Adapters;

import java.util.ArrayList;

import com.example.myanmarnews.MainActivity;
import com.example.myanmarnews.R;
import com.example.myanmarnews.BasicFunctions.BasicFunctions;
import com.example.myanmarnews.Items.NewsItem;
import com.example.myanmarnews.Items.SocialNetworkItem;
import com.example.myanmarnews.R.id;
import com.example.myanmarnews.R.layout;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SocialNetworkItemAdapter extends ArrayAdapter<SocialNetworkItem> {
	int orientation; //Screen orientation
	
	public SocialNetworkItemAdapter(Context context, int resource) {
		super(context, resource);
		
		// TODO Auto-generated constructor stub
	}

	// declaring our ArrayList of items
		private ArrayList<SocialNetworkItem> objects;

		/* here we must override the constructor for ArrayAdapter
		* the only variable we care about now is ArrayList<Item> objects,
		* because it is the list of objects we want to display.
		* @params orientation to be used in setting up components' layout
		*/
		public SocialNetworkItemAdapter(Context context, int textViewResourceId, ArrayList<SocialNetworkItem> objects, int orientation) {
			super(context, textViewResourceId, objects);
			this.objects = objects;
			
		}

		/*
		 * we are overriding the getView method here - this is what defines how each
		 * list item will look.
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent){

			// assign the view we are converting to a local variable
			View v = convertView;

			// first check to see if the view is null. if so, we have to inflate it.
			// to inflate it basically means to render, or show, the view.
			if (v == null) {
				LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = inflater.inflate(R.layout.single_social_network_header, null);
				
			}

			/*
			 * Recall that the variable position is sent in as an argument to this method.
			 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
			 * iterates through the list we sent it)
			 * 
			 * Therefore, i refers to the current Item object.
			 */
			SocialNetworkItem i = objects.get(position);

			if (i != null) {

				// This is how you obtain a reference to the TextViews.
				// These TextViews are created in the XML files we defined.

				
				ImageView icon = (ImageView) v.findViewById(R.id.icon);
				
				
				/**
				 * set Icon resource and size
				 */
				
				if (icon != null){
					icon.setImageResource(i.getImageID());
					/**
					 * get suitable size to scale image
					 */
					int size=0;
					int screenHeight = MainActivity.getScreenHeight();
					int screenWidth = MainActivity.getScreenWidth();
					if(screenHeight<=screenWidth){
						// in landscape mode
						size = (screenHeight/objects.size())/3;
					}
					if(screenHeight>screenWidth){
						//in portrait mode
						size = (screenWidth/objects.size())/3;
					}
					BasicFunctions.ResizeImageView(size, icon);
				}
				/**
				 * Set background color
				 */
				RelativeLayout relativeLayout = (RelativeLayout)v.findViewById(R.id.background);
				relativeLayout.setBackgroundColor(i.getBackground());
			}

			// the view must be returned to our activity
			return v;

		}

	
}
