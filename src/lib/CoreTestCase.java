package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;

public class CoreTestCase extends TestCase {
    //значения, с которыми будем сравнивать переменные среды
    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_ANDROID = "android";

    //аппиум драйвер будет использоваться в и других классах поэтому протектед
    protected AppiumDriver driver;
    //выносим урл в отдельную переменную
    private static String AppiumUrl = "http://127.0.0.1:4723";

    @Override
    protected void setUp () throws Exception
    {
        super.setUp();

        DesiredCapabilities capabilities = this.getCapabilitiesByPlatformEny();

        driver = new AndroidDriver(new URL(AppiumUrl), capabilities);
        driver = new IOSDriver(new URL(AppiumUrl), capabilities);

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

    //метод, который запускает нужную платформу
    private DesiredCapabilities getCapabilitiesByPlatformEny() throws Exception{
        //в переменную platform получаем значение переменной среды, которая сейчас выбрана в edit conf
        String platform = System.getenv("PLATFORM");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        //сравниваем переменную с одной из констант, если выполняется условие,то подключаем андройд, если нет, то айос
        if(platform.equals(PLATFORM_ANDROID)){
            capabilities.setCapability("appium:platformName","Android");
            capabilities.setCapability("appium:deviceName","AndroidTestDevice");
            capabilities.setCapability("appium:platformVersion","14.0");
            capabilities.setCapability("appium:automationName","UiAutomator2");
            capabilities.setCapability("appium:appPackage","org.wikipedia");
            capabilities.setCapability("appium:appActivity",".main.MainActivity");
            capabilities.setCapability("appium:app","/Users/citylink/Documents/JavaAppiumAutomation/apks/wikipedia2023-12-12.apk");

        }
        else if (platform.equals(PLATFORM_IOS)) {

        capabilities.setCapability("appium:platformName","iOS");
        capabilities.setCapability("appium:deviceName","iPhone 11");
        capabilities.setCapability("appium:platformVersion","17.4");
        capabilities.setCapability("appium:automationName","XCUITest");
        capabilities.setCapability("appium:appPackage","wikipedia.app");
        capabilities.setCapability("appium:appActivity",".main.MainActivity");
        capabilities.setCapability("appium:app","/Users/citylink/Documents/JavaAppiumAutomation/apks/Wikipedia.app");
        } //если нет совпадения и платформу не определить, то выводим исключение с сообщением
        else {
            throw new Exception("Cannot get platform from env variable. Platform value "+platform);
        }
        //метод возвращает копабтлитис
        return capabilities;
    }

}
