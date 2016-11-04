
// To run in IDE: uncomment line
// package tests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class IdentityTestPositiveScenario {
    public static void main(String args[]) {
        Socket s = null;
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            s = new Socket("localhost", 5000);
            System.out.println("Created socket connection.");
            in = new BufferedReader(
                    new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream(), true);
            in.readLine();
            out.println("Who Am I?");
            System.out.println("Who Am I?");
            String id = in.readLine();
            System.out.println("I am: " + id);
            out.println("Exiting.");
            System.out.println(".");
        } catch (Exception e) {
            System.err.println("Identity Test Exception:");
            e.printStackTrace();
        }
    }
}
