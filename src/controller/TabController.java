package controller;

import ViewLoader.Controller;
import com.sun.istack.internal.Nullable;
import model.Index;
import view.CustomTabView;

import java.util.ArrayList;

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

    @Nullable
    private ArrayList<Index> getMatches(String searchString) {
        ArrayList<Index> indices = new ArrayList<Index>();
        String text = getCodeAreaText();
        int searchStringLength = searchString.length();
        int index = 0;
        Index occurence;
        while (index <= text.length()) {
            index = text.indexOf(searchString);
            if (index == -1) {
                return null;
            }
            int end = index + searchStringLength;
            occurence = new Index();
            occurence.setStart(index);
            occurence.setEnd(end);
            index += searchStringLength;
            if (indices.size() >= 1) {
                Index indexOfLast = indices.get(indices.size() - 1);
                int incrementer = indexOfLast.getEnd();
                occurence.incrementStartAndEnd(incrementer);
            }
            indices.add(occurence);
            text = text.substring(index);
        }

        return indices;}

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
        System.out.println("Index value should be zero: " + findIndex);
    }
}
