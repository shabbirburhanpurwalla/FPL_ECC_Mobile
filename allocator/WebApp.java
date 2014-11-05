package allocator;
import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebApp {
       RemoteWebDriver driver;
       
       @Before
       public void setup() throws MalformedURLException{
              DesiredCapabilities dc=new DesiredCapabilities();
              //dc.setCapability("automationName", "Appium");
              dc.setCapability("deviceName", "01498A001200A017");
              //dc.setCapability("udid", "01498A001200A017");
              dc.setCapability("browserName", "Chrome");
              dc.setCapability("platformName", "Android");
              driver=new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"),dc);
          
              driver.get("http://mtest.fpl.com/");
              
              
       }

       @Test
       public void test() {
              System.out.println("Web App launched");
              
       }
       
       public void waitTime(long millis){
              try {
                     Thread.sleep(millis);
              } catch (InterruptedException e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
              }
       }
       
       @After
       public void tearDown(){
              driver.quit();
       }

}
