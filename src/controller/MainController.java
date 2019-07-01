package controller;

import ViewLoader.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.Paragraph;
import org.reactfx.collection.LiveList;
import view.CustomTabView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class MainController extends Controller {

    final KeyCombination zoomCombination = new KeyCodeCombination(KeyCode.ADD, KeyCombination.SHORTCUT_DOWN);
    final KeyCombination zoomCombination_equal = new KeyCodeCombination(KeyCode.EQUALS, KeyCombination.SHORTCUT_DOWN);

    @FXML
    private MenuBar fileMb;
    @FXML
    private MenuItem newMi;
    @FXML
    private MenuItem openMi;
    @FXML
    private MenuItem closeMi;
    @FXML
    private MenuItem saveMi;
    @FXML
    private MenuItem findMi;
    @FXML
    private TextArea textTa;
    @FXML
    private VBox containerAp;
    @FXML
    private TabPane filesTp;

    private CodeArea currentCodeArea;

    @FXML
    public void initialize() {
        //Sets the key bindings to open a new file
        openMi.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_DOWN));
        //Sets the key bindings to create a new file (opens a new tab)
        newMi.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN));
        //Sets the key bindings to close the current tab
        closeMi.setAccelerator(new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN));
        //Sets the key bindings to save the file in the currenttab
        saveMi.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN));
        //Sets the key bindings to open the search bar
        findMi.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.SHORTCUT_DOWN));
        //Handles the Ctrl + Plus OR Ctrl + Equal for zoom in
        containerAp.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (zoomCombination.match(event) || zoomCombination_equal.match(event)) {
                System.out.printf("ZOOM!");
            }
        });
        /**
         * It is also possible to use KeyCombination.CONTROL_DOWN however, this will limit
         * the key combination to the control button only, which will prevent macs from using
         * the command key
         */

        //Closes the tab which was middle button clicked
        filesTp.setOnMouseReleased(e -> {
            if (e.isMiddleButtonDown()) {
                handleClose(null);
            }
        });

        //Observe the user changing to different tabs
        filesTp.getSelectionModel().selectedIndexProperty().addListener((o, oldValue, newValue) -> {
            //Once the user changes to a new tab, set the current code area to the code area in the
            //selected tab
            if (newValue.intValue() > -1) {
                currentCodeArea = (CodeArea) filesTp.getTabs().get(newValue.intValue()).getContent().lookup("#textCa");
            }
        });

    }

    public void handleNew(ActionEvent actionEvent) throws IOException {
        CustomTabView tab = new CustomTabView("untitled");
        filesTp.getTabs().add(tab);
        filesTp.getSelectionModel().select(tab);
    }

    public void handleOpen(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select item to open");
        fileChooser.showOpenDialog(stage);
    }

    public void handleClose(ActionEvent actionEvent) {
        Tab tab = filesTp.getSelectionModel().getSelectedItem();
        filesTp.getTabs().remove(tab);
    }

    public void handleSave(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As");
        File file = fileChooser.showSaveDialog(stage);
        LiveList<Paragraph<Collection<String>, String, Collection<String>>> paragraphs = currentCodeArea.getParagraphs();
        Iterator<Paragraph<Collection<String>, String, Collection<String>>> it = paragraphs.iterator();
        if (file != null) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
                while (it.hasNext()) {
                    writer.write(it.next().getText());
                    writer.newLine();
                }
                writer.flush();
                writer.close();
                filesTp.getSelectionModel().getSelectedItem().setText(file.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

     }

    public void handleFind(ActionEvent actionEvent) {
        CustomTabView currentTab = (CustomTabView) filesTp.getSelectionModel().getSelectedItem();
        currentTab.handleFind();
    }
}
