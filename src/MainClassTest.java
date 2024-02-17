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
        int actual = MainClass.secondGetLocalNumber();
        //проверка, сравнение чисел (второй способ)
        assertEquals("Метод getLocalNumber должен возвращать число 14", expected,actual);
    }

    //Test2
    @Test
    public void testGetClassNumber()
    {
        //ожидаемое число больше 45
        int expectedMinValue = 45;
        //число,которое возвращает метод
        int actual = MainClass.getClassNumber();
        Assert.assertTrue("Метод getClassNumber должен возвращать число больше 45", actual > expectedMinValue);
    }
}

