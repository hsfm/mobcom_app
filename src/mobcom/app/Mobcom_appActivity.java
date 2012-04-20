package mobcom.app;

import java.io.BufferedReader;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

public class Mobcom_appActivity extends Activity 
{
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
        
        ParseW pw = new ParseW();
        
        TextView date = (TextView)findViewById(R.id.date);        
        TextView m1 = (TextView)findViewById(R.id.morningToday);         
        TextView n1 = (TextView)findViewById(R.id.noonToday);         
        TextView e1 = (TextView)findViewById(R.id.eveToday);
        
        TextView tmr = (TextView)findViewById(R.id.tomorrow);
        
        TextView m2 = (TextView)findViewById(R.id.mT);         
        TextView n2 = (TextView)findViewById(R.id.nT);         
        TextView e2 = (TextView)findViewById(R.id.eT);
        
        String t;
        try 
		{        	
			BufferedReader weather = pw.read("http://www.wetter.de/wettervorhersage/49-3240-50/wetter-fulda.html");
			BufferedReader loc = pw.read("http://www.infosniper.net");
			String line = loc.readLine();
			
			while(line != null)
			{
				t = pw.search("content-td2\">(.+?)<", line);
				if(!t.equals("nomatch"))
				{
					pw.setLoc(t);
				}
				line = loc.readLine();
			}			
			line = weather.readLine();
			while(line != null)
			{
				t = pw.search("temperature\">(\\d+?)&nbsp;&deg;C<", line);
				if(!t.equals("nomatch"))
				{
					pw.setData(t);
				}
				//pw.setTemp());
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
		 //pw.moveTempToData();
		 date.setText("Wetter in "+ pw.getLocAt(1) + " " + pw.getDate());
		 
		 m1.setText("Morgens: " + pw.getDataAt(1) + "°C");		 
		 n1.setText("Mittags: " + pw.getDataAt(2) + "°C");
		 e1.setText("Abends: " + pw.getDataAt(3) + "°C");
		 
		 pw.nextDate();
		 tmr.setText("Wetter in "+ pw.getLocAt(1) + " " + pw.getDate());
		 
		 m2.setText("Morgens: " + pw.getDataAt(5) + "°C");		 
		 n2.setText("Mittags: " + pw.getDataAt(6) + "°C");
		 e2.setText("Abends: " + pw.getDataAt(7) + "°C");
    }
}