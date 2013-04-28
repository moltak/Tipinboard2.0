package com.corping.taskboard;

import java.util.ArrayList;
import java.util.List;

import utils.LazyAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.corping.R;
import com.corping.menual.MenualActivity_Post;
import com.corping.menual.MenualActivity_Menual;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class TaskList_Fragment_Category extends Fragment {
	ListView MyList;
	// LazyAdapter adapter;
	ArrayList<String> items = new ArrayList<String>();
	ArrayList<String> objectIds = new ArrayList<String>();
	ArrayList<String> categories = new ArrayList<String>();

	LazyAdapter lazyadapter;

	TextView content, aaaa, tv_category;
	LinearLayout linear_bottom, linear_bottom_textfield;
	Button bt_insert_complete;
	ImageButton image_bt_insert;
	EditText et_insert;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Bundle bundle = getArguments();

		final String boardId = bundle.getString("boardId");
		final String category = bundle.getString("category");

		View view = inflater.inflate(R.layout.tasklist_fragment_category, null, false);
		
		MyList = (ListView) view.findViewById(R.id.listView);
		tv_category = (TextView) view.findViewById(R.id.tv_category);
		tv_category.setText(category);

		ParseQuery query = new ParseQuery("TodoList");
		query.whereEqualTo("boardId", boardId);
		query.whereEqualTo("category", category);
		query.findInBackground(new FindCallback() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				for (ParseObject obj : objects) {
					String item = obj.getString("item");
					items.add(item);
					String objectId = obj.getObjectId();
					objectIds.add(objectId);
					String names = obj.getString("name");
					categories.add(names);
				}
				if(getActivity() != null) {
					lazyadapter = new LazyAdapter(getActivity(), items, objectIds,categories);
					MyList.setAdapter(lazyadapter);
				}
			}
		});

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
				Toast.makeText(getActivity(), objId.getText().toString(), 3000)
						.show();

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
							MenualActivity_Post.class);
					intent.putExtra("objectId", objId.getText().toString());
					startActivity(intent);

				} else {
					Intent intent2 = new Intent(getActivity(),
							MenualActivity_Menual.class);
					intent2.putExtra("objectId", objId.getText().toString());
					startActivity(intent2);
					Toast.makeText(getActivity(), "Under construction", 3000)
							.show();

				}

			}

		});

		MyList.setOnLongClickListener(new OnLongClickListener() {

			public boolean onLongClick(View view) {
				// TODO Auto-generated method stub
				TextView objId = (TextView) view.findViewById(R.id.obejctId);
				String objectId = objId.getText().toString();
				Toast.makeText(getActivity(), objId.getText().toString(), 3000)
						.show();
				return false;
			}
		});

	}

	public void insertTask(String boardId, String username, String item) {

		ParseObject insertTask = new ParseObject("TodoList");
		insertTask.put("boardId", boardId);
		insertTask.put("username", username);
		insertTask.put("item", item);
		insertTask.saveInBackground();

	}

}
