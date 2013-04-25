package com.corping.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.corping.R;


// 5자리의 승인번호를 만들고, SMS 메시지를 통해 유저의 폰으로 메시지를 보냄.
// 만약 유저의 폰 번호, 승인번호가 일치하면 Broadcast_SMS로 인텐트를 보냄.


@TargetApi(9)
public class MainActivity_GetStarted extends Activity {

	String password, email, phone_no;

	EditText editText_email, editText_password, et_phone;
	Bundle bundle = new Bundle();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_getstarted);

		init();

	}


	public void verification(View v) {


		phone_no = et_phone.getText().toString();


		char[] temp = phone_no.toCharArray();

		String number = "";
		ArrayList<Character> num = new ArrayList<Character>();

		for (int i = 0; i < temp.length; i++) {

			if (temp[i] == '-') {

//				Toast.makeText(getApplicationContext(), "- " + "at " + i, 3000)
//						.show();

			} else {

				num.add(temp[i]);

			}

		}

		for (Character i : num) {

			number += i;

		}


		int[] verification_no = new int[5];
		String verification_number = "";
		for (int i = 0; i < 5; i++) {

			int tmp = (int) (Math.random() * 10);

			verification_no[i] = tmp;

			verification_number += verification_no[i];
		}


		try {
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(phone_no, null, verification_number,
					null, null);
			Toast.makeText(getApplicationContext(), "잠시만 기다려 주세요.",
					Toast.LENGTH_LONG).show();
			
			
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"죄송합니다. 다시 시도 해주세요.", Toast.LENGTH_LONG)
					.show();
			e.printStackTrace();
		}

		

		SharedPreferences myPref = getSharedPreferences("myPref",
				Activity.MODE_WORLD_WRITEABLE);

		SharedPreferences.Editor myEditor = myPref.edit();
		myEditor.putString("verfication_number", verification_number);
		myEditor.putString("phone", number);
		myEditor.commit();

	}

	public void init() {

		et_phone = (EditText) findViewById(R.id.et_phone);

	}

}