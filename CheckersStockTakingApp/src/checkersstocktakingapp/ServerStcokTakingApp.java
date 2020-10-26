/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkersstocktakingapp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Statement;

/**
 *
 * @author Christo
 */
public class ServerStcokTakingApp {

    public static void main(String[] args) throws Exception {
       
        try {
            ServerSocket ss = new ServerSocket(8000);
            System.out.println("Server starting..");
            
            
            Socket Socket = ss.accept();

            DataInputStream din = new DataInputStream(Socket.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter printW = new PrintWriter(Socket.getOutputStream(), true);

            String productName = "";
            String productType = "";
            double productPrice = 0;
            do {
                printW.println("Enter Product Name:");
                productName = din.readUTF();
                printW.flush();
                if (productName.equals("stop")) {
                    System.out.println("Server is stopping...");
                    break;
                }
                printW.println("Enter Product Type:");
                productType = din.readUTF();
                printW.flush();
                printW.println("Enter Product Price:");
                productPrice = Double.parseDouble(din.readUTF());
                printW.flush();

                System.out.println("Server received Product Details: " + productName + " " + productType + " " + productPrice);

                try {
                    java.sql.Connection con = (java.sql.Connection) java.sql.DriverManager.getConnection("jdbc:mysql://127.0.0.1:3308/checkersproducts","root","");
                    System.out.println("Database connected");
                    String query = "INSERT INTO products (prodName, prodType, prodPrice) " + "VALUES ('" + productName + "', '" + productType + "', " + productPrice + ");";
                    Statement ps = con.createStatement();
                    ps.executeUpdate(query);
                    System.out.println("Product added to database");
                    printW.println("Product has been added");
                    printW.flush();
                    
                } catch (Exception e) {
                    System.out.println(e.toString());
                }

            } while (!productName.equals("stop"));

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
