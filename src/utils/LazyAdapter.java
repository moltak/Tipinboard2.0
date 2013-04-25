package utils;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.corping.R;

public class LazyAdapter extends BaseAdapter {

	private Activity activity;
	ArrayList<String> items;
	ArrayList<String> objectIds;
	ArrayList<String> categories;
	private static LayoutInflater inflater = null;

	// public ImageLoader imageLoader;
	// private ArrayList<String> data;
	// ArrayList<String> title;
	// ArrayList<String> mProfilePic;
	// ArrayList<String> mContent;
	// ArrayList<String> objectId;

	public LazyAdapter(Activity a, ArrayList<String> items,
			ArrayList<String> objectIds, ArrayList<String> categories) {
		activity = a;
		// data = mStrings;
		this.items = items;
		this.objectIds = objectIds;
		this.categories = categories;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	public int getCount() {
		return objectIds.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public static class ViewHolder {
		// public TextView text;
		public TextView item;
		public TextView obejctId;
		public TextView category;
		// public ImageView image;
		// public ImageView profilePic;
		// public TextView username2;
		// public TextView objectId2;
		// public TextView seen;
		// public TextView recom;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.featured_adapter_taskboard_list,
					null);
			holder = new ViewHolder();
			// holder.profilePic = (ImageView) vi.findViewById(R.id.profilePic);
			// holder.image = (ImageView) vi.findViewById(R.id.image);
			// holder.objectId2 = (TextView) vi.findViewById(R.id.objectId);
			
			holder.category = (TextView) vi.findViewById(R.id.category);
			holder.obejctId = (TextView) vi.findViewById(R.id.obejctId);
			holder.item = (TextView) vi.findViewById(R.id.item);
			vi.setTag(holder);
		}
		holder = (ViewHolder) vi.getTag();

		// holder.image.setTag(data.get(position));
		// holder.profilePic.setTag(mProfilePic.get(position));
		// imageLoader.DisplayImage(data.get(position), activity, holder.image);
		// imageLoader.DisplayImage(mProfilePic.get(position), activity,
		// holder.profilePic);
		holder.category.setText(categories.get(position));
		holder.obejctId.setText(objectIds.get(position));
		holder.item.setText(items.get(position));

		return vi;
	}
}