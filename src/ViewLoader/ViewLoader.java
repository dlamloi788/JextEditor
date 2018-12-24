package ViewLoader;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewLoader {

    public static <T> void load(T model, String fxml, String title, Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(ViewLoader.class.getResource(fxml), null, null,
                controllerFactory -> {
                    try {
                        Controller<T> controller = (Controller<T>) controllerFactory.newInstance();
                        controller.model = model;
                        controller.stage = stage;
                        return controller;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                });
        Parent root = loader.load();
        stage.setTitle(title);
        stage.sizeToScene();
        stage.setScene(new Scene(root));
        stage.show();

    }


}
