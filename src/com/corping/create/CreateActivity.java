
package com.corping.create;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.corping.R;
import com.parse.ParseObject;

public class CreateActivity extends Activity {

	
	EditText et_todolist, et_incharge;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.createpage);
		init();
	}
	
	public void init(){
		
		et_todolist = (EditText) findViewById(R.id.et_todolist);
		et_incharge = (EditText) findViewById(R.id.et_incharge);
	}
	
	public void save(View v){
		
		String todolist = et_todolist.getText().toString();
		String personInDuty = et_incharge.getText().toString();
		
		ParseObject taskboard = new ParseObject("TaskBoard");
		taskboard.put("boardTitle", todolist);
		taskboard.put("inDuty", personInDuty);
		taskboard.put("members", 3);
		taskboard.saveInBackground();
		
		
		
		Toast.makeText(getApplicationContext(), todolist +"/"+personInDuty, 3000).show();
		
		finish();
		
	}
}
