package library.bot.domain;

public class User {
    private String userName;
    private int userId;

    public User(String userName)
    {
        this.userName = userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
}
