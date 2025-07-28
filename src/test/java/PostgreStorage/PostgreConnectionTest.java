package PostgreStorage;

import org.junit.jupiter.api.Test;

import java.sql.*;

public class PostgreConnectionTest {

    @Test
    public void postgreConectTest() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/FinanceTracker";
        String user = "postgres";
        String password = "1234";
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Successfully connected to Database");
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM public.expenses");
                System.out.println("You query is correct");

                while (rs.next()) {
                    int id = rs.getInt("id");
                    Date date = rs.getDate("date");
                    String name = rs.getString("name");
                    System.out.printf("Expense #%d: %s — %s%n", id, date, name);
                }
                System.out.println();

                ResultSet rs2 = stmt.executeQuery("SELECT * FROM public.expenses");
                while (rs2.next()) {
                    int id = rs2.getInt("id");
                    Date date = rs2.getDate("date");
                    String name = rs2.getString("name");
                    String descr = rs2.getString("Description");
                    String category = rs2.getString("Category");
                    Integer cost = rs2.getInt("Cost");
                    System.out.printf("#%d: %s — %s — %s — %s — %d%n", id, date, name, descr, category, cost);


                }
            } catch (SQLException e) {
                System.out.println("error while connecting " + e.getMessage());
            }


        }
    }
}


