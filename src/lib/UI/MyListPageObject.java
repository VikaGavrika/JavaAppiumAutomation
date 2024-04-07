package lib.UI;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

abstract public class MyListPageObject extends MainPageObject{
    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL,
            CLOSE_MODAL_WINDOW_BUTTON,
            ARTICLE_CONTAINER,
            DELETE_BUTTON;




    private static String getFolderXpathByName(String name_of_folder){
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }
    private static String getSavedArticleXpathByName(String article_title){
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }


    public MyListPageObject(AppiumDriver driver){
        super(driver);
    }

    //для Айос. метод закрывающий мод окно, возникшее на экране списков сохр статей
    public void close_modal_window(){
        this.waitForElementAndClick(
                CLOSE_MODAL_WINDOW_BUTTON,
                "Cannot find close modal window button to cancel search",
                20
        );
    }

    //поиск папки с именем и открываем ее
    public void openFolderByName(String name_of_folder){
        String folder_name_xpath = getFolderXpathByName(name_of_folder);
        //поиск списка статей по названию, название задано в переменную выше. клик на список статей
        this.waitForElementAndClick(
                folder_name_xpath,
                "Cannot find folder by name" +name_of_folder,
                20
        );
    }
    //Метод ожидания статьи
    public void waitForArticleToAppearByTitle(String article_title){
        String article_xpath = getSavedArticleXpathByName(article_title);
        //поиск списка статей по названию
        this.waitForElementPresent(
                article_xpath,
                "Cannot find saved article by title" +article_title,
                20
        );
    }
    public void waitForArticleToAppearByTitleAndClick(String article_title){
        String article_xpath = getFolderXpathByName(article_title);
        //поиск списка статей по названию
        this.waitForElementPresent(
                article_xpath,
                "Cannot find saved article by title" +article_title,
                20
        );
        this.waitForElementAndClick(
                article_xpath,
                "Cannot find saved article by title" +article_title,
                20

        );
    }

    public void swipeByArticleToDelete(String article_title){
        //находится статья на экране
        this.waitForArticleToAppearByTitle(article_title);
        //удаление статьи свайпом влево
        this.leftSwipe (200,826,977,92,941);
    }
    public void swipeByArticleToDeleteFromList(String article_title){
        //находится статья на экране
        this.waitForArticleToAppearByTitle(article_title);
        //удаление статьи свайпом влево
        this.leftSwipe(200,826,977,92,941);

    }
    //для Айос
    public void swipeByArticleToDeleteFromIOSList(String article_title){
        //находится статья на экране
        this.waitForArticleToAppearByTitle(article_title);
        //получаем элемент статьи
        WebElement element = waitForElementPresent(ARTICLE_CONTAINER,"Cannot find article container",15);
        //вычисление offsetX
        int elementX = element.getSize().getWidth();
        int offsetX = elementX / 2;
        //удаление статьи свайпом влево
        this.leftSwipeWithOffsetX(200,  elementX, offsetX, 277, 277);
        //клик по корзинке удаления
        this.waitForElementAndClick(DELETE_BUTTON, "Cannot find delete action button", 15);
        System.out.println("Статья удалена...");


    }


    public void waitForArticleToDisappearByTitle(String article_title){
        String article_xpath = getSavedArticleXpathByName(article_title);
        //поиск списка статей по названию
        this.waitForElementNotPresent(
                article_xpath,
                "Saved article still present" +article_title,
                20
        );
    }

}
