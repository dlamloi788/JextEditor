package view;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.awt.*;
import java.io.IOException;

public class CustomTab extends Tab {

    private VBox root;
    private CodeArea codeArea;
    private HBox searchBar;
    private TextField searchTf;
    private Button findNextBtn;
    private Button closeBtn;

    public CustomTab(String text) throws IOException {
        super(text);
        root = new VBox(5);
        root.setPrefHeight(10000);
        root.setPrefWidth(10000);
        codeArea = new CodeArea();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        VBox.setMargin(codeArea, new Insets(10, 10, 10, 10));
        VBox.setVgrow(codeArea, Priority.ALWAYS);
        searchBar = new HBox(10);
        searchBar.setAlignment(Pos.CENTER_LEFT);
        searchBar.setPadding(new Insets(10, 10, 10, 10));
        searchBar.setStyle("-fx-background-color: #d2d2d2");
        searchTf = new TextField();
        HBox.setHgrow(searchTf, Priority.ALWAYS);
        findNextBtn = new Button("Find Next");
        findNextBtn.setOnAction(e -> handleFind());
        //Create and style the close button
        closeBtn = new Button("\u00D7");
        closeBtn.setFont(Font.font(15));
        closeBtn.setPrefHeight(findNextBtn.getPrefHeight());
        closeBtn.setPrefWidth(findNextBtn.getPrefWidth());
        closeBtn.setBackground(Background.EMPTY);
        closeBtn.setPadding(new Insets(5));
        closeBtn.setOnAction(e -> hideSearchBar());

        searchBar.getChildren().addAll(searchTf, findNextBtn, closeBtn);
        root.getChildren().addAll(codeArea);
        this.setContent(root);
    }

    private void handleFind() {
        String searchString = getSearchText();
        
    }

    public String getCodeAreaText() {
        return codeArea.getText();
    }

    public void showSearchBar() {
        root.getChildren().add(searchBar);
    }

    private void hideSearchBar() {
        root.getChildren().remove(searchBar);
    }

    private String getSearchText() {
        return searchTf.getText();
    }



}




