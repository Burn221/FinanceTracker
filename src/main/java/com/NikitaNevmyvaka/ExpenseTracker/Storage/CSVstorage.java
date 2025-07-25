package com.NikitaNevmyvaka.ExpenseTracker.Storage;

import com.NikitaNevmyvaka.ExpenseTracker.model.Expense;
import com.NikitaNevmyvaka.ExpenseTracker.service.ExpenseService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;

import java.io.IOException;
import java.util.List;

public class CSVstorage {
    static ExpenseService service = new ExpenseService();
    String csvFilePath = "C:\\Users\\HP\\Desktop\\FinanceTracker\\FileStorages\\CSVexpensesStorage.csv";

    public String getCsvFilePath() {
        return csvFilePath;
    }

    public void exportToCsv(List<Expense> expensesList, String filePath) {

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

