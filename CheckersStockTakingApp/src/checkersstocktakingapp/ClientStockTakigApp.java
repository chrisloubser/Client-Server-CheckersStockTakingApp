/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 8000);
            System.out.println("Welcome to Checkers Stock Taking App");

            s.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
 */
package checkersstocktakingapp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author Christo
 */
public class ClientStockTakigApp {

    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 8000);
            System.out.println("Welcome to Checkers Stock Taking App");

            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            BufferedReader brW = new BufferedReader(new InputStreamReader(System.in));

            String productName = "";
            String productType = "";
            String productPrice = "";

            do {

                System.out.println(br.readLine());
                productName = brW.readLine();
                dout.writeUTF(productName);
                dout.flush();
                
                if(productName.equals("stop")){
                    System.out.println("Exiting Checkers Stock Taking App, Good Bye!");
                    break;
                }

                System.out.println(br.readLine());
                productType = brW.readLine();
                dout.writeUTF(productType);
                dout.flush();

                System.out.println(br.readLine());
                productPrice = brW.readLine();
                dout.writeUTF(productPrice);
                dout.flush();
                
                System.out.println("Server says: "+br.readLine());

            }while (!productName.equals("stop"));

            s.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
