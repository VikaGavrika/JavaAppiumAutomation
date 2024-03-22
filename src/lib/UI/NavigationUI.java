package lib.UI;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class NavigationUI extends MainPageObject{
    private static final String
            MY_LIST_LINK = "//android.widget.FrameLayout[@content-desc=\"Saved\"]";

    public NavigationUI(AppiumDriver driver){
        super(driver);
    }
    //навигация по приложению
    public void clickMyLists(){
        //кнопка Saved в меню
        this.waitForElementAndClick(
                By.xpath(MY_LIST_LINK),
                "Cannot find navigation Saved button to My list",
                5
        );
    }


}
