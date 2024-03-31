package tests.ANDROID;

import lib.CoreTestCase;
import lib.UI.ArticlePageObject;
import lib.UI.MyListPageObject;
import lib.UI.NavigationUI;
import lib.UI.SearchPageObject;
import org.junit.Test;

public class MyListTests extends CoreTestCase {
    //Тесты связанные с MyLists списком сохраненных статей
    //Тест8. Поиск определенной статьи, выбрать статью, нажать на кнопку с выпадающем списком, после открытия выбрать
    // и нажать на кнопку из списка, в батоншите создать новый список (нажав на кнопку), ввести название списка в поле,
    // нажать ОК, выйти из статьи, нажать на кнопку списки, перейти на экран со спискими, выбрать один их них, нажать,
    // убедиться что в списке присутствует выбранная статья, удалить статью,
    // убедиться, что она удалена, тест будет считаться законченным
    @Test
    public void testSavedFirstArticleToMyList(){


        //инициализация
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInputAndClick();
        //поиск элемента и отправки значения в поле
        SearchPageObject.typeSearchLine("Java");
        //Поиск элемента и клик по нему
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        //Работа с заголовком статьи. Инициализация
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        //поиск заголовка нужной статьи
        ArticlePageObject.waitForTitleElement("Java (programming language)");
        //делаем отдельную переменную для названия статьи
        String article_title = ArticlePageObject.getArticleTitle("Object-oriented programming language");
        //задаем переменную с названием списка, тк будем исп-ть ее в нескольких местах
        String name_of_folder = "articles";
        // добавляем статью в список статей
        ArticlePageObject.addArticleToMyList(name_of_folder);
        //нажать кнопку назад 3 раза, чтобы вернуться на главную страницу
        //цикл, повторяем код, пока не будет выполнено определенное условие.
        int i = 0;
        while (i < 3) {
            ArticlePageObject.closeArticle();
            i++;
        }
        //инициализация навигация по приложению
        NavigationUI NavigationUI = new NavigationUI(driver);
        //нажать кнопку Save в меню
        NavigationUI.clickMyLists();

        //инициализация объектов в списке My list
        MyListPageObject MyListPageObject = new MyListPageObject(driver);
        //поиск списка статей по названию, название задано в переменную выше. клик на список статей
        MyListPageObject.openFolderByName(name_of_folder);

        //удаление статьи свайпом влево
        MyListPageObject.swipeByArticleToDelete(article_title);
        //убеждаемся, что нужной статьи нет в списке
        MyListPageObject.waitForArticleToDisappearByTitle(article_title);


    }

    //Ex5. Tecт13, сохранить две статьи в список, одну статью удалить, убелиться, что вторая статья осталась,
    // зайти в нее и сравнить заголовки
    @Test
    public void testSavedTwoArticleToMyList() {

        //инициализация
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInputAndClick();
        //поиск элемента и отправки значения в поле
        SearchPageObject.typeSearchLine("Java");
        //Клик по статье
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        //Работа с заголовком статьи. Инициализация
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        //поиск заголовка нужной статьи
        ArticlePageObject.waitForTitleElement("Java (programming language)");
        //делаем отдельную переменную для названия статьи
        String title_first_article = ArticlePageObject.getArticleTitle("Java (programming language)");
        //задаем переменную с названием списка, тк будем исп-ть ее в нескольких местах
        String name_of_folder = "articles";
        // добавляем статью в список статей
        ArticlePageObject.addArticleToMyList(name_of_folder);

        //нажать кнопку назад 3 раза, чтобы вернуться на главную страницу
        //цикл, повторяем код, пока не будет выполнено определенное условие.
        int i = 0;
        while (i < 3) {
            ArticlePageObject.closeArticle();
            i++;
        }

        //поиска строки элемента и клика
        SearchPageObject.initSearchInputAndClick();
        //поиск элемента и отправки значения в поле
        SearchPageObject.typeSearchLine("Appium");
        //Клик по статье
        SearchPageObject.clickByArticleWithSubstring("Appium");
        //поиск заголовка нужной статьи
        ArticlePageObject.waitForTitleElement("Appium");
        //делаем отдельную переменную для названия статьи
        String title_second_article = ArticlePageObject.getArticleTitle("Appium");

        // добавляем статью в список статей
        ArticlePageObject.addSecondArticleToMyList(name_of_folder);

        //инициализация объектов в списке My list
        MyListPageObject MyListPageObject = new MyListPageObject(driver);
        //убеждаемся, что есть заголовок первой статьи в открывшемся списке
        MyListPageObject.waitForArticleToAppearByTitle(title_first_article);
        //Выводим в консоль название
        System.out.println("Найден " + title_first_article + " элемент с текстом.");
        //убеждаемся что есть вторая статья в открывшемся списке
        MyListPageObject.waitForArticleToAppearByTitle(title_second_article);
        //Выводим в консоль название
        System.out.println("Найден " + title_second_article + " элемент с текстом.");

        //удаление статьи свайпом влево
        MyListPageObject.swipeByArticleToDeleteFromList(title_second_article);

        //убеждаемся, что нет второй статьи в списке
        MyListPageObject.waitForArticleToDisappearByTitle(title_second_article);
        //убеждаемся что есть первая статья в списке и кликаем на нее
        MyListPageObject.waitForArticleToAppearByTitleAndClick(title_first_article);

        //Выводим в консоль название
        System.out.println("Найден " + title_first_article + " элемент с текстом после изменений в списке.");

        //снова получаем значение названия статьи
        String title_first_article_after_list_change = ArticlePageObject.getArticleTitle("Java (programming language)");

        //yбеждаемся, что заголовок в первой статье совпадает
        //Сравниваем два значения
        assertEquals(
                "Article title have been changed after rotation",
                title_first_article,
                title_first_article_after_list_change
        );


    }



}
