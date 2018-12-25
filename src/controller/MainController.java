package controller;

import ViewLoader.Controller;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import model.TextStats;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.Paragraph;
import org.reactfx.collection.LiveList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Key;
import java.util.Collection;
import java.util.Iterator;

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
    private MenuItem saveMi;
    @FXML
    private TextArea textTa;
    @FXML
    private VBox containerAp;
    @FXML
    private TabPane filesTp;


    @FXML
    public void initialize() {
        Tab tab = filesTp.getSelectionModel().getSelectedItem();
        //Sets the key bindings to open a new file
        openMi.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        //Sets the key bindings to create a new file (opens a new tab)
        newMi.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        //Sets the key bindings to close the current tab
        closeMi.setAccelerator(new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN));
        //Sets the key bindings to save the file in the currenttab
        saveMi.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

        //Closes the tab which was middle button clicked
        filesTp.setOnMouseReleased(e -> {
            if (e.isMiddleButtonDown()) {
                handleClose(null);
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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select item to open");
        fileChooser.showOpenDialog(stage);
    }

    public void handleClose(ActionEvent actionEvent) {
        Tab tab = filesTp.getSelectionModel().getSelectedItem();
        filesTp.getTabs().remove(tab);
    }

    public void handleSave(ActionEvent actionEvent) {
        Tab tab = filesTp.getSelectionModel().getSelectedItem();
        CodeArea codeArea = (CodeArea) tab.getContent().lookup("#textCa");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As");
        File file = fileChooser.showSaveDialog(stage);
        LiveList<Paragraph<Collection<String>, String, Collection<String>>> paragraphs = codeArea.getParagraphs();
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
                tab.setText(file.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

     }
}
