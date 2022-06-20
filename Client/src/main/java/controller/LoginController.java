package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import model.User;
import services.IObserver;
import services.IServices;
import services.ServiceException;
import utils.Utils;

public class LoginController implements IController {

    @FXML
    public TextField usernameInput;
    @FXML
    public PasswordField passwordInput;
    @FXML
    public Button loginButton;
    @FXML
    public Label hiddenLabel;

    private MainController mainController = null;


    private IServices service = null;
    private Pane pane = null;
    private Stage stage;

    public LoginController() {
    }

    @Override
    public void load() {
        assignSignals();
        this.stage.setTitle("Login");
        this.stage.setScene(new Scene(pane));
        this.stage.show();
    }

    private void assignSignals() {
        loginButton.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent event) {
                try {
                    User user = loginUser();
                    System.out.println(user);
                    loadMainWindow(user);
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadMainWindow(User user) {
        System.out.println("Here");
        Stage _stage = new Stage();
        mainController.setStage(_stage);
        _stage.setOnCloseRequest(new EventHandler<WindowEvent>(){
            @Override
            public void handle(WindowEvent event) {
                try {
                    mainController.logout();
                    show();
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
            }
        });
        mainController.setLoggedUser(user);
        mainController.setService(service);
        usernameInput.setText("");
        passwordInput.setText("");
        mainController.load();
        this.stage.hide();
    }

    private User loginUser() throws ServiceException {
        String username = usernameInput.getText();
        String password = Utils.hashPassword(passwordInput.getText());
        User user = service.login(new User(username), mainController);
        if(user == null)
            throw new ServiceException("The credentials do not match");
        return user;
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
    }

    @Override
    public void show() {
        this.stage.show();
    }

    @Override
    public void hide() {
        this.stage.hide();
    }

    public void setPane(Pane pane){
        this.pane = pane;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

}
