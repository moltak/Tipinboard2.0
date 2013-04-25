package com.corping.manual;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import utils.ImageLoader;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.corping.R;
import com.corping.taskboard.VideoPlayActivity;
import com.corping.taskboard.VoicePlayActivity;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

public class ManualActivity extends Activity {
	private Gallery gallery;
	TextView missionContent, d_day;
	AddImgAdp adapter;
	String num;
	String videoUrl;
	ArrayList<String> example = new ArrayList<String>();
	ArrayList<String> missionPic = new ArrayList<String>();

	// ArrayList<Boolean> verification = new ArrayList<Boolean>();

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.missionpage);
		init();

	}

	public void init() {

		missionContent = (TextView) findViewById(R.id.missionContent);

		gallery = (Gallery) findViewById(R.id.examplegallery);

		gallery.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView parent, View v, int position,
					long id) {

				// imgView.setImageResource(Imgid[position]);
				Toast.makeText(getApplicationContext(), position + "", 3000)
						.show();
//				if (position == 2) {
//
//					ParseQuery query = new ParseQuery("Post");
//					query.orderByDescending("createdAt");
//					query.findInBackground(new FindCallback() {
//
//						@Override
//						public void done(List<ParseObject> objects,
//								ParseException e) {
//							// TODO Auto-generated method stub
//							ParseObject obj = objects.get(0);
//
//							ParseFile videoFile = (ParseFile) obj
//									.get("contentVideo");
//							videoUrl = videoFile.getUrl();
//							Toast.makeText(getApplicationContext(), videoUrl,
//									3000).show();
//							Intent intent = new Intent(getApplicationContext(),
//									VideoPlayActivity.class);
//							intent.putExtra("url", videoUrl);
//							startActivity(intent);
//						}
//					});
//				}
		

			}

		});

		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub

				switch (position) {
				case 0:
					missionContent.setText("1/6");
					break;
				case 1:
					missionContent.setText("2/6");
					break;
				case 2:
					missionContent.setText("3/6");
					break;
				case 3:
					missionContent.setText("4/6");
					break;
				case 4:
					missionContent.setText("5/6");
					break;
				case 5:
					missionContent.setText("6/6");
					break;
				}
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		example.add("1. 파리바케트에 간다.");
		example.add("2. 피자빵을 산다.");
//		example.add("3. 돈을 지불한다.");
		missionPic
				.add("http://files.parse.com/8d8ba28f-1d90-4295-a334-650b32b27fae/f5574c3e-db21-4f22-a4ce-20651bc941e6-contentPic.png");
		missionPic
				.add("http://files.parse.com/8d8ba28f-1d90-4295-a334-650b32b27fae/f5574c3e-db21-4f22-a4ce-20651bc941e6-contentPic.png");
