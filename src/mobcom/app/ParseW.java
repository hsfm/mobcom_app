package mobcom.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseW 
{
	private String date;
	private String day;
	private ArrayList<String> data;
	private ArrayList<String> locD;
	private HashMap<String, String> cond; // holds the different weather conditions and their respective translation
	
	public ParseW()
	{
		locD = new ArrayList<String>();
		data = new ArrayList<String>();
		cond = new HashMap<String, String>();
		cond.put("Chance of Rain", "Vereinzelt Regen");
		cond.put("Sunny", "Sonnig");
		cond.put("Mostly Sunny", "Meistens Sonnig");
		cond.put("Partly Cloudy", "Vereinzelt Bewölkt");
		cond.put("Mostly Cloudy", "Meistens Bewölkt");
		cond.put("Chance of Storm", "Vereinzelt Stürmisch");
		cond.put("Showers", "Wolkenbruch");
		cond.put("Rain", "Regen");
		cond.put("Chance of Snow", "Vereinzelt Schnee");
		cond.put("Cloudy", "Bewölkt");
		cond.put("Mist", "Nebel");
		cond.put("Storm", "Stürmisch");
		cond.put("Thunderstorm", "Gewitter");
		cond.put("Chance of TStorm", "Vereinzelt Gewitter");
		cond.put("Sleet", "Graupel");
		cond.put("Snow", "Schnee");
		cond.put("Icy", "Eisig");
		cond.put("Dust", "Staubig");
		cond.put("Fog", "Starker Nebel");
		cond.put("Smoke", "Dunstig");
		cond.put("Haze", "Start Dunstig");
		cond.put("Flurries", "Starker Schneefall");		
		cond.put("Clear", "Klar");
		setDate();
	}
	
	public BufferedReader read(String url) throws Exception
	{
		return new BufferedReader(new InputStreamReader(new URL(url).openStream()));
	}
	
	private void setDate()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Calendar cal = Calendar.getInstance();
		this.date = dateFormat.format(cal.getTime());
		
		DateFormat dayFormat = new SimpleDateFormat("dd");
		Calendar cal2 = Calendar.getInstance();
		this.day = dayFormat.format(cal2.getTime());
	}
	
	public String getDate()
	{
		return this.date;
	}
	
	/* loc determines if the result is written to locD/data */
	public void search(String pattern, String text, boolean loc)
	{				
		// Create a Pattern object
	  	Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
		
	  	// Now create matcher object.
	  	Matcher m = r.matcher(text);
	  	if(m.find())
	  	{	  		
	  		//return m.group(1);
	  		if(loc)
	  		{
	  			setLoc(m.group(1));
	  		}	  		
	  		else
	  		{
	  			setData(m.group(1));
	  		}
	  	}
	  	//return "nomatch";
	}	
	
	public String convert(float f)
	{
		//(°F  -  32)  x  5/9 = °C
		int t = (int)(f - 32) * 5/9;
		return Integer.toString(t);
	}
	
	public String getCond(String input)
	{
		return cond.get(input);
	}
	
	public void setData(String value)
	{
		this.data.add(value);
	}
	
	public void setLoc(String value)
	{
		this.locD.add(value);
	}
	
	public String getDataAt(int index)
	{
		return data.get(index);
	}
	
	public String getLocAt(int index)
	{
		return locD.get(index);
	}
	
	public void nextDate()
	{		
		Integer d = Integer.parseInt(day);
		d++;
		date = date.replaceAll("^\\d+", d.toString());
	}		
}
