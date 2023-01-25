package com.qa.automation.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PropFileHandler {
    public static Properties config = new Properties();

    public static String readProperty(String property) {
        String value;
        try {
            FileInputStream in =
                    new FileInputStream("src/test/resources/testdata/Config.properties");
            try {
                config.load(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        value = config.getProperty(property);
        return value;
    }

    public static String readAPIJsonFile(String groupName, String endPointName) {
        JSONParser parser = new JSONParser();
        String apiEndPoint = null;
        try {
            String filePath = System.getProperty("user.dir");
            String DataFilepath = filePath + "/src/test/resources/testdata/API_endpoint.json";
            Object obj = parser.parse(new FileReader(DataFilepath));
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject endPointGroup = (JSONObject) jsonObject.get(groupName);
            apiEndPoint = (String) endPointGroup.get(endPointName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apiEndPoint;
    }

    public static String readAPIJsonFile(String groupName, String endPointName, String param1) {
        return readAPIJsonFile(groupName, endPointName) + "/" + param1;
    }

    public static String readAPIJsonFile(String groupName, String endPointName, String param1,
            String param2) {
        return readAPIJsonFile(groupName, endPointName, param1) + "/" + param2;
    }

}
