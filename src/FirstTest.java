import lib.CoreTestCase;
import lib.UI.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class FirstTest extends CoreTestCase {

    private MainPageObject MainPageObject;
    protected void setUp() throws Exception{
        super.setUp();
        MainPageObject = new MainPageObject(driver);
    }

    //Тест1. Поиск
    @Test
    public void testSearch() throws InterruptedException {
        driver.findElementByXPath("//*[@text='Skip']").click();

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInput();
        //поиск элемента и отправки значения в поле
        SearchPageObject.typeSearchLine("Java");
        //метод проверяющий, что поиск по значению "Java" работает корректно и находится нужная статья с нужным заголовком
        SearchPageObject.waitForSearchResult("Object-oriented programming language");

    }
    //Тест2. Отменяет поиск
    @Test
    public void testCanselSearch() {
        //Смахиваем онбординг
        driver.findElementByXPath("//*[@text='Skip']").click();

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInput();
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
        driver.findElementByXPath("//*[@text='Skip']").click();
        //инициализация
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInput();
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
        driver.findElementByXPath("//*[@text='Skip']").click();
        //инициализация
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInput();
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
        driver.findElementByXPath("//*[@text='Skip']").click();
        //поиск поля поиска
        WebElement inputElement = MainPageObject.waitForElementPresent(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find search input",
                5
        );

        // Проверка текста
        String expectedText = "Search Wikipedia";
        MainPageObject.assertElementHasText(inputElement, expectedText);

    }

    //EX3. Тест6, который делает поиск по какому-то слову. Затем убеждается, найдены несколько статей со словом в листе результатов,
    // затем удаляет результаты поиска и убеждается что лист с результатами пуст
    @Test
    public void testSearchAndCanselSearch() {
        //Смахиваем онбординг
        driver.findElementByXPath("//*[@text='Skip']").click();
        //дожидаемся элемента строка поиска и кликаем по нему
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find search input",
                5
        );
        //вводим значение в поле поиска
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                "planet",
                "Cannot find search input",
                5
        );
        //находим элемент 'лист результатов' поиска
        WebElement resultsList = MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Cannot find search resultList",
                5
        );
        //проверяем, что найдены несколько статей со словом planet в листе результатов
        String expectedText = "planet";
        MainPageObject.assertMultipleSearchResultsWithText(resultsList, expectedText);


        //очищаем поле поиска, путем нажатия кнопки Закрытия
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find close button",
                5
        );

        //проверяем, что нет статей в листе результатов, есть пустой лист результатов
        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/search_empty_container"),
                "Cannot find search input",
                10
        );

    }

    //Ex4. Тест7, который делает поиск по какому-то слову. Затем убеждается, что в каждом результате поиска есть это слово.
    @Test
    public void testSearchTextAndCheckTextInTitles() {
        //Смахиваем онбординг
        driver.findElementByXPath("//*[@text='Skip']").click();
        //дожидаемся элемента строка поиска и кликаем по нему
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find search input",
                5
        );
        //вводим значение в поле поиска
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                "java",
                "Cannot find search input",
                5
        );
        //находим элемент 'лист результатов' поиска
        WebElement resultsList = MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Cannot find search resultList",
                5
        );
        //проверяем, что в каждой статье в листе результатов есть ожидаемое слово
        String expectedText = "java";
        MainPageObject.assertMultipleSearchResultsWithText(resultsList, expectedText);

    }
    //Тест8. Поиск определенной статьи, выбрать статью, нажать на кнопку с выпадающем списком, после открытия выбрать
    // и нажать на кнопку из списка, в батоншите создать новый список (нажав на кнопку), ввести название списка в поле,
    // нажать ОК, выйти из статьи, нажать на кнопку списки, перейти на экран со спискими, выбрать один их них, нажать,
    // убедиться что в списке присутствует выбранная статья, удалить статью,
    // убедиться, что она удалена, тест будет считаться законченным
    @Test
    public void testSavedFirstArticleToMyList(){
        driver.findElementByXPath("//*[@text='Skip']").click();

        //инициализация
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInput();
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
        driver.findElementByXPath("//*[@text='Skip']").click();
        //инициализация
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInput();
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
        driver.findElementByXPath("//*[@text='Skip']").click();

        //инициализация
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInput();
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
        driver.findElementByXPath("//*[@text='Skip']").click();
        //инициализация
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInput();
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
        driver.findElementByXPath("//*[@text='Skip']").click();

        //инициализация
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInput();
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
        driver.findElementByXPath("//*[@text='Skip']").click();

        // поиск элемента, затем кликаем по полю поиска
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find search input",
                5

        );
        String search_line = "Java";
        //поиск первой статьи, отправка значения в поле поиска
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                search_line,
                "Cannot find search input",
                5
        );
        //поиск нужной статьи и клик
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Object-oriented programming language']"),
                "Cannot find article in results list",
                5

        );
        //получаем значение названия статьи
        String title_first_article = MainPageObject.waitForElementAndGetAttribute(
                By.xpath("//*[@text='Java (programming language)']"),
                "text",
                "Cannot find title of Article",
                15
        );

        //нажать на кнопку с выпадающим списком
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc=\"More options\"]"),
                "Cannot find button to open article options",
                5
        );
        //необходимо настроить тулбар, чтобы в меню появилась кнопка Save
        //нажать на кнопку настроек тулбара
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id=\"org.wikipedia:id/customize_toolbar\"]"),
                "Cannot find button to open customize_toolbar",
                5
        );

        //перенос элемента по координатам из категории тулбар в категорию меню
        MainPageObject.moveButton(200, 1010, 693, 1005, 1748);

        //возврат к статье, нажав Назад
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Cannot find back-button to cancel search",
                5
        );
        //снова нажать на кнопку с выпадающим списком
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc=\"More options\"]"),
                "Cannot find button to open article options",
                5
        );
        //нажать на кнопку Save в выпадающем списке
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id=\"org.wikipedia:id/page_save\"]"),
                "Cannot find options to add article to reading list",
                5
        );
        //в появившимся снэк-баре нажать кнопку добавления в список
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text=\"Add to list\"]"),
                "Cannot find button Add to list",
                5
        );
        //В появившемся мод окне в поле ввода ввести название списка, в этот список будем сохранять статью
        //задаем переменную с названием списка, тк будем исп-ть ее в нескольких местах
        String name_of_folder = "articles";

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[@resource-id=\"org.wikipedia:id/text_input\"]"),
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );
        //нажать на кнопку ОК
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text=\"OK\"]"),
                "Cannot press ОК button",
                5

        );
        //поиск элемента строки поиска и клик по полю
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find search input",
                5

        );
        String search_second_line = "Appium";
        //поиск второй статьи, отправка значения в поле поиска
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                search_second_line,
                "Cannot find search input",
                5
        );
        //кликаем на нужную статью
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id=\"org.wikipedia:id/page_list_item_title\" and @text=\"Appium\"]"),
                "Cannot find search input",
                5

        );
        //получаем заголовок второй статьи

        String title_second_article= MainPageObject.waitForElementAndGetAttribute(
                By.xpath("//*[@text=\"Appium\"]"),
                "text",
                "Cannot get title of Article",
                15
        );
        //нажать на кнопку с выпадающим списком
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc=\"More options\"]"),
                "Cannot find button to open article options",
                5
        );
        //нажать на кнопку Save в выпадающем списке
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id=\"org.wikipedia:id/page_save\"]"),
                "Cannot find options to add article to reading list",
                5
        );
        //в появившимся снэк-баре нажать кнопку добавления в список
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text=\"Add to list\"]"),
                "Cannot find button Add to list",
                5
        );
        //в открывшемся батоншите списков найти нужный список и кликнуть
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='"+name_of_folder+"']"),
                "Cannot find folder articles into My list",
                5
        );
        //нажать на кнопку в снэк баре View list
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text=\"View list\"]"),
                "Cannot press View list button",
                5
        );
        //убеждаемся, что есть заголовок первой статьи в открывшемся списке
        MainPageObject.waitForElementPresent(
                By.xpath("//*[@text='"+ title_first_article +"']"),
                "Cannot find folder articles into My list",
                5
        );
        //Выводим в консоль название
        System.out.println("Найден " + title_first_article + " элемент с текстом.");


        //убеждаемся что есть вторая статья в открывшемся списке
        MainPageObject.waitForElementPresent(
                By.xpath("//*[@text='"+ title_second_article +"']"),
                "Cannot find folder articles into My list",
                5
        );
        //Выводим в консоль название
        System.out.println("Найден " + title_second_article + " элемент с текстом.");

        //удаление второй статьи свайпом влево
        MainPageObject.leftSwipe (200,831,1010,216,1023);


        //убеждаемся, что нет второй статьи в открывшемся списке
        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[@text='"+ title_second_article +"']"),
                "Cannot find title article into My list",
                5
        );
        //убеждаемся, что есть первая статья в открывшемся списке есть и кликаем на нее
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='"+ title_first_article +"']"),
                "Cannot find folder articles into My list",
                5
        );
        //Выводим в консоль название
        System.out.println("Найден " + title_first_article + " элемент с текстом после изменений в списке.");

        //снова получаем значение названия статьи
        String title_first_article_after_list_change = MainPageObject.waitForElementAndGetAttribute(
                By.xpath("//*[@text='Java (programming language)']"),
                "text",
                "Cannot find title of Article",
                15
        );
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
        driver.findElementByXPath("//*[@text='Skip']").click();

        // поиск элемента, затем кликаем по полю поиска
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find search input",
                5

        );
        String search_second_line = "Appium";
        //поиск второй статьи, отправка значения в поле поиска
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                search_second_line,
                "Cannot find search input",
                5
        );
        //кликаем на нужную статью
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id=\"org.wikipedia:id/page_list_item_title\" and @text=\"Appium\"]"),
                "Cannot find search input",
                5
        );

        // Проверяем, что у статьи есть элемент title
        MainPageObject.assertElementPresent(By.xpath("//*[@text=\"Appium\"]"));


    }


}



