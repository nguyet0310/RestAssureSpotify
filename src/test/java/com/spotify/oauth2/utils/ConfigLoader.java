package com.spotify.oauth2.utils;

import java.util.Properties;

public class ConfigLoader {
    private final Properties properties;
    private static ConfigLoader configLoader;

    private ConfigLoader(){
        properties = PropertyUtils.propertyLoader("src/test/resources/config.properties");
    }
    public static ConfigLoader getInstance(){
        if(configLoader == null){
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }
    public String getClientSecret(){
        String prop = properties.getProperty("client_secret");
        if (prop != null) return prop;
        else throw new RuntimeException(" client_secret is not specified");
    }

    public String getGrantType(){
        String prop = properties.getProperty("grant_type");
        if (prop != null) return prop;
        else throw new RuntimeException("grant_type is not specified");
    }
    public String getRefreshToken(){
        String prop = properties.getProperty("refresh_token");
        if (prop != null) return prop;
        else throw new RuntimeException("refresh_token is not specified");
    }
    public String getClientId(){
        String prop = properties.getProperty("client_id");
        if (prop != null) return prop;
        else throw new RuntimeException("client_id is not specified");
    }
    public String getUserId(){
        String prop = properties.getProperty("userid");
        if (prop != null) return prop;
        else throw new RuntimeException("userid is not specified");
    }
}
