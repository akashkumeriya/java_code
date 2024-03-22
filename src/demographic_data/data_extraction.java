package demographic_data;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.bytecode.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;

public class data_extraction {
   
	public static void main(String[] args) throws InterruptedException, IOException, TimeoutException {
    	
    	
    	  System.setProperty("webdriver.chrome.driver", "/home/shatam-system-i2/chromedriver");
          WebDriver driver = new ChromeDriver();
          CSVWriter writer = new CSVWriter(new FileWriter("/home/shatam-system-i2/Desktop/mpsc_extracted_data30.csv"));
    	
    	try
    	{
    	

        driver.get("https://www.gktoday.in/quizbase/current-affairs-quiz-september-2023");
                    
        driver.manage().window().maximize();

        Thread.sleep(10000);

     //   CSVWriter writer = new CSVWriter(new FileWriter("/home/shatam-system-i2/Desktop/mpsc_extracted_data1.csv"));
        String[] header = {"Question", "Option1", "Option2", "Option3", "Option4","Answer"};
        writer.writeNext(header);

        // Extract data from each page
        int pageCount = 1;
        while (pageCount <= 5) {
            List<WebElement> questionElements = driver.findElements(By.xpath("//div[contains(@class, 'wp_quiz_question testclass')]"));
            List<WebElement> answerElements = driver.findElements(By.xpath("//div[contains(@class, 'wp_quiz_question_options')]"));
            
            List<WebElement> showAnswerButtons = driver.findElements(By.cssSelector("a.wp_basic_quiz_showans_btn"));

            

            for (int i = 0; i < questionElements.size(); i++) {
                WebElement questionElement = questionElements.get(i);
                WebElement answerElement = answerElements.get(i);

             
                
                
                String question = questionElement.getText();
                String optionsText = answerElement.getText();
                
               
                
                
                System.out.println("Question: " + question);
                System.out.println("Options: " + optionsText);
                
                
                
//                
//                driver.findElement(By.className("wp_basic_quiz_showans_btn")).click();
//
//                String correctAnswer = driver.findElement(By.className("ques_answer")).getText();
//
//                System.out.println("correctAnswer==================" + correctAnswer);
//
//               
                showAnswerButtons.get(i).click(); // Click the button associated with the current question

             // Wait for a brief moment to ensure the correct answer is visible
             Thread.sleep(1000);

             // Find the correct answer directly from the page source
             List<WebElement> correctAnswerElements = driver.findElements(By.className("ques_answer"));
             String correctAnswer = correctAnswerElements.get(i).getText();
             System.out.println("Correct Answer:--------------------------- " + correctAnswer);

                
                
                

                String[] options = optionsText.split("\\n");

                if (options.length >= 4) {
                    String[] data = {question, options[0], options[1], options[2], options[3],correctAnswer.replace("Correct Answer: ", "")};
                    writer.writeNext(data);
                } else {
                    // If there are not enough options, fill with empty strings
                    String[] data = {question, "", "", "", "",""};
                    writer.writeNext(data);
                }
            }

            // Check if there's a next page
            WebElement nextPageButton = null;
            try {
                WebDriverWait wait = new WebDriverWait(driver, 10);
                nextPageButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(Integer.toString(pageCount + 1))));
            } catch (NoSuchElementException e) {
                // If there's no next page or timeout, break the loop
                break;
            }

            if (nextPageButton != null) {
                // Navigate to the next page
                nextPageButton.click();
                Thread.sleep(25000); // Add a delay to allow the page to load
                pageCount++;
            }
            
            
        }

    	 
    	 
        writer.close();
      //  writer.flush();
        driver.quit();
        
        
    	}
    	catch(Exception obj)
    	{
    		
    	}
    	finally
    	{
    		 
            writer.close();
          //  writer.flush();
            driver.quit();
    	}
        
        
    	
    	
    }
}
