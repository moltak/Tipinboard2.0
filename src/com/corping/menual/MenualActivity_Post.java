package com.corping.menual;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.corping.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class MenualActivity_Post extends Activity {
	ParseFile contentPicFile;
	ImageView imageView_contentPic;

	private static final int SELECT_PICTURE = 1;
	private static final int SELECT_AUDIO = 2;
	private static final int SELECT_VIDEO = 3;

	private String selectedImagePath;
	// ADDED
	private String filemanagerstring;
	Bitmap myBitmap;
	byte[] contentPicture;
	// byte[] contentAudio;
	byte[] contentVideo;
	// /////
	EditText et_comment;
	Dialog dialog;

	Intent intent;
	String objectId;

	ProgressDialog progressdialog;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.menualpage_post);
		init();

		intent = getIntent();
		objectId = intent.getStringExtra("objectId");
	}

	public void complete(View v) {

		final String comment = et_comment.getText().toString();

		if (comment.equals("") || comment == null) {

			Toast.makeText(getApplicationContext(), "메뉴얼을 적어주세요!", 3000).show();

		} else {

			progressdialog = ProgressDialog.show(MenualActivity_Post.this, "",
					"메뉴얼 저장 중...");

			final ParseFile contentPicFile = new ParseFile("contentPic.png",
					contentPicture);
			contentPicFile.saveInBackground(new SaveCallback() {

				@Override
				public void done(ParseException e) {
					// TODO Auto-generated method stub

					final ParseFile contentVideoFile = new ParseFile(
							"video.MPEG_4", contentVideo);
					contentVideoFile.saveInBackground(new SaveCallback() {

						@Override
						public void done(ParseException e) {

							ParseQuery query = new ParseQuery("TodoList");
							query.whereEqualTo("objectId", objectId);
							query.getFirstInBackground(new GetCallback() {

								@Override
								public void done(ParseObject object,
										ParseException e) {
									// TODO Auto-generated method stub
									object.put("comment", comment);
									object.put("contentPic", contentPicFile);
									object.put("contentVideo", contentVideoFile);
									object.saveInBackground(new SaveCallback() {

										@Override
										public void done(ParseException e) {
											// TODO Auto-generated method stub
											progressdialog.dismiss();
											Toast.makeText(
													getApplicationContext(),
													"메뉴얼 저장 완료!",
													Toast.LENGTH_LONG).show();
											finish();
										}
									});
								}
							});

						}
					});
				}
			});

		}

	}

	public void media(View v) {

		switch (v.getId()) {
		case R.id.imageView_contentPic:
			// Toast.makeText(getApplicationContext(), "pic", 3000).show();

			Intent intentCamera = new Intent();
			intentCamera.setType("image/*");
			intentCamera.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(
					Intent.createChooser(intentCamera, "Select Picture"),
					SELECT_PICTURE);
			break;

		case R.id.media_video:
			// Toast.makeText(getApplicationContext(), "video" + "",
			// 3000).show();
			Intent intent2 = new Intent(getApplicationContext(),
					MenualActivity_Record_Video.class);
			startActivityForResult(intent2, SELECT_VIDEO);
			break;

		}

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {

			switch (requestCode) {
			case SELECT_PICTURE:
				Uri selectedImageUri = data.getData();

				// OI FILE Manager
				filemanagerstring = selectedImageUri.getPath();

				// MEDIA GALLERY
				selectedImagePath = getPath(selectedImageUri);

				// DEBUG PURPOSE - you can delete this if you want

				// NOW WE HAVE OUR WANTED STRING
				if (selectedImagePath != null) {

					BitmapToBytes(selectedImagePath);

				} else {

					BitmapToBytes(filemanagerstring);

				}
				break;
			// case SELECT_AUDIO:
			// Toast.makeText(getApplicationContext(), "from audio", 3000)
			// .show();
			// contentAudio = data.getByteArrayExtra("audio");
			// break;
			case SELECT_VIDEO:
				Toast.makeText(getApplicationContext(), "비디오가 추가 됐습니다.", 3000)
						.show();
				contentVideo = data.getByteArrayExtra("video");
				break;

			}

		}

	}

	public void BitmapToBytes(String filename) {

		// Toast.makeText(getApplicationContext(),
		// "selectedImagePath is the right one for you!", 3000).show();

		myBitmap = BitmapFactory.decodeFile(filename);
		int width = myBitmap.getWidth();
		int height = myBitmap.getHeight();

		while (true) {
			width = (int) (width / 1.2);
			height = (int) (height / 1.2);

			if (width <= 400) {

				break;
			}

			// height = myBitmap.getHeight();
		}

		imageView_contentPic.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView_contentPic.setImageBitmap(myBitmap);

		myBitmap = Bitmap.createScaledBitmap(
				BitmapFactory.decodeFile(filename), width, height, false);
		ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
		myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream2);
		contentPicture = stream2.toByteArray();

	}

	// UPDATED!
	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		if (cursor != null) {
			// HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
			// THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} else
			return null;
	}

	public void init() {
		imageView_contentPic = (ImageView) findViewById(R.id.imageView_contentPic);
		et_comment = (EditText) findViewById(R.id.et_comment);

	}
}
