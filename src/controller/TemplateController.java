package controller;

import ViewLoader.Controller;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

public class TemplateController extends Controller {

    @FXML
    private VBox rootVb;
    @FXML
    private CodeArea textCa;
    @FXML
    private HBox searchbox;

    @FXML
    public void initialize() {
        textCa.setParagraphGraphicFactory(LineNumberFactory.get(textCa));
        textCa.setStyle("-fx-border-color: #d1d1d1; -fx-border-width: 0.5px");
        searchbox.setStyle("-fx-background-color: #d2d2d2;");
        rootVb.getChildren().remove(searchbox);

    }


}
