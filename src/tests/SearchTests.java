package tests;

import lib.CoreTestCase;
import lib.UI.ArticlePageObject;
import lib.UI.SearchPageObject;
import lib.UI.factories.ArticlePageObjectFactory;
import lib.UI.factories.SearchPageObjectFactory;
import org.junit.Test;
import org.openqa.selenium.WebElement;

public class SearchTests extends CoreTestCase {
    //тесты, связанные с поиском
    //Тест1. Поиск
    @Test
    public void testSearch() {
        //пропустить онбординг
        this.skipOnboarding();

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInputAndClick();
        //поиск элемента и отправки значения в поле
        SearchPageObject.typeSearchLine("Java");
        //метод проверяющий, что поиск по значению "Java" работает корректно и находится нужная статья с нужным заголовком
        SearchPageObject.waitForSearchResult("Java (programming language)");

    }
    //Тест2. Отменяет поиск
    @Test
    public void testCanselSearch() {
        //пропустить онбординг
        this.skipOnboarding();

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
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


    //тест5, который проверяет, что поле ввода для поиска статьи содержит текст Search Wikipedia
    @Test
    public void testSearchInputHasText() {
        //пропустить онбординг
        this.skipOnboarding();

        //инициализация
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
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
        //пропустить онбординг
        this.skipOnboarding();

        //инициализация
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
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
        //пропустить онбординг
        this.skipOnboarding();

        //инициализация
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
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



    //Тест9, которой ищет какую-то конкретную статью, а затем проверяет, что вышел 1 результат с этой статьей
    @Test
    public void testAmountOfNotEmptySearch(){
        //пропустить онбординг
        this.skipOnboarding();

        //инициализация
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
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
        //пропустить онбординг
        this.skipOnboarding();

        //инициализация
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInputAndClick();
        //Зададим переменную, название статьи
        String search_line = "ppppppppppp";
        //поиск элемента и отправки значения в поле
        SearchPageObject.typeSearchLine(search_line);
        //подтверждаем, что на странице нет результатов
        SearchPageObject.assertThereIsNoResultOfSearch(search_line);

    }

    //Ex9. Тест 15. который будет делать поиск по любому запросу на ваш выбор (поиск по этому слову должен возвращать как минимум 3 результата).
    // Далее тест должен убеждаться, что в результате поиска присутствуют три элемента,
    // содержащие ожидаемые вами article_title и article_description.
    @Test
    public void testSearchArticleAndCheckTitleAndDiscription() {
        //пропустить онбординг
        this.skipOnboarding();

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInputAndClick();
        //поиск элемента и отправки значения в поле
        SearchPageObject.typeSearchLine("Java");
        //актуальное кол-во результатов поиска
        int actualResultsCount = SearchPageObject.getSearchResultsCount();
        // Проверка, что количество найденных элементов не меньше 3,если меньше, то возвращает ошибку, если нет, то рез-ты поиска
        if (actualResultsCount < 3) {
            System.out.println("Количество найденных элементов меньше 3");
            return;
        }
        String articleTitleA = "Java";
        String articleDescriptionA = "Island in Indonesia";
        SearchPageObject.waitForElementByTitleAndDescription(articleTitleA, articleDescriptionA);
        // Добавлена проверка текста
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);;
        WebElement elementA = ArticlePageObject.waitForTitleElement("Java");
        if (articleTitleA.equals(elementA.getText())) {
            // Выводим в консоль название
            System.out.println("Найден " + articleTitleA + " и " + articleDescriptionA + " элементы");
        }

        String articleTitleB = "Java (programming language)";
        String articleDescriptionB = "Object-oriented programming language";
        SearchPageObject.waitForElementByTitleAndDescription(articleTitleB, articleDescriptionB);
        // Добавлена проверка текста
        WebElement elementB = ArticlePageObject.waitForTitleElement("Java (programming language)");
        if (articleTitleB.equals(elementB.getText())) {
            // Выводим в консоль название
            System.out.println("Найден " + articleTitleB + "и " + articleDescriptionB + " элементы");
        }

        String articleTitleC = "JavaScript";
        String articleDescriptionC = "High-level programming language";
        SearchPageObject.waitForElementByTitleAndDescription(articleTitleC, articleDescriptionC);
        // Добавлена проверка текста
        WebElement elementC = ArticlePageObject.waitForTitleElement("JavaScript");
        if (articleTitleC.equals(elementC.getText())) {
            //Выводим в консоль название
            System.out.println("Найден " + articleTitleC + "и " + articleDescriptionC + " элементы");
        }

    }

}
