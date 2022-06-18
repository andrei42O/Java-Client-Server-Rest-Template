package objprotocol.Requests;

import model.User;
import objprotocol.Request;

public class LogoutRequest implements Request {
    private User user = null;

    public LogoutRequest(User user) {
        this.user = user;
    }

    public User getUser(){
        return user;
    }
}
