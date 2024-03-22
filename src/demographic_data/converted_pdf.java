package demographic_data;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import au.com.bytecode.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class converted_pdf {

	public static void main(String[] args) {
	    System.setProperty("webdriver.chrome.driver", "/home/shatam-system-i2/chromedriver");
	    WebDriver driver = new ChromeDriver();
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	    CSVWriter writer = null;
	    try {
	        writer = new CSVWriter(new FileWriter("/home/shatam-system-i2/Desktop/mpsc_extracted_data34.csv"));
	        driver.get("https://mrmcq.com/computer-course/ms-cit/page-10.html");
	        driver.manage().window().maximize();

	        String[] header = {"Question", "Option1", "Option2", "Option3", "Option4", "Option5", "Option6", "Option7", "Option8", "Answer"};
	        writer.writeNext(header);

	        int totalPages = 10; // Assuming you know the total number of pages
	        int currentPage = 1;

	        while (currentPage <= totalPages) {
	            List<WebElement> questionElements = driver.findElements(By.xpath("//div[contains(@class, 'question-main')]"));
	            List<WebElement> answerElements = driver.findElements(By.xpath("//div[@class='form-inputs clearfix question-options']"));

	            for (int i = 0; i < questionElements.size(); i++) {
	                WebElement questionElement = questionElements.get(i);
	                WebElement answerElement = answerElements.get(i);

	                String question = questionElement.getText();
	                List<WebElement> optionsElements = answerElement.findElements(By.tagName("label"));
	                String[] options = new String[8];

	                for (int j = 0; j < optionsElements.size(); j++) {
	                    String optionText = optionsElements.get(j).getText();
	                    options[j] = optionText;
	                }

	                // Click the box for each question
	                List<WebElement> boxes = driver.findElements(By.className("box"));
	                boxes.get(i).click();

	                Thread.sleep(1000);

	                // Find the correct answer directly from the page source
	                List<WebElement> correctAnswerElements = driver.findElements(By.className("t_left"));
	                String correctAnswer = correctAnswerElements.get(i).getText();
	                System.out.println("Correct Answer:--------------------------- " + correctAnswer);

	                String[] row = {question, options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], correctAnswer};
	                writer.writeNext(row);

	                System.out.println("Question: " + question);
	                System.out.println("Options: " + Arrays.toString(options));
	                System.out.println("Correct Answer: " + correctAnswer);
	            }

	            currentPage++;

	            // Click next page
	            WebElement nextPageButton = driver.findElement(By.xpath("//div[@class='pagination:number' and text()='" + currentPage + "']"));
	            nextPageButton.click();
	            Thread.sleep(2000); // Wait for the page to load (adjust as needed)
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        // If "Next »" link is not found, exit the loop
	        System.out.println("End of pages reached.");
	    } finally {
	        if (writer != null) {
	            try {
	                writer.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        driver.quit();
	    }
	}
}
