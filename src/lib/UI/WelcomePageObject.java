package lib.UI;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;



public class WelcomePageObject extends MainPageObject{
    private static final String
            LINK_ELEMENT = "//*[@name=\"Learn more about Wikipedia\"]";
    private static final String
            NEXT_BUTTON = "//*[@name=\"Next\"]";
    private static final String
            GET_STARTED_BUTTON = "//*[@name=\"Get started\"]";
    private static final String
            TITLE_ONBOARDING_TPL= "//*[@name=\"{TITLE}\"]";


    /*TEMPLATES METHODS */
    //метод, который подставляет подстроку по шаблону
    private static String getResultTitleElement(String onboarding_title){
        //меняем значение переменной TITLE на строчку onboarding_title
        return TITLE_ONBOARDING_TPL.replace("{TITLE}", onboarding_title);
    }

    /*TEMPLATES METHODS */


    //инициализация драйвера
    public WelcomePageObject(AppiumDriver driver){
        super(driver);
    }
    //поиск характерного элемента на странице онбординга
    //ссылку на 1 онбординге
    public void waitForLearnMoreLink(){
        this.waitForElementPresent(By.xpath(LINK_ELEMENT),"Cannot find link",10);
    }
    //заголовок 2,3,4 онбордингов
    public void waitForOnboardingTitle(String onboarding_title){
        String onboarding_title_xpath = getResultTitleElement(onboarding_title);
        this.waitForElementPresent(By.xpath(onboarding_title_xpath),"Cannot find onboarding title " +onboarding_title,15);
    }

    //клик по кнопке
    public void clickNextButton(){
        this.waitForElementAndClick(By.xpath(NEXT_BUTTON),"Cannot find next button and click",10);
    }
    //клик по кнопке
    public void clickGetStartedButton(){
        this.waitForElementAndClick(By.xpath(GET_STARTED_BUTTON),"Cannot find get started button and click",10);
    }





}
