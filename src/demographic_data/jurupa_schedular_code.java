package demographic_data;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.sql.SQLException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
public class jurupa_schedular_code {

    public static void main(String[] args) {
    	
    	
    	
       ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		
	   System.out.println("Program execution will start soon......");
		
		
		long oneDay = 24 * 60 * 60 * 1000;
		long initDelay  = getTimeMillis("15:55:00") - System.currentTimeMillis();
		
		
		initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;
		
		//scheduler.scheduleAtFixedRate( new Query_Database() , initDelay, oneDay, TimeUnit.MILLISECONDS);
		//scheduler.scheduleAtFixedRate( new waterview_merge() , initDelay, oneDay, TimeUnit.MILLISECONDS);
    	
		scheduler.scheduleAtFixedRate(() -> waterview_login(), initDelay, oneDay, TimeUnit.MILLISECONDS);

     
    }

    private static void addCookiesFromFile(WebDriver driver, String filePath) 
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("; ");
                String[] keyValue = parts[0].split("=");
                String domain = parts[1].substring("domain=".length());

                Cookie cookie = new Cookie.Builder(keyValue[0], keyValue[1])
                        .domain(domain)
                        .build();

                driver.manage().addCookie(cookie);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
   

	private static long getTimeMillis(String time) {
		try {
			
			DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
			
			Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
			
			return curDate.getTime();
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	
	
	
	public static void waterview_login()
	{
				
		System.setProperty("webdriver.chrome.driver", "/home/shatam-system-i2/chromedriver");
 
        WebDriver driver = new ChromeDriver();
      
        driver.get("https://waterviewportal.com/");
        
        driver.manage().window().maximize(); 
       
        addCookiesFromFile(driver, "/home/shatam-system-i2/Music/a1.txt");
     
        driver.get("https://waterviewportal.com/waterdistrict");
		
	}
    
    
    
    
}
