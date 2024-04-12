package lib.UI.ios;

import io.appium.java_client.AppiumDriver;
import lib.UI.SearchPageObject;

public class iOSSearchPageObject extends SearchPageObject {

    //переносим необходимые константы
    static {
        SEARCH_INPUT = "xpath://*[@name='Search Wikipedia']";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://XCUIElementTypeStaticText[contains(@name,'{SUBSTRING}')]";
        SEARCH_RESULTS_TITLE_TPL = "//XCUIElementTypeStaticText[contains(@name,'{SUBSTRING_TITLE}')]";
        SEARCH_RESULTS_DESCRIPTION_TPL = "//XCUIElementTypeStaticText[contains(@name,'{SUBSTRING_DESCRIPTION}')]";
        //локатор кнопки возврата
        SEARCH_CANCEL_BUTTON = "id:Back";

        //локатор кнопки закрытия
        SEARCH_CLOSE_BUTTON = "id:Clear text";
        SEARCH_RESULT_ELEMENT = "xpath://XCUIElementTypeStaticText";
        SEARCH_RESULT_ELEMENTS = "//XCUIElementTypeStaticText";
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://XCUIElementTypeStaticText[@name,'No results found']";
        RESULT_LIST = "xpath:XCUIElementTypeCollectionView";
        EMPTY_RESULT_LIST = "xpath:XCUIElementTypeOther";
    }
    public iOSSearchPageObject(AppiumDriver driver){
        super(driver);
    }
}
