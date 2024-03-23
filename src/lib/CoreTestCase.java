package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;

public class CoreTestCase extends TestCase {
    //аппиум драйвер будет использоваться в и других классах поэтому протектед
    protected AppiumDriver driver;
    //выносим урл в отдельную переменную
    private static String AppiumUrl = "http://127.0.0.1:4723";

    @Override
    protected void setUp () throws Exception
    {
        super.setUp();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium:platformName","Android");
        capabilities.setCapability("appium:deviceName","AndroidTestDevice");
        capabilities.setCapability("appium:platformVersion","14.0");
        capabilities.setCapability("appium:automationName","UiAutomator2");
        capabilities.setCapability("appium:appPackage","org.wikipedia");
        capabilities.setCapability("appium:appActivity",".main.MainActivity");
        capabilities.setCapability("appium:app","C:\\Users\\veekeetee\\Desktop\\JavaAppiumAutomation\\apks\\wikipedia2023-12-12.apk");


        driver = new AndroidDriver(new URL(AppiumUrl), capabilities);
        //переворачиваем телефон в вертик ориентацию в начале каждого теста
        this.resetScreenOrientation();
        //Смахиваем онбординг в начале каждого теста
        this.skipOnboarding();


    }
    @Override
    protected void tearDown() throws Exception
    {
        driver.quit();
        super.tearDown();
    }
    //методы для поворота телефона
    protected void rotateScreenPortrait(){
        driver.rotate(ScreenOrientation.PORTRAIT);
    }
    protected void rotateScreenLandscape(){
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    protected void backgroundApp(int seconds){
        driver.runAppInBackground(Duration.ofSeconds(seconds));
    }
    //метод, который делает экран всегда в портретной ориентации после завершения теста.
    public void resetScreenOrientation() {
        try {
            driver.rotate(ScreenOrientation.PORTRAIT);
        } catch (Exception e) {
            // Исключение, если телефон уже в портретной ориентации
        }
    }
    protected void skipOnboarding(){
        driver.findElementByXPath("//*[@text='Skip']").click();
    }



}
