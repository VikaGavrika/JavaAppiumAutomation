public class MainClass
{
    //метод, который возвращает число
    public int getLocalNumber()
    {
        return 14;
    }
    //второй способ
    //метод, который всегда возвращает число 14
    public static int secondGetLocalNumber()
    {
        return 14;
    }

    //Test2
    //приватное поле класса
    private static int class_number = 20;
    //метод, который возвращает значение class_number
    public static int getClassNumber()
    {
        return class_number;
    }

    //Test3
    //приватное поле класса
    private final static String class_string = "Hello, world";
    //метод, который возвращает строку
    public static String getClassString() {
        return class_string;
    }

}

