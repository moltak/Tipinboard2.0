package com.corping.manual;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.corping.R;
import com.corping.animation.ActivitySwitcher;
import com.corping.taskboard.TaskList_Sorted_By_Category;

public class ManualActivity_Timeline extends Activity {

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.manualpage_timeline);

	}

	public void back(View v) {

		animatedStartActivity();
	}

	@Override
	protected void onResume() {
		// animateIn this activity
		ActivitySwitcher.animationIn(findViewById(R.id.container),
				getWindowManager());
		super.onResume();
	}

	private void animatedStartActivity() {
		// we only animateOut this activity here.
		// The new activity will animateIn from its onResume() - be sure to
		// implement it.
		final Intent intent = new Intent(getApplicationContext(),
				TaskList_Sorted_By_Category.class);
		// disable default animation for new intent
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
//				| Intent.FLAG_ACTIVITY_NEW_TASK);
		ActivitySwitcher.animationOut(findViewById(R.id.container),
				getWindowManager(),
				new ActivitySwitcher.AnimationFinishedListener() {
					public void onAnimationFinished() {
						startActivity(intent);
					}
				});
		finish();
	}

}