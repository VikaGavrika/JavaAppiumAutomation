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
}

