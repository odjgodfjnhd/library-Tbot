package library.bot.utils;

public interface Utils {
    public static void askUserName()
    {
        System.out.print("Введите свой userName: ");
    }

    public static void askBookName(String userName)
    {
        System.out.print("Отлично, " + userName + "! Теперь введите название книги: ");
    }

    public static void askAuthorName(String bookName)
    {
        System.out.print("Хорошо, но для книги " + " \"" + bookName + "\" нужен автор. Введите автора книги: ");
    }


}
