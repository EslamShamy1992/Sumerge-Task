package DriverFactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class factory {

    public static WebDriver driver;

    public static WebDriver intializeDriver(){
        String browser = System.getProperty("browser","CHROME");
        switch (browser) {
            case "CHROME":
//                WebDriverManager.chromedriver().clearDriverCache().setup();
//                WebDriverManager.chromedriver().clearResolutionCache().setup();
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(options);
                break;
            case "FIREFOX":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            default:
                throw new RuntimeException("the broswer is not supported");
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(9));
        driver.manage().window().maximize();
        return driver;
    }
}
