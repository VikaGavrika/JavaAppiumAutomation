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
        String search_result_xpath = getResultTitleElement(substring);
        return this.waitForElementPresent(By.xpath(search_result_xpath),"Cannot find article title",15);

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

}
