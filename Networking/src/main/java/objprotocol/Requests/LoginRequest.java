package objprotocol.Requests;

import model.User;
import objprotocol.Request;

public class LoginRequest implements Request {
    private User user = null;

    public LoginRequest(User user) {
        this.user = user;
    }

    public User getUser(){
        return user;
    }
}
