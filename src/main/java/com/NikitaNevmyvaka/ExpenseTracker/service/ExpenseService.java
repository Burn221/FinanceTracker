package com.NikitaNevmyvaka.ExpenseTracker.service;

import com.NikitaNevmyvaka.ExpenseTracker.Exceptions.BackException;
import com.NikitaNevmyvaka.ExpenseTracker.Storage.CSVstorage;
import com.NikitaNevmyvaka.ExpenseTracker.Storage.JsonStorage;
import com.NikitaNevmyvaka.ExpenseTracker.model.Expense;
import com.NikitaNevmyvaka.ExpenseTracker.repository.RepositoryInterface;


import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ExpenseService implements RepositoryInterface {

    CSVstorage csVstorage= new CSVstorage();


    //region Increment


    private static final AtomicInteger nextId = new AtomicInteger(1);

    public int increaseNextId() {
        return nextId.getAndIncrement();
    }

    public void setNextId(int id) {
        nextId.set(id);
    }


    public void resetNextId() {
        nextId.set(1);
    }
    //endregion

    //region Local Storage(hashmap)
    private LinkedHashMap<Integer, Expense> expenses = new LinkedHashMap<>();

    public LinkedHashMap<Integer, Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(LinkedHashMap<Integer, Expense> expenses) {
        this.expenses = expenses;
    }

    private List<Expense> expensesList= new ArrayList<>();

    public List<Expense> getExpensesList() {
        return expensesList;
    }

    public void setExpensesList(List<Expense> expensesList) {
        this.expensesList = expensesList;
    }

    //endregion

    //region TO-DO operations
    @Override
    public void addExpence(Expense expense) {
        expenses.put(expense.getId(), expense);
        System.out.println("The expense has been successfully added!");
        System.out.println();


    }

    @Override
    public void deleteExpense(Scanner scanner) {
        allExpences();
        while (true) {


            try {
                System.out.println("Enter the id of a expense you'd like to delete");
                int idToDelete = parseInteger(scanner);
                checkIfCollectionIsNull(idToDelete);
                expenses.remove(idToDelete);
                System.out.println();
                System.out.println("The expense has been successfully deleted!");
                System.out.println();
                break;
            } catch (NullPointerException e) {
                System.out.println("Current id doesn't exist! Please try again or write /Back to return back to the main menu");
                System.out.println();
            }
        }

    }

    @Override
    public void updateExpense(Scanner scanner) {

        while (true) {
            try {
                allExpences();

                System.out.println("Enter the id of the expense you'd like to update");
                int idToUpdate = parseInteger(scanner);
                checkIfCollectionIsNull(idToUpdate);
                System.out.println();
                System.out.println("What attribute would you like to update? Available: Everything, Name, Date, Description, Category, Cost. You can also write /Back to return back to the main menu ");
                String line = readLineOrBack(scanner).trim().toLowerCase();
                casesToUpdateExpense(line, idToUpdate, scanner);
                break;
            } catch (NullPointerException e) {
                System.out.println("This id doesn't exist! Please try again or write /Back to get back to the main menu");
                System.out.println();
            }
        }


    }

    @Override
    public void allExpences() {
        System.out.println("Showing all expenses...");
        System.out.println();
        int maxLength = 0;

        List<String[]> rows = new ArrayList<>();
        String[] headers = {"ID", "Date", "Name", "Description", "Category", "Cost"};
        rows.add(headers);

        for (Map.Entry<Integer, Expense> entry : expenses.entrySet()) {
            Expense t = entry.getValue();
            rows.add(new String[]{String.valueOf(t.getId())
                    , t.getDate().toString()
                    , t.getName()
                    , t.getDescription(),
                    t.getCategory().displayName
                    , String.valueOf(t.getCost())});


        }

        int colsSize = headers.length;
        int[] width = new int[colsSize];

        for (int i = 0; i < colsSize; i++) {
            maxLength = 0;
            for (String[] str : rows) {
                maxLength = Math.max(maxLength, str[i].length());
            }
            width[i] = maxLength;
        }

        StringBuilder fmt = new StringBuilder();
        for (int w : width) {
            fmt.append("%-").append(w).append("s  ");

        }
        fmt.append("\n");

        for (String[] row : rows) {
            System.out.printf(fmt.toString(), (Object[]) row);
        }
        System.out.println();
    }




    @Override
    public void sumOfAllExpenses() {

        int sum = expenses.values().stream()
                .mapToInt(Expense::getCost)
                .sum();
        System.out.println();
        System.out.println("Your total expenses are: " + sum + "₸");
        System.out.println();

    }

    @Override
    public void sumOfExpensesForMonth(Scanner scanner) {
        Month month = parseMonth(scanner);

        int sumMonth = expenses.values().stream()
                .filter(entry -> entry.getDate().getMonth().equals(month))
                .mapToInt(Expense::getCost)
                .sum();
        System.out.println();
        System.out.println("Your total expenses for " + month + " are: " + sumMonth + "₸");
        System.out.println();

    }

    public void showListOfAllCommands() {
        System.out.println();
        System.out.println("/add or add: this command adds a new expense to your tracker");
        System.out.println();
        System.out.println("/delete or delete; this command let you delete any existing expense by it's id");
        System.out.println();
        System.out.println("/all or all: this command shows you all expenses that you have");
        System.out.println();
        System.out.println("/update or update: this command let you update 1 or all attributes of your existing expense");
        System.out.println();
        System.out.println("/sum or sum: this command shows you summary of all expenses that you've made");
        System.out.println();
        System.out.println("/month or month: this command shows you summary of expenses in chosen month");
        System.out.println();
        System.out.println("/csv or csv- this command let you export your expenses to csv file and then open it in Excel or other programs");
        System.out.println("/back: returns you back to the main menu");
        System.out.println();
        System.out.println("/exit or exit: exits from the program");
        System.out.println();

    }

    public void ExportToCSV() throws IOException {
        for(Map.Entry<Integer,Expense> entry: expenses.entrySet()){
            expensesList.add(entry.getValue());
        }

        csVstorage.exportToCsv(expensesList, csVstorage.getCsvFilePath());

        System.out.println("Successfully exported your data to .csv format!");

    }


    //endregion

    //region creating a new expense realization
    public Expense createNewExpense(Scanner scanner) {

        System.out.println();
        int id = increaseNextId();
        System.out.println("Enter a name for the expense");
        String name = readLineOrBack(scanner);
        System.out.println();

        System.out.println("Enter a description for the expense");
        String description = readLineOrBack(scanner);
        System.out.println();

        String categories = Arrays.toString(Category.values()).replace("[", "").replace("]", "");
        System.out.println("Enter a category for the expense, available categories: " + categories);
        Category category = checkRightCategory(scanner);

        LocalDate date = LocalDate.now();


        System.out.println("Enter a cost for your expense");
        int cost = parseInteger(scanner);


        return new Expense(id, date, name, description, category, cost);

    }
    //endregion

    //region Enum Category
    public enum Category {
        FOOD("Food"),
        TRANSPORT("Transport"),
        ENTERTAINMENT("Entertainment"),
        SUBSCRIPTIONS("Subscriptions"),
        HOUSE_PAYMENTS("House payments");

        private final String displayName;


        Category(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }
    //endregion

    //region /Back realization
    public String readLineOrBack(Scanner scanner) {
        String line = scanner.nextLine();
        if ("/Back".equalsIgnoreCase(line)) {
            throw new BackException();
        }
        return line;
    }
    //endregion

    //region Parsing methods to exclude errors
    private Category checkRightCategory(Scanner scanner) {
        while (true) {
            try {

                String normalized = readLineOrBack(scanner)
                        .trim()
                        .replace(" ", "_")
                        .replace("-", "_")
                        .toUpperCase();

                Category category = Category.valueOf(normalized);
                System.out.println();
                return category;
            } catch (IllegalArgumentException e) {
                System.out.println();
                System.out.println("You've entered a wrong category, please try again");
                System.out.println();

            }

        }
    }

    private LocalDate parseLocalDate(Scanner scanner) {
        while (true) {
            try {
                return LocalDate.parse(readLineOrBack(scanner));

            } catch (DateTimeParseException e) {
                System.out.println("Wrong Date format, please try again");

            }
        }
    }


    private int parseInteger(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(readLineOrBack(scanner));

            } catch (NumberFormatException e) {
                System.out.println("Wrong number format, please try again");
            }
        }
    }

    private Month parseMonth(Scanner scanner) {
        String names = Arrays.stream(Month.values())
                .map(Month::name)
                .collect(Collectors.joining(","));

        while (true) {
            try {
                System.out.println("Choose a month you'd like to summarize (" + names + ") or write /Back to cancel");
                return Month.valueOf(readLineOrBack(scanner).trim().toUpperCase());

            } catch (IllegalArgumentException e) {
                System.out.println("Wrong month name, please try again");
            }
        }
    }

    private void checkIfCollectionIsNull(int id) throws NullPointerException {
        if (!expenses.containsKey(id)) throw new NullPointerException();

    }


    //endregion

    //region Case Handler
    private void caseEverythingUpdate(int id, Scanner scanner) {
        Expense expenseToReplace = createNewExpense(scanner);
        expenses.replace(id, expenseToReplace);

    }

    private void caseNameUpdate(int id, Scanner scanner) {
        Expense expenseToReplace = expenses.get(id);
        System.out.println("Enter a new Name for the expense");
        expenseToReplace.setName(readLineOrBack(scanner));
        expenses.replace(id, expenseToReplace);
        System.out.println();
        System.out.println("The Name has been successfully updated! ");
        System.out.println();
    }

    private void caseDateUpdate(int id, Scanner scanner) {

        Expense expenseToReplace = expenses.get(id);
        System.out.println("Enter a new Date for an expense using format YYYY-MM-DD");
        expenseToReplace.setDate(parseLocalDate(scanner));
        expenses.replace(id, expenseToReplace);
        System.out.println();
        System.out.println("The Date has been successfully updated! ");
        System.out.println();


    }

    private void caseDescriptionUpdate(int id, Scanner scanner) {
        Expense expenseToReplace = expenses.get(id);
        System.out.println("Enter a new Description for the expense");
        expenseToReplace.setDescription(readLineOrBack(scanner));
        expenses.replace(id, expenseToReplace);
        System.out.println();
        System.out.println("The Description has been successfully updated! ");
        System.out.println();
    }

    private void caseCategoryUpdate(int id, Scanner scanner) {
        Expense expenseToReplace = expenses.get(id);
        System.out.println("Enter a new Category for the expense");
        expenseToReplace.setCategory(checkRightCategory(scanner));
        expenses.replace(id, expenseToReplace);
        System.out.println();
        System.out.println("The Category has been successfully updated! ");
        System.out.println();
    }

    private void caseCostUpdate(int id, Scanner scanner) {

        Expense expenseToReplace = expenses.get(id);
        System.out.println("Enter a new Cost for the expense");
        expenseToReplace.setCost(parseInteger(scanner));
        expenses.replace(id, expenseToReplace);
        System.out.println();
        System.out.println("The Cost has been successfully updated! ");
        System.out.println();
    }

    private void casesToUpdateExpense(String field, int idToUpdate, Scanner scanner) {
        switch (field) {
            case ("everything"):
                caseEverythingUpdate(idToUpdate, scanner);
                break;

            case ("name"):
                caseNameUpdate(idToUpdate, scanner);
                break;
            case ("date"):
                caseDateUpdate(idToUpdate, scanner);
                break;

            case ("description"):
                caseDescriptionUpdate(idToUpdate, scanner);
                break;

            case ("category"):
                caseCategoryUpdate(idToUpdate, scanner);
                break;

            case ("cost"):
                caseCostUpdate(idToUpdate, scanner);
                break;

            default:
                System.out.println("Found no attributes with this name. Please try again");


        }


    }

    //endregion

}

