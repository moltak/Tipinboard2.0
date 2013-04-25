package com.corping.media;

import java.util.List;

import utils.ImageLoader2;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.corping.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MediaActivity_Timeline extends Activity {
	String audioUrl, videoUrl;
	TextView tv_comment;
	ImageView iv_contentPic;
	SurfaceHolder holder;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mediaactivity_timeline);
		init();
		

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getPosts();
	}

	public void getPosts() {

		ParseQuery query = new ParseQuery("Post");
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

				ParseFile audioFile = (ParseFile) obj.get("contentAudio");
				audioUrl = audioFile.getUrl();

				ParseFile videoFile = (ParseFile) obj.get("contentVideo");
				videoUrl = videoFile.getUrl();
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
	
	public void videoMedia(View v){
		
		MediaPlayer player = null;
		Toast.makeText(getApplicationContext(), "녹화보기", Toast.LENGTH_LONG)
		.show();
		
		if (player == null) {
			player = new MediaPlayer();
		}

		try {
			player.setDataSource(videoUrl);
			player.setDisplay(holder);

			player.prepare();
			player.start();
		} catch (Exception e) {
		}
		
	}

	public void init() {

		tv_comment = (TextView) findViewById(R.id.tv_comment);
		iv_contentPic = (ImageView) findViewById(R.id.iv_contentPic);
		SurfaceView surface = new SurfaceView(this);
		holder = surface.getHolder();
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		FrameLayout frame = (FrameLayout) findViewById(R.id.videoLayout);
		frame.addView(surface);
	}
	
	
	

}
