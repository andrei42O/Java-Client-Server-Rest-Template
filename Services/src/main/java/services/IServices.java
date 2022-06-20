package services;

import model.GameConfiguration;
import model.User;

public interface IServices {

    User login(User user, IObserver observer) throws ServiceException;
    void logout(User user, IObserver observer) throws ServiceException;
}
