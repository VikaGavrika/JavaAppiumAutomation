import lib.CoreTestCase;
import lib.UI.MainPageObject;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;

import java.time.Duration;


public class FirstTest extends CoreTestCase {

    private MainPageObject MainPageObject;
    protected void setUp() throws Exception{
        super.setUp();
        MainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testSearch() throws InterruptedException {
        driver.findElementByXPath("//*[@text='Skip']").click();

        //поиска строки элемента и клика
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find search input",
                5

        );
        //метод поиска элемента и отправки значения в поле
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Java",
                "Cannot find search input",
                5
        );

        //метод проверяющий, что поиск по значению "Java" работает корректно и находится нужная строчка а теме "Java"
        MainPageObject.waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                15
        );

    }

    @Test
    public void testCanselSearch() {
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
                "Java",
                "Cannot find search input",
                5
        );
        //очищаем поле поиска
        MainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                5
        );

        //дожидаемся кнопки возврата и кликаем по ней
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Cannot find back-button to cancel search",
                5
        );
        //Метод, который проверяет,что после нажатия "Назад", мы вернулись на страницу, где нет элемента стрелки "назад"
        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Cannot find back-button to cancel search",
                5
        );

    }

    @Test
    public void testCompareArticleTitle() {
        driver.findElementByXPath("//*[@text='Skip']").click();

        // поиск элемента, затем кликаем по полю поиска
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find search input",
                5

        );
        //поиск элемента и отправки значения в поле поиска
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Java",
                "Cannot find search input",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Object-oriented programming language']"),
                "Cannot find search input",
                5

        );
        //поиск заголовка нужной статьи
        WebElement title_element = MainPageObject.waitForElementPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find article title",
                15
        );
        //получаем название статьи, текст этой статьи
        String article_title = title_element.getAttribute("text");
        //использвем это название статьи для сравнения
        assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_title
        );

    }

    //Тест, свайп до конца страницы до текста в футере
    @Test
    public void testSwipeArticle() {
        driver.findElementByXPath("//*[@text='Skip']").click();

        // поиск элемента, затем кликаем по полю поиска
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find search input",
                5

        );
        //поиск элемента и отправки значения в поле поиска
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                "appium",
                "Cannot find search input",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id=\"org.wikipedia:id/page_list_item_title\" and @text=\"Appium\"]"),
                "Cannot find search input",
                5

        );
        //поиск заголовка нужной статьи
        MainPageObject.waitForElementPresent(
                By.xpath("//*[@text=\"Automation for Apps\"]"),
                "Cannot find article title",
                15
        );
        //swipeQuick();
        MainPageObject.verticalSwipeToFindElement(
                By.xpath("//*[@text=\"View article in browser\"]"),
               "Cannot find the end of the article",
                6
        );

    }


    //тест, который проверяет, что поле ввода для поиска статьи содержит текст Search Wikipedia
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

    //Тест, который делает поиск по какому-то слову. Затем убеждается, найдены несколько статей со словом в листе результатов,
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

    //тест, который делает поиск по какому-то слову. Затем убеждается, что в каждом результате поиска есть это слово.
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
    //Поиск определенной статьи, выбрать статью, нажать на кнопку с выпадающем списком, после открытия выбрать
    // и нажать на кнопку из списка, в батоншите создать новый список (нажав на кнопку), ввести название списка в поле,
    // нажать ОК, выйти из статьи, нажать на кнопку списки, перейти на экран со спискими, выбрать один их них, нажать,
    // убедиться что в списке присутствует выбранная статья, удалить статью,
    // убедиться, что она удалена, тест будет считаться законченным
    @Test
    public void testSavedFirstArticleToMyList(){
        driver.findElementByXPath("//*[@text='Skip']").click();

        // поиск элемента, затем кликаем по полю поиска
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find search input",
                5

        );
        //поиск элемента и отправки значения в поле поиска
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Java",
                "Cannot find search input",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Object-oriented programming language']"),
                "Cannot find search input",
                5

        );
        //поиск заголовка нужной статьи
        MainPageObject.waitForElementPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find article title",
                15
        );
        //нажать на кнопку с выпадающим списком
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc=\"More options\"]"),
                "Cannot find button to open article options",
                5
        );
        //нажать на кнопку настроек тулбара
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id=\"org.wikipedia:id/customize_toolbar\"]"),
                "Cannot find button to open customize_toolbar",
                5
        );

        //перенос кнопки элемента по координатам
        MainPageObject.moveButton (200,1010,693,1005,1748);

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
        //нажать на кнопку в снэк баре View list
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text=\"View list\"]"),
                "Cannot press View list button",
                5
        );
        //нажать кнопку назад 3 раза, чтобы вернуться на главную страницу
        //цикл, повторяем код, пока не будет выполнено определенное условие.
        int i = 0;
        while (i < 3) {
            MainPageObject.waitForElementAndClick(
                    By.xpath("//*[@content-desc='Navigate up']"),
                    "Cannot find back-button to cancel search",
                    5
            );
            i++;
        }
        //кнопка Saved в меню
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc=\"Saved\"]"),
                "Cannot find navigation Saved button to My list",
                5
        );
        //поиск списка статей по названию, название задано в переменную выше. клик на список статей
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='"+name_of_folder+"']"),
                "Cannot find folder articles into My list",
                5
        );
        //удаление статьи свайпом влево
        MainPageObject.leftSwipe (200,826,977,92,941);


        //убеждаемся, что нужной статьи нет в списке
        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article",
                15
        );

    }
    //Тест, которой ищет какую-то конкретную статью, а затем проверяет, что вышел 1 результат с этой статьей
    @Test
    public void testAmountOfNotEmptySearch(){
        driver.findElementByXPath("//*[@text='Skip']").click();

        // поиск элемента, затем кликаем по полю поиска
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find search input",
                5

        );
        //Зададим переменную, название статьи
        String search_line = "Linkin Park discography";

        //поиск элемента и отправки значения в поле поиска
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                search_line,
                "Cannot find search input",
                5
        );
        //задаем переменную, которые будем исп-ть неск раз
        String search_results_locator = "//*[@resource-id=\"org.wikipedia:id/search_results_list\"]/android.view.ViewGroup";
        //поиск элемента
        MainPageObject.waitForElementPresent(
                By.xpath(search_results_locator),
                "Cannot find anything by the request " +search_line,
                15
        );
        int amount_of_search_results = MainPageObject.getAmountOfElements(
                By.xpath(search_results_locator)
        );
        //убеждаемся, что кол-во полученных элементов больше нуля
        Assert.assertTrue(
                "We found too few results",
                amount_of_search_results > 0
        );


    }
    //Тест10, который будет проверять, что результаты поиска не содержат элементы с определенным текстом.
    // И есть элемент "нет результатов"
    @Test
    public void testAmountOfEmptySearch(){
        driver.findElementByXPath("//*[@text='Skip']").click();

        // поиск элемента, затем кликаем по полю поиска
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find search input",
                5

        );
        //Зададим переменную, название статьи
        String search_line = "ppppppppppp";

        //поиск элемента и отправки значения в поле поиска
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                search_line,
                "Cannot find search input",
                5
        );
        //задаем переменную, которые будем исп-ть неск раз
        String search_results_locator = "//*[@resource-id=\"org.wikipedia:id/search_results_list\"]/android.view.ViewGroup";
        //создаем локатор, в котором будем искать текст No results
        String empty_result = "//*[@text = 'No results']";
        //поиск элемента
        MainPageObject.waitForElementPresent(
                By.xpath(empty_result),
                "Cannot find empty result label by the request " +search_line,
                15

        );
        MainPageObject.assertNoElementsPresentWithText(
                By.xpath(search_results_locator),
                search_line,
                "We've found some results by request" + search_line
        );

    }
    //Тест11, который вводит значение в поиск, выбирает статью, после поворачивать экран телефона,
    // проверять, что название статьи не изменилось
    @Test
    public void testChangeScreenOrientationOnSearchResults(){
        driver.findElementByXPath("//*[@text='Skip']").click();

        //поиска строки элемента и клика
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find search input",
                5

        );
        String search_line = "java";

        //поиск элемента и отправки значения в поле поиска
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                search_line,
                "Cannot find search input",
                5
        );
        //находим статью
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by" +search_line,
                5
        );
        //получаем название заголовок статьи до ротации, чтобы в дальнейшем ее сравнить после ротации экрана
        //аттрибут (текст), который будем получать, запишем в переменную
        String title_before_rotation = MainPageObject.waitForElementAndGetAttribute(
                By.xpath("//*[@text='Java (programming language)']"),
                "text",
                "Cannot find title of Article",
                15
        );
        //повернуть телефон,в скобках указываем в какую сторону хотим повернуть альбомная- горизонт или портретная- вертик
        driver.rotate(ScreenOrientation.LANDSCAPE);

        //снова получаем значение названия статьи
        String title_after_rotation = MainPageObject.waitForElementAndGetAttribute(
                By.xpath("//*[@text='Java (programming language)']"),
                "text",
                "Cannot find title of Article",
                15
        );
        //Сравниваем два значения
        Assert.assertEquals(
                "Article title have been changed after rotation",
                title_before_rotation,
                title_after_rotation
        );
        //сделаем еще одну ротацию
        driver.rotate(ScreenOrientation.PORTRAIT);

        //снова получаем значение названия статьи после ротации
        String title_after_second_rotation = MainPageObject.waitForElementAndGetAttribute(
                By.xpath("//*[@text='Java (programming language)']"),
                "text",
                "Cannot find title of Article",
                15
        );

        // сравниваем два значения
        Assert.assertEquals(
                "Article title have been changed after rotation",
                title_before_rotation,
                title_after_second_rotation
        );
        //еще раз перевернем, чтобы проверить, вернет ли метод экран в портретную ориентацию
        driver.rotate(ScreenOrientation.LANDSCAPE);
        //переворот экрана в вертикальную ориентацию, кроме случаев, когда уже в вертикальной ориентации
        MainPageObject.resetScreenOrientation();

    }

    //Тест12, который вводит значение в поиск, находить определенный элемент в результатах поиска,
    // потом приложение сворачиваем, через некоторое время разворачиваем, после открытия приложения проверяем,
    // что элемент, остался на месте.
    @Test
    public void testCheckSearchArticleInBackground(){
        driver.findElementByXPath("//*[@text='Skip']").click();

        // поиск элемента, затем кликаем по полю поиска
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find search input",
                5

        );
        //поиск элемента и отправки значения в поле поиска
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Java",
                "Cannot find search input",
                5
        );
        //находим нужную статью
        MainPageObject.waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Object-oriented programming language']"),
                "Cannot find search input",
                5
        );
        //отправляем приложение в бэкграунд
        driver.runAppInBackground(Duration.ofSeconds(5));
        //после этого приложение автоматически развернется

        //убеждаемся,что та же самая статья действительно присутствует
        MainPageObject.waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Object-oriented programming language']"),
                "Cannot find article after returned from background",
                5
        );

    }
    //Tecт13, сохранить две статьи в список, одну статью удалить, убелиться, что вторая статья осталась,
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
        Assert.assertEquals(
                "Article title have been changed after rotation",
                title_first_article,
                title_first_article_after_list_change
        );


    }
    //Тест14 тест, который открывает статью и убеждается, что у нее есть элемент title.  тест не должен
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



