package com.NikitaNevmyvaka.expenseTracker.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    private final Properties properties= new Properties();

    public AppConfig()  {
        try(InputStream in=
                getClass().getClassLoader().getResourceAsStream("application.properties")){
            if (in==null){
                throw new RuntimeException("Couldn't find the file");
            }
            properties.load(in);

        }
        catch (IOException e){
            System.out.println("Couldn't find the file");
            System.out.println(e.getMessage());

        }
        catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }



    public String getJsonPath(){
        return properties.getProperty("json.path");
    }

    public String getCsvPath(){
        return properties.getProperty("csv.path");
    }
}
