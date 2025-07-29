package com.NikitaNevmyvaka.expenseTracker.repository;

import com.NikitaNevmyvaka.expenseTracker.config.AppConfig;
import com.NikitaNevmyvaka.expenseTracker.model.Expense;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CSVstorage {

    AppConfig config= new AppConfig();






    public void exportToCsv(List<Expense> expensesList, String filePath)  {

        try {
            Path path= Paths.get(config.getCsvPath());
            Files.deleteIfExists(path);
        } catch (IOException e) {
            System.out.println("Wrong input! Please try again");
        }

        try (FileWriter writer = new FileWriter(filePath)) {



            CSVFormat csvFormat = CSVFormat.DEFAULT.withDelimiter(';')

                    .builder()
                    .setHeader("ID", "Date", "Name", "Description", "Category", "Cost")
                    .build();




            try (CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat)){
                for(Expense expense: expensesList){
                    csvPrinter.printRecord(expense.getId()
                            ,expense.getDate().toString()
                            ,expense.getName()
                            ,expense.getDescription()
                            ,expense.getCategory()
                            ,expense.getCost()
                            );
                }
            }


        } catch (IOException e) {
            System.out.println("Wrong input! Please try again");
        }

    }

}

