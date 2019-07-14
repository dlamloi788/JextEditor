package view;

import controller.TabController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.io.IOException;
import java.util.Set;

public class CustomTabView extends Tab {

    private IntegerProperty fontSize = new SimpleIntegerProperty(11);

    /*
     * Declaring the leaf nodes up at the top of the class
     */
    private VBox root;
    private CodeArea codeArea;
    private HBox findBar;
    private TextField findTf;
    private Button findNextBtn;
    private Button closeBtn;

    private TabController controller;

    public CustomTabView(String text) throws IOException {
        super(text);
        root = new VBox(5);
        root.setPrefHeight(10000);
        root.setPrefWidth(10000);
        root.getStylesheets().add("/view/style.css");
        codeArea = new CodeArea();
        codeArea.setParagraphGraphicFactory(new NumberStripFactory(codeArea));
        codeArea.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue    ) controller.resetFind();
        }));
        codeArea.addEventFilter(ScrollEvent.SCROLL, e -> {
            if (e.isControlDown()) {
                if (e.getDeltaY() > 0) {
                    fontSize.set(fontSize.get() + 2);
                } else {
                    fontSize.set(fontSize.get() - 2);
                }
            }
        });


        codeArea.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            KeyCode keyPressed = e.getCode();
            if (e.isControlDown()) {
                if (keyPressed == KeyCode.EQUALS) {
                    fontSize.set(fontSize.get() + 2);
                } else if (keyPressed == KeyCode.MINUS) {
                    fontSize.set(fontSize.get() - 2);
                }
            }
        });
        codeArea.setStyle("-fx-alignment: center");
        VBox.setMargin(codeArea, new Insets(10, 10, 10, 10));
        VBox.setVgrow(codeArea, Priority.ALWAYS);
        findBar = new HBox(10);
        findBar.setAlignment(Pos.CENTER_LEFT);
        findBar.setPadding(new Insets(10, 10, 10, 10));
        findBar.setStyle("-fx-background-color: #d2d2d2");
        findBar.setOnKeyPressed(event -> controller.handleKeyPush(event));

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

        //Listen to change to the fontsize property
        fontSize.addListener(e -> {
            for (Node n : codeArea.lookupAll(".lineno")) {
                if (n instanceof Label) {
                    ((Label)n).setAlignment(Pos.CENTER);
                }
            }
            setFont(fontSize.get());
        });

        controller = new TabController(this);
    }

    public CustomTabView(String text, int fontSize) throws IOException {
        this(text);
        codeArea.setStyle("-fx-font-size: " + fontSize + "px;");
    }

    public void handleFind() {
        controller.showFindBar();
    }

    public void setFont(int fontSize) {
        if (fontSize > 0) {
            String fontStyle = "-fx-font-size: " + fontSize + "px;";
            codeArea.setStyle(fontStyle);
        }
    }

    public void printOutAllNodes() {
        //for (Node n : codeArea.lookupAll(".lineno")) {
            //System.out.println(n.getStyle());
        //}
        System.out.println(codeArea.lookupAll(".lineno").size());
    }

    /* Getters and setters*/

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

    public Scene getScene() {
        return this.getContent().getScene();
    }
}




