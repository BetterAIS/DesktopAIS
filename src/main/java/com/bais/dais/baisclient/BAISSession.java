package com.bais.dais.baisclient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BAISSession {
    @AllArgsConstructor
    public class Session {
        @Getter
        private String accessToken;
        @Getter
        private String refreshToken;
        @Getter
        private long expireTime;
        @Getter
        private String theme;
        @Getter
        private String lang;
    }

    private static BAISSession _instance = null;
    private static Logger logger = LoggerFactory.getLogger(BAISSession.class);
    private static final String LOCAL_DB = "jdbc:sqlite:./bais.db";
    private static final String sessionTableName = "__session";
    private static final String sessionTableInit = """
    CREATE TABLE IF NOT EXISTS %s (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        access_token TEXT NOT NULL,
        refresh_token TEXT NOT NULL,
        expire_time INTEGER NOT NULL,
        theme TEXT DEFAULT 'light',
        lang TEXT DEFAULT 'en'
    );""";
    private Connection dbConnection;

    public static BAISSession getInstance() {
        if (_instance == null) {
            _instance = new BAISSession();
        }
        return _instance;
    }

    private BAISSession() {
        dbConnection = null;
    }

    private boolean checkDBConnection() {
        if (dbConnection == null) {
            logger.error("DB connection is null.");
            return false;
        }
        return true;
    }

    private void initSessionTable() {
        if (!checkDBConnection()) {
            return;
        }
        try {
            dbConnection.createStatement().executeUpdate(sessionTableInit.formatted(sessionTableName));
            logger.debug("Session table initialized.");
        } catch (SQLException e) {
            logger.error("Session table init failed.");
        }
    }

    private boolean isSessionTableExist() {
        if (!checkDBConnection()) {
            return false;
        }
        try {
            // Is not sql injection safe, but it's ok for now.
            dbConnection.createStatement().executeQuery("SELECT * FROM " + sessionTableName + ";");
        } catch (SQLException e) {
            logger.error("Session table does not exist.");
            return false;
        }
        return true;
    }

    public void initDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            dbConnection = DriverManager.getConnection(LOCAL_DB);
            logger.debug("DB connection established.");
        } catch (ClassNotFoundException e) {
            logger.error("DB connection failed. Class not found.");
        } catch (SQLException e) {
            logger.error("DB connection failed. SQL error.");
        }
        if (dbConnection == null) {
            return;
        }
        if (!isSessionTableExist()) {
            initSessionTable();
        }
    }

    public boolean sessionExist() {
        if (!checkDBConnection()) {
            return false;
        }
        try {
            var resultSet = dbConnection.createStatement().executeQuery("""
            SELECT * FROM %s
            ORDER BY id DESC
            LIMIT 1;
            """.formatted(sessionTableName));
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            logger.error("Session exist check failed.");
        }
        return false;
    }

    public Session addSession(String accessToken, String refreshToken, long expireTime, String theme, String lang) {
        if (!checkDBConnection()) {
            return null;
        }
        try {
            dbConnection.createStatement().executeUpdate("""
            INSERT INTO %s (access_token, refresh_token, expire_time, theme, lang)
            VALUES ('%s', '%s', %d, '%s', '%s');
            """.formatted(sessionTableName, accessToken, refreshToken, expireTime, theme, lang));
            logger.debug("Session added.");
        } catch (SQLException e) {
            logger.error("Session add failed.");
        }
        return new Session(accessToken, refreshToken, expireTime, theme, lang);
    }

    public Session addSession(String accessToken, String refreshToken, long expireTime) {
        return addSession(accessToken, refreshToken, expireTime, "nord", "en");
    }

    public Session setTheme(String theme) {
        if (!checkDBConnection()) {
            return null;
        }
        try {
            dbConnection.createStatement().executeUpdate("""
            UPDATE %s
            SET theme = '%s'
            WHERE id = (
                SELECT id FROM %s
                ORDER BY id DESC
                LIMIT 1
            );
            """.formatted(sessionTableName, theme, sessionTableName));
            logger.debug("Session theme updated.");
        } catch (SQLException e) {
            logger.error("Session theme update failed.");
        }
        return getLastSession();
    }

    public Session setLang(String lang) {
        if (!checkDBConnection()) {
            return null;
        }
        try {
            dbConnection.createStatement().executeUpdate("""
            UPDATE %s
            SET lang = '%s'
            WHERE id = (
                SELECT id FROM %s
                ORDER BY id DESC
                LIMIT 1
            );
            """.formatted(sessionTableName, lang, sessionTableName));
            logger.debug("Session lang updated.");
        } catch (SQLException e) {
            logger.error("Session lang update failed.");
        }
        return getLastSession();
    }



    public Session getLastSession() {
        if (!checkDBConnection()) {
            return null;
        }
        try {
            var resultSet = dbConnection.createStatement().executeQuery("""
            SELECT * FROM %s
            ORDER BY id DESC
            LIMIT 1;
            """.formatted(sessionTableName));
            if (resultSet.next()) {
                return new Session(
                        resultSet.getString("access_token"),
                        resultSet.getString("refresh_token"),
                        resultSet.getLong("expire_time"),
                        resultSet.getString("theme"),
                        resultSet.getString("lang")
                );
            }
        } catch (SQLException e) {
            logger.error("Session get failed.");
        }
        return null;
    }

    public void deleteLastSession() {
        if (!checkDBConnection()) {
            return;
        }
        try {
            dbConnection.createStatement().executeUpdate("""
            DELETE FROM %s
            WHERE id = (
                SELECT id FROM %s
                ORDER BY id DESC
                LIMIT 1
            );
            """.formatted(sessionTableName, sessionTableName));
            logger.debug("Session deleted.");
        } catch (SQLException e) {
            logger.error("Session delete failed.");
        }
    }

    public void closeDB() {
        if (!checkDBConnection()) {
            return;
        }
        try {
            dbConnection.close();
            dbConnection = null;
            logger.debug("DB connection closed.");
        } catch (SQLException e) {
            logger.error("DB connection close failed.");
        }
    }
}
