package serviceTest;

import com.NikitaNevmyvaka.expenseTracker.model.Expense;
import com.NikitaNevmyvaka.expenseTracker.service.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.verify;


public class ExpenseServiceTest {
    ExpenseService service = new ExpenseService();

    @BeforeEach
    void setUp() {
        ExpenseService service = new ExpenseService();
        service.resetNextId();

    }

    @Test
    void createNewExpenseTest() {
        Scanner scanner = mock(Scanner.class);

        when(scanner.nextLine()).thenReturn(
                "Coffee",
                "Morning latte",
                "house payments",
                "150"
        );


        Expense exp = service.createNewExpense(scanner);

        assertEquals(1, exp.getId());
        assertEquals("Coffee", exp.getName());
        assertEquals("Morning latte", exp.getDescription());
        assertEquals(ExpenseService.Category.HOUSE_PAYMENTS, exp.getCategory());
        assertEquals(LocalDate.now(), exp.getDate());
        assertEquals("150", String.valueOf(exp.getCost()));


    }

    @Test
    void addNewExpenseTest() {
        Expense expenseTest = new Expense(1
                , LocalDate.of(2025, 12, 12)
                , "Test"
                , "test"
                , ExpenseService.Category.FOOD
                , 12000);

        PrintStream originalOut = System.out;
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buf));

        try {
            service.addExpence(expenseTest);

        } finally {
            System.setOut(originalOut);

        }

        assertTrue(service.getExpenses().containsKey(1));
        assertSame(expenseTest, service.getExpenses().get(1));

        String output = buf.toString();

        assertTrue(output.contains("The expense has been successfully added!"));

    }

    @Test
    void deleteNewExpenseTest() {

        Scanner scanner = mock(Scanner.class);

        when(scanner.nextLine()).thenReturn(
                "1"
        );

        Expense expenseTest = new Expense(1
                , LocalDate.of(2025, 12, 12)
                , "Test"
                , "test"
                , ExpenseService.Category.FOOD
                , 12000);

        service.addExpence(expenseTest);


        service.deleteExpense(scanner);


        assertTrue(service.getExpenses().isEmpty());
        assertFalse(service.getExpenses().containsValue(expenseTest));

    }
    @Test
    void sumOfAllExpensesTest(){
        Expense expenseTest = new Expense(1
                , LocalDate.of(2025, 12, 12)
                , "Test"
                , "test"
                , ExpenseService.Category.FOOD
                , 12000);

        Expense expenseTest2 = new Expense(2
                , LocalDate.of(2025, 12, 12)
                , "Test"
                , "test"
                , ExpenseService.Category.TRANSPORT
                , 14000);

        service.addExpence(expenseTest);
        service.addExpence(expenseTest2);





    }

    void sumOfAllMonthExpensesTest(){

    }
    @Test
    void updateExpensesTest(){

        Scanner scanner= mock(Scanner.class);

        when(scanner.nextLine()).thenReturn(
                "1",
                "category",
                "Nikita"
        );

        Expense expenseTest = new Expense(1
                , LocalDate.of(2025, 12, 12)
                , "Test"
                , "test"
                , ExpenseService.Category.FOOD
                , 12000);

        service.addExpence(expenseTest);

        service.updateExpense(scanner);

        assertSame(ExpenseService.Category.FOOD,expenseTest.getCategory());




    }}

