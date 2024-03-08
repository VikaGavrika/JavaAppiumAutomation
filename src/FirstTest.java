import lib.CoreTestCase;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FirstTest extends CoreTestCase {

    @Test
    public void testSearch() throws InterruptedException
    {
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

        //метод проверяющий,что поиск по значению "Java" работает корректно и находится нужная строчка а теме "Java"
        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                15
        );

    }

    @Test
    public void testCanselSearch()
    {
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
    public void testCompareArticleTitle()
    {
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
                By.xpath("//*[@resource-id='pcs']//*[@text='Java (programming language)']"),
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
    //тест, который проверяет, что поле ввода для поиска статьи содержит текст Search Wikipedia
    @Test
    public void testSearchInputHasText()
    {
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
    public void testSearchAndCanselSearch()
    {
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
        WebElement resultsList= waitForElementPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Cannot find search resultList",
                5
        );
        //проверяем, что найдены несколько статей со словом planet в листе результатов
        String expectedText = "planet";
        assertMultipleSearchResultsWithText( resultsList ,expectedText);


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
    public void testSearchTextAndCheckTextInTitles()
    {
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
        WebElement resultsList= waitForElementPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Cannot find search resultList",
                5
        );
        //проверяем, что в каждой статье в листе результатов есть ожидаемое слово
        String expectedText = "java";
        assertMultipleSearchResultsWithText( resultsList ,expectedText);

    }


    //метод, который проверяет наличие нескольких результатов поиска на странице с ожидаемым текстом
    private void assertMultipleSearchResultsWithText (WebElement resultsList,  String expectedText)
    {
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
    private void assertElementHasText (WebElement element, String expectedText)
    {
        String actualText = element.getText();
        assertEquals(
                "Element does not contain expected text",
                expectedText,
                actualText
        );
    }


    //метод, котрый будет искать элемент по любому атрибуту
    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSecond)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond);
        wait.withMessage(error_message +"\n");
        return wait.until(
                //ждем выполнения конкретного условия, ждем элемент by
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }
    //метод адаптированный, который ищет элемент с дефолтной задержкой в 3 сек
    private WebElement waitForElementPresent(By by, String error_message)
    {
       return waitForElementPresent(by, error_message, 3);
    }

    //метод, испол-я который тесты сначала будут дожидаться элемента, а после этого происзойдет клик
    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSecond)
    {
        WebElement element = waitForElementPresent(by,error_message,timeoutInSecond);
        element.click();
        return element;
    }
    //метод, испол-я который тесты сначала будут дожидаться элемента, а после этого происзойдет отправка текста
    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSecond)
    {
        WebElement element = waitForElementPresent(by,error_message,timeoutInSecond);
        element.sendKeys(value);
        return element;
    }

    //Метод отсутствия элемента на странице
    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSecond)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond);
        wait.withMessage(error_message +"\n");
        return wait.until(
                //ждем выполнения конкретного условия, ждем элемент by
                ExpectedConditions.invisibilityOfElementLocated(by)
        );

    }

    //метод очистки поля ввода
    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSecond)
    {
        WebElement element = waitForElementPresent(by,error_message,timeoutInSecond);
        element.clear();
        return element;

    }


}


