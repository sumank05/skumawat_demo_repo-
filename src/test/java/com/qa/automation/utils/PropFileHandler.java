package com.qa.automation.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
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

    public static String readAPIJsonFile(String groupName, String EndPointName) {
        JSONParser parser = new JSONParser();
        String APIEndPoint = null;
        try {
            Object obj = parser.parse(new FileReader(
                    "C:\\Users\\SumanKumawat\\Desktop\\projects\\hrone_bdd\\src\\test\\resources\\testdata\\API_endpoint.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject endPointGroup = (JSONObject) jsonObject.get(groupName);
            APIEndPoint = (String) endPointGroup.get(EndPointName);
            System.out.println("#################" + APIEndPoint);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return APIEndPoint;
    }

}
