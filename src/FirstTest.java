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
    public void firstTest() throws InterruptedException
    {
        driver.findElementByXPath("//*[@text='Skip']").click();

        //модернизировали метод поиска элемента и клика
        waitForElementByXpathAndClick(
                "//*[@text='Search Wikipedia']",
                "Cannot find search input",
                5

        );
        //модернизировали метод поиска элемента и отправки значения
        waitForElementByXpathAndSendKeys(
                "//*[@text='Search Wikipedia']",
                "Java",
                "Cannot find search input",
                5
        );

        //метод проверяющий,что поиск по значению "Java" работает корректно и находится нужная строчка а теме "Java"
        waitForElementPresentByXpath(
                "//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Object-oriented programming language']",
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                15
        );

    }

    @Test
    public void testCanselSearch()
    {
        //Смахиваем онбординг
        driver.findElementByXPath("//*[@text='Skip']").click();
        //дожидаемся эдемента строка поиска и кликаем по нему
        waitForElementByIdAndClick(
                "org.wikipedia:id/search_container",
                "Cannot find search input",
                5
        );

        //дожидаемся кнопки возврата и кликаем по ней
        waitForElementByXpathAndClick(
                "//*[@content-desc='Navigate up']",
                "Cannot find back-button to cancel search",
                5
        );
        //Метод, который проверяет,что после нажатия "Назад", мы вернулись на страницу, где нет элемента стрелки "назад"
        waitForElementNotPresent(
                "//*[@content-desc='Navigate up']",
                "Cannot find back-button to cancel search",
                5
        );

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

    //метод, испол-я который тесты сначала будут дожидаться элемента xpath, а после этого происзойдет клик
    private WebElement waitForElementByXpathAndClick(String xpath, String error_message, long timeoutInSecond)
    {
        WebElement element = waitForElementPresentByXpath(xpath,error_message,timeoutInSecond);
        element.click();
        return element;
    }
    //метод, испол-я который тесты сначала будут дожидаться элемента xpath, а после этого происзойдет отправка текста
    private WebElement waitForElementByXpathAndSendKeys(String xpath, String value, String error_message, long timeoutInSecond)
    {
        WebElement element = waitForElementPresentByXpath(xpath,error_message,timeoutInSecond);
        element.sendKeys(value);
        return element;
    }

    //метод поиска элемента по ID
    private WebElement waitForElementPresentById(String id, String error_message, long timeoutInSecond)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond);
        wait.withMessage(error_message +"\n");
        By by = By.id(id);
        return wait.until(
                //ждем выполнения конкретного условия, ждем элемент by
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }
    private WebElement waitForElementByIdAndClick(String id, String error_message, long timeoutInSecond)
    {
        WebElement element = waitForElementPresentById(id,error_message,timeoutInSecond);
        element.click();
        return element;
    }

    //Метод отсутствия элемента на странице
    private boolean waitForElementNotPresent(String xpath, String error_message, long timeoutInSecond)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond);
        wait.withMessage(error_message +"\n");
        By by = By.xpath(xpath);
        return wait.until(
                //ждем выполнения конкретного условия, ждем элемент by
                ExpectedConditions.invisibilityOfElementLocated(by)
        );

    }


}


