package mobcom.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.Timestamp;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class Mobcom_appActivity extends Activity 
{	
	private TextView tmr;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	// disable all the nagging bs
    	StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
        .detectDiskReads()
        .detectDiskWrites()
        .penaltyLog()
        .build());
    	StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
        .detectLeakedSqlLiteObjects()
        //.detectLeakedClosableObjects()
        .penaltyLog()
        .penaltyDeath()
        .build());
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TextView date = (TextView)findViewById(R.id.date);        
        TextView m1 = (TextView)findViewById(R.id.morningToday);         
        TextView n1 = (TextView)findViewById(R.id.noonToday);         
        TextView e1 = (TextView)findViewById(R.id.eveToday);
        
        /*tmr = (TextView)findViewById(R.id.tomorrow);
        
        TextView m2 = (TextView)findViewById(R.id.mT);         
        TextView n2 = (TextView)findViewById(R.id.nT);         
        TextView e2 = (TextView)findViewById(R.id.eT);*/        
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() 
        {        	
            public void onLocationChanged(Location location) 
            {            	
            	// Called when a new location is found by the network location provider.
            	//makeUseOfNewLocation(location);
            	Toast.makeText(Mobcom_appActivity.this, "foobar", Toast.LENGTH_LONG).show();
            	tmr.setText("asd");
            }
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
        };
        // Register the listener with the Location Manager to receive location updates
        //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
        
        /* weather related stuff starts here */
        ParseW pw = new ParseW();                        
        try 
		{        				
			BufferedReader loc = pw.read("http://www.infosniper.net");
			String line = loc.readLine();			
			while(line != null)
			{
				pw.search("content-td2\">(.+?)<", line, true);				
				line = loc.readLine();
			}		
			String url = "http://www.google.com/ig/api?weather=" + pw.getLocAt(1);			
			//Toast.makeText(Mobcom_appActivity.this, url, Toast.LENGTH_LONG).show();
			BufferedReader weather = pw.read(url);
			line = weather.readLine();
			while(line != null)
			{
				// search for min temperature, this will be at index 0
				pw.search("<low data=\"(\\d+?)\"/>", line, false);				
				// search for max temperature, this will be at index 1
				pw.search("<high data=\"(\\d+?)\"/>", line, false);							
				// search for condition, this will be at index 2
				pw.search("<condition data=\"(.+?)\"/>", line, false);				
				line = weather.readLine();
			}
		 } 
		 catch (IOException e) 
		 {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 date.setText("Wetter in " + pw.getLocAt(1) + pw.getDate());		 
		 m1.setText("min. " + pw.convert(Integer.parseInt(pw.getDataAt(0))) + "°C | "); // min
		 n1.setText("max. " + pw.convert(Integer.parseInt(pw.getDataAt(1))) + "°C"); // max
		 e1.setText("Wetterlage: " + pw.getCond(pw.getDataAt(2))); // condition
		 
		 //pw.nextDate();
		 //tmr.setText("");
		 
		 /*m2.setText("");		 
		 n2.setText("");
		 e2.setText("");*/
    }        
}