package mobcom.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseW 
{
	private String date;
	private String day;
	private ArrayList<String> data;
	private ArrayList<String> locD;
	
	public ParseW()
	{
		locD = new ArrayList<String>();
		data = new ArrayList<String>();		
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

	public String search(String pattern, String text)
	{				
		// Create a Pattern object
	  	Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
		
	  	// Now create matcher object.
	  	Matcher m = r.matcher(text);
	  	if(m.find())
	  	{	  		
	  		return m.group(1);  
	  	}
	  	return "nomatch";
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