//		missionPic
//				.add("http://files.parse.com/8d8ba28f-1d90-4295-a334-650b32b27fae/f5574c3e-db21-4f22-a4ce-20651bc941e6-contentPic.png");
		adapter = new AddImgAdp(ManualActivity.this, example, missionPic);

		gallery.setAdapter(adapter);

	}

	protected void onResume() {
		super.onResume();
		// loadContent();
	}

	public void loadContent() {

		final ParseUser user = ParseUser.getCurrentUser();
		final ParseRelation relation = user.getRelation("mission");
		example.clear();
		missionPic.clear();

		SharedPreferences myPref = getSharedPreferences("myPref",
				Activity.MODE_WORLD_WRITEABLE);

		num = myPref.getString("num", "");
		if (!num.equals(Calendar.WEEK_OF_MONTH + "")) {

			ParseQuery query = new ParseQuery(user.getUsername());
			query.findInBackground(new FindCallback() {

				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					// TODO Auto-generated method stub

					for (ParseObject obj : objects) {

						obj.deleteInBackground();

					}

				}
			});

			ParseQuery query2 = new ParseQuery("Dummy");
			query2.findInBackground(new FindCallback() {

				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					// TODO Auto-generated method stub

					for (ParseObject obj : objects) {

						int num = (Integer) obj.get("order");
						String missionContent = obj.getString("missionContent");
						Toast.makeText(getApplicationContext(), num + "", 3000)
								.show();
						ParseObject object = new ParseObject(user.getUsername());
						ParseFile picture = (ParseFile) obj.get("picture");
						object.put("order", num);
						object.put("missionContent", missionContent);
						object.put("picture", picture);
						object.saveInBackground();

					}

				}

			});

			// }

			SharedPreferences.Editor myEditor = myPref.edit();
			myEditor.putString("num", Calendar.WEEK_OF_MONTH + "");
			myEditor.commit();

		} else {

			// ParseUser user = ParseUser.getCurrentUser();

			ParseQuery query = new ParseQuery(user.getUsername());
			query.orderByAscending("order");
			query.findInBackground(new FindCallback() {

				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					// TODO Auto-generated method stub

					for (ParseObject obj : objects) {
						example.add(obj.getString("missionContent"));
						ParseFile pic = (ParseFile) obj.get("picture");
						Log.d("url", pic.getUrl());
						missionPic.add(pic.getUrl());
						// verification.add(obj.getBoolean("verification"));

					}

					// adapter = new AddImgAdp(ManualActivity.this, example,
					// missionPic, verification);
					//
					// gallery.setAdapter(adapter);
					adapter.notifyDataSetChanged();

				}
			});

		}

	}

	public static class AddImgAdp extends BaseAdapter {
		private Activity activity;
		private static LayoutInflater inflater = null;
		ArrayList<String> text;
		ArrayList<String> missionPic;
		ArrayList<Boolean> verified;
		int[] missionPlaceholder = { R.drawable.up_image, R.drawable.up_video,
				R.drawable.up_video };

		private Context cont;
		public ImageLoader imageLoader;

		public AddImgAdp(Activity a, ArrayList<String> text,
				ArrayList<String> missionPic) {
			this.missionPic = missionPic;
			this.text = text;
			this.verified = verified;
			activity = a;
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			imageLoader = new ImageLoader(activity.getApplicationContext());

		}

		public int getCount() {

			return text.size();
		}

		public Object getItem(int position) {

			return position;

		}

		public long getItemId(int position) {

			return position;

		}

		public static class ViewHolder {
			public TextView missions;
			public ImageView missionImage;
			public ImageView missionNo;
			public ImageView verification;
			public LinearLayout missionPlaceholder;

		}

		public View getView(int position, View convertView, ViewGroup parent) {

			View vi = convertView;
			ViewHolder holder;
			if (convertView == null) {
				vi = inflater.inflate(
						R.layout.featured_gallery_adpater, null);
				holder = new ViewHolder();
				holder.missions = (TextView) vi.findViewById(R.id.mission);
				holder.missionImage = (ImageView) vi
						.findViewById(R.id.missionImage);
				holder.missionNo = (ImageView) vi.findViewById(R.id.missionNo);
				// holder.verification = (ImageView) vi
				// .findViewById(R.id.verification);
				holder.missionPlaceholder = (LinearLayout) vi
						.findViewById(R.id.missionPlaceholder);

				vi.setTag(holder);

			}
			holder = (ViewHolder) vi.getTag();

			holder.missions.setText(text.get(position));
			holder.missionImage.setTag(missionPic.get(position));
			imageLoader.DisplayImage(missionPic.get(position), activity,
					holder.missionImage);
			// holder.missionNo.setImageResource(missionNos[position]);
			holder.missionPlaceholder
					.setBackgroundResource(missionPlaceholder[position]);

			// if (verified.get(position)) {
			// holder.verification.setImageResource(R.drawable.verified);
			// }
			return vi;
		}

	}

	public void post(View v) {

		Toast.makeText(getApplicationContext(), "Post", 3000).show();

	}
}