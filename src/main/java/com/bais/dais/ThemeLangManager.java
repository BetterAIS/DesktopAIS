package com.bais.dais;

import javafx.scene.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;

public class ThemeLangManager {
    private static Logger logger = LoggerFactory.getLogger(ThemeLangManager.class);
    private static ThemeLangManager _instance = null;
    private static LinkedList<String> themes = new LinkedList<String>() {{
        add("nord");
        add("light");
    }};
    private String theme;
    private Scene scene = null;
    private ResourceBundle langBundle = null;
    private static LinkedList<String> languages = new LinkedList<String>() {{
        add("sk");
        add("en");
    }};
    private String language;

    private ThemeLangManager() {
        theme = themes.getFirst();
        langBundle = ResourceBundle.getBundle("Lang");
    }
    public static ThemeLangManager getInstance() {
        if (_instance == null) {
            _instance = new ThemeLangManager();
        }
        return _instance;
    }

    public Scene getScene() {
        return scene;
    }
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;

        if (scene != null) {
            try {
                scene.getStylesheets().clear();
                String css = getClass().getResource("css/" + theme + ".css").toExternalForm();
                scene.getStylesheets().add(css);
            } catch (Exception e) {
                logger.error("Error loading CSS file: " + e.getMessage());
            }
        }
    }

    public ResourceBundle getLangBundle() {
        return langBundle;
    }

    public void setLang(String lang) {
        if (langBundle != null) {
            langBundle = ResourceBundle.getBundle("Lang", new Locale(lang));
            language = lang;
        }
    }

    public String getLang() {
        return language;
    }

    public void switchLang() {
        int index = languages.indexOf(language);
        if (index == languages.size() - 1) {
            index = 0;
        } else {
            index++;
        }
        setLang(languages.get(index));
    }


    public void switchTheme() {
        int index = themes.indexOf(theme);
        if (index == themes.size() - 1) {
            index = 0;
        } else {
            index++;
        }
        setTheme(themes.get(index));
    }
}
