package library.bot.domain;

import java.util.UUID;

public class User {
    private String userName;
    private String userId;

    public User(String userName)
    {
        this.userName = userName;
        this.userId = UUID.randomUUID().toString();
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
}
