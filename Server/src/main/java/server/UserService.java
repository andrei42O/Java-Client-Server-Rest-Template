package server;

import model.User;
import persistance.IRepositoryORMUser;
import services.IObserver;
import services.IServices;
import services.ServiceException;

import java.util.HashMap;
import java.util.Objects;

public class UserService implements IServices {
    private IRepositoryORMUser repoUser = null;

    private HashMap<String, IObserver> loggedUsers = new HashMap<>();

    public UserService(IRepositoryORMUser repoUser) {
        this.repoUser = repoUser;
    }

    public UserService() {
    }

    @Override
    public User login(User user, IObserver observer) throws ServiceException {
        if(user == null)
            return null;
        User foundUser = repoUser.findByUsername(user.getUsername());
       if(foundUser != null && !loggedUsers.containsKey(user.getUsername()) && foundUser.getPassword().equals(user.getPassword())){
           loggedUsers.put(user.getUsername(), observer);
           return foundUser;
       }
       return null;
    }

    @Override
    public void logout(User user, IObserver observer) throws ServiceException {
        if(user != null && loggedUsers.containsKey(user.getUsername())){
            loggedUsers.remove(user.getUsername());
        } else{
            throw new ServiceException("The logout couldn't be done...");
        }
        System.out.println(loggedUsers.size());
    }

    public void setRepoUser(IRepositoryORMUser repoUser) {
        this.repoUser = repoUser;
    }
}
