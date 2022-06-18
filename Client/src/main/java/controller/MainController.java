package controller;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import model.User;
import services.IObserver;
import services.IServices;
import services.ServiceException;
import utils.Utils;

public class MainController implements IController, IObserver {

    private IServices service = null;
    private User user = null;
    private Parent parent = null;
    private Stage stage;
    private volatile boolean loaded = false;

    @Override
    public void load() {
        assignSignals();
        this.stage.setTitle("Main");
        if(!loaded) {
            this.stage.setScene(new Scene(parent));
            loaded = true;
        }
        this.stage.show();
    }

    private void assignSignals() {

    }

    public void logout() throws ServiceException {
        service.logout(user, this);
    }

    @Override
    public void setService(IServices service) {
        this.service = service;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setParent(Parent parent) {
        this.parent = parent;
    }

    @Override
    public void show() {
        this.stage.show();
    }

    @Override
    public void hide() {
        this.stage.hide();
    }

    public void setLoggedUser(User user) {
        this.user = user;
    }

}
