package com.NikitaNevmyvaka.expenseTracker.repository;

import com.NikitaNevmyvaka.expenseTracker.config.AppConfig;
import com.NikitaNevmyvaka.expenseTracker.model.Expense;
import com.NikitaNevmyvaka.expenseTracker.service.ExpenseService;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;

import java.util.LinkedHashMap;
import java.util.Map;


public class JsonStorage {



    public JsonStorage(ExpenseService service){
        this.service=service;
    }


    ExpenseService service= new ExpenseService();


    Gson gson= new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class
            , (JsonDeserializer<LocalDate>)(json,type,ctx)->LocalDate.parse(json.getAsString()))
            .registerTypeAdapter(LocalDate.class
            , (JsonSerializer<LocalDate>)(date,type,ctx)-> new JsonPrimitive(date.toString())).create();

    public void saveToFile(String path){
        try(Writer writer= new FileWriter(path)){
            Type maptype= new TypeToken<Map<Integer, Expense>>(){}.getType();
            gson.toJson(service.getExpenses(),maptype,writer);
        }
        catch (IOException e){
            System.out.println("This path doesn't exist!");
        }
    }


    public void loadFromFile(String path){
        File file= new File(path);
        if (!file.exists()) return;

        try(Reader reader= new FileReader(path)){

            Type maptype= new TypeToken<Map<Integer, Expense>>(){}.getType();
            LinkedHashMap<Integer,Expense> loaded= gson.fromJson(reader, maptype);
            service.setExpenses(loaded);
            service.setNextId(loaded.keySet().stream().max(Integer::compare).orElse(0)+1);




        } catch (IOException e) {
            System.out.println("This path doesn't exist!");
        }

    }
}
