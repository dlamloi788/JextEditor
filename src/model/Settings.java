package model;

import java.util.prefs.Preferences;

/**
 * This class wraps the user settings that were persisted after the applications lifetime
 */
public class Settings {

    public static final String FONT_SIZE_KEY = "fontSize";
    public static final String MINIMUM_FONT_SIZE_KEY = "minimumFontSize";
    public static final String MAXIMUM_FONT_SIZE_KEY = "maximumFontSize";
    private static final int DEFAULT_FONT_SIZE = 11;
    private static final int DEFAULT_MINIMUM_FONT_SIZE = 11;
    private static final int DEFAULT_MAXIMUM_FONT_SIZE = 70;

    private static Settings instance = null;
    private Preferences preferences;
    private int fontSize;
    private int minimumFontSize;
    private int maximumFontSize;

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    private Settings() {
        preferences = Preferences.userRoot().node(this.getClass().getName());
        fontSize = preferences.getInt(FONT_SIZE_KEY, DEFAULT_FONT_SIZE);
        minimumFontSize = preferences.getInt(MINIMUM_FONT_SIZE_KEY, DEFAULT_MINIMUM_FONT_SIZE);
        maximumFontSize = preferences.getInt(MAXIMUM_FONT_SIZE_KEY, DEFAULT_MAXIMUM_FONT_SIZE);
    }

    public int getFontSize() {
        return fontSize;
    }

    public int getMinimumFontSize() {
        return minimumFontSize;
    }

    public int getMaximumFontSize() {
        return maximumFontSize;
    }
}
