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
            SEARCH_INPUT = "xpath://*[@text='Search Wikipedia']";

    private static final String
            //в константу будет подставляться нужная подстрока для Java
            //TPL метод шаблона. Заменяем какое-то значение по шаблону
            SEARCH_REASULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='{SUBSTRING}']";
    private static final String
            SEARCH_RESULTS_TITLE_TPL = "xpath://*[@resource-id=\"org.wikipedia:id/page_list_item_title\" and contains(@text, \"{SUBSTRING_TITLE}\")]";
    private static final String
            SEARCH_RESULTS_DESCRIPTION_TPL = "xpath://*[@resource-id=\"org.wikipedia:id/page_list_item_description\" and contains(@text, \"{SUBSTRING_DESCRIPTION}\")]";


    private static final String
            //локатор кнопки возврата
            SEARCH_CANCEL_BUTTON = "xpath://*[@content-desc='Navigate up']";
    private static final String
            //локатор кнопки возврата
            SEARCH_CLOSE_BUTTON = "id:org.wikipedia:id/search_close_btn";

    private static final String
            SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id=\"org.wikipedia:id/search_results_list\"]/android.view.ViewGroup";

    private static final String
            SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@text = 'No results']";

    private static final String
            RESULT_LIST = "id:org.wikipedia:id/search_results_list";
    private static final String
            EMPTY_RESULT_LIST = "id:org.wikipedia:id/search_empty_container";




    /*TEMPLATES METHODS */
    //метод, который подставляет подстроку по шаблону
    private static String getResultSearchElement(String substring){
        //меняем значение переменной SUBSTRING на строчку substring
        return SEARCH_REASULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    private static String getResultTitleAndDescriptionElements(String title, String description){
        //меняем значение переменной SUBSTRING на строчку substring
        String title_Elements_xpath = SEARCH_RESULTS_TITLE_TPL.replace("{SUBSTRING_TITLE}", title);
        String description_Elements_xpath = SEARCH_RESULTS_DESCRIPTION_TPL.replace("{SUBSTRING_DESCRIPTION}", description);
        return String.format("(%s)[%s]", title_Elements_xpath, description_Elements_xpath);
    }



    /*TEMPLATES METHODS */

    public WebElement waitForElementByTitleAndDescription(String title, String description) {
        String title_description_Elements_xpath = getResultTitleAndDescriptionElements(title,description);
        return this.waitForElementPresent(title_description_Elements_xpath, "Cannot find article title and description", 5);
    }


    //инициализируем драйвер
    public SearchPageObject(AppiumDriver driver){
        //берем дравер из MainPageObject
        super(driver);
    }
    public WebElement initSearchInput() {
        this.waitForElementPresent(SEARCH_INPUT, "Cannot find search input after clicking search init element",5);
        // Находим элемент
        WebElement element = driver.findElement(By.xpath(SEARCH_INPUT));
        // Возвращаем найденный элемент
        return element;
    }

    public void initSearchInputAndClick() {
        this.waitForElementPresent(SEARCH_INPUT, "Cannot find search input after clicking search init element",5);
        this.waitForElementAndClick(SEARCH_INPUT, "Cannot find and click search init element", 5);
    }

    public void typeSearchLine(String search_line){
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Cannot find and type into search input",5);
    }

    public void waitForSearchResult (String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(search_result_xpath,"Cannot find search result with substring" +substring);
    }

    public void clickByArticleWithSubstring (String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath,"Cannot find and click search result with substring" +substring, 10);
    }
    //метод, который будет искать пустой список результатов
    public void waitForEmptyResultsList(){
        this.waitForElementPresent(EMPTY_RESULT_LIST, "There are several articles in the results list",5);
        // вывести в консоль
        System.out.println("Results list is empty");
    }

    //метод, который будет искать кнопку возврата
    public void waitForCancelButtonToAppear(){
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button",5);
    }
    //метод, который будет ожидать отсутствие этой кнопки по окончанию теста
    public void waitForCancelButtonToDisappear(){
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button is still present",5);
    }
    //клик по кнопку возврата
    public void clickCancelSearch(){
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click search cancel button",5);
    }
    //клик по кнопку закрыть
    public void clickCloseSearch(){
        this.waitForElementAndClick(SEARCH_CLOSE_BUTTON, "Cannot find and click search close button",5);
    }



    //метод, который выдает кол-во статей
    public int getAmountOfFoundArticles(){
        //поиск элемента
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request ",
                15
        );
        //метод возвращал нам кол-во элементов по определенному xpath
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);

    }
    //находим элемент 'лист результатов' поиска
    public WebElement resultsList() {
        this.waitForElementPresent(RESULT_LIST, "Cannot find search resultList",5);
        // Находим элемент
        WebElement element = driver.findElement(By.id(RESULT_LIST));
        // Возвращаем найденный элемент
        return element;
    }
    //счетчик результатов поиска
    public int getSearchResultsCount() {
        return driver.findElements(By.xpath(SEARCH_RESULT_ELEMENT)).size();
    }




    //подтверждаем, что на странице нет результатов
    public void waitForEmptyResultsLabel(){
        //поиск элемента
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT,"Cannot find empty result element", 15);

    }
    public void assertThereIsNoResultOfSearch(String search_line){
        //подтверждаем, что на странице нет результатов
        this.assertNoElementsPresentWithText(SEARCH_RESULT_ELEMENT, search_line, "We've found some results by request" + search_line);

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
