package com.corping.taskboard;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.corping.R;

public class GridAdapter extends BaseAdapter {
	private Context context;
	
	
	ArrayList<String> boardTitles;
	ArrayList<Integer> members;
	ArrayList<String> objectIds;
	public GridAdapter(Context context, ArrayList<String> boardTitles, ArrayList<Integer> members, ArrayList<String> objectIds) {
		this.context = context;
		this.boardTitles = boardTitles;
		this.members = members;
		this.objectIds = objectIds;
		
	}
 
	public View getView(int position, View convertView, ViewGroup parent) {
 
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View gridView;
 
		if (convertView == null) {
 
			gridView = new View(context);
 
			// get layout from mobile.xml
			gridView = inflater.inflate(R.layout.featured_adapter_grid_items, null);
 
			// set value into textview
			TextView tv_title = (TextView) gridView
					.findViewById(R.id.tv_title);
			
			TextView objectId = (TextView) gridView
					.findViewById(R.id.objectId);
 
			// set image based on selected text
			ImageView imageView = (ImageView) gridView
					.findViewById(R.id.grid_item_image);
			objectId.setText(objectIds.get(position));
			tv_title.setText(boardTitles.get(position));
			imageView.setImageResource(R.drawable.ic_board_cafe_l_blue);
			

 
		} else {
			gridView = (View) convertView;
		}
 
		return gridView;
	}
 
	public int getCount() {
		return boardTitles.size();
	}
 
	public Object getItem(int position) {
		return null;
	}
 
	public long getItemId(int position) {
		return 0;
	}
 
}