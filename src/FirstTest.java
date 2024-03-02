import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class FirstTest {
    private AppiumDriver driver;
    @Before
    public void setUp () throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium:platformName","Android");
        capabilities.setCapability("appium:deviceName","AndroidTestDevice");
        capabilities.setCapability("appium:platformVersion","14.0");
        capabilities.setCapability("appium:automationName","UiAutomator2");
        capabilities.setCapability("appium:appPackage","org.wikipedia");
        capabilities.setCapability("appium:appActivity",".main.MainActivity");
        capabilities.setCapability("appium:app","C:\\Users\\veekeetee\\Desktop\\JavaAppiumAutomation\\apks\\wikipedia2023-12-12.apk");


        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), capabilities);

    }
    @After
    public void tearDown()
    {
        driver.quit();
    }
    @Test
    public void firstTest() throws InterruptedException {
        driver.findElementByXPath("//*[@text='Skip']").click();
        //вместо driver.findElementByXPath("//*[@text='Search Wikipedia']").click(); пишем метод с таймаутом
        WebElement element_to_init_search = waitForElementPresentByXpath (
                "//*[@text='Search Wikipedia']",
                "Cannot find search input"
        );
        element_to_init_search.click();
        //вместо driver.findElementByXPath("//*[@text='Search Wikipedia']");
        WebElement element_to_enter_search_line = waitForElementPresentByXpath (
                "//*[@text='Search Wikipedia']",
                "Cannot find search input",
                5
        );

        element_to_enter_search_line.sendKeys("Appium");


    }
    //метод, который ищет элемент по xpath с задержкой. В течение этих сек ищет пока не найдет,если не найдет то сообщение об ошибке. Задержку в сек прописываем в тесте
    private WebElement waitForElementPresentByXpath(String xpath, String error_message, long timeoutInSecond)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond);
        wait.withMessage(error_message +"\n");
        By by = By.xpath(xpath);
        return wait.until(
                //ждем выполнения конкретного условия, ждем элемент by
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }
    //метод адаптированный, который ищет элемент по xpath с дефолтной задержкой в 3 сек
    private WebElement waitForElementPresentByXpath(String xpath, String error_message)
    {
       return waitForElementPresentByXpath(xpath, error_message, 3);
    }

}


