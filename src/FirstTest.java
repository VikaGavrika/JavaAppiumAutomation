import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
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

        //поиска строки элемента и клика
        waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find search input",
                5

        );
        //метод поиска элемента и отправки значения в поле
        waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Java",
                "Cannot find search input",
                5
        );

        //метод проверяющий,что поиск по значению "Java" работает корректно и находится нужная строчка а теме "Java"
        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Object-oriented programming language']"),
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
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find search input",
                5
        );

        //дожидаемся кнопки возврата и кликаем по ней
        waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Cannot find back-button to cancel search",
                5
        );
        //Метод, который проверяет,что после нажатия "Назад", мы вернулись на страницу, где нет элемента стрелки "назад"
        waitForElementNotPresent(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Cannot find back-button to cancel search",
                5
        );

    }

    @Test
    public void testCompareArticleTitle()
    {
        driver.findElementByXPath("//*[@text='Skip']").click();

        // поиск элемента, затем кликаем по полю поиска
        waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find search input",
                5

        );
        //поиск элемента и отправки значения в поле поиска
        waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Java",
                "Cannot find search input",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Object-oriented programming language']"),
                "Cannot find search input",
                5

        );
        //поиск заголовока нужной статьи
        WebElement title_element = waitForElementPresent(
                By.xpath("//*[@resource-id='pcs']//*[@text='Java (programming language)']"),
                "Cannot find article title",
                15
        );
        //получаем название статьи, текст этой статьи
        String article_title = title_element.getAttribute("text");
        //использвем это название статьи для сравнения
        Assert.assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_title
        );

    }


    //метод, котрый будет искать элемент по любому атрибуту
    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSecond)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond);
        wait.withMessage(error_message +"\n");
        return wait.until(
                //ждем выполнения конкретного условия, ждем элемент by
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }
    //метод адаптированный, который ищет элемент с дефолтной задержкой в 3 сек
    private WebElement waitForElementPresent(By by, String error_message)
    {
       return waitForElementPresent(by, error_message, 3);
    }

    //метод, испол-я который тесты сначала будут дожидаться элемента, а после этого происзойдет клик
    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSecond)
    {
        WebElement element = waitForElementPresent(by,error_message,timeoutInSecond);
        element.click();
        return element;
    }
    //метод, испол-я который тесты сначала будут дожидаться элемента, а после этого происзойдет отправка текста
    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSecond)
    {
        WebElement element = waitForElementPresent(by,error_message,timeoutInSecond);
        element.sendKeys(value);
        return element;
    }

    //Метод отсутствия элемента на странице
    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSecond)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond);
        wait.withMessage(error_message +"\n");
        return wait.until(
                //ждем выполнения конкретного условия, ждем элемент by
                ExpectedConditions.invisibilityOfElementLocated(by)
        );

    }


}


