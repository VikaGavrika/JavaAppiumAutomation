import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import junit.framework.TestCase;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class CoreTestCase extends TestCase {
    //аппиум драйвер будет использоваться в и других классах поэтому протектед
    protected AppiumDriver driver;
    //выносим урл в отдельную переменную
    private static String AppiumUrl = "http://127.0.0.1:4723";

    @Override
    public void setUp () throws Exception
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

    }
    @Override
    public void tearDown() throws Exception
    {
        driver.quit();
        super.tearDown();
    }
}
