package lib.UI;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListPageObject extends MainPageObject{
    public static final String
            FOLDER_BY_NAME_TPL = "//*[@text='{FOLDER_NAME}']";
    public static final String
            ARTICLE_BY_TITLE_TPL = "//*[@text='{TITLE}']";

    private static String getFolderXpathByName(String name_of_folder){
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }
    private static String getSavedArticleXpathByName(String article_title){
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }


    public MyListPageObject(AppiumDriver driver){
        super(driver);
    }

    //поиск папки с именем и открываем ее
    public void openFolderByName(String name_of_folder){
        String folder_name_xpath = getFolderXpathByName(name_of_folder);
        //поиск списка статей по названию, название задано в переменную выше. клик на список статей
        this.waitForElementAndClick(
                By.xpath(folder_name_xpath),
                "Cannot find folder by name" +name_of_folder,
                5
        );
    }
    //Метод ожидания статьи
    public void waitForArticleToAppearByTitle(String article_title){
        String article_xpath = getFolderXpathByName(article_title);
        //поиск списка статей по названию
        this.waitForElementPresent(
                By.xpath(article_xpath),
                "Cannot find saved article by title" +article_title,
                15
        );
    }
    public void waitForArticleToAppearByTitleAndClick(String article_title){
        String article_xpath = getFolderXpathByName(article_title);
        //поиск списка статей по названию
        this.waitForElementPresent(
                By.xpath(article_xpath),
                "Cannot find saved article by title" +article_title,
                15
        );
        this.waitForElementAndClick(
                By.xpath(article_xpath),
                "Cannot find saved article by title" +article_title,
                15

        );
    }

    public void swipeByArticleToDelete(String article_title){
        //
        this.waitForArticleToAppearByTitle(article_title);
        //удаление статьи свайпом влево
        this.leftSwipe (200,826,977,92,941);
    }
    public void swipeByArticleToDeleteFromList(String article_title){
        //
        this.waitForArticleToAppearByTitle(article_title);
        //удаление статьи свайпом влево
        this.leftSwipe (200,826,977,92,941);
    }
    public void waitForArticleToDisappearByTitle(String article_title){
        String article_xpath = getFolderXpathByName(article_title);
        //поиск списка статей по названию
        this.waitForElementNotPresent(
                By.xpath(article_xpath),
                "Cannot find saved article" +article_title,
                15
        );
    }

}
