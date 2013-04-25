package com.corping.taskboard;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.corping.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class TaskboardActivity_Create extends Activity {

	EditText et_members1, et_members2, et_members3, et_members4, et_todolist;
	EditText et_category1, et_category2, et_category3;

	/** Called when the activity is first created. */
	
	
	byte[] boardImageBytes;
	ProgressDialog progressdialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.taskboardpage_create);
		init();

	}

	public void init() {

		et_todolist = (EditText) findViewById(R.id.et_todolist);

		et_category1 = (EditText) findViewById(R.id.et_category1);
		et_category2 = (EditText) findViewById(R.id.et_category2);
		et_category3 = (EditText) findViewById(R.id.et_category3);

		et_members1 = (EditText) findViewById(R.id.et_members1);
		et_members2 = (EditText) findViewById(R.id.et_members2);
		et_members3 = (EditText) findViewById(R.id.et_members3);
		et_members4 = (EditText) findViewById(R.id.et_members4);
	}

	public void boardImage(View v) {
		Resources res = getResources();
		Drawable boardImage = null;
		switch (v.getId()) {
		case R.id.image1:
			Toast.makeText(getApplicationContext(), "image1", 3000).show();
			boardImage = res.getDrawable(R.drawable.ic_board_cafe_l);
			
			break;
		case R.id.image2:
			Toast.makeText(getApplicationContext(), "image2", 3000).show();
			boardImage = res.getDrawable(R.drawable.ic_board_company_l);
			break;
		case R.id.image3:
			Toast.makeText(getApplicationContext(), "image3", 3000).show();
			boardImage = res.getDrawable(R.drawable.ic_board_flower_l);
			break;
		case R.id.image4:
			Toast.makeText(getApplicationContext(), "image4", 3000).show();
			boardImage = res.getDrawable(R.drawable.ic_board_spoon_l);
			break;
		default:
			break;
		}
		
		Bitmap bitmap = ((BitmapDrawable)boardImage).getBitmap();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		boardImageBytes = stream.toByteArray();

	}

	public void categorySave(View v) {

		switch (v.getId()) {
		case R.id.category1:
			LinearLayout layout_category2 = (LinearLayout) findViewById(R.id.layout_category2);

			layout_category2.setVisibility(View.VISIBLE);

			break;
		case R.id.category2:
			LinearLayout layout_category3 = (LinearLayout) findViewById(R.id.layout_category3);
			layout_category3.setVisibility(View.VISIBLE);

			break;
		case R.id.category3:
			Toast.makeText(getApplicationContext(), "최대 3개까지 추가할 수 있습니다.", 3000)
					.show();

			break;
		}

	}

	public void memberSave(View v) {

		switch (v.getId()) {
		case R.id.member1:
			LinearLayout layout_member2 = (LinearLayout) findViewById(R.id.layout_member2);

			layout_member2.setVisibility(View.VISIBLE);

			break;
		case R.id.member2:
			LinearLayout layout_member3 = (LinearLayout) findViewById(R.id.layout_member3);
			layout_member3.setVisibility(View.VISIBLE);

			break;
		case R.id.member3:
			LinearLayout layout_member4 = (LinearLayout) findViewById(R.id.layout_member4);
			layout_member4.setVisibility(View.VISIBLE);
			break;
		case R.id.member4:
			// LinearLayout layout_member2 = (LinearLayout)
			// findViewById(R.id.layout_member2);
			Toast.makeText(getApplicationContext(), "최대 4명까지 추가할 수 있습니다.", 3000)
					.show();

			break;
		}

	}

	public void save(View v) {

		progressdialog = ProgressDialog.show(TaskboardActivity_Create.this, "",
				"보드 저장 중...");

		final ParseUser user = ParseUser.getCurrentUser();

		final String todolist = et_todolist.getText().toString();
		String member1 = et_members1.getText().toString();
		String member2 = et_members2.getText().toString();
		String member3 = et_members3.getText().toString();
		String member4 = et_members4.getText().toString();

		final String category1 = et_category1.getText().toString();
		final String category2 = et_category2.getText().toString();
		final String category3 = et_category3.getText().toString();

		ArrayList<String> members = new ArrayList<String>();
		members.add(user.getUsername());
		members.add(member1);
		members.add(member2);
		members.add(member3);
		members.add(member4);

		ParseQuery query = ParseUser.getQuery();
		query.whereContainedIn("username", members);

		query.findInBackground(new FindCallback() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				// TODO Auto-generated method stub
				final ArrayList<String> usernames = new ArrayList<String>();
				final ArrayList<String> names = new ArrayList<String>();
				final ArrayList<String> categories = new ArrayList<String>();

				categories.add("미지정");
				categories.add(category1);
				categories.add(category2);
				categories.add(category3);

				for (ParseObject obj : objects) {

					usernames.add(((ParseUser) obj).getUsername());

					names.add(obj.getString("name"));

				}
				
				final ParseFile boardImagefile = new ParseFile("boardImage.png", boardImageBytes);
				boardImagefile.saveInBackground(new SaveCallback() {
					
					@Override
					public void done(ParseException e) {
						// TODO Auto-generated method stub
						final ParseObject taskboard = new ParseObject("TaskBoard");
						taskboard.put("boardTitle", todolist);
						taskboard.put("adminName", user.getUsername());
						taskboard.put("category", categories);
						taskboard.put("usernames", usernames);
						taskboard.put("names", names);
						taskboard.put("boardImage", boardImagefile);
						taskboard.saveInBackground(new SaveCallback() {

							@Override
							public void done(ParseException e) {
								// TODO Auto-generated method stub
								finish();
								progressdialog.show();
								Toast.makeText(getApplicationContext(), "보드 저장 완료!",
										Toast.LENGTH_LONG).show();
							}
						});
					}
				});
				
			
			}
		});

	}
}
