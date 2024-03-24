package lib.UI;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject{
    //константа
    private static final String
            TITLE_TPL = "//*[@text=\"{SUBSTRING}\"]";
    private static final String
            FOOTER_ELEMENT = "//*[@text=\"View article in browser\"]";
    private static final String
            OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc=\"More options\"]";
    private static final String
            TOOLBAR_BUTTON = "//android.widget.TextView[@resource-id=\"org.wikipedia:id/customize_toolbar\"]";
    private static final String
            NAVIGATE_BUTTON = "//*[@content-desc='Navigate up']";
    private static final String
            SAVE_BUTTON = "//android.widget.TextView[@resource-id=\"org.wikipedia:id/page_save\"]";
    private static final String
            OPTIONS_ADD_TO_MY_LIST_BUTTON = "//*[@text=\"Add to list\"]";
    private static final String
            MY_LIST_NAME_INPUT = "//*[@resource-id=\"org.wikipedia:id/text_input\"]";
    private static final String
            MY_LIST_OK_BUTTON = "//*[@text=\"OK\"]";
    private static final String
            OPTIONS_VIEW_LIST_BUTTON = "//*[@text=\"View list\"]";




    //инициализация драйвера
    public ArticlePageObject(AppiumDriver driver){
        super(driver);
    }

    /*TEMPLATES METHODS */
    //метод, который подставляет подстроку по шаблону
    private static String getResultTitleElement(String substring){
        //меняем значение переменной SUBSTRING на строчку substring
        return TITLE_TPL.replace("{SUBSTRING}", substring);
    }
    /*TEMPLATES METHODS */

    //метод ожидания статьи
    public WebElement waitForTitleElement(String substring){
        String title_Element_xpath = getResultTitleElement(substring);
        return this.waitForElementPresent(By.xpath(title_Element_xpath),"Cannot find article title",15);

    }
    //метод в котором будем получать название статьи
    public String getArticleTitle(String substring){
        WebElement title_element = waitForTitleElement(substring);
        //метод будет возвращать название статьи
        return title_element.getAttribute("text");
    }
    //сделаем метод свайпа до футера
    public void swipeToFooter(){
        this.verticalSwipeToFindElement(
                By.xpath(FOOTER_ELEMENT),
                "Cannot find the end or Article",
                20
        );
    }
    //метод с шагами, которые добавляют статью в список статей
    public void addArticleToMyList(String name_of_folder){
        //делаем переменные для каждого из элемента, так их будет проще менять


        //нажать на кнопку с выпадающим списком
        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cannot find button to open article options",
                5
        );
        //нажать на кнопку настроек тулбара
        this.waitForElementAndClick(
                By.xpath(TOOLBAR_BUTTON),
                "Cannot find button to open customize_toolbar",
                5
        );

        //перенос кнопки элемента по координатам
        this.moveButton (200,1010,693,1005,1748);

        //возврат к статье, нажав Назад
        this.waitForElementAndClick(
                By.xpath(NAVIGATE_BUTTON),
                "Cannot find back-button to cancel search",
                5
        );
        //снова нажать на кнопку с выпадающим списком
        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cannot find button to open article options",
                5
        );
        //нажать на кнопку Save в выпадающем списке
        this.waitForElementAndClick(
                By.xpath(SAVE_BUTTON),
                "Cannot find options to add article to reading list",
                5
        );
        //в появившимся снэк-баре нажать кнопку добавления в список
        this.waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "Cannot find button Add to list",
                5
        );

        this.waitForElementAndSendKeys(
                By.xpath(MY_LIST_NAME_INPUT),
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );
        //нажать на кнопку ОК
        this.waitForElementAndClick(
                By.xpath(MY_LIST_OK_BUTTON),
                "Cannot press ОК button",
                5
        );
        //нажать на кнопку в снэк баре View list
        this.waitForElementAndClick(
                By.xpath(OPTIONS_VIEW_LIST_BUTTON),
                "Cannot press View list button",
                5
        );


    }

    //метод с шагами, который добавляет еще одну статью в список
    public void addSecondArticleToMyList(String name_of_folder){
        //нажать на кнопку с выпадающим списком
        this.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc=\"More options\"]"),
                "Cannot find button to open article options",
                5
        );
        //нажать на кнопку Save в выпадающем списке
        this.waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id=\"org.wikipedia:id/page_save\"]"),
                "Cannot find options to add article to reading list",
                5
        );
        //в появившимся снэк-баре нажать кнопку добавления в список
        this.waitForElementAndClick(
                By.xpath("//*[@text=\"Add to list\"]"),
                "Cannot find button Add to list",
                5
        );
        //в открывшемся батоншите списков найти нужный список и кликнуть
        this.waitForElementAndClick(
                By.xpath("//*[@text='"+name_of_folder+"']"),
                "Cannot find folder articles into My list",
                5
        );
        //нажать на кнопку в снэк баре View list
       this.waitForElementAndClick(
                By.xpath("//*[@text=\"View list\"]"),
                "Cannot press View list button",
                5
        );

    }
    //метод закрытия
    public void closeArticle(){
        this.waitForElementAndClick(
                By.xpath(NAVIGATE_BUTTON),
                "Cannot find back-button to cancel search",
                5
        );
    }
    // Проверяем, что у статьи есть элемент title
    public void assertElementPresentWithSearchTitle(String substring){
        String title_Element_xpath = getResultTitleElement(substring);
        this.assertElementPresent(By.xpath(title_Element_xpath));
    }



}
