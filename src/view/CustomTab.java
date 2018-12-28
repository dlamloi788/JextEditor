package view;

import com.sun.istack.internal.Nullable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import model.Index;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.io.IOException;
import java.util.ArrayList;

public class CustomTab extends Tab {

    /*
     * Declaring the leaf nodes up at the top of the class
     */
    private VBox root;
    private CodeArea codeArea;
    private HBox findBar;
    private TextField findTf;
    private Button findNextBtn;
    private Button closeBtn;

    /**
     * Any fields required (flags)
     */

    //Flags whether the user is searching for a case or not
    private boolean isFinding = false;

    //Stores the search index the user is currently up to
    private int findIndex = 0;

    //Stores the indicies for each matching word
    private ArrayList<Index> indices;

    public CustomTab(String text) throws IOException {
        super(text);
        root = new VBox(5);
        root.setPrefHeight(10000);
        root.setPrefWidth(10000);
        codeArea = new CodeArea();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        VBox.setMargin(codeArea, new Insets(10, 10, 10, 10));
        VBox.setVgrow(codeArea, Priority.ALWAYS);
        findBar = new HBox(10);
        findBar.setAlignment(Pos.CENTER_LEFT);
        findBar.setPadding(new Insets(10, 10, 10, 10));
        findBar.setStyle("-fx-background-color: #d2d2d2");
        findTf = new TextField();
        findTf.textProperty().addListener((observable, oldValue, newValue) -> {
           if (!oldValue.equals(newValue) && indices != null) {
               indices.clear();
               isFinding = false;
               findIndex = 0;
           }
        });
        HBox.setHgrow(findTf, Priority.ALWAYS);
        findNextBtn = new Button("Find Next");
        findNextBtn.setOnAction(e -> handleFind());
        //Create and style the close button
        closeBtn = new Button("\u00D7");
        closeBtn.setFont(Font.font(15));
        closeBtn.setPrefHeight(findNextBtn.getPrefHeight());
        closeBtn.setPrefWidth(findNextBtn.getPrefWidth());
        closeBtn.setBackground(Background.EMPTY);
        closeBtn.setPadding(new Insets(5));
        closeBtn.setOnAction(e -> hideFindBar());

        findBar.getChildren().addAll(findTf, findNextBtn, closeBtn);
        root.getChildren().addAll(codeArea);

        

        this.setContent(root);
    }

    private void handleFind() {
        if (!isFinding) {
            indices = getMatches(getSearchText());
        }
        if (indices != null) {
            System.out.println(indices.size());
            if (findIndex < indices.size()) {
                highlightText(indices.get(findIndex++));

            } else if (findIndex >= indices.size()) {
                findIndex= 0;
            }
        } else {
            unhighlightText();
        }
    }

    private void unhighlightText() {
        codeArea.selectRange(0, 0);
    }

    @Nullable
    private ArrayList<Index> getMatches(String searchString) {
        ArrayList<Index> indices = new ArrayList<Index>();
        String text = getCodeAreaText();
        int searchStringLength = searchString.length();
        int index = 0;
        while (index < text.length()) {
            index = text.indexOf(searchString);
            if (index == -1) {
                return null;
            }
            Index occurence = new Index(index, index += searchStringLength);
            indices.add(occurence);
            text = text.substring(index);
        }
        return indices;


    }

    private void highlightText(Index index) {
        codeArea.selectRange(index.getStart(), index.getEnd());
    }

    private boolean isFindBarShown() {
        return root.getChildren().indexOf(findBar) != -1;
    }

    private String getCodeAreaText() {
        return codeArea.getText();
    }

    public void showFindBar() {
        root.getChildren().add(findBar);
        findTf.requestFocus();
    }

    private void hideFindBar() {
        root.getChildren().remove(findBar);
        this.isFinding = false;
        this.indices = null;
        this.findIndex = 0;
    }

    private String getSearchText() {
        return findTf.getText();
    }



}




