package controller;

import ViewLoader.Controller;
import ViewLoader.ViewLoader;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableIntegerValue;
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
import javafx.stage.Stage;
import model.Settings;
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
import java.util.prefs.Preferences;

public class MainController extends Controller {

    final KeyCombination zoomCombination = new KeyCodeCombination(KeyCode.ADD, KeyCombination.SHORTCUT_DOWN);
    final KeyCombination zoomCombination_equal = new KeyCodeCombination(KeyCode.EQUALS, KeyCombination.SHORTCUT_DOWN);

    private IntegerProperty fontSize = new SimpleIntegerProperty(11);
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
    private MenuItem settingsMi;
    @FXML
    private TextArea textTa;
    @FXML
    private VBox containerAp;
    @FXML
    private TabPane filesTp;

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


        //Closes the tab which was middle button clicked
        filesTp.setOnMouseReleased(e -> {
            if (e.isMiddleButtonDown()) {
                handleClose(null);
            }
        });

        fontSize.addListener((observable, oldValue, newValue) -> {
            //changeFontForAll(newValue.intValue());
        });
    }

    public void handleNew(ActionEvent actionEvent) throws IOException {
        CustomTabView tab = new CustomTabView("untitled", fontSize.get());
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
        /*
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
        } */
     }

    public void handleFind(ActionEvent actionEvent) {
        CustomTabView currentTab = (CustomTabView) filesTp.getSelectionModel().getSelectedItem();
        currentTab.handleFind();
    }

    public void handleSettings(ActionEvent actionEvent) throws IOException {
        ViewLoader.load(Settings.getInstance(), "/view/settings.fxml", "Settings", new Stage());
    }

}
