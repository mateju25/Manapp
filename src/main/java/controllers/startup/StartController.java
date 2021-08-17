package controllers.startup;

import com.dustinredmond.fxtrayicon.FXTrayIcon;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class StartController extends Application {
    public static Stage mainStage;
    public static FXTrayIcon trayIcon;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/mainPage.fxml")));
        mainStage.setTitle("Manapp");
        mainStage.setScene(new Scene(root));
        mainStage.getIcons().add(new Image("/images/icon.png"));
        mainStage.setResizable(false);
        mainStage.show();

        trayIcon = new FXTrayIcon(stage, getClass().getResource("/images/icon.png"));
        trayIcon.show();
        trayIcon.setTrayIconTooltip("Manage app");
        MenuItem menuItem = new MenuItem("Exit application");
        menuItem.setOnAction( e -> {
            Platform.exit();
            System.exit(0);
        });
        trayIcon.addMenuItem(menuItem);
    }

    public static void main(String[] args) {
        launch();
    }

}