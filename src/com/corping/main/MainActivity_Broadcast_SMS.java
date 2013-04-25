package com.corping.main;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;


// SMS로 받은 인증번호가 기존에 생성된 인증번호가 일치할 경우, MainActivity_GetStarted_Verified로 인텐트를 보냄.

public class MainActivity_Broadcast_SMS extends BroadcastReceiver {
	String username, phone, verfication_number;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub


		Intent sendIntent = new Intent(context, MainActivity_GetStarted_Verified.class);

		Bundle bundle = intent.getExtras();
		Object messages[] = (Object[]) bundle.get("pdus");
		SmsMessage smsMessages[] = new SmsMessage[messages.length];

		for (int i = 0; i < messages.length; i++) {

			smsMessages[i] = SmsMessage.createFromPdu((byte[]) messages[i]);

		}

		String message = smsMessages[0].getMessageBody().toString();
		String number = smsMessages[0].getOriginatingAddress();

		sendIntent.putExtra("verification_message", message);
		sendIntent.putExtra("verification_num",
				intent.getStringExtra("verification_num"));
		sendIntent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
		sendIntent.addFlags(intent.FLAG_ACTIVITY_NO_ANIMATION);

		
		SharedPreferences myPref = context.getSharedPreferences("myPref", Activity.MODE_WORLD_WRITEABLE);
		if (myPref != null && myPref.contains("verfication_number")) {
			verfication_number = myPref.getString("verfication_number", "");
			phone = myPref.getString("phone", "");
		}
		
		
		if(verfication_number.equals(message)){
			
			context.startActivity(sendIntent);
			
		}
		
	}

}
