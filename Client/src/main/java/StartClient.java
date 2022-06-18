import controller.LoginController;
import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import objprotocol.ObjectProxy;
import services.IServices;

import java.io.IOException;
import java.util.Properties;

public class StartClient extends Application {
    private static int defaultPort = 55555;
    private static String defaultIP = "localhost";
    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties props = new Properties();
        try{
            props.load(StartClient.class.getResourceAsStream("/client.properties"));
            System.out.println("Client props set");
            props.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find client.properties " + e);
            return;
        }
        String ip = props.getProperty("server.host", defaultIP);
        int port = defaultPort;
        try{
            port = Integer.parseInt(props.getProperty("server.port"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        System.out.println("Client props\nIP: " + ip + "\nPORT: " + port);

        IServices server = new ObjectProxy(ip, port);

        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("view/login.fxml")
        );
        Pane pane = (Pane) loader.load();
        LoginController loginController = loader.<LoginController>getController();
        loginController.setService(server);
        loginController.setStage(primaryStage);
        loginController.setPane(pane);


        FXMLLoader mainLoader = new FXMLLoader(
                getClass().getClassLoader().getResource("view/main.fxml")
        );
        Parent mainRoot = mainLoader.load();
        MainController mainController = mainLoader.<MainController>getController();
        mainController.setService(server);
        mainController.setParent(mainRoot);

        loginController.setMainController(mainController);
        loginController.load();
    }
}
