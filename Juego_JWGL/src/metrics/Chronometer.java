package metrics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Chronometer{
    public long begin, end;
    private DateFormat formatter;
    private Date date;
 
    public Chronometer(){
    	start();
    	formatter = new SimpleDateFormat("mm:ss:SSS");
    }
    
    public void start(){
        begin = System.currentTimeMillis();
    }
 
    public void stop(){
        end = System.currentTimeMillis();
    }
 
    public long getMilliseconds() {
        return end - begin;
    }
 
    public int getSeconds() {
        return (int)((end - begin) / 1000);
    }
 
    public int getMinutes() {
        return (int)((end - begin) / 60000);
    }
 
    public int getHours() {
        return (int)((end - begin) / 3600000);
    }
    
    public String getTime(){
    	stop();
    	date = new Date(end - begin);
    	String time = formatter.format(date);
    	return time;
    }
}