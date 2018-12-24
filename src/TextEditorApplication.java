import ViewLoader.ViewLoader;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.swing.text.View;
import java.io.IOException;

public class TextEditorApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ViewLoader.load(null, "/view/main.fxml", "JextEditor", primaryStage);
    }
}
