package controller;

import ViewLoader.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.TextStats;

import java.io.IOException;

public class MainController extends Controller<TextStats> {

    @FXML
    private MenuBar fileMb;
    @FXML
    private MenuItem newMi;
    @FXML
    private MenuItem openMi;
    @FXML
    private MenuItem closeMi;
    @FXML
    private TextArea textTa;
    @FXML
    private VBox containerAp;
    @FXML
    private TabPane filesTp;


    @FXML
    public void initialize() {
        newMi.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        filesTp.setOnMouseReleased(e -> {
            if (e.isMiddleButtonDown()) {
                Tab tab = filesTp.getSelectionModel().getSelectedItem();
                filesTp.getTabs().remove(tab);
            }
        });
    }

    public void handleNew(ActionEvent actionEvent) throws IOException {
        Tab tab = new Tab("untitled");
        tab.setContent(FXMLLoader.load(getClass().getResource("/view/template.fxml")));
        filesTp.getTabs().add(tab);
        filesTp.getSelectionModel().select(tab);
    }

    public void handleOpen(ActionEvent actionEvent) {

    }

    public void handleClose(ActionEvent actionEvent) {

    }
}
