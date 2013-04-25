package com.corping.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.corping.R;
import com.corping.taskboard.TaskboardActivity;
import com.parse.ParseAnalytics;
import com.parse.ParseUser;

public class ProjectActivity extends Activity {

	ParseUser currentUser = ParseUser.getCurrentUser();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);

		ParseAnalytics.trackAppOpened(getIntent());

		String name = currentUser.getString("name");

//		 currentUser = null;

		if (currentUser != null && name != null) {

			Intent intent = new Intent(getApplicationContext(),
					TaskboardActivity.class);

			startActivity(intent);
			finish();

		} else {

			Toast.makeText(getApplicationContext(), "로그인 해주세요~", 5000).show();
			getStarted();
		}

	}

	public void getStarted() {

		Intent intent = new Intent(getApplicationContext(),
				MainActivity_GetStarted.class);
		startActivity(intent);
		finish();
	}

}
