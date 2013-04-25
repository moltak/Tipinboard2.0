package com.corping.taskboard;

import java.util.ArrayList;
import java.util.List;

import utils.LazyAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.corping.R;
import com.corping.manual.MediaActivity_Post;
import com.corping.manual.MediaActivity_Timeline;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@TargetApi(8)
public class TaskList_Fragment_Member extends Fragment {
	ListView MyList;
	ArrayList<String> items = new ArrayList<String>();
	ArrayList<String> objectIds = new ArrayList<String>();
	ArrayList<String> categories = new ArrayList<String>();

	LazyAdapter lazyadapter;
	TextView content, aaaa, tv_username, tv_user;
	LinearLayout linear_bottom, linear_bottom_textfield;
	ImageView bt_insert_complete;
	ImageButton image_bt_insert;
	EditText et_insert;
	private Context MyFragment;
	String boardId;
	CharSequence[] categoryCs;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Bundle bundle = getArguments();

		boardId = bundle.getString("boardId");
		categoryCs = bundle.getCharSequenceArray("categoryCS");
		final String userName = bundle.getString("username");
		final String name = bundle.getString("name");

		View view = inflater.inflate(R.layout.fragment, null, false);

		MyList = (ListView) view.findViewById(R.id.listView);
		tv_username = (TextView) view.findViewById(R.id.tv_username);
		linear_bottom = (LinearLayout) view.findViewById(R.id.linear_bottom);
		linear_bottom_textfield = (LinearLayout) view
				.findViewById(R.id.linear_bottom_textfield);
		et_insert = (EditText) view.findViewById(R.id.et_insert);
		image_bt_insert = (ImageButton) view.findViewById(R.id.image_bt_insert);
		bt_insert_complete = (ImageView) view
				.findViewById(R.id.bt_insert_complete);

		image_bt_insert.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				linear_bottom.setVisibility(View.GONE);
				linear_bottom_textfield.setVisibility(View.VISIBLE);
			}
		});

		bt_insert_complete.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				String item = et_insert.getText().toString();
				items.add(item);
				objectIds.add("");
				categories.add("미지정");
				linear_bottom_textfield.setVisibility(View.GONE);
				linear_bottom.setVisibility(View.VISIBLE);
				lazyadapter.notifyDataSetChanged();
				insertTask(boardId, userName, item, name);
				et_insert.setText("");
				InputMethodManager imm = (InputMethodManager) v.getContext()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

			}

		});

		String usernum = bundle.getString("usernum");


		if (usernum.equals("1")) {

			linear_bottom.setVisibility(View.VISIBLE);

		}

		tv_username.setText(name);

		ParseQuery query = new ParseQuery("TodoList");
		query.whereEqualTo("boardId", boardId);
		query.whereEqualTo("username", userName);
		query.findInBackground(new FindCallback() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				// TODO Auto-generated method stub
				for (ParseObject obj : objects) {

					String item = obj.getString("item");
					items.add(item);

					String objectId = obj.getObjectId();
					objectIds.add(objectId);

					String category = obj.getString("category");
					categories.add(category);

				}
				// lazyadapter = new LazyAdapter(getActivity(), items,
				// objectIds, categories);
				lazyadapter = new LazyAdapter(getActivity(), items, objectIds,
						categories);
				MyList.setAdapter(lazyadapter);
			}
		});

		init();
		// adapter.notifyDataSetChanged();
		return view;
	}

	public void init() {

		MyList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				// TODO Auto-generated method stub

				// Toast.makeText(getActivity(), position + "", 3000).show();

				TextView objId = (TextView) view.findViewById(R.id.obejctId);
				String objectId = objId.getText().toString();

				ParseQuery query = new ParseQuery("TodoList");
				query.whereEqualTo("objectId", objId.getText().toString());
				String comment = null;
				ParseObject obj;
				try {
					obj = query.getFirst();
					comment = obj.getString("comment");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (comment == null) {

					Intent intent = new Intent(getActivity(),
							MediaActivity_Post.class);
					intent.putExtra("objectId", objId.getText().toString());
					startActivity(intent);
					Toast.makeText(getActivity(), "메뉴얼을 기제해주세요.", 3000)
					.show();
				} else {
					Intent intent2 = new Intent(getActivity(),
							MediaActivity_Timeline.class);
					intent2.putExtra("objectId", objId.getText().toString());
					startActivity(intent2);
					Toast.makeText(getActivity(), "메뉴얼 존재", 3000)
							.show();

				}

			}

		});

		MyList.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View view,
					int arg2, long arg3) {
				TextView objId = (TextView) view.findViewById(R.id.obejctId);
				String objectId = objId.getText().toString();
				// Toast.makeText(getActivity(), objectId, 3000).show();
				Dialog dialog = onCreateDialogSingleChoice(objectId);
				// onCreateDialogSingleChoice();
				dialog.show();
				return true;
			}
		});

	}

	public Dialog onCreateDialogSingleChoice(final String objectId) {

		
		
		
		// Initialize the Alert Dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		// Set the dialog title
		builder.setTitle("카테고리를 골라주세요!").setSingleChoiceItems(categoryCs, 1,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									final int which) {
								// TODO Auto-generated method stub
								
								CharSequence category = null;
								
								switch (which) {

								case 0:

									category = categoryCs[which];
									break;
								case 1:
									category = categoryCs[which];
									break;
								case 2:
									category = categoryCs[which];
									break;
								case 3:
									category = categoryCs[which];
									break;
								default:
									category = categoryCs[which];
									break;
								}
								
								ParseQuery query = new ParseQuery("TodoList");
								query.whereEqualTo("objectId", objectId);
								query.getFirstInBackground(new GetCallback() {
									
									@Override
									public void done(ParseObject object, ParseException e) {
										
//										Toast.makeText(getActivity(), object.getString("category"), 3000).show();
										
										object.put("category", categoryCs[which]);
										object.saveInBackground();
										
									}
								});
							
								

							}
						})

				// Set the action buttons
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, final int id) {

						

					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

							}
						});

		return builder.create();
	}

	public void insertTask(String boardId, String username, String item,
			String name) {

		ParseObject insertTask = new ParseObject("TodoList");
		insertTask.put("boardId", boardId);
		insertTask.put("username", username);
		insertTask.put("item", item);
		insertTask.put("category", "미지정");
		insertTask.put("name", name);
		insertTask.saveInBackground();

	}

}
