package com.abhi.location;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main20Activity extends ActionBarActivity implements OnClickListener {
	 Button sendBtn;
	   EditText txtphoneNo;
	   EditText txtMessage;
	   TextView lt, lg;
	   double l1,l2;
	  TextView et;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main20);
		Button b1= (Button) findViewById(R.id.button1);
        Button b2= (Button) findViewById(R.id.button2);       
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.button1:		startService(new Intent(getBaseContext(),service.class));
		break;
		case R.id.button2:		stopService(new Intent(getBaseContext(),service.class));

		}
	}
}
