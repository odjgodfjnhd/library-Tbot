package library.bot;
import java.time.LocalDate;
public class Main
{
    public static void main(String[] args)
    {
        String today = (LocalDate.now()).toString();
        System.out.println("Текущая дата: " + today);
    }
}
