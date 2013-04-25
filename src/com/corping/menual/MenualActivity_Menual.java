package com.corping.menual;

import java.util.List;

import utils.ImageLoader2;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.corping.R;
import com.corping.taskboard.VideoPlayActivity;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MenualActivity_Menual extends Activity {
	String audioUrl, videoUrl;
	TextView tv_comment;
	ImageView iv_contentPic;
	SurfaceHolder holder;
	String objectId;

	Intent intent;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.menualpage_menual);
		init();

		intent = getIntent();
		objectId = intent.getStringExtra("objectId");

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getPosts();
	}

	public void getPosts() {

		ParseQuery query = new ParseQuery("TodoList");
		query.whereEqualTo("objectId", objectId);
		query.orderByDescending("createdAt");
		query.findInBackground(new FindCallback() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				// TODO Auto-generated method stub
				ImageLoader2 imageloader = new ImageLoader2(
						getApplicationContext());
				ParseObject obj = objects.get(0);

				String comment = obj.getString("comment");
				tv_comment.setText(comment);

				ParseFile contentPic = (ParseFile) obj.get("contentPic");
				String pictureUrl = contentPic.getUrl();
				imageloader.DisplayImage(pictureUrl, iv_contentPic);


				ParseFile videoFile = (ParseFile) obj.get("contentVideo");
				videoUrl = videoFile.getUrl();
				Log.d("videoUrl", videoUrl);
			}
		});

	}

	public void audioMedia(View v) {
		MediaPlayer player = null;
		// Toast.makeText(getApplicationContext(), "hi", 3000).show();

		if (player != null) {
			player.stop();
			player.release();
			player = null;
		}

		Toast.makeText(getApplicationContext(), "재생합니다.", Toast.LENGTH_LONG)
				.show();

		try {
			player = new MediaPlayer();

			player.setDataSource(audioUrl);
			player.prepare();
			player.start();
		} catch (Exception ex) {
			Log.e("SampleAudioRecorder", "Audio play failed.", ex);
		}

	}

	public void videoMedia(View v) {

		Intent intent = new Intent(getApplicationContext(), VideoPlayActivity.class);
		intent.putExtra("url", videoUrl);
		startActivity(intent);


	}

	public void init() {

		tv_comment = (TextView) findViewById(R.id.tv_comment);
		iv_contentPic = (ImageView) findViewById(R.id.iv_contentPic);
		SurfaceView surface = new SurfaceView(this);
		holder = surface.getHolder();
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//		FrameLayout frame = (FrameLayout) findViewById(R.id.videoLayout);
//		frame.addView(surface);
	}

}
