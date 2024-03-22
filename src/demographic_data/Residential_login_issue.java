package demographic_data;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Residential_login_issue {
	
	public static void executeResidentialIssue()  
	{
	
	 System.setProperty("webdriver.chrome.driver", "/home/shatam-system-i2/chromedriver");
		
		WebDriver driver = new ChromeDriver();
		

          driver.get("https://waterviewresidential.azurewebsites.net/");
          
          driver.manage().window().maximize(); 
          
        

	//	driver.findElement(By.id("email")).sendKeys("pallavi.shatam@gmail.com");
		
		
	//		Thread.sleep(1000);
		
        
     //   driver.findElement(By.id("pass")).sendKeys("pallavi#12");
       
	//		Thread.sleep(1000);
		
         
         
			
		    	 WebElement emailInput = driver.findElement(By.id("email"));
		        emailInput.sendKeys("pallavi.shatam@gmail.com");

		        
		        
		        WebDriverWait wait = new WebDriverWait(driver, 10);

		        // Wait until password input field is visible
		        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pass")));
		        passwordInput.sendKeys("pallavi#12");

			
         
                long startTime = System.currentTimeMillis(); 
		   
                driver.findElement(By.id("SignIn")).click();
          
       //.................................................................................         
             
                long timeoutMs = 10000; // 10 seconds
                String expectedUrl = "https://waterviewresidential.azurewebsites.net/";
                
                String currentUrl = driver.getCurrentUrl();
                while (!currentUrl.equals(expectedUrl)) {
                    
                    if (System.currentTimeMillis() - startTime > timeoutMs) {
                        System.out.println("Timeout occurred. Login process took longer than expected.");
                        break;
                    }

                    // Poll for the current URL every 500 milliseconds
                    try {
                        Thread.sleep(500); // Sleep for 500 milliseconds
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    currentUrl = driver.getCurrentUrl();
                }
          //............................................................................
                
                
      		long endTime = System.currentTimeMillis();

    	    long totalTime = endTime - startTime;
    			

    	   System.out.println("after sign in, home page Loading time is: " + totalTime + "  milliseconds");
            
          
    	 
		       
		 //      String expectedUrl = "https://waterviewresidential.azurewebsites.net/";
		       
		  //     String ActualTitle = driver.getCurrentUrl();
		     
		      
		       
		       
		       
		    //   System.out.println("expectedUrl===="+expectedUrl);
		    //   System.out.println("ActualTitle===="+ActualTitle);
		       
				
				
				if (expectedUrl.equals(currentUrl))
				{
					System.out.println("log in page open properly");
					
//						try {
//							Thread.sleep(2000);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
					
					 
					
						
						
						 WebDriverWait wait1 = new WebDriverWait(driver, 10);

					        // Wait until the link is visible
					        WebElement link = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='content']/div/a")));

					        
					        
					        
					        long startTime2 = System.currentTimeMillis();
					        
					        
					        // Click the link
					        link.click();

					        // Wait until the link disappears (invisibility of the element)
					        wait1.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='content']/div/a")));

						
						
						
						
			    	
			    	   
			    	//   driver.findElement(By.xpath("//*[@id=\"content\"]/div/a")).click();
			  	  
			    	   long endTime2 = System.currentTimeMillis();

			   	       long totalTime2 = endTime2 - startTime2;
			   	       
			   	  
			   	       
			   	 
			   	   
			   	       
			   	    String ActualTitle_dashboard = "https://waterviewresidential.azurewebsites.net/WaterDistrictImg";
			   
				
					String expectedUrl11 = driver.getCurrentUrl();
			   	       
			   	       
			   			
					
			   	       System.out.println("map page Loading Time is: " + totalTime2 + "  milliseconds");
					 
					 
					 
					 
						if (expectedUrl11.equals(ActualTitle_dashboard))
						{
							System.out.println("map page loaded successfully");
							
							
							//	Thread.sleep(2000);
							
						}
						else
						{
							System.out.println("still map page not open");
							
							Properties props = new Properties();
				   			props.put("mail.smtp.host", "smtp.gmail.com");
				   			props.put("mail.smtp.socketFactory.port", "587");
				   			props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
				   			props.put("mail.smtp.auth", "true");
				   			props.put("smtp.STARTTLS.enable","true");
				   			props.put("smtp.starttls.enable","true");
				   			
				   		  props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
				   			
				   			props.put("mail.smtp.starttls.enable", "true");
				   			props.put("mail.smtp.port", "587");
				   					Session session = Session.getDefaultInstance(props,
				   							new javax.mail.Authenticator() {
				   								protected PasswordAuthentication getPasswordAuthentication() {
				   								return new PasswordAuthentication("akashkumeriya25@gmail.com", "giiqvfbznbpgynmh");
				   								}
				   							});
				   					
				   					try {
				   						Message message = new MimeMessage(session);
				   						message.setFrom(new InternetAddress("akashkumeriya25@gmail.com"));
				   						message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("akash.shatam@gmail.com"));
				   						
				   						
				   						message.setSubject("Status of residential map page");
				   						BodyPart messageBodyPart1 = new MimeBodyPart();
				   						messageBodyPart1.setText("Negative = map page not opening");
				   											
				   						Multipart multipart = new MimeMultipart();
				   					//	multipart.addBodyPart(messageBodyPart2);
				   						multipart.addBodyPart(messageBodyPart1);
				   						message.setContent(multipart);
				   						
				   						Transport.send(message); 
				   						System.out.println("=====Email Sent for map page loading failed=====");
				   					  } 
				   					catch (MessagingException e) 
				   					{

				   						throw new RuntimeException(e);

				   					}
						}
							
							
							
						
					 
					 
					 
				}
				else
				{
					System.out.println("still log in page not open");
					
					
					Properties props = new Properties();
		   			props.put("mail.smtp.host", "smtp.gmail.com");
		   			props.put("mail.smtp.socketFactory.port", "587");
		   			props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		   			props.put("mail.smtp.auth", "true");
		   			props.put("smtp.STARTTLS.enable","true");
		   			props.put("smtp.starttls.enable","true");
		   			
		   		  props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
		   			
		   			props.put("mail.smtp.starttls.enable", "true");
		   			props.put("mail.smtp.port", "587");
		   					Session session = Session.getDefaultInstance(props,
		   							new javax.mail.Authenticator() {
		   								protected PasswordAuthentication getPasswordAuthentication() {
		   								return new PasswordAuthentication("akashkumeriya25@gmail.com", "giiqvfbznbpgynmh");
		   								}
		   							});
		   					
		   					try {
		   						Message message = new MimeMessage(session);
		   						message.setFrom(new InternetAddress("akashkumeriya25@gmail.com"));
		   						message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("akash.shatam@gmail.com"));
		   						
		   						
		   						message.setSubject("Status of residential login page");
		   						BodyPart messageBodyPart1 = new MimeBodyPart();
		   						messageBodyPart1.setText("Negative = sorry....we are unable to login residential");
		   											
		   						Multipart multipart = new MimeMultipart();
		   					//	multipart.addBodyPart(messageBodyPart2);
		   						multipart.addBodyPart(messageBodyPart1);
		   						message.setContent(multipart);
		   						
		   						Transport.send(message); 
		   						System.out.println("=====Email Sent for login failed=====");
		   					  } 
		   					catch (MessagingException e) 
		   					{

		   						throw new RuntimeException(e);

		   					}
				}
				
				
				 WebDriverWait wait2 = new WebDriverWait(driver, 10);

			       
			        WebElement element = wait2.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='headerNav']/nav/div/div[2]/div/a")));

			      
			        element.click();
				
				
				
				driver.quit();
				System.out.println("execution terminated successfully");
				
				
				
				
				
				Properties props = new Properties();
	   			props.put("mail.smtp.host", "smtp.gmail.com");
	   			props.put("mail.smtp.socketFactory.port", "587");
	   			props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
	   			props.put("mail.smtp.auth", "true");
	   			props.put("smtp.STARTTLS.enable","true");
	   			props.put("smtp.starttls.enable","true");
	   			
	   		  props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
	   			
	   			props.put("mail.smtp.starttls.enable", "true");
	   			props.put("mail.smtp.port", "587");
	   					Session session = Session.getDefaultInstance(props,
	   							new javax.mail.Authenticator() {
	   								protected PasswordAuthentication getPasswordAuthentication() {
	   								return new PasswordAuthentication("akashkumeriya25@gmail.com", "giiqvfbznbpgynmh");
	   								}
	   							});
	   					
	   					try {
	   						Message message = new MimeMessage(session);
	   						message.setFrom(new InternetAddress("akashkumeriya25@gmail.com"));
	   						message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("akash.shatam@gmail.com"));
	   						
	   						
	   						message.setSubject("Status of residential code execution");
	   						BodyPart messageBodyPart1 = new MimeBodyPart();
	   						messageBodyPart1.setText("execution done successfully");
	   											
	   						Multipart multipart = new MimeMultipart();
	   					//	multipart.addBodyPart(messageBodyPart2);
	   						multipart.addBodyPart(messageBodyPart1);
	   						message.setContent(multipart);
	   						
	   						Transport.send(message); 
	   						System.out.println("=====Email Sent for each iteration=====");
	   						System.out.println("-------------------------");
	   					  } 
	   					catch (MessagingException e) 
	   					{

	   						throw new RuntimeException(e);

	   					}
				
				
				
				
				
				
	}
	
	
	

}
