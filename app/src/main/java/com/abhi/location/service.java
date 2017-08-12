package com.abhi.location;

import java.text.DateFormat;
import java.util.Date;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class service extends IntentService implements LocationListener,
GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener  {
	 private static final String TAG = "LocationActivity";
	    private static final long INTERVAL = 1000 * 60 * 2;
	    private static final long FastINTERVAL = 1000 * 60 +  1000 *30;
	    
	    Button btnFusedLocation;
	    TextView tvLocation ;
	    LocationRequest mLocationRequest;
	    GoogleApiClient mGoogleApiClient;
	    Location mCurrentLocation;
	    String mLastUpdateTime;
	    EditText et;
	    String lat,lng,phone;

	public service() {
	 super("service");
	// TODO Auto-generated constructor stub
}
public service(String name) {
	super(name);
	// TODO Auto-generated constructor stub
}
public void onDestroy(){
	Toast.makeText(this,"Service Destroyed", Toast.LENGTH_SHORT).show();
	stopLocationUpdates();
	super.onDestroy();
}

@Override
protected void onHandleIntent(Intent intent) {
	// TODO Auto-generated method stub
	
}
public IBinder oBind(Intent arg0){
return null;
}

public int onStartCommand(Intent intent, int flags, int startId){
    Log.d("Service", "In Service");

	Toast.makeText(this,"Service Started",Toast.LENGTH_SHORT).show();
	 createLocationRequest();
     mGoogleApiClient = new GoogleApiClient.Builder(this)
             .addApi(LocationServices.API)
             .addConnectionCallbacks(this)
             .addOnConnectionFailedListener(this)
             .build();
     		 mGoogleApiClient.connect();
	 phone="8341770470";
	 updateUI();
   	return START_STICKY;
}
protected void createLocationRequest() {
    mLocationRequest = new LocationRequest();
    mLocationRequest.setInterval(INTERVAL);
    mLocationRequest.setFastestInterval(FastINTERVAL);
    mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
}

protected void sendSMSMessage() {
    Log.i("Send SMS", "");
    String message = ""+lat+","+lng;
    String me="H: ";
    String st=me.concat(message);
     mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

    try {
       SmsManager smsManager = SmsManager.getDefault();
       smsManager.sendTextMessage(phone, null, st, null, null);
       Toast.makeText(getApplicationContext(),"Message sent",Toast.LENGTH_LONG).show();
       
      } catch (Exception e) {
       Toast.makeText(getApplicationContext(),"Failed, please try again.",Toast.LENGTH_LONG).show();
       e.printStackTrace();
    }
 }
@Override
public void onConnectionFailed(ConnectionResult connectionResult) {
	// TODO Auto-generated method stub
	  Log.d(TAG, "Connection failed: " + connectionResult.toString());
	  
}
@Override
public void onConnected(Bundle arg0) {
	// TODO Auto-generated method stub
	  Log.d(TAG, "onConnected - isConnected ...............: " + mGoogleApiClient.isConnected());
      startLocationUpdates();
  
}
@Override
public void onConnectionSuspended(int arg0) {
	// TODO Auto-generated method stub
	
}
@Override
public void onLocationChanged(Location location) {
    Log.d(TAG, "Firing onLocationChanged..............................................");
    mCurrentLocation = location; //dd
    mLastUpdateTime = "Time:"+DateFormat.getTimeInstance().format(new Date());
    updateUI();
}

private void updateUI() {
    Log.d(TAG, "UI update initiated .............");
    if (null != mCurrentLocation) {
         lat = String.valueOf(mCurrentLocation.getLatitude());
         lng = String.valueOf(mCurrentLocation.getLongitude());
//         sendSMSMessage();
  //       for(;;){
    //     Handler handler = new Handler(); 
 	  //   handler.postDelayed(new Runnable() { 
 	    //     public void run() { 
 	            sendSMSMessage();
 	        // } 
 	   // }, 30000);
 	     //}
     }        
     else {
        Log.d(TAG, "location is null ...............");
     }
}

protected void stopLocationUpdates() {
    LocationServices.FusedLocationApi.removeLocationUpdates(
            mGoogleApiClient, this);
    Log.d(TAG, "Location update stopped .......................");
}

protected void startLocationUpdates() {
    PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
            mGoogleApiClient, mLocationRequest, this);
    Log.d(TAG, "Location update started ..............: ");
}

private boolean isGooglePlayServicesAvailable() {
    int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
    if (ConnectionResult.SUCCESS == status) {
        return true;
    } else {
         return false;
    }
}

	
}
