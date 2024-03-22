
package demographic_data;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class schedualr_code {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        System.out.println("Program execution will start soon...");

        long sixHours = 1 * 60 * 60 * 1000; 
        long initDelay = getTimeMillis("15:39:00") - System.currentTimeMillis();

        
     //   long sixHours = 2 * 60 * 1000; // 2 minutes in milliseconds
    //    long initDelay = getTimeMillis("11:09:00") - System.currentTimeMillis(); 

        
        
       
        initDelay = initDelay > 0 ? initDelay : sixHours + initDelay;

        // Schedule the task to run every 6 hours
        scheduler.scheduleAtFixedRate(new YourTask(), initDelay, sixHours, TimeUnit.MILLISECONDS);
    }

    private static long getTimeMillis(String time) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");

            // Combine current date with the specified time
            Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);

            return curDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

class YourTask implements Runnable {
    @Override
    public void run() {
        // Put your code that needs to be executed every 6 hours here
        System.out.println("Executing task...");
        // Call your class directly here
       
			
				Residential_login_issue.executeResidentialIssue();
			
		
       // tm.methodToExecute(); // Assuming you have a method to execute in tonemerge
    }
}
