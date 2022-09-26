package vilcartAutomation;

import org.testng.annotations.Test;

import com.vilcart.util.AngularWait;
import com.vilcart.util.LoginTest;

import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;

public class CreateSKU {
	
	WebDriver driver ;
	JavascriptExecutor js;
	WebDriverWait wait ;
	AngularWait aw;
	LoginTest loginObj ;
	
	    
	
	
  @Test
  public void f() throws IOException {
	  
	  loginObj.logintest();   // method through exception. 
	  
	  
	  
	  
  
  }
  @BeforeTest
  public void beforeTest() {
	  System.setProperty("webdriver.chrome.driver","F:\\webdriver\\chromedriver.exe" );
		driver = new ChromeDriver();
		driver.get("https://vilcart-buy.web.app/");
//		driver.get("http://localhost:4200/");
		driver.manage().window().maximize();
	  	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
//	  	Reporter.log(driver.getTitle(), true);
	  	 js = ((JavascriptExecutor) driver);
	  	 wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	  	 aw = new AngularWait(driver);
	    loginObj = new LoginTest(driver,aw);   
	  
	  
  }

  @AfterTest
  public void afterTest() {
  }

}
