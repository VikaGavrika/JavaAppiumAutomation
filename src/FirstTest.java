import lib.CoreTestCase;
import lib.UI.*;
import org.junit.Test;
import org.openqa.selenium.WebElement;


public class FirstTest extends CoreTestCase {

    private MainPageObject MainPageObject;
    protected void setUp() throws Exception{
        super.setUp();
        MainPageObject = new MainPageObject(driver);
    }

    //Тест1. Поиск
    @Test
    public void testSearch() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInputAndClick();
        //поиск элемента и отправки значения в поле
        SearchPageObject.typeSearchLine("Java");
        //метод проверяющий, что поиск по значению "Java" работает корректно и находится нужная статья с нужным заголовком
        SearchPageObject.waitForSearchResult("Object-oriented programming language");

    }
    //Тест2. Отменяет поиск
    @Test
    public void testCanselSearch() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInputAndClick();
        //поиск элемента и отправки значения в поле
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForCancelButtonToAppear();
        //дожидаемся кнопки возврата и кликаем по ней
        SearchPageObject.clickCancelSearch();
        //Метод, который проверяет, что после нажатия "Назад", мы вернулись на страницу, где нет элемента стрелки "назад"
        SearchPageObject.waitForCancelButtonToDisappear();


    }
    //Тест3 Сравнить название статьи
    @Test
    public void testCompareArticleTitle() {

        //инициализация
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInputAndClick();
        //поиск элемента и отправки значения в поле
        SearchPageObject.typeSearchLine("Java");
        //Поиск элемента и клик по нему
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        //используем новый метод. инициализация
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        //поиск заголовка нужной статьи
        ArticlePageObject.waitForTitleElement("Java (programming language)");
        //получаем название статьи, текст этой статьи и записываем ее в переменную
        String article_title = ArticlePageObject.getArticleTitle("Object-oriented programming language");

        //используем это название статьи для сравнения
        assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_title
        );

    }

    //Тест4, свайп до конца страницы до текста в футере
    @Test
    public void testSwipeArticle() {

        //инициализация
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInputAndClick();
        //поиск элемента и отправки значения в поле
        SearchPageObject.typeSearchLine("appium");
        //Поиск элемента и клик по нему
        SearchPageObject.clickByArticleWithSubstring("Appium");
        //используем новый метод. инициализация
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        //поиск заголовка нужной статьи, ждем появления названия
        ArticlePageObject.waitForTitleElement("Automation for Apps");
        //swipe
        ArticlePageObject.swipeToFooter();

    }


    //тест5, который проверяет, что поле ввода для поиска статьи содержит текст Search Wikipedia
    @Test
    public void testSearchInputHasText() {
        //инициализация
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        //поиск поля поиска
        WebElement element = SearchPageObject.initSearchInput();
        // Ожидаемый текст
        String expectedText = "Search Wikipedia";
        // Проверка актуального текста и ожидаемого
        SearchPageObject.assertThereIsTextInSearchInput(element, expectedText);

    }

    //EX3. Тест6, который делает поиск по какому-то слову. Затем убеждается, найдены несколько статей со словом в листе результатов,
    // затем удаляет результаты поиска и убеждается что лист с результатами пуст
    @Test
    public void testSearchAndCanselSearch() {
        //инициализация
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInputAndClick();
        //поиск элемента и отправки значения в поле
        SearchPageObject.typeSearchLine("planet");
        //находим элемент 'лист результатов' поиска
        WebElement element = SearchPageObject.resultsList();
        //проверяем, что найдены несколько статей со словом planet в листе результатов
        String expectedText = "planet";
        SearchPageObject.assertSearchResultsWithText(element, expectedText);
        //дожидаемся кнопки закрытия и кликаем по ней
        SearchPageObject.clickCloseSearch();
        //проверяем, что нет статей в листе результатов, есть пустой лист результатов
        SearchPageObject.waitForEmptyResultsList();

    }

    //Ex4. Тест7, который делает поиск по какому-то слову. Затем убеждается, что в каждом результате поиска есть это слово.
    @Test
    public void testSearchTextAndCheckTextInTitles() {

        //инициализация
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInputAndClick();
        //поиск элемента и отправки значения в поле
        SearchPageObject.typeSearchLine("Java");
        //находим элемент 'лист результатов' поиска
        WebElement element = SearchPageObject.resultsList();
        //проверяем, что в каждой статье в листе результатов есть ожидаемое слово
        String expectedText = "java";
        SearchPageObject.assertSearchResultsWithText( element, expectedText);

    }

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


    //Тест9, которой ищет какую-то конкретную статью, а затем проверяет, что вышел 1 результат с этой статьей
    @Test
    public void testAmountOfNotEmptySearch(){

        //инициализация
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInputAndClick();
        //Зададим переменную, название статьи
        String search_line = "Linkin Park discography";
        //поиск элемента и отправки значения в поле
        SearchPageObject.typeSearchLine(search_line);
        //выдает кол-во статей
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();

        //убеждаемся, что кол-во полученных элементов больше нуля
        assertTrue(
                "We found too few results",
                amount_of_search_results > 0
        );


    }
    //Тест10, который будет проверять, что результаты поиска не содержат элементы с определенным текстом.
    // И есть элемент "нет результатов"
    @Test
    public void testAmountOfEmptySearch(){

        //инициализация
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInputAndClick();
        //Зададим переменную, название статьи
        String search_line = "ppppppppppp";
        //поиск элемента и отправки значения в поле
        SearchPageObject.typeSearchLine(search_line);
        //подтверждаем, что на странице нет результатов
        SearchPageObject.assertThereIsNoResultOfSearch(search_line);

    }


    //Тест11, который вводит значение в поиск, выбирает статью, после поворачивать экран телефона,
    // проверять, что название статьи не изменилось
    @Test
    public void testChangeScreenOrientationOnSearchResults(){

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
        //получаем название заголовок статьи до ротации, чтобы в дальнейшем ее сравнить после ротации экрана
        //аттрибут (текст), который будем получать, запишем в переменную
        String title_before_rotation = ArticlePageObject.getArticleTitle("Object-oriented programming language");

        //повернуть телефон,в скобках указываем в какую сторону хотим повернуть альбомная- горизонт или портретная- вертик
        this.rotateScreenLandscape();

        //снова получаем значение названия статьи
        String title_after_rotation = ArticlePageObject.getArticleTitle("Object-oriented programming language");

        //Сравниваем два значения
        assertEquals(
                "Article title have been changed after rotation",
                title_before_rotation,
                title_after_rotation
        );
        //сделаем еще одну ротацию
        this.rotateScreenPortrait();

        //снова получаем значение названия статьи после ротации
        String title_after_second_rotation = ArticlePageObject.getArticleTitle("Object-oriented programming language");


        // сравниваем два значения
        assertEquals(
                "Article title have been changed after rotation",
                title_before_rotation,
                title_after_second_rotation
        );
        //еще раз перевернем, чтобы проверить, вернет ли метод экран в портретную ориентацию
        this.rotateScreenLandscape();
        //переворот экрана в вертикальную ориентацию, кроме случаев, когда уже в вертикальной ориентации
        this.resetScreenOrientation();

    }

    //Тест12, который вводит значение в поиск, находить определенный элемент в результатах поиска,
    // потом приложение сворачиваем, через некоторое время разворачиваем, после открытия приложения проверяем,
    // что элемент, остался на месте.
    @Test
    public void testCheckSearchArticleInBackground(){

        //инициализация
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInputAndClick();
        //поиск элемента и отправки значения в поле
        SearchPageObject.typeSearchLine("Java");
        //Поиск элемента в результатах
        SearchPageObject.waitForSearchResult("Object-oriented programming language");

        //отправляем приложение в бэкграунд
        this.backgroundApp(5);

        //после этого приложение автоматически развернется

        //убеждаемся,что та же самая статья действительно присутствует
        SearchPageObject.waitForSearchResult("Object-oriented programming language");

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
    //Ex6. Тест14, который открывает статью и убеждается, что у нее есть элемент title.  тест не должен
    // дожидаться появления title, проверка должна производиться сразу. Если title не найден - тест падает с ошибкой.
    @Test
    public void testAssertTitle() {

        //инициализация
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInputAndClick();
        //поиск элемента и отправки значения в поле
        SearchPageObject.typeSearchLine("Java");
        //Поиск элемента в результатах
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
        //Работа с заголовком статьи. Инициализация
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        // Проверяем, что у статьи есть элемент title
        ArticlePageObject.assertElementPresentWithSearchTitle("Java (programming language)");

    }


}



