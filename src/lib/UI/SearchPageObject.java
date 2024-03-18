package lib.UI;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

//методы, которые будут использоваться для поиска
//этот метод наследуется от MainPageObject
public class SearchPageObject extends  MainPageObject {
    //задаем константы. То, что всегда знаем, как выглядит. Например, поиск на странице. Он не изменен, его можно задать константой
    //или локатор кнопки, которая скипает онбординг


    private static final String
            SEARCH_INPUT = "//*[@text='Search Wikipedia']";

    private static final String
            //в константу будет подставляться нужная подстрока
            //TPL метод шаблона. Заменяем какое-то значение по шаблону
            SEARCH_REASULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='{SUBSTRING}']";
    private static final String
            //локатор кнопки возврата
            SEARCH_CANCEL_BUTTON = "//*[@content-desc='Navigate up']";


    /*TEMPLATES METHODS */
    //метод, который подставляет подстроку по шаблону
    private static String getResultSearchElement(String substring){
        //меняем значение переменной SUBSTRING на строчку substring
        return SEARCH_REASULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    /*TEMPLATES METHODS */



    //инициализируем драйвер
    public SearchPageObject(AppiumDriver driver){
        //берем дравер из MainPageObject
        super(driver);
    }

    public void initSearchInput() {
        this.waitForElementPresent(By.xpath(SEARCH_INPUT), "Cannot find search input after clicking search init element");
        this.waitForElementAndClick(By.xpath(SEARCH_INPUT), "Cannot find and click search init element", 5);
    }

    public void typeSearchLine(String search_line){
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT), search_line, "Cannot find and type into search input",5);
    }

    public void waitForSearchResult (String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(By.xpath(search_result_xpath),"Cannot find search result with substring" +substring);
    }

    //метод, который будет искать кнопку возврата
    public void waitForCancelButtonToAppear(){
        this.waitForElementPresent(By.xpath(SEARCH_CANCEL_BUTTON), "Cannot find search cancel button",5);
    }
    //метод, который будет ожидать отсутствие этой кнопки по окончанию теста
    public void waitForCancelButtonToDisappear(){
        this.waitForElementNotPresent(By.xpath(SEARCH_CANCEL_BUTTON), "Search cancel button is still present",5);
    }
    //клик по кнопку возврата
    public void clickCancelSearch(){
        this.waitForElementAndClick(By.xpath(SEARCH_CANCEL_BUTTON), "Cannot find and click search cancel button",5);
    }



}
