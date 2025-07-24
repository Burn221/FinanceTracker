package com.NikitaNevmyvaka.ExpenseTracker.repository;

import com.NikitaNevmyvaka.ExpenseTracker.model.Expense;

import java.time.Month;
import java.util.Scanner;

public interface RepositoryInterface {
    void addExpence(Expense expense);

    void updateExpense(Scanner scanner);

    void deleteExpense(Scanner scanner);

    void allExpences();

    void sumOfAllExpenses();

    void sumOfExpensesForMonth(Scanner scanner);

}

