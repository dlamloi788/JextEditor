package view;

import com.sun.istack.internal.Nullable;
import controller.TabController;
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

public class CustomTabView extends Tab {

    /*
     * Declaring the leaf nodes up at the top of the class
     */
    private VBox root;
    private CodeArea codeArea;
    private HBox findBar;
    private TextField findTf;
    private Button findNextBtn;
    private Button closeBtn;

    private TabController controller = new TabController(this);

    public CustomTabView(String text) throws IOException {
        super(text);
        root = new VBox(5);
        root.setPrefHeight(10000);
        root.setPrefWidth(10000);
        codeArea = new CodeArea();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue) controller.resetFind();
        }));
        VBox.setMargin(codeArea, new Insets(10, 10, 10, 10));
        VBox.setVgrow(codeArea, Priority.ALWAYS);
        findBar = new HBox(10);
        findBar.setAlignment(Pos.CENTER_LEFT);
        findBar.setPadding(new Insets(10, 10, 10, 10));
        findBar.setStyle("-fx-background-color: #d2d2d2");
        findTf = new TextField();
        HBox.setHgrow(findTf, Priority.ALWAYS);
        findNextBtn = new Button("Find Next");
        findNextBtn.setOnAction(e -> controller.find());
        //Create and style the close button
        closeBtn = new Button("\u00D7");
        closeBtn.setFont(Font.font(15));
        closeBtn.setPrefHeight(findNextBtn.getPrefHeight());
        closeBtn.setPrefWidth(findNextBtn.getPrefWidth());
        closeBtn.setBackground(Background.EMPTY);
        closeBtn.setPadding(new Insets(5));
        closeBtn.setOnAction(e ->  controller.hideFindBar());

        findBar.getChildren().addAll(findTf, findNextBtn, closeBtn);
        root.getChildren().addAll(codeArea);


        this.setContent(root);
    }

    public void handleFind() {
        controller.showFindBar();
    }

    public VBox getRoot() {
        return root;
    }

    public void setRoot(VBox root) {
        this.root = root;
    }

    public CodeArea getCodeArea() {
        return codeArea;
    }

    public void setCodeArea(CodeArea codeArea) {
        this.codeArea = codeArea;
    }

    public HBox getFindBar() {
        return findBar;
    }

    public void setFindBar(HBox findBar) {
        this.findBar = findBar;
    }

    public TextField getFindTf() {
        return findTf;
    }

    public void setFindTf(TextField findTf) {
        this.findTf = findTf;
    }

    public Button getFindNextBtn() {
        return findNextBtn;
    }

    public void setFindNextBtn(Button findNextBtn) {
        this.findNextBtn = findNextBtn;
    }

    public Button getCloseBtn() {
        return closeBtn;
    }

    public void setCloseBtn(Button closeBtn) {
        this.closeBtn = closeBtn;
    }
}




