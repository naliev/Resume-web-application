package com.basejava.webapp;

import com.basejava.webapp.storage.SqlStorage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

public class Config {
    private static final String PROPS = "/resumes.properties";
    private static final Config INSTANCE = new Config();
    private final File storageDir;
    private final SqlStorage sqlStorage;

    private Config() {
        try (InputStream is = Config.class.getResourceAsStream(PROPS)) {
            Properties props = new Properties();
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));

            String herokuDBUrl = System.getenv("DATABASE_URL");

            if (herokuDBUrl != null) {
                URI dbUri = new URI(herokuDBUrl);
                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
                sqlStorage = new SqlStorage(dbUrl, username, password);
            } else {
                sqlStorage = new SqlStorage(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
            }
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file" + PROPS);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static Config getConfig() {
        return INSTANCE;
    }

    public File getStorageDir() {
        return storageDir;
    }

    public SqlStorage getSqlStorage() {
        return sqlStorage;
    }
}
