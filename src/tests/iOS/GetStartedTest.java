package tests.iOS;

import lib.UI.WelcomePageObject;
import lib.iOSTestCase;
import org.junit.Test;

public class GetStartedTest extends iOSTestCase {
    @Test
    public void testPassThroughWelcome(){
        //инициализация
        WelcomePageObject WelcomePageObject = new WelcomePageObject(driver);
        //находим ссылку на 1 онбор
        WelcomePageObject.waitForLearnMoreLink();
        //клик на кнопке некст
        WelcomePageObject.clickNextButton();
        //находим заголовок 2 онборд
        WelcomePageObject.waitForOnboardingTitle("New ways to explore");
        //клик на кнопке некст
        WelcomePageObject.clickNextButton();
        //находим заголовок 3 онборд
        WelcomePageObject.waitForOnboardingTitle("Search in over 300 languages");
        //клик на кнопке некст
        WelcomePageObject.clickNextButton();
        //находим заголовок 4 онборд
        WelcomePageObject.waitForOnboardingTitle("Data & Privacy");
        //клик на кнопке get started
        WelcomePageObject.clickGetStartedButton();


    }



}
