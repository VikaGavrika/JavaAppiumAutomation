package lib.UI;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

//методы, которые будут использоваться для поиска
//этот метод наследуется от MainPageObject
public class SearchPageObject extends  MainPageObject {
    //задаем константы. То, что всегда знаем, как выглядит. Например, поиск на странице. Он не изменен, его можно задать константой
    //или локатор кнопки, которая скипает онбординг


    private static final String
            SEARCH_INPUT = "//*[@text='Search Wikipedia']";

    private static final String
            //в константу будет подставляться нужная подстрока  для Java
            //TPL метод шаблона. Заменяем какое-то значение по шаблону
            SEARCH_REASULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='{SUBSTRING}']";

    private static final String
            //локатор кнопки возврата
            SEARCH_CANCEL_BUTTON = "//*[@content-desc='Navigate up']";
    private static final String
            //локатор кнопки возврата
            SEARCH_CLOSE_BUTTON = "org.wikipedia:id/search_close_btn";

    private static final String
            SEARCH_RESULT_ELEMENT = "//*[@resource-id=\"org.wikipedia:id/search_results_list\"]/android.view.ViewGroup";

    private static final String
            SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text = 'No results']";

    private static final String
            RESULT_LIST = "org.wikipedia:id/search_results_list";
    private static final String
            EMPTY_RESULT_LIST = "org.wikipedia:id/search_empty_container";




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
    public WebElement initSearchInput() {
        this.waitForElementPresent(By.xpath(SEARCH_INPUT), "Cannot find search input after clicking search init element",5);
        // Находим элемент
        WebElement element = driver.findElement(By.xpath(SEARCH_INPUT));
        // Возвращаем найденный элемент
        return element;
    }

    public void initSearchInputAndClick() {
        this.waitForElementPresent(By.xpath(SEARCH_INPUT), "Cannot find search input after clicking search init element",5);
        this.waitForElementAndClick(By.xpath(SEARCH_INPUT), "Cannot find and click search init element", 5);
    }

    public void typeSearchLine(String search_line){
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT), search_line, "Cannot find and type into search input",5);
    }

    public void waitForSearchResult (String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(By.xpath(search_result_xpath),"Cannot find search result with substring" +substring);
    }

    public void clickByArticleWithSubstring (String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(By.xpath(search_result_xpath),"Cannot find and click search result with substring" +substring, 10);
    }
    //метод, который будет искать пустой список результатов
    public void waitForEmptyResultsList(){
        this.waitForElementPresent(By.id(EMPTY_RESULT_LIST), "There are several articles in the results list",5);
        // вывести в консоль
        System.out.println("Results list is empty");
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
    //клик по кнопку закрыть
    public void clickCloseSearch(){
        this.waitForElementAndClick(By.id(SEARCH_CLOSE_BUTTON), "Cannot find and click search close button",5);
    }



    //метод, который выдает кол-во статей
    public int getAmountOfFoundArticles(){
        //поиск элемента
        this.waitForElementPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "Cannot find anything by the request ",
                15
        );
        //метод возвращал нам кол-во элементов по определенному xpath
        return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));

    }
    //находим элемент 'лист результатов' поиска
    public WebElement resultsList() {
        this.waitForElementPresent(By.id(RESULT_LIST), "Cannot find search resultList",5);
        // Находим элемент
        WebElement element = driver.findElement(By.id(RESULT_LIST));
        // Возвращаем найденный элемент
        return element;
    }




    //подтверждаем, что на странице нет результатов
    public void waitForEmptyResultsLabel(){
        //поиск элемента
        this.waitForElementPresent(By.xpath(SEARCH_EMPTY_RESULT_ELEMENT),"Cannot find empty result element", 15);

    }
    public void assertThereIsNoResultOfSearch(String search_line){
        //подтверждаем, что на странице нет результатов
        this.assertNoElementsPresentWithText(By.xpath(SEARCH_RESULT_ELEMENT), search_line, "We've found some results by request" + search_line);

    }
    public void assertThereIsTextInSearchInput(WebElement element, String expectedText){
        //подтверждаем, что в поле поиска есть текст
        this.assertElementHasText(element, expectedText);
    }
    public void assertSearchResultsWithText(WebElement element, String expectedText){
        //подтверждаем, что в поле поиска есть текст
        this.assertMultipleSearchResultsWithText(element, expectedText);
    }


}
