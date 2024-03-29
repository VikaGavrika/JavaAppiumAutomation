package lib.UI;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MainPageObject {
    //инициализируем драйвер
    protected AppiumDriver driver;
    //пишем конструктор класса, к которому будут обращаться все наши тесты
    public MainPageObject(AppiumDriver driver){
        this.driver = driver;
    }
    //переносим все методы, которыми пользуются тесты


    public void assertElementPresent(By by){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement titleElement = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        //проверка, что элемент присутствует, не null
        Assert.assertNotNull("Cannot find element", titleElement);
        // Проверка, что элемент отображается
        Assert.assertTrue("Element is not displayed", titleElement.isDisplayed());
        // вывод в консоль
        System.out.println("Element is Present");

    }




    //метод получения заголовка статьи
    public String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInSeconds ){
        WebElement element = waitForElementPresent(by, error_message,timeoutInSeconds);
        return element.getAttribute(attribute);

    }




    //метод считает количество элементов, которые нашли
    public int getAmountOfElements(By by)
    {
        //Функция, которая создает список
        List elements = driver.findElements(by);
        //возвращаем кол-во элементов,которые были найдены
        return elements.size();
    }

    //метод, проверяющий, что не нашлось ни одного элемента с текстом из поиска
    public void assertNoElementsPresentWithText(By by, String search_line, String error_message){
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
    public void assertMultipleSearchResultsWithText(WebElement resultsList, String expectedText) {
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
    public void assertElementHasText(WebElement element, String expectedText) {
        String actualText = element.getText();
        assertEquals(
                "Element does not contain expected text",
                expectedText,
                actualText
        );
    }


    //метод, котрый будет искать элемент по любому атрибуту
    public WebElement waitForElementPresent(By by, String error_message, long timeoutInSecond) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond);
        wait.withMessage(error_message + "\n");
        return wait.until(
                //ждем выполнения конкретного условия, ждем элемент by
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    //метод адаптированный, который ищет элемент с дефолтной задержкой в 3 сек
    public WebElement waitForElementPresent(By by, String error_message) {
        return waitForElementPresent(by, error_message, 3);
    }

    //метод, испол-я который тесты сначала будут дожидаться элемента, а после этого происзойдет клик
    public WebElement waitForElementAndClick(By by, String error_message, long timeoutInSecond) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSecond);
        element.click();
        return element;
    }

    //метод, испол-я который тесты сначала будут дожидаться элемента, а после этого происзойдет отправка текста
    public WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSecond) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSecond);
        element.sendKeys(value);
        return element;
    }

    //Метод отсутствия элемента на странице
    public boolean waitForElementNotPresent(By by, String error_message, long timeoutInSecond) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond);
        wait.withMessage(error_message + "\n");
        return wait.until(
                //ждем выполнения конкретного условия, ждем элемент by
                ExpectedConditions.invisibilityOfElementLocated(by)
        );

    }

    //метод очистки поля ввода
    public WebElement waitForElementAndClear(By by, String error_message, long timeoutInSecond) {
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
    public void swipeQuick()
    {
        verticalSwipe(2000);

    }

    //метод, в котором будем свайпить до определенного элемента
    public void verticalSwipeToFindElement(By by, String error_message, int max_swipes)
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
    public void moveButton(int timeOfSwipe, int startX, int startY, int endX,int endY) {

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
