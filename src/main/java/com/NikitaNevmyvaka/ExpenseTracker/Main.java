package com.NikitaNevmyvaka.ExpenseTracker;

import com.NikitaNevmyvaka.ExpenseTracker.Storage.JsonStorage;
import com.NikitaNevmyvaka.ExpenseTracker.intefrace.ExpenseCLI;
import com.NikitaNevmyvaka.ExpenseTracker.model.Expense;
import com.NikitaNevmyvaka.ExpenseTracker.service.ExpenseService;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ExpenseService service= new ExpenseService();
        ExpenseCLI CLI= new ExpenseCLI(service);
        JsonStorage storage= new JsonStorage(service);
        Scanner scanner= new Scanner(System.in);

        storage.loadFromFile(storage.getPath());

        CLI.run();

        storage.saveToFile(storage.getPath());
    }
}
