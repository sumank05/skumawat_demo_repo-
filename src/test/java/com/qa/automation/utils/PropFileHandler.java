package com.qa.automation.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropFileHandler {
    public static Properties config = new Properties();
    
    
    public static String readProperty(String property)
    {
        String value;
        try 
        {
            FileInputStream in = new FileInputStream("src/test/resources/testData/Config.properties");
            try 
            {
                config.load(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        value=config.getProperty(property);
        return value;
    }

}
