package demographic_data;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.gargoylesoftware.htmlunit.javascript.host.Console;
public class temp_for_git {

	
	
      public static void main(String [] args) throws InterruptedException
	  {
    	  
    	  
    	  String expectedUrl_categories="http://www.nobiai.com/categories";
    		
    		String expectedUrl_search="http://www.nobiai.com/search";
    		
    		String expectedUrl_order_history="http://www.nobiai.com/order_history";
    	  
    	  
		
		      System.setProperty("webdriver.chrome.driver", "/home/shatam-system-i2/chromedriver");
		
	    	 WebDriver driver = new ChromeDriver();
		 
	
            driver.get("http://www.nobiai.com/");
            
            driver.manage().window().setSize(new Dimension(200,800));
            
            
          
            
         
			driver.findElement(By.xpath("//*[@id=\"form1\"]/div[3]/div[1]/div[1]/button/i")).click();
			
			
			Thread.sleep(3000);
			
			driver.findElement(By.xpath("//*[@id=\"menus\"]/div[2]/div[1]/a")).click();
			
			String actual_url = driver.getCurrentUrl();

			
			
			   if (expectedUrl_categories.equals(actual_url))
				{
				    System.out.println("after categoris click = page open successfully");
				}
			   else
			   {
				   System.out.println("after categoris click = page not open");
			   }
			   
			   
			   Thread.sleep(3000);
			   
			   driver.findElement(By.xpath("//*[@id=\"form1\"]/div[3]/div/div[1]/div[1]/button/i")).click();
			   
			   
			    Thread.sleep(2000);
				
				driver.findElement(By.xpath("//*[@id=\"menus\"]/div[2]/div[2]/a")).click();
				
				String actual_url1 = driver.getCurrentUrl();
				
				 if (expectedUrl_search.equals(actual_url1))
					{
					    System.out.println("after click on search = page open successfully");
					}
				   else
				   {
					   System.out.println("after click on search = page not open");
				   }
				   
			
			Thread.sleep(2000);
			
			
			driver.findElement(By.xpath("//*[@id=\"form1\"]/div[3]/div/div/div[1]/div/a/i")).click();  // back
			
			Thread.sleep(2000);
			
			
		//	 driver.findElement(By.xpath("//*[@id=\"form1\"]/div[3]/div[1]/div[1]/button/i")).click();
			   
			   
		//	    Thread.sleep(4000);
				
		//		driver.findElement(By.xpath("//*[@id=\"menus\"]/a[4]")).click();
			
			
		//	
		//		String actual_url2 = driver.getCurrentUrl();
				
		//		 if (expectedUrl_order_history.equals(actual_url2))
		//			{
		//			    System.out.println("after click on order history = page open successfully");
		//			}
		//		   else
		//		   {
		//			   System.out.println("after click on order history = page not open");
		//		   }
				 
				 
				 
			//	 Thread.sleep(2000);
					
					
			   driver.findElement(By.xpath("//*[@id=\"form1\"]/div[3]/div/div[1]/div[1]/button/i")).click();  // back	 
			   
			   Thread.sleep(2000);
			   
			   
			   
			   driver.findElement(By.xpath("//*[@id=\"gid://shopify/MenuItem/522999267609\"]/a/b")).click();  // home	 
			   
			 //  System.out.println("111111");
			   
			   Thread.sleep(2000);
			   
			   driver.findElement(By.cssSelector("#Topproducts > div:nth-child(3) > div > a > img")).click();
			   
			   
			   String expectedUrl_product_view="http://www.nobiai.com/productview?id=8335076688153";
			   
			   String actual_url3 = driver.getCurrentUrl();
			   
			   
			   
			   if (actual_url3.contains("http://www.nobiai.com/productview?id=")) 
			   
					{
					    System.out.println("after click on perticular product = product view - page open successfully");
					}
				   else
				   {
					   System.out.println("after click on perticular product = product view - not open");
				   }
			   
			   
			   Thread.sleep(4000);
			   
			   driver.findElement(By.xpath("//*[@id=\"form1\"]/div[3]/div/div/div[1]/div/a/i")).click();  // back	  
			   
			   
			   
			   
			   driver.findElement(By.xpath("//*[@id=\"example-search-input\"]")).sendKeys("sugar");
			   
			   
			   Thread.sleep(2000);
			   
			   driver.findElement(By.xpath("//*[@id=\"search-button\"]/i")).click();
			   
			   Thread.sleep(4000);
			   
			   String actual_url4 = driver.getCurrentUrl();
			   
			   
			   
			   if (actual_url4.contains("http://www.nobiai.com/search?search=")) 
				   
				{
				    System.out.println("after search perticular product = product getting displayed successfully");
				}
			   else
			   {
				   System.out.println("after search perticular product = products not displayed");
			   }
			   
			   
			   driver.findElement(By.xpath("//*[@id=\"form1\"]/div[3]/div/div/div[1]/div/a/i")).click();  // back	 
			   
			   
			   Thread.sleep(2000);
			   
			   
			   
			   
			   
			   driver.findElement(By.xpath("//*[@id=\"Topproducts\"]/div[1]/div/div/div[2]/button")).click(); 
			   
			   Thread.sleep(2000);
			   
			   driver.findElement(By.xpath("//*[@id=\"form1\"]/div[3]/div[1]/div[3]/i")).click(); 
			   Thread.sleep(2000);
			   
			   driver.findElement(By.xpath("//*[@id=\"checkoutbtn\"]")).click(); 
			   
			   
			   String actual_url5 = driver.getCurrentUrl();
			   
			   if (actual_url5.contains("http://www.nobiai.com/Login")) 
				   
				{
				    System.out.println("product added successfully in cart - but still not logged in thats why redirect on login page");
				}
			   else
			   {
				   System.out.println("some issue occured - while product adding in cart");
			   }
			   
			   
			   driver.findElement(By.id("mobno")).sendKeys("7219020198");
			   
			   Thread.sleep(3000);
			   
			   driver.findElement(By.xpath("//*[@id=\"mobilebox\"]/center/div/button")).click(); 
			   
			  Thread.sleep(4000);
			   
			   Alert alert = driver.switchTo().alert();
			   
			   Thread.sleep(3000);

		      
		        String alertText = alert.getText();
		        System.out.println("Alert Text: " + alertText);
		        
		        Thread.sleep(3000);
		        
		        String regexPattern = "\\d+"; // Matches one or more digits
		        Pattern pattern = Pattern.compile(regexPattern);
		        Matcher matcher = pattern.matcher(alertText);

		        
		        String dynamicNumber = null;
		        
		        if (matcher.find()) {
		            dynamicNumber = matcher.group();
		            System.out.println("otp Number: " + dynamicNumber);
		        }  
		        
		        
		     //   Thread.sleep(2000);
		        alert.accept();
		        
		        
		        
		        driver.findElement(By.id("otpinput")).sendKeys(dynamicNumber); 
		        
		        
		        
		        try
		        {
		        	 Thread.sleep(2000);
				     alert.accept();
		        }
		        catch(Exception obj)
		        {
		        	
		        }
		       
		        
		       
		        
		        driver.findElement(By.id("matchbutton")).click(); 
		        
		        
		        Thread.sleep(2000);
		        
		        
		        try
		        {
		        	 Thread.sleep(2000);
				     alert.accept();
		        }
		        catch(Exception obj)
		        {
		        	
		        }
		        
		        
		        
		        
		        
		        String actual_url6 = driver.getCurrentUrl();
		        
		        
		        Thread.sleep(4000);
				   
				   if (actual_url6.contains("http://www.nobiai.com/Login")) 
					   
					{
					    System.out.println("successfully login using otp");
					}
				   else
				   {
					   System.out.println("some issue occured - while login");
				   }
				   
				   
				   Thread.sleep(2000);
				   
				  
				   driver.findElement(By.xpath("//*[@id=\"Topproducts\"]/div[1]/div/div/div[2]/button")).click(); 
				   
				   Thread.sleep(2000);
				   
				   driver.findElement(By.xpath("//*[@id=\"form1\"]/div[3]/div[1]/div[3]/i")).click(); 
				   Thread.sleep(2000);
				   
				   driver.findElement(By.xpath("//*[@id=\"checkoutbtn\"]")).click(); 	
				   
				   
				   
				   String actual_url7 = driver.getCurrentUrl();
				   
				   
				   Thread.sleep(2000);
				   
				   if (actual_url7.contains("http://www.nobiai.com/selectaddress")) 
					   
					{
					    System.out.println(" after check uut - successfully redirect on select address page ");
					}
				   else
				   {
					   System.out.println("not redirected on select address page after check out");
				   }
				   
				   
				   
				   driver.findElement(By.xpath("//*[@id=\"form1\"]/footer/div[1]/a")).click(); 
				   
				   
				   
                   String actual_url8 = driver.getCurrentUrl();
				   
				   
				   Thread.sleep(2000);
				   
				   if (actual_url8.contains("http://www.nobiai.com/addnewaddress")) 
					   
					{
					    System.out.println(" after click on Add new address button  - successfully redirect new address page  ");
					}
				   else
				   {
					   System.out.println("not redirected on Add new address =====something error occured");
				   }
				   
				   Thread.sleep(2000);
				   
				   driver.findElement(By.xpath("//*[@id=\"form1\"]/div[3]/a/i")).click();    //back
				   
				   
				   Thread.sleep(2000);
				   
				   
				   driver.findElement(By.xpath("//*[@id=\"form1\"]/footer/div[2]/button")).click();  
				   
				   
				   
				   
                    String actual_url9 = driver.getCurrentUrl();
				   
				   
				   Thread.sleep(2000);
				   
				   if (actual_url9.contains("http://www.nobiai.com/order_summary")) 
					   
					{
					    System.out.println(" after click on continue button  - successfully redirect on order summary page  ");
					}
				   else
				   {
					   System.out.println("not redirected on order summary page =====something error occured");
				   } 
				   
				   
				   Thread.sleep(2000);
				   
				   
				   driver.findElement(By.xpath("/html/body/footer/div/div[2]/a")).click();  
				   
				   Thread.sleep(2000);
				   
				   
				   
				   
				   
                   String actual_url10 = driver.getCurrentUrl();
				   
				   
				   Thread.sleep(2000);
				   
				   if (actual_url10.contains("http://www.nobiai.com/success")) 
					   
					{
					    System.out.println(" after click on order placed  - successfully order placed  ");
					}
				   else
				   {
					   System.out.println("order not placed successfully =====something error occured");
				   } 
				   
				   Thread.sleep(2000);
				   
				   driver.findElement(By.xpath("/html/body/div[2]/div/form/div/div/a/span/h6")).click();  
				   
				   
                  String actual_url11 = driver.getCurrentUrl();
				   
				   
				   Thread.sleep(2000);
				   
				   if (actual_url11.contains("http://www.nobiai.com/")) 
					   
					{
					    System.out.println(" after click on back to home button  - successfully redirected on home page   ");
					}
				   else
				   {
					   System.out.println("after click on back to home button =====  not redirected on home page -something error occured");
				   } 
				   
				   
				   Thread.sleep(3000); 
				   
				   driver.findElement(By.xpath("//*[@id=\"form1\"]/div[3]/div[1]/div[1]/button/i")).click();  
				   
				   
				   Thread.sleep(3000);  
				   
				   driver.findElement(By.xpath("//*[@id=\"menus\"]/div[2]/div[8]/a")).click(); 
				   
				   Thread.sleep(3000);
				   
				   
                     String actual_url12 = driver.getCurrentUrl();
				   
				   
				   Thread.sleep(3000);
				   
				   if (actual_url12.contains("http://www.nobiai.com/Login")) 
					   
					{
					    System.out.println(" after click on log out button - successfully logout   ");
					}
				   else
				   {
					   System.out.println("after click on log out button  =====  not logout -something error occured");
				   }  
				   
				   
				System.out.println("execution completed successfully");   
			
				
				
			//	 if (actual_url12.contains("http://www.nobiai.com/Login")) 
				
			//	 {
				
				Properties props5 = new Properties();
				props5.put("mail.smtp.host", "smtp.gmail.com");
				props5.put("mail.smtp.socketFactory.port", "587");
				props5.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
				props5.put("mail.smtp.auth", "true");
				props5.put("smtp.STARTTLS.enable","true");
				props5.put("smtp.starttls.enable","true");
				
				
				  props5.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
				
				props5.put("mail.smtp.starttls.enable", "true");
				props5.put("mail.smtp.port", "587");
						Session session5 = Session.getDefaultInstance(props5,
								new javax.mail.Authenticator() {
									protected PasswordAuthentication getPasswordAuthentication() {
									return new PasswordAuthentication("akashkumeriya25@gmail.com", "giiqvfbznbpgynmh");
									}
								});
						
						try {
							Message message = new MimeMessage(session5);
							message.setFrom(new InternetAddress("akashkumeriya25@gmail.com"));
							message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("akshaykhshatam@gmail.com"));
							
							
							message.setSubject("Status of pinktempo project");
							BodyPart messageBodyPart1 = new MimeBodyPart();
							messageBodyPart1.setText("Dear Akshay....Your application working fine, execution completed successfully");
												
							Multipart multipart = new MimeMultipart();
						//	multipart.addBodyPart(messageBodyPart2);
							multipart.addBodyPart(messageBodyPart1);
							message.setContent(multipart);
							
							Transport.send(message); 
							
							
							System.out.println("=====Email Sent 1 st=====");
							
							 Thread.sleep(4000);
							
						
						}
				

						 catch (MessagingException e) 
							{

								throw new RuntimeException(e);

							}	
				
				// }
				
				
			   
      }
      
      
      
		 

	

}

