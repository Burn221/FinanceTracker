package com.NikitaNevmyvaka.expenseTracker.service;

import com.NikitaNevmyvaka.expenseTracker.model.Expense;

import java.util.Scanner;

public interface ExpenseServiceInterface {
    void addExpence(Expense expense);

    void updateExpense(Scanner scanner);

    void deleteExpense(Scanner scanner);

    void allExpences();

    void sumOfAllExpenses();

    void sumOfExpensesForMonth(Scanner scanner);

}

