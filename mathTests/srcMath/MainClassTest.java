import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;


public class MainClassTest extends MainClass
{
    //первый способ
    @Test
    public void testGetLocalNumber()
    {
        //ожидаемое число
        int expected = 14;
        //число,которое возвращает метод
        int actual = this.getLocalNumber();
        //проверка, сравнение чисел
        Assert.assertTrue( "Метод getLocalNumber должен возвращать число 14",actual == expected);
    }

    //Второй способ
    @Test
    public void secondTestGetLocalNumber()
    {
        //ожидаемое число
        int expected = 14;
        //число,которое возвращает метод
        int actual = secondGetLocalNumber();
        //проверка, сравнение чисел (второй способ)
        Assert.assertEquals("Метод getLocalNumber должен возвращать число 14", expected,actual);
    }

    //Test2
    @Test
    public void testGetClassNumber()
    {
        //ожидаемое число больше 45
        int expectedMinValue = 45;
        //число,которое возвращает метод
        int actual = getClassNumber();
        Assert.assertTrue("Метод getClassNumber должен возвращать число больше 45", actual > expectedMinValue);
    }

    //Test3
    @Test
    public void  testGetClassString()
    {
        // Ожидаемая строка содержит подстроку1 "hello" или подстроку1"Hello"
        String expectedSubstring1 = "hello";
        String expectedSubstring2 = "Hello";

        // строка, которая возвращается
        String actual = getClassString();

        //проверка, что актуал строка содержит подстроку1 или подстроку2, если нет, то тест падает и выводим сообщение об ошибке
        Assert.assertTrue("Метод getClassString должен возвращать строку,  в которой есть подстрока \"hello\" или \"Hello\"", actual.contains(expectedSubstring1) || actual.contains(expectedSubstring2));

    }

}

