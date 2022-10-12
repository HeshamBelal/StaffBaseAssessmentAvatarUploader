package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class RunConfig {

    private Properties getProperties() {
        Properties properties = new Properties();
        String path = System.getProperty("user.dir") + File.separator + "config" + File.separator + "default.properties";
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            try {
                properties.load(fileInputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
    public String getToken()
    {
        return getProperties().getProperty("Token");
    }
    public String getFinalUri() {
        return getProperties().getProperty("BaseUri");
    }
}
