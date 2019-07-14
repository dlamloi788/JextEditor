package controller;

import ViewLoader.Controller;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import model.Index;
import view.CustomTabView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TabController extends Controller {

    private CustomTabView tabView;
    //Flags whether the user is searching for a case or not
    private boolean isFinding = false;

    //Stores the search index the user is currently up to
    private int findIndex = 0;

    //Stores the indicies for each matching word
    private ArrayList<Index> indices;

    public TabController(CustomTabView tabView) {
        this.tabView = tabView;
    }

    @FXML
    public void initialize() {

    }


    public void find() {
        if (!isFinding) {
            if (!getSearchText().isEmpty()) {
                indices = getMatches(getSearchText());
            }
        }
        if (indices != null && indices.size() > 0) {
            System.out.println(indices.size());
            if (findIndex >= indices.size()) {
                resetFind();
            }
            highlightText(indices.get(findIndex++));
            } else {
            unhighlightText();
        }
      }

    private ArrayList<Index> getMatches(String searchString) {
        ArrayList<Index> indices = new ArrayList<Index>();
        Pattern pattern = Pattern.compile(searchString);
        Matcher matcher = pattern.matcher(getCodeAreaText());
        while (matcher.find()) {
            indices.add(new Index(matcher.start(), matcher.end()));
        }
        return indices.size() == 0 ? null : indices;
    }

    private void unhighlightText() {
        tabView.getCodeArea().selectRange(0, 0);
    }

    private void highlightText(Index index) {
        tabView.getCodeArea().selectRange(index.getStart(), index.getEnd());
    }

    private boolean isFindBarShown() {
        return tabView.getRoot().getChildren().indexOf(tabView.getFindBar()) != -1;
    }

    private String getCodeAreaText() {
        return tabView.getCodeArea().getText();
    }

    public void showFindBar() {
        if (tabView.getRoot().getChildren().indexOf(tabView.getFindBar()) == -1) {
            tabView.getRoot().getChildren().add(tabView.getFindBar());
        }
        tabView.getFindTf().requestFocus();
    }

    public void hideFindBar() {
        tabView.getRoot().getChildren().remove(tabView.getFindBar());
        this.isFinding = false;
        this.indices = null;
        this.findIndex = 0;
    }

    private String getSearchText() {
        return tabView.getFindTf().getText();
    }

    public void resetFind() {
        findIndex = 0;
    }

    public void handleKeyPush(KeyEvent event) {
        switch (event.getCode()) {
            case ENTER:
                find();
                break;
            case ESCAPE:
                hideFindBar();
                break;
        }
    }

}
