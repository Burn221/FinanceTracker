package com.NikitaNevmyvaka.ExpenseTracker.Storage;

import com.NikitaNevmyvaka.ExpenseTracker.model.Expense;
import com.NikitaNevmyvaka.ExpenseTracker.service.ExpenseService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CSVstorage {

    String csvFilePath = "C:\\Users\\HP\\Desktop\\FinanceTracker\\FileStorages\\CSVexpensesStorage.csv";


    public String getCsvFilePath() {
        return csvFilePath;
    }

    public void exportToCsv(List<Expense> expensesList, String filePath) throws IOException {

        Path path= Paths.get(csvFilePath);
        Files.deleteIfExists(path);

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

    public void clearCurrentFile(String path)throws IOException{
        try(FileWriter writer= new FileWriter(path,false)){

        }
    }

}

