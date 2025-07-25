package com.NikitaNevmyvaka.ExpenseTracker.intefrace;

import com.NikitaNevmyvaka.ExpenseTracker.Exceptions.BackException;
import com.NikitaNevmyvaka.ExpenseTracker.service.ExpenseService;

import java.io.IOException;
import java.util.Scanner;

public class ExpenseCLI {
    ExpenseService service= new ExpenseService();

    public ExpenseCLI(ExpenseService service){
        this.service=service;
    }
    Scanner scanner= new Scanner(System.in);

    public void run() throws IOException {
        System.out.println("Welcome to the Finance Tracker 1.0. Write command \"Help\" to get list of all commands ");
        outer:
        while (true){
            System.out.println();
            System.out.println("Write a command to execute");
            try{
                String command= scanner.nextLine().trim();
                if (command.startsWith("/")) command=command.substring(1).toLowerCase();
                CLImainLogic(command);
                if(command.equals("exit")) break outer;


            }
            catch (BackException e){
                System.out.println("Returning back to the main menu...");
            }
        }
    }

    private void CLImainLogic(String command) throws IOException {
        switch (command){
            case("help"):
                service.showListOfAllCommands();
                break;

            case("add"):
                service.addExpence(service.createNewExpense(scanner));
                break;

            case("delete"):
                service.deleteExpense(scanner);
                break;

            case("update"):
                service.updateExpense(scanner);
                break;

            case("all"):
                service.allExpences();
                break;

            case("sum"):
                service.sumOfAllExpenses();
                break;

            case ("month"):
                service.sumOfExpensesForMonth(scanner);
                break;

            case ("csv"):
                service.ExportToCSV();
                break;

            case ("exit"):
                break ;




            default:
                System.out.println("Wrong command! To see all available commands type: /Help");


        }

    }




}
