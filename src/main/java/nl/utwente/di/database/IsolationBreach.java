package nl.utwente.di.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class IsolationBreach {
    public static Connection conn;

    static Scanner sc = new Scanner(System.in);
    static String dbuser = "dab_di21222b_251";       // TODO: CHANGE THIS LINE
    static String passwd = "O3ZCMvwn+K5DCNdh";   // TODO: CHANGE THIS LINE

    public static void main (String[] args) {
        try {
            Class.forName("org.postgresql.Driver");

            try {
                conn = DriverManager.getConnection(
                        "jdbc:postgresql://bronto.ewi.utwente.nl/"+dbuser,
                        dbuser, passwd);
                conn.setAutoCommit(true);
                conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

                // Preliminaries
                System.out.println("Isolation Breach test");
                System.out.println();

                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

                // Now start the scenario
                getMake("BMW");
                // Finalize
                conn.setAutoCommit(true);
                conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
                System.out.println();
                System.out.println("Final state in database");
                conn.close();
            } catch(SQLException e) {
                System.err.println("Oops: " + e.getMessage() );
                System.err.println("SQLState: " + e.getSQLState() );
            }
        }
        catch (ClassNotFoundException e) {
            System.err.println("JDBC driver not loaded");
        }
    }

    public static void waitForKey(String msg) {
        System.out.print(msg+" ... Press ENTER");
        sc.nextLine();
        // Reads everything until the ENTER
        // We do not use System.in.read() because on Windows a
        // return/enter produces TWO characters
    }


    public static String getMake(String name) {
        String make = " ";
        String query="SELECT c.make FROM betterbe.car c WHERE c.make LIKE " + "'" + name + "'";
        try {
            Statement st = conn.createStatement();
            System.out.println("Query: "+query);
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                make =rs.getString("make");
                System.out.println("Result : "+make);
            }
            rs.close();
            st.close();
        } catch(SQLException e) {
            System.err.println("Oops: " + e.getMessage() );
            System.err.println("SQLState: " + e.getSQLState() );
        }
        return make;
    }

    public static void updateCar(String acc, String amount) {
        String query="UPDATE car.cars SET amount="+amount+"WHERE name='"+acc+"'";
        try {
            Statement st = conn.createStatement();
            System.out.println("Query: "+query);
            st.executeUpdate(query);
            System.out.println("Done");
            st.close();
        } catch(SQLException e) {
            System.err.println("Oops: " + e.getMessage() );
            System.err.println("SQLState: " + e.getSQLState() );
        }
    }
}


