package com.corping.manual;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.corping.R;
import com.corping.media.ConvertToBytes;

/**
 * Audio Recorder
 * 
 * @author Mike
 */
public class AudioRecorderActivity2 extends Activity {
	final private static String RECORDED_FILE = "/sdcard/recorded.mp4";

	MediaPlayer player;
	MediaRecorder recorder;
	byte[] audio;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainpage);

		Button recordBtn = (Button) findViewById(R.id.recordBtn);
		Button recordStopBtn = (Button) findViewById(R.id.recordStopBtn);
//		Button playBtn = (Button) findViewById(R.id.playBtn);
//		Button playStopBtn = (Button) findViewById(R.id.playStopBtn);

		recordBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (recorder != null) {
					recorder.stop();
					recorder.release();
					recorder = null;
				}

				recorder = new MediaRecorder();

				recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
				recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
				recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
				recorder.setOutputFile(RECORDED_FILE);

				try {
					Toast.makeText(getApplicationContext(), "레코딩을 시작합니다~",
							Toast.LENGTH_LONG).show();

					recorder.prepare();
					recorder.start();
				} catch (Exception ex) {
					Log.e("SampleAudioRecorder", "Exception : ", ex);
				}
			}
		});

		recordStopBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (recorder == null)
					return;

				recorder.stop();
				recorder.release();
				recorder = null;

				audio = ConvertToBytes.toBytes(RECORDED_FILE);
				done(audio);
//				final ParseFile file = new ParseFile("audio.MPEG_4", audio);
//				file.saveInBackground(new SaveCallback() {
//
//					@Override
//					public void done(ParseException e) {
//						// TODO Auto-generated method stub
//						ParseObject example = new ParseObject("Example");
//						example.put("audioFile", file);
//						example.saveInBackground();
//
//					}
//				});

				Toast.makeText(getApplicationContext(), "리코드를 마칩니다.",
						Toast.LENGTH_LONG).show();
			}
		});

//		playBtn.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//				if (player != null) {
//					player.stop();
//					player.release();
//					player = null;
//				}
//
//				Toast.makeText(getApplicationContext(), "재생합니다.",
//						Toast.LENGTH_LONG).show();
//				
//				ParseQuery query = new ParseQuery("Example");
//				query.orderByDescending("createdAt");
//				query.findInBackground(new FindCallback() {
//
//					@Override
//					public void done(List<ParseObject> objects, ParseException e) {
//						// TODO Auto-generated method stub
//
//						ParseObject obj = objects.get(0);
//						ParseFile audioFile = (ParseFile) obj.get("audioFile");
//						String url = audioFile.getUrl();
//						
//						try {
//							player = new MediaPlayer();
//
//							player.setDataSource(url);
//							player.prepare();
//							player.start();
//						} catch (Exception ex) {
//							Log.e("SampleAudioRecorder", "Audio play failed.", e);
//						}
//					}
//				});
//
//			
//			}
//		});

//		playStopBtn.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//				if (player == null)
//					return;
//
//				Toast.makeText(getApplicationContext(), "재생을 종료합니다.",
//						Toast.LENGTH_LONG).show();
//
//				player.stop();
//				player.release();
//				player = null;
//			}
//		});
	}

	protected void onPause() {
		if (recorder != null) {
			recorder.release();
			recorder = null;
		}

		if (player != null) {
			player.release();
			player = null;
		}

		super.onPause();
	}

	
	public void done(byte[] audio){
		
		Toast.makeText(getApplicationContext(), "works", 3000).show();
		Intent intent = getIntent();
		intent.putExtra("audio", audio);
		setResult(RESULT_OK, intent);
		finish();
		
	}

}