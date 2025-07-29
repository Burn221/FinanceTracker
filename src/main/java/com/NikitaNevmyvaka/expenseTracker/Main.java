package com.NikitaNevmyvaka.expenseTracker;

import com.NikitaNevmyvaka.expenseTracker.config.AppConfig;
import com.NikitaNevmyvaka.expenseTracker.repository.JsonStorage;
import com.NikitaNevmyvaka.expenseTracker.CLI.ExpenseCLI;
import com.NikitaNevmyvaka.expenseTracker.service.ExpenseService;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        ExpenseService service= new ExpenseService();
        ExpenseCLI CLI= new ExpenseCLI(service);
        JsonStorage storage= new JsonStorage(service);
        AppConfig config= new AppConfig();
        Scanner scanner= new Scanner(System.in);

        storage.loadFromFile(config.getJsonPath());

        CLI.run();

        storage.saveToFile(config.getJsonPath());
    }
}
