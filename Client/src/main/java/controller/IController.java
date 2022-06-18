package controller;

import javafx.scene.Parent;
import javafx.stage.Stage;
import services.IServices;

public interface IController {
    void load();
    void setService(IServices service);
    void setStage(Stage stage);
    void setParent(Parent parent);
    void show();
    void hide();
}
