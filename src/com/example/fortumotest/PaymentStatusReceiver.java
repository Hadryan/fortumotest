package com.example.fortumotest;

import com.fortumo.android.Fortumo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class PaymentStatusReceiver extends BroadcastReceiver {
	private static String TAG = "PaymentStatusReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle extras = intent.getExtras();
		int billingStatus = extras.getInt("billing_status");
		if (billingStatus == Fortumo.MESSAGE_STATUS_BILLED) {
			// Extras names are not documented. We need to get service id here
			// to distinguish payments
			int credits = Integer.parseInt(intent.getStringExtra("credit_amount"));
			Log.d(TAG, "Credits = " + credits);
		}
	}
}
