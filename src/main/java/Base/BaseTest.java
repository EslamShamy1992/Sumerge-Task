package Base;

import DriverFactory.factory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;

public class BaseTest {


    protected WebDriver driver;


    @BeforeMethod
    public void setup() {
        driver = factory.intializeDriver();
    }

    @AfterMethod
    public void take_screen_shot(ITestResult result) throws IOException {


//        driver.quit();
        if (ITestResult.FAILURE == result.getStatus()) {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("D:\\screenshot\\fail.png"));


        }
    }
}