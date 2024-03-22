package demographic_data;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class another_residential_issue {
   
    	
    	 public static void executeResidentialIssue() {
    	
    	
        System.setProperty("webdriver.chrome.driver", "/home/shatam-system-i2/chromedriver");
        WebDriver driver = new ChromeDriver();

        try {
        	
            driver.get("https://waterviewresidential.azurewebsites.net/");
            driver.manage().window().maximize();

           
            login(driver);

          
            if (driver.getCurrentUrl().equals("https://waterviewresidential.azurewebsites.net/")) 
            {
                System.out.println("Logged in successfully");
                navigateToMapPage(driver);
                
            }
            else 
            {
                System.out.println("Failed to log in");
                sendEmail("Status of residential login page", "Negative = Sorry, we are unable to login to the residential site", "akash.shatam@gmail.com");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    
    
    
    private static void login(WebDriver driver) throws InterruptedException 
    {
        driver.findElement(By.id("email")).sendKeys("pallavi.shatam@gmail.com");
        Thread.sleep(1000);
        driver.findElement(By.id("pass")).sendKeys("pallavi#12");
        Thread.sleep(1000);
        driver.findElement(By.id("SignIn")).click();
    }

    
    
    
    private static void navigateToMapPage(WebDriver driver) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"content\"]/div/a")));
        driver.findElement(By.xpath("//*[@id=\"content\"]/div/a")).click();
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Map page loading time: " + totalTime + " milliseconds");

        if (driver.getCurrentUrl().equals("https://waterviewresidential.azurewebsites.net/WaterDistrictImg")) {
            System.out.println("Map page loaded successfully");
        } else {
            System.out.println("Failed to load map page");
            sendEmail("Status of residential map page", "Negative = Map page not opening", "akash.shatam@gmail.com");
        }
    }

    private static void sendEmail(String subject, String body, String recipient) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "587");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.port", "587");

            Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("akashkumeriya25@gmail.com", "giiqvfbznbpgynmh");
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("akashkumeriya25@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    
    }
}

