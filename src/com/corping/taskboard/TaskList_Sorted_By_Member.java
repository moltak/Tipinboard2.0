package com.corping.taskboard;

import java.util.ArrayList;

import android.annotation.SuppressLint;
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

public class TaskList_Sorted_By_Member extends FragmentActivity {

	ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
	ViewPager pager;
	pagerAdpater adapter;

	int num;
	TextView tv_boardtitle;
	Intent intent;
	String OBJECTID, boardTitle;
	
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
		boardTitle = intent.getStringExtra("boardTitle");

		tv_boardtitle.setText(boardTitle);
		// Toast.makeText(getApplicationContext(), OBJECTID, 3000).show();
		getTaskboardList();

	}
	


	@SuppressLint("NewApi")
	public void getTaskboardList() {
		
		progressdialog = ProgressDialog.show(TaskList_Sorted_By_Member.this, "", "로딩중...");

		ParseQuery query = new ParseQuery("TaskBoard");
		query.whereEqualTo("objectId", OBJECTID);
		query.getFirstInBackground(new GetCallback() {

			@Override
			public void done(ParseObject object, ParseException e) {
				// TODO Auto-generated method stub

				
				
				String myself = object.getString("adminName");
				
				
				ArrayList<String> usernames = (ArrayList<String>) object
						.get("usernames");
				ArrayList<String> names = (ArrayList<String>) object
						.get("names");
			
				for(int i = 0 ; i < usernames.size(); i++){
					
					Bundle bundle = new Bundle();
					bundle.putString("username", usernames.get(i));
					bundle.putString("name", names.get(i));
					bundle.putString("boardId", OBJECTID);
					
					ArrayList<String> categories = (ArrayList<String>) object.get("category");
					CharSequence[] cs = categories.toArray(new CharSequence[categories.size()]);
					
					bundle.putCharSequenceArray("categoryCS", cs);

					if ((ParseUser.getCurrentUser().getUsername())
							.equals(myself)) {

						bundle.putString("usernum", "1");

					} else {
						bundle.putString("usernum", "0");
					}

					TaskList_Fragment_Member fragment = new TaskList_Fragment_Member();
					fragment.setArguments(bundle);
					fragmentList.add(fragment);
					
				}
				
				adapter.notifyDataSetChanged();
				progressdialog.dismiss();
			}
		});

	}



	public void addemployee(View v) throws ParseException {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle("추가할 멤버의 휴대폰번호를 기입하세요.");
		final EditText input = new EditText(this);
		builder.setView(input);
		builder.setInverseBackgroundForced(true);
		builder.setPositiveButton("완료", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				String phone = input.getText().toString();

				try {
					inviteEmployee(phone);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				dialog.dismiss();
			}
		});
		builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
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

		Intent intent = new Intent(getApplicationContext(),
				TaskList_Sorted_By_Category.class);
		intent.addFlags(intent.FLAG_ACTIVITY_NO_ANIMATION);
		intent.putExtra("objectId", OBJECTID);
		intent.putExtra("boardTitle", boardTitle);
		startActivity(intent);

	}

	public void inviteEmployee(String phone) throws ParseException {
		ParseQuery adduser = ParseUser.getQuery();

		adduser.whereEqualTo("username", phone);
		final ParseUser user = (ParseUser) (adduser.getFirst());

		ParseQuery query = new ParseQuery("TaskBoard");
		query.whereEqualTo("objectId", OBJECTID);
		query.getFirstInBackground(new GetCallback() {

			@Override
			public void done(ParseObject object, ParseException e) {
				// TODO Auto-generated method stub

				object.add("usernames", user.getUsername());
				object.add("names", user.getString("name"));
				object.saveInBackground(new SaveCallback() {
					
					@Override
					public void done(ParseException e) {
						// TODO Auto-generated method stub
						fragmentList.clear();
						getTaskboardList();
					}
				});
			}
		});
	}

	class pagerAdpater extends FragmentPagerAdapter {

		ArrayList<Fragment> fragmentList;

		public pagerAdpater(FragmentManager fm, ArrayList<Fragment> fragmentList) {
			super(fm);
			// TODO Auto-generated constructor stub
			this.fragmentList = fragmentList;
		}



		@Override
		public Fragment getItem(int arg0) {
			return fragmentList.get(arg0);
		}

		@Override
		public int getCount() {
			return fragmentList.size();
		}

	}


}
