package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.io.IOException;

public class CustomTab extends Tab {

    private VBox root;
    private CodeArea codeArea;
    private HBox searchBar;
    private TextField searchTf;
    private Button findNextBtn;

    public CustomTab(String text) throws IOException {
        super(text);
        root = new VBox(10);
        root.setPrefHeight(10000);
        root.setPrefWidth(10000);
        codeArea = new CodeArea();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        VBox.setMargin(codeArea, new Insets(10, 10, 5, 10));
        VBox.setVgrow(codeArea, Priority.ALWAYS);
        searchBar = new HBox(10);
        searchBar.setAlignment(Pos.CENTER_LEFT);
        searchBar.setPadding(new Insets(10, 10, 10, 10));
        searchBar.setStyle("-fx-background-color: #d2d2d2");
        searchTf = new TextField();
        HBox.setHgrow(searchTf, Priority.ALWAYS);
        findNextBtn = new Button("Find Next");
        searchBar.getChildren().addAll(searchTf, findNextBtn);
        root.getChildren().addAll(codeArea, searchBar);
        this.setContent(root);
    }

    public String getCodeAreaText() {
        return codeArea.getText();
    }

}




