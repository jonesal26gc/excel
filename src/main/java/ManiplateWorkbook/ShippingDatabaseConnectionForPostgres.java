package ManiplateWorkbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ShippingDatabaseConnectionForPostgres {

    public Connection c = null;

    public void establishConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/shipping_network_3",
                            "postgres", "postgres");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }


    public void terminateConnection() {
        try {
            c.close();
        } catch (SQLException SQLex) {
            SQLex.printStackTrace();
        }
    }
}



