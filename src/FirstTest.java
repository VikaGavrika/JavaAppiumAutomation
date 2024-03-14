import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;


public class FirstTest extends CoreTestCase {

    @Test
    public void testSearch() throws InterruptedException {
        driver.findElementByXPath("//*[@text='Skip']").click();

        //поиска строки элемента и клика
        waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find search input",
                5

        );
        //метод поиска элемента и отправки значения в поле
        waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Java",
                "Cannot find search input",
                5
        );

        //метод проверяющий, что поиск по значению "Java" работает корректно и находится нужная строчка а теме "Java"
        waitForElementPresent(
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
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find search input",
                5
        );
        //вводим значение в поле поиска
        waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Java",
                "Cannot find search input",
                5
        );
        //очищаем поле поиска
        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                5
        );

        //дожидаемся кнопки возврата и кликаем по ней
        waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Cannot find back-button to cancel search",
                5
        );
        //Метод, который проверяет,что после нажатия "Назад", мы вернулись на страницу, где нет элемента стрелки "назад"
        waitForElementNotPresent(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Cannot find back-button to cancel search",
                5
        );

    }

    @Test
    public void testCompareArticleTitle() {
        driver.findElementByXPath("//*[@text='Skip']").click();

        // поиск элемента, затем кликаем по полю поиска
        waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find search input",
                5

        );
        //поиск элемента и отправки значения в поле поиска
        waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Java",
                "Cannot find search input",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Object-oriented programming language']"),
                "Cannot find search input",
                5

        );
        //поиск заголовка нужной статьи
        WebElement title_element = waitForElementPresent(
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
        waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find search input",
                5

        );
        //поиск элемента и отправки значения в поле поиска
        waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                "appium",
                "Cannot find search input",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id=\"org.wikipedia:id/page_list_item_title\" and @text=\"Appium\"]"),
                "Cannot find search input",
                5

        );
        //поиск заголовка нужной статьи
        waitForElementPresent(
                By.xpath("//*[@text=\"Automation for Apps\"]"),
                "Cannot find article title",
                15
        );
        //swipeQuick();
        verticalSwipeToFindElement(
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
        WebElement inputElement = waitForElementPresent(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find search input",
                5
        );

        // Проверка текста
        String expectedText = "Search Wikipedia";
        assertElementHasText(inputElement, expectedText);

    }

    //Тест, который делает поиск по какому-то слову. Затем убеждается, найдены несколько статей со словом в листе результатов,
    // затем удаляет результаты поиска и убеждается что лист с результатами пуст
    @Test
    public void testSearchAndCanselSearch() {
        //Смахиваем онбординг
        driver.findElementByXPath("//*[@text='Skip']").click();
        //дожидаемся элемента строка поиска и кликаем по нему
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find search input",
                5
        );
        //вводим значение в поле поиска
        waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                "planet",
                "Cannot find search input",
                5
        );
        //находим элемент 'лист результатов' поиска
        WebElement resultsList = waitForElementPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Cannot find search resultList",
                5
        );
        //проверяем, что найдены несколько статей со словом planet в листе результатов
        String expectedText = "planet";
        assertMultipleSearchResultsWithText(resultsList, expectedText);


        //очищаем поле поиска, путем нажатия кнопки Закрытия
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find close button",
                5
        );

        //проверяем, что нет статей в листе результатов, есть пустой лист результатов
        waitForElementPresent(
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
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find search input",
                5
        );
        //вводим значение в поле поиска
        waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                "java",
                "Cannot find search input",
                5
        );
        //находим элемент 'лист результатов' поиска
        WebElement resultsList = waitForElementPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Cannot find search resultList",
                5
        );
        //проверяем, что в каждой статье в листе результатов есть ожидаемое слово
        String expectedText = "java";
        assertMultipleSearchResultsWithText(resultsList, expectedText);

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
        waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find search input",
                5

        );
        //поиск элемента и отправки значения в поле поиска
        waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Java",
                "Cannot find search input",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Object-oriented programming language']"),
                "Cannot find search input",
                5

        );
        //поиск заголовка нужной статьи
        waitForElementPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find article title",
                15
        );
        //нажать на кнопку с выпадающим списком
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc=\"More options\"]"),
                "Cannot find button to open article options",
                5
        );
        //нажать на кнопку настроек тулбара
        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id=\"org.wikipedia:id/customize_toolbar\"]"),
                "Cannot find button to open customize_toolbar",
                5
        );

        //перенос кнопки элемента по координатам
        moveButton (200,1010,693,1005,1748);

        //возврат к статье, нажав Назад
        waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Cannot find back-button to cancel search",
                5
        );
        //снова нажать на кнопку с выпадающим списком
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc=\"More options\"]"),
                "Cannot find button to open article options",
                5
        );
        //нажать на кнопку Save в выпадающем списке
        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id=\"org.wikipedia:id/page_save\"]"),
                "Cannot find options to add article to reading list",
                5
        );
        //в появившимся снэк-баре нажать кнопку добавления в список
        waitForElementAndClick(
                By.xpath("//*[@text=\"Add to list\"]"),
                "Cannot find button Add to list",
                5
        );
        //В появившемся мод окне в поле ввода ввести название списка, в этот список будем сохранять статью
        //задаем переменную с названием списка, тк будем исп-ть ее в нескольких местах
        String name_of_folder = "articles";

        waitForElementAndSendKeys(
                By.xpath("//*[@resource-id=\"org.wikipedia:id/text_input\"]"),
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );
        //нажать на кнопку ОК
        waitForElementAndClick(
                By.xpath("//*[@text=\"OK\"]"),
                "Cannot press ОК button",
                5
        );
        //нажать на кнопку в снэк баре View list
        waitForElementAndClick(
                By.xpath("//*[@text=\"View list\"]"),
                "Cannot press View list button",
                5
        );
        //нажать кнопку назад 3 раза, чтобы вернуться на главную страницу
        //цикл, повторяем код, пока не будет выполнено определенное условие.
        int i = 0;
        while (i < 3) {
            waitForElementAndClick(
                    By.xpath("//*[@content-desc='Navigate up']"),
                    "Cannot find back-button to cancel search",
                    5
            );
            i++;
        }
        //кнопка Saved в меню
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc=\"Saved\"]"),
                "Cannot find navigation Saved button to My list",
                5
        );
        //поиск списка статей по названию, название задано в переменную выше. клик на список статей
        waitForElementAndClick(
                By.xpath("//*[@text='"+name_of_folder+"']"),
                "Cannot find folder articles into My list",
                5
        );
        //удаление статьи свайпом влево
        leftSwipe (200,826,977,92,941);


        //убеждаемся, что нужной статьи нет в списке
        waitForElementNotPresent(
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
        waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find search input",
                5

        );
        //Зададим переменную, название статьи
        String search_line = "Linkin Park discography";

        //поиск элемента и отправки значения в поле поиска
        waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                search_line,
                "Cannot find search input",
                5
        );
        //задаем переменную, которые будем исп-ть неск раз
        String search_results_locator = "//*[@resource-id=\"org.wikipedia:id/search_results_list\"]/android.view.ViewGroup";
        //поиск элемента
        waitForElementPresent(
                By.xpath(search_results_locator),
                "Cannot find anything by the request " +search_line,
                15
        );
        int amount_of_search_results = getAmountOfElements(
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
        waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find search input",
                5

        );
        //Зададим переменную, название статьи
        String search_line = "ppppppppppp";

        //поиск элемента и отправки значения в поле поиска
        waitForElementAndSendKeys(
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
        waitForElementPresent(
                By.xpath(empty_result),
                "Cannot find empty result label by the request " +search_line,
                15

        );
        assertNoElementsPresentWithText(
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
        waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find search input",
                5

        );
        String search_line = "java";

        //поиск элемента и отправки значения в поле поиска
        waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                search_line,
                "Cannot find search input",
                5
        );
        //находим статью
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by" +search_line,
                5
        );
        //получаем название заголовок статьи до ротации, чтобы в дальнейшем ее сравнить после ротации экрана
        //аттрибут (текст), который будем получать, запишем в переменную
        String title_before_rotation = waitForElementAndGetAttribute(
                By.xpath("//*[@text='Java (programming language)']"),
                "text",
                "Cannot find title of Article",
                15
        );
        //повернуть телефон,в скобках указываем в какую сторону хотим повернуть альбомная- горизонт или портретная- вертик
        driver.rotate(ScreenOrientation.LANDSCAPE);

        //снова получаем значение названия статьи
        String title_after_rotation = waitForElementAndGetAttribute(
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
        String title_after_second_rotation = waitForElementAndGetAttribute(
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

    }

    //Тест12, который вводит значение в поиск, находить определенный элемент в результатах поиска,
    // потом приложение сворачиваем, через некоторое время разворачиваем, после открытия приложения проверяем,
    // что элемент, остался на месте.
    @Test
    public void testCheckSearchArticleInBackground(){
        driver.findElementByXPath("//*[@text='Skip']").click();

        // поиск элемента, затем кликаем по полю поиска
        waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find search input",
                5

        );
        //поиск элемента и отправки значения в поле поиска
        waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Java",
                "Cannot find search input",
                5
        );
        //находим нужную статью
        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Object-oriented programming language']"),
                "Cannot find search input",
                5
        );
        //отправляем приложение в бэкграунд
        driver.runAppInBackground(Duration.ofSeconds(5));
        //после этого приложение автоматически развернется

        //убеждаемся,что та же самая статья действительно присутствует
        waitForElementPresent(
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
        waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find search input",
                5

        );
        String search_line = "Java";
        //поиск первой статьи, отправка значения в поле поиска
        waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                search_line,
                "Cannot find search input",
                5
        );
        //поиск нужной статьи и клик
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Object-oriented programming language']"),
                "Cannot find article in results list",
                5

        );
        //получаем значение названия статьи
        String title_first_article = waitForElementAndGetAttribute(
                By.xpath("//*[@text='Java (programming language)']"),
                "text",
                "Cannot find title of Article",
                15
        );

        //нажать на кнопку с выпадающим списком
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc=\"More options\"]"),
                "Cannot find button to open article options",
                5
        );
        //необходимо настроить тулбар, чтобы в меню появилась кнопка Save
        //нажать на кнопку настроек тулбара
        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id=\"org.wikipedia:id/customize_toolbar\"]"),
                "Cannot find button to open customize_toolbar",
                5
        );

        //перенос элемента по координатам из категории тулбар в категорию меню
        moveButton(200, 1010, 693, 1005, 1748);

        //возврат к статье, нажав Назад
        waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Cannot find back-button to cancel search",
                5
        );
        //снова нажать на кнопку с выпадающим списком
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc=\"More options\"]"),
                "Cannot find button to open article options",
                5
        );
        //нажать на кнопку Save в выпадающем списке
        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id=\"org.wikipedia:id/page_save\"]"),
                "Cannot find options to add article to reading list",
                5
        );
        //в появившимся снэк-баре нажать кнопку добавления в список
        waitForElementAndClick(
                By.xpath("//*[@text=\"Add to list\"]"),
                "Cannot find button Add to list",
                5
        );
        //В появившемся мод окне в поле ввода ввести название списка, в этот список будем сохранять статью
        //задаем переменную с названием списка, тк будем исп-ть ее в нескольких местах
        String name_of_folder = "articles";

        waitForElementAndSendKeys(
                By.xpath("//*[@resource-id=\"org.wikipedia:id/text_input\"]"),
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );
        //нажать на кнопку ОК
        waitForElementAndClick(
                By.xpath("//*[@text=\"OK\"]"),
                "Cannot press ОК button",
                5

        );
        //поиск элемента строки поиска и клик по полю
        waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find search input",
                5

        );
        String search_second_line = "Appium";
        //поиск второй статьи, отправка значения в поле поиска
        waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                search_second_line,
                "Cannot find search input",
                5
        );
        //кликаем на нужную статью
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id=\"org.wikipedia:id/page_list_item_title\" and @text=\"Appium\"]"),
                "Cannot find search input",
                5

        );
        //получаем заголовок второй статьи

        String title_second_article= waitForElementAndGetAttribute(
                By.xpath("//*[@text=\"Appium\"]"),
                "text",
                "Cannot get title of Article",
                15
        );
        //нажать на кнопку с выпадающим списком
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc=\"More options\"]"),
                "Cannot find button to open article options",
                5
        );
        //нажать на кнопку Save в выпадающем списке
        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id=\"org.wikipedia:id/page_save\"]"),
                "Cannot find options to add article to reading list",
                5
        );
        //в появившимся снэк-баре нажать кнопку добавления в список
        waitForElementAndClick(
                By.xpath("//*[@text=\"Add to list\"]"),
                "Cannot find button Add to list",
                5
        );
        //в открывшемся батоншите списков найти нужный список и кликнуть
        waitForElementAndClick(
                By.xpath("//*[@text='"+name_of_folder+"']"),
                "Cannot find folder articles into My list",
                5
        );
        //нажать на кнопку в снэк баре View list
        waitForElementAndClick(
                By.xpath("//*[@text=\"View list\"]"),
                "Cannot press View list button",
                5
        );
        //убеждаемся, что есть заголовок первой статьи в открывшемся списке
        waitForElementPresent(
                By.xpath("//*[@text='"+ title_first_article +"']"),
                "Cannot find folder articles into My list",
                5
        );
        //Выводим в консоль название
        System.out.println("Найден " + title_first_article + " элемент с текстом.");


        //убеждаемся что есть вторая статья в открывшемся списке
        waitForElementPresent(
                By.xpath("//*[@text='"+ title_second_article +"']"),
                "Cannot find folder articles into My list",
                5
        );
        //Выводим в консоль название
        System.out.println("Найден " + title_second_article + " элемент с текстом.");

        //удаление второй статьи свайпом влево
        leftSwipe (200,831,1010,216,1023);


        //убеждаемся, что нет второй статьи в открывшемся списке
        waitForElementNotPresent(
                By.xpath("//*[@text='"+ title_second_article +"']"),
                "Cannot find title article into My list",
                5
        );
        //убеждаемся, что есть первая статья в открывшемся списке есть и кликаем на нее
        waitForElementAndClick(
                By.xpath("//*[@text='"+ title_first_article +"']"),
                "Cannot find folder articles into My list",
                5
        );
        //Выводим в консоль название
        System.out.println("Найден " + title_first_article + " элемент с текстом после изменений в списке.");

        //снова получаем значение названия статьи
        String title_first_article_after_list_change = waitForElementAndGetAttribute(
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
        waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Cannot find search input",
                5

        );
        String search_second_line = "Appium";
        //поиск второй статьи, отправка значения в поле поиска
        waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                search_second_line,
                "Cannot find search input",
                5
        );
        //кликаем на нужную статью
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id=\"org.wikipedia:id/page_list_item_title\" and @text=\"Appium\"]"),
                "Cannot find search input",
                5
        );

        // Проверяем, что у статьи есть элемент title
        assertElementPresent(By.xpath("//*[@text=\"Appium\"]"));


    }

    private void assertElementPresent(By by){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement titleElement = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        //проверка, что элемент присутствует, не null
        Assert.assertNotNull("Cannot find element", titleElement);
        // Проверка, что элемент отображается
        Assert.assertTrue("Element is not displayed", titleElement.isDisplayed());

    }




    //метод получения заголовка статьи
    private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInSeconds ){
        WebElement element = waitForElementPresent(by, error_message,timeoutInSeconds);
        return element.getAttribute(attribute);

    }




    //метод считает количество элементов, которые нашли
    private int getAmountOfElements(By by)
    {
        //Функция, которая создает список
        List elements = driver.findElements(by);
        //возвращаем кол-во элементов,которые были найдены
        return elements.size();
    }

    //метод, проверяющий, что не нашлось ни одного элемента с текстом из поиска
    private void assertNoElementsPresentWithText(By by, String search_line, String error_message){
        //получаем кол-во элементов
        int amount_of_elements = getAmountOfElements(by);
        // Вывод в консоль сколько всего результатов статей
        System.out.println("Найден " + amount_of_elements + " элемент с текстом.");
        //если нашли элемент, то проверяем, какой текст он содержит. Если содержит текст поиска,
        // то выдаем исключение с сообщением
        if (amount_of_elements>0) {
            //получаем список найденных элементов
            List <WebElement> elements = driver.findElements(by);
            // Проверить каждый элемент
            for (WebElement element : elements) {
                String elementText = element.getText();
                // Если элемент содержит ожидаемый текст, вывести сообщение об ошибке
                if (elementText.contains(search_line)) {
                    //передаем элемент by в строковое значение, предполагается, что этот элемент отсутствует
                    //формируем строку с этим элементом
                    String default_message = "An element '" + by.toString() + "'supposed to be not present";
                    //обозначает проблему, что этого элемента не должно быть, а он есть, и кидаем сообщение об этом
                    throw new AssertionError(default_message + " " + error_message);
                }
            }
            // Если ни один элемент не содержит текст с поиска, то успех
            System.out.println("No elements found with text: " + search_line);
        }

    }



    //метод, который проверяет наличие нескольких результатов поиска на странице с ожидаемым текстом
    private void assertMultipleSearchResultsWithText(WebElement resultsList, String expectedText) {
        // Получаем кол-во статей с ожидаемым словом
        int resultsCount = resultsList.findElements(By.id("org.wikipedia:id/page_list_item_title")).size();
        //считаем сколько заголовков содержит ожидаемый текст
        //инициализируем переменную, присваиваем значение 0 для инициализации счетчика
        int expectedTextResultsCount = 0;
        //перебираем в цикле все элементы в листе
        for (WebElement result : resultsList.findElements(By.id("org.wikipedia:id/page_list_item_title"))) {
            //получаем текстовое содержимое текущего WebElement (представляющего результат поиска) и преобразуем его
            // в нижний регистр, для более корректного сравнения в будущем, также проверяем, содержит ли expectedText.
            if (result.getText().toLowerCase().contains(expectedText)) {
                String resultText = result.getText().toLowerCase();
                // Проверка, что ожидаемое слово есть в каждом результате
                String errorMessage = "Ожидалось, что слово '" + expectedText + "' будет найдено в " + result.getText() + ", но его там нет.";
                assert resultText.contains(expectedText.toLowerCase()) : errorMessage;
                //Если ожидаемое слово найдено в текущем результате поиска, значение expectedWordResultsCount увеличивается на 1.
                // отслеживаем общее количество результатов поиска, содержащих ожидаемое слово
                expectedTextResultsCount++;
            }
        }

        // проверяем, что больше, чем один результат, содержит ожидаемое слово
        String errorMessage = "Ожидалось несколько результатов с ожидаемым текстом, но найдено только .. " + expectedTextResultsCount;
        assert expectedTextResultsCount > 1 : errorMessage;

        // Вывод в консоль сколько всего результатов статей
        System.out.println("Найдено " + resultsCount + " статей.");

        // Вывод в консоль сколько результатов содержит ожидаемое слово
        System.out.println("Найдено " + expectedTextResultsCount + " статей с текстом '" + expectedText + "'.");


    }


    //метод, который проверяет наличие ожидаемого текста у элемента.
    private void assertElementHasText(WebElement element, String expectedText) {
        String actualText = element.getText();
        assertEquals(
                "Element does not contain expected text",
                expectedText,
                actualText
        );
    }


    //метод, котрый будет искать элемент по любому атрибуту
    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSecond) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond);
        wait.withMessage(error_message + "\n");
        return wait.until(
                //ждем выполнения конкретного условия, ждем элемент by
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    //метод адаптированный, который ищет элемент с дефолтной задержкой в 3 сек
    private WebElement waitForElementPresent(By by, String error_message) {
        return waitForElementPresent(by, error_message, 3);
    }

    //метод, испол-я который тесты сначала будут дожидаться элемента, а после этого происзойдет клик
    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSecond) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSecond);
        element.click();
        return element;
    }

    //метод, испол-я который тесты сначала будут дожидаться элемента, а после этого происзойдет отправка текста
    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSecond) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSecond);
        element.sendKeys(value);
        return element;
    }

    //Метод отсутствия элемента на странице
    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSecond) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond);
        wait.withMessage(error_message + "\n");
        return wait.until(
                //ждем выполнения конкретного условия, ждем элемент by
                ExpectedConditions.invisibilityOfElementLocated(by)
        );

    }

    //метод очистки поля ввода
    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSecond) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSecond);
        element.clear();
        return element;

    }

    //НЕ РАБОТАЕТ метод свайпа снизу-вверх
    protected void swipeUp(int timeOfSwipe) {
        // Определяем размер экрана
        Dimension size = driver.manage().window().getSize();
        // Вычисляем координаты
        int startY = (int) (size.height * 0.70); // Начальная точка Y (70% от высоты экрана)
        int endY = (int) (size.height * 0.30); // Конечная точка Y (30% от высоты экрана)
        int centerX = size.width / 2; // Центральная точка X
        //для отслеживания выполнения свайпа добавила исключения и логирование
        try {
            TouchAction touchAction = new TouchAction(driver);
            System.out.println("Начинаю свайп...");
            PointOption pointOption = PointOption.point(centerX, startY);
            touchAction.press(pointOption).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(timeOfSwipe))).moveTo(PointOption.point(centerX, endY)).release().perform();
            System.out.println("Свайп выполнен успешно!");
        } catch (Exception e) {
            // Обработка исключения
            System.out.println("Ошибка при выполнении свайпа: " + e.getMessage());
        }
    }
    //РАБОТАЕТ метод свайпа снизу-вверх
    public void verticalSwipe(int timeOfSwipe) {
        Dimension size = driver.manage().window().getSize();
        int startY = (int) (size.height * 0.70);
        int endY = (int) (size.height * 0.30);
        int centerX = size.width / 2;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH,"finger");
        Sequence swipe = new Sequence(finger,1);

        System.out.println("Начинаю свайп...");

        //Двигаем палец на начальную позицию
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(timeOfSwipe),
                PointerInput.Origin.viewport(),centerX, startY));
        //Палец прикасается к экрану
        swipe.addAction(finger.createPointerDown(0));

        //Палец двигается к конечной точке
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(timeOfSwipe),
                PointerInput.Origin.viewport(),centerX, endY));

        //Убираем палец с экрана
        swipe.addAction(finger.createPointerUp(0));

        //Выполняем действия
        driver.perform(Arrays.asList(swipe));
        System.out.println("Свайп выполнен успешно!");
    }
    //Быстрый свайп
    protected void swipeQuick()
    {
        verticalSwipe(2000);

    }

    //метод, в котором будем свайпить до определенного элемента
    protected void verticalSwipeToFindElement(By by, String error_message, int max_swipes)
    {
        //поиск всех элементов и считаем кол-во найденных элементов
        //цикл будет работать (свайпить) пока функция не находит ни одного элемента, как только элемент найдется, цикл завершится
        //если превысим кол-во свайпов, то цикл остановится
        int already_swiped = 0;      //начальный счетчик свайпов
        while(driver.findElements(by).size() == 0)
        {
            //остановка цикла, если свайпы превысили макс значение
            if (already_swiped > max_swipes){
                //проверяем, что этого элемента все еще нет
                waitForElementPresent(by, "Cannot find element by swiping up. \n" +error_message,0);
                //если элемент нашелся, выходим с метода и идем дальше по коду
                return;
            }
            try {
                swipeQuick();
            }catch (Exception e) {
                // Обработка исключения
                System.out.println("Ошибка при выполнении свайпа: " + e.getMessage());
            }
            ++already_swiped;    //счетчик свайпов с каждым циклом
            // Вывод в консоль сколько свайпов было сделано
            System.out.println("сделано " + already_swiped + " свайпов");
        }
    }
    //метод перемещения элемента по координатам
    protected void moveButton(int timeOfSwipe, int startX, int startY, int endX,int endY) {

        // Нажатие и перемещение кнопки
        try {
            //создаем PointerInput
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH,"finger");
            //создаем последовательность действий
            Sequence move = new Sequence(finger,1);
            System.out.println("Начинаю перемещение кнопки...");
            move.addAction(finger.createPointerMove(Duration.ofMillis(timeOfSwipe),
                    PointerInput.Origin.viewport(),startX, startY));
            //Палец прикасается к экрану
            move.addAction(finger.createPointerDown(1));

            //Палец двигается к конечной точке
            move.addAction(finger.createPointerMove(Duration.ofMillis(timeOfSwipe),
                    PointerInput.Origin.viewport(),endX, endY));

            //Убираем палец с экрана
            move.addAction(finger.createPointerUp(1));

            //Выполняем действия
            driver.perform(Arrays.asList( move));

            System.out.println("Перемещение выполнено успешно!");
        } catch (Exception e) {
            // Обработка исключения
            System.out.println("Ошибка при перемещении кнопки: " + e.getMessage());


        }
    }
    //Метод свайпа влево
    public void leftSwipe(int timeOfSwipe, int startX, int startY, int endX,int endY) {
        try {
            //создаем PointerInput
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            //создаем последовательность действий
            Sequence swipe = new Sequence(finger, 1);
            System.out.println("Начинаю свайп влево...");
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(timeOfSwipe),
                    PointerInput.Origin.viewport(), startX, startY));
            //Палец прикасается к экрану
            swipe.addAction(finger.createPointerDown(0));

            //Палец двигается к конечной точке
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(timeOfSwipe),
                    PointerInput.Origin.viewport(), endX, endY));

            //Убираем палец с экрана
            swipe.addAction(finger.createPointerUp(0));

            //Выполняем действия
            driver.perform(Arrays.asList(swipe));

            System.out.println("Свайп влево выполнен успешно!");
        } catch (Exception e) {
            // Обработка исключения
            System.out.println("Ошибка при свайпе влево: " + e.getMessage());

        }
    }

}



