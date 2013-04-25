package com.corping.taskboard;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.corping.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class TaskList_Sorted_By_Category extends FragmentActivity {

	ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
//	ArrayList<Fragment> fragmentList2 = new ArrayList<Fragment>();
	ViewPager pager;
	pagerAdpater adapter;
//	pagerAdpater2 adapter2;
	int num;
	TextView tv_boardtitle;
	Intent intent;
	String OBJECTID;

	ProgressDialog progressdialog;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tasklistpage);

		tv_boardtitle = (TextView) findViewById(R.id.tv_boardtitle);

		pager = (ViewPager) findViewById(R.id.viewpager);
		adapter = new pagerAdpater(getSupportFragmentManager(), fragmentList);
		// adapter2 = new pagerAdpater2(getSupportFragmentManager());
		pager.setAdapter(adapter);
		// pager.setAdapter(adapter2);
		pager.setOffscreenPageLimit(10);

		// Intent intent = getIntent();
		intent = getIntent();
		OBJECTID = intent.getStringExtra("objectId");
		String boardTitle = intent.getStringExtra("boardTitle");

		tv_boardtitle.setText(boardTitle);
		// Toast.makeText(getApplicationContext(), OBJECTID, 3000).show();
		getTaskboardList2();

	}


	public void getTaskboardList2() {
		progressdialog = ProgressDialog.show(TaskList_Sorted_By_Category.this, "", "로딩중...");
		
		ParseQuery query = new ParseQuery("TaskBoard");
		query.whereEqualTo("objectId", OBJECTID);
		query.getFirstInBackground(new GetCallback() {

			public void done(ParseObject object, ParseException e) {
		
				ArrayList<String> list = (ArrayList<String>) object
						.get("category");

				for (String category : list) {

					Bundle bundle = new Bundle();
					bundle.putString("boardId", OBJECTID);
					bundle.putString("category", category);

					TaskList_Fragment_Category fragment2 = new TaskList_Fragment_Category();
					fragment2.setArguments(bundle);
					fragmentList.add(fragment2);

				}
				adapter.notifyDataSetChanged();
				progressdialog.dismiss();

			}
		});

	}

	public void addemployee(View v) throws ParseException {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle("추가할분의 휴대폰번호를 기입해주세요.");
		final EditText input = new EditText(this);
		builder.setView(input);
		builder.setInverseBackgroundForced(true);
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				String phone = input.getText().toString();
				Toast.makeText(getApplicationContext(), phone, 3000).show();

				try {
					inviteEmployee(phone);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				dialog.dismiss();
			}
		});
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();

		// phone = "01055872130";

		Toast.makeText(getApplicationContext(), "+ person!", 3000).show();

	}

	public void convertView(View v) {

		finish();
		
	}

	public void inviteEmployee(String phone) throws ParseException {
		ParseQuery adduser = ParseUser.getQuery();

		adduser.whereEqualTo("username", phone);
		Toast.makeText(getApplicationContext(), "+ person!" + phone, 3000)
				.show();
		final ParseUser user = (ParseUser) (adduser.getFirst());

		Toast.makeText(getApplicationContext(), user.getUsername(), 3000)
				.show();

		ParseQuery query = new ParseQuery("TaskBoard");
		query.whereEqualTo("objectId", OBJECTID);
		query.getFirstInBackground(new GetCallback() {

			@Override
			public void done(ParseObject object, ParseException e) {
				// TODO Auto-generated method stub

				object.add("user", user);
				object.saveInBackground(new SaveCallback() {
					
					@Override
					public void done(ParseException e) {
						// TODO Auto-generated method stub
						
						fragmentList.clear();
						getTaskboardList2();
					}
				});
			}
		});

	}

	class pagerAdpater extends FragmentPagerAdapter {

		ArrayList<Fragment> fragmentList;

		public pagerAdpater(FragmentManager fm,
				ArrayList<Fragment> fragmentList) {
			super(fm);
			// TODO Auto-generated constructor stub
			this.fragmentList = fragmentList;
		}
//
//		public pagerAdpater(FragmentManager fm,
//				ArrayList<MyFragment2> fragmentList2) {
//			super(fm);
//			// TODO Auto-generated constructor stub
//			this.fragmentList = fragmentList2;
//		}

		@Override
		public Fragment getItem(int arg0) {
			return fragmentList.get(arg0);
		}

		@Override
		public int getCount() {
			return fragmentList.size();
		}

	}

//	class pagerAdpater2 extends FragmentPagerAdapter {
//
//		ArrayList<MyFragment> fragmentList;
//
//		public pagerAdpater2(FragmentManager fm,
//				ArrayList<MyFragment> fragmentList) {
//			super(fm);
//			// TODO Auto-generated constructor stub
//			this.fragmentList = fragmentList;
//		}
//
//		@Override
//		public Fragment getItem(int arg0) {
//			return fragmentList2.get(arg0);
//		}
//
//		@Override
//		public int getCount() {
//			return fragmentList2.size();
//		}
//
//	}
}
