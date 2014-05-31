package com.example.myanmarnews;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListNewsItemAdapter extends ArrayAdapter<NewsItem> {

	public ListNewsItemAdapter(Context context, int resource) {
		super(context, resource);
		// TODO Auto-generated constructor stub
	}

	// declaring our ArrayList of items
		private ArrayList<NewsItem> objects;

		/* here we must override the constructor for ArrayAdapter
		* the only variable we care about now is ArrayList<Item> objects,
		* because it is the list of objects we want to display.
		*/
		public ListNewsItemAdapter(Context context, int textViewResourceId, ArrayList<NewsItem> objects) {
			super(context, textViewResourceId, objects);
			this.objects = objects;
		}

		/*
		 * we are overriding the getView method here - this is what defines how each
		 * list item will look.
		 */
		public View getView(int position, View convertView, ViewGroup parent){

			// assign the view we are converting to a local variable
			View v = convertView;

			// first check to see if the view is null. if so, we have to inflate it.
			// to inflate it basically means to render, or show, the view.
			if (v == null) {
				LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = inflater.inflate(R.layout.main_content_news_layout, null);
			}

			/*
			 * Recall that the variable position is sent in as an argument to this method.
			 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
			 * iterates through the list we sent it)
			 * 
			 * Therefore, i refers to the current Item object.
			 */
			NewsItem i = objects.get(position);

			if (i != null) {

				// This is how you obtain a reference to the TextViews.
				// These TextViews are created in the XML files we defined.

				TextView title = (TextView) v.findViewById(R.id.title_single_news);
				ImageView icon = (ImageView) v.findViewById(R.id.image_single_news);
				TextView content = (TextView)v.findViewById(R.id.content_single_news);
				TextView publicDate = (TextView)v.findViewById(R.id.date_time_single_news);
				
				// check to see if each individual textview is null.
				// if not, assign some text!
				if (title != null){
					title.setText( i.getTitle());
				}
				if (icon != null){
					icon.setImageResource(i.getImageID());
				}
				if (content != null){
					content.setText(i.getContent());
				}
				if (publicDate != null){
					publicDate.setText(i.getPublicDate());
				}
				
			}

			// the view must be returned to our activity
			return v;

		}

	
}
