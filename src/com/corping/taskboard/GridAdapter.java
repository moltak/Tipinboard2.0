package com.corping.taskboard;

import java.util.ArrayList;

import utils.ImageLoader2;
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
	ArrayList<String> boardimageurls;
	public ImageLoader2 imageLoader;
	
	public GridAdapter(Context context, ArrayList<String> boardTitles,
			ArrayList<Integer> members, ArrayList<String> objectIds, ArrayList<String> boardimageurls ) {
		this.context = context;
		this.boardTitles = boardTitles;
		this.members = members;
		this.objectIds = objectIds;
		this.boardimageurls = boardimageurls;
		imageLoader = new ImageLoader2(context);
	}

	public static class ViewHolder {

		public TextView tv_title;
		TextView objectId;
		ImageView boardImage;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View gridView = convertView;
		ViewHolder holder;

		if (convertView == null) {

			gridView = inflater.inflate(R.layout.taskboard_gridadapter_items,
					null);

			holder = new ViewHolder();

			holder.tv_title = (TextView) gridView.findViewById(R.id.tv_title);

			holder.objectId = (TextView) gridView.findViewById(R.id.objectId);

			holder.boardImage = (ImageView) gridView
					.findViewById(R.id.grid_item_image);

			gridView.setTag(holder);
		}
		holder = (ViewHolder) gridView.getTag();
		
		holder.boardImage.setTag(boardimageurls.get(position));
//		holder.boardImage
		imageLoader.DisplayImage(boardimageurls.get(position), holder.boardImage);
		
		holder.objectId.setText(objectIds.get(position));
		holder.tv_title.setText(boardTitles.get(position));

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