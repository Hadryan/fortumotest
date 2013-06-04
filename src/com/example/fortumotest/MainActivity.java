package com.example.fortumotest;

import com.fortumo.android.Fortumo;
import com.fortumo.android.PaymentActivity;
import com.fortumo.android.PaymentRequestBuilder;
import com.fortumo.android.PaymentResponse;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends PaymentActivity {

	private static final String CONSUMABLE_SERVICE_ID = "3a1b1a49bf052fb45b4c87c0b8cbdd37";
	private static final String NONCONSUMABLE_SERVICE_ID = "ec59b524b3cebaabe552629b56ac36a7";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Fortumo.enablePaymentBroadcast(this, Manifest.permission.PAYMENT_BROADCAST_PERMISSION);
		
		// Restoring non-consumable items
		new AsyncTask<Void, Void, Integer>() {			

			@Override
			protected Integer doInBackground(Void... params) {
				int medalStatus = Fortumo.getNonConsumablePaymentStatus(MainActivity.this, NONCONSUMABLE_SERVICE_ID, 
						"32640d4a75f36981cd132f952c64e9f1", "Medal");
				return medalStatus;
			}
			
			@Override
			protected void onPostExecute(Integer result) {
				if (result.intValue() == Fortumo.MESSAGE_STATUS_BILLED) {
					// We can refresh our UI here to indicate that "Medal" is billed
					// For example unlock some additional level in game
				}
			}
			
		}.execute();
		
		// TODO Restoring consumable items
		// Depends on implementation, we can just save this info on device or send some request to our server
		
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				PaymentRequestBuilder builder = new PaymentRequestBuilder();
				builder.setService(NONCONSUMABLE_SERVICE_ID, "32640d4a75f36981cd132f952c64e9f1");
		        builder.setDisplayString("Buy Medal");    		// shown on user receipt
		        builder.setProductName("Medal");  				// non-consumable purchases are restored using this value
		        builder.setConsumable(false);              		// non-consumable items can be later restored
		        builder.setIcon(R.drawable.medal);
		        makePayment(builder.build());
			}
		});
		
		findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				PaymentRequestBuilder builder = new PaymentRequestBuilder();
		        builder.setService(CONSUMABLE_SERVICE_ID, "849a7c5d90ea88bbcc3988fafbbe4371");
		        builder.setDisplayString("Buy golden eggs");    // shown on user receipt
		        builder.setProductName("GoldenEggs");  			// non-consumable purchases are restored using this value
		        builder.setConsumable(true);              		// non-consumable items can be later restored
		        builder.setIcon(R.drawable.gold);
		        makePayment(builder.build());
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	protected void onPaymentCanceled(PaymentResponse arg0) {
		super.onPaymentCanceled(arg0);
	}

	@Override
	protected void onPaymentFailed(PaymentResponse arg0) {
		super.onPaymentFailed(arg0);
	}

	@Override
	protected void onPaymentPending(PaymentResponse arg0) {
		super.onPaymentPending(arg0);
	}

	@Override
	protected void onPaymentSuccess(PaymentResponse paymentResponse) {
		super.onPaymentSuccess(paymentResponse);
		if (paymentResponse.getServiceId().equals(CONSUMABLE_SERVICE_ID)) {
			int creditAmount = Integer.parseInt(paymentResponse.getCreditAmount());
			// Increase amount of credits related to this service (maybe coins in game)
			// TODO Save purchase data (on device or send request to our server)
		} else if (paymentResponse.getServiceId().equals(NONCONSUMABLE_SERVICE_ID)) {
			// Unlock functionality related to this service (maybe additional level in game)
		}
	}

}
