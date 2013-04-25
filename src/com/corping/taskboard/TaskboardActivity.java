package com.corping.taskboard;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.corping.R;
import com.corping.create.CreateActivity;
import com.corping.create.CreateTaskboardActivity;
import com.corping.main.ProjectActivity;
import com.corping.manual.ManualActivity;
import com.corping.mypage.MypageActivity;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class TaskboardActivity extends Activity {
	TextView tv_comment, objectId, tv_title;
	ImageView iv_contentPic;
	SurfaceHolder holder;
	TextView tv_username;
	String videoUrl;
	ParseUser userObj = ParseUser.getCurrentUser();
	String user = userObj.getUsername();
	GridView gridView;

	ArrayList<String> boardTitles = new ArrayList<String>();
	ArrayList<String> objectIds = new ArrayList<String>();
	ArrayList<Integer> members = new ArrayList<Integer>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.taskboardpage);
		init();

		loadTaskBoard();

		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(getApplicationContext(),
						TaskList_Sorted_By_Member.class);
				TextView objID = (TextView) view.findViewById(R.id.objectId);
				TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
				
				String objectId = objID.getText().toString();
				String boardTitle = tv_title.getText().toString();
				intent.putExtra("objectId", objectId);
				intent.putExtra("boardTitle", boardTitle);
				startActivity(intent);
			}
		});

	}

	public void init() {

		gridView = (GridView) findViewById(R.id.gridView1);
		tv_username = (TextView) findViewById(R.id.tv_username);
		String name = userObj.getString("name");
		objectId = (TextView) findViewById(R.id.objectId);
		tv_username.setText(name);
//		tv_title = (TextView) findViewById(R.id.tv_title);

	}

	public void myPage(View v) {

		Toast.makeText(getApplicationContext(), "My page", 3000).show();
		Intent intent = new Intent(getApplicationContext(),
				MypageActivity.class);
		startActivity(intent);

	}

	public void plus(View v) {

		Intent intent = new Intent(getApplicationContext(),
				CreateTaskboardActivity.class);
		startActivity(intent);

	}

	public void loadTaskBoard() {

		ParseQuery query = new ParseQuery("TaskBoard");
		query.orderByDescending("createdAt");
		query.whereEqualTo("usernames", user);
		query.findInBackground(new FindCallback() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				// TODO Auto-generated method stub

				if (e == null) {
					for (ParseObject obj : objects) {

						String boardTitle = obj.getString("boardTitle");
						int member = obj.getInt("members");

						boardTitles.add(boardTitle);
						members.add(member);

						ArrayList<String> list = (ArrayList<String>) obj
								.get("members");
						
						objectIds.add(obj.getObjectId());
						
//						Toast.makeText(getApplicationContext(),obj.getObjectId(), 3000).show();

					}

					gridView.setAdapter(new GridAdapter(
							getApplicationContext(), boardTitles, members, objectIds));
				}
			}
		});

	}

	public void logout() {

		Toast.makeText(getApplicationContext(), "로그아웃!", 3000).show();

		ParseUser.logOut();

		Intent intent = new Intent(getApplication(), ProjectActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(intent);
		finish();

	}

}
