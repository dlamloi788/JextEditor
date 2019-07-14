package controller;

import ViewLoader.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Settings;

import java.util.Set;

public class SettingsController extends Controller<Settings> {

    @FXML
    private TextField defaultFontSizeTf;
    @FXML
    public TextField minimumFontSizeTf;
    @FXML
    public TextField maximumFontSizeTf;

    public Settings getModel() {
        return model;
    }

    @FXML
    public void initialize() {
        defaultFontSizeTf.setText(String.valueOf(model.getFontSize()));
        minimumFontSizeTf.setText(String.valueOf(model.getMinimumFontSize()));
        maximumFontSizeTf.setText(String.valueOf(model.getMaximumFontSize()));

        /* Force the font size textfields only to accept numbers*/
        defaultFontSizeTf.textProperty().addListener((e, oldValue, newValue) -> {
            if (!isValid(newValue)) {
                defaultFontSizeTf.setText(oldValue);
            }
        });

        minimumFontSizeTf.textProperty().addListener((e, oldValue, newValue) -> {
            if (!isValid(newValue)) {
                minimumFontSizeTf.setText(oldValue);
            }
        });

        maximumFontSizeTf.textProperty().addListener((e, oldValue, newValue) -> {
            if (!isValid(newValue)) {
                maximumFontSizeTf.setText(oldValue);
            }
        });

    }

    /**
     * Determines if the input is a number and is the number
     * is between 0 to 3 characters long
     * @param input the input to validate
     * @return true if the input is valid; false otherwise
     */
    private boolean isValid(String input) {
        if (input.length() == 0) {
            return true;
        } else if (input.length() < 4) {
            return input.matches("^[0-9]+$");
        }
        return false;
    }



}
