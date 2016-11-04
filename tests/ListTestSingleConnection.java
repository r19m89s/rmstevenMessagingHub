
// Please only un-comment this line in the case of building using an IDE
// package tests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ListTestSingleConnection {
    public static void main(String args[]) {
        Socket s = null;
        Socket t = null;
        PrintWriter s_out = null;
        BufferedReader s_in = null;
        PrintWriter t_out = null;
        BufferedReader t_in = null;
        try {
            // Creating first server connection and accompanying parameters
            s = new Socket("localhost", 5000);
            System.out.println("Created socket connection.");
            s_in = new BufferedReader(
                    new InputStreamReader(s.getInputStream()));
            s_out = new PrintWriter(s.getOutputStream(), true);

            s_in.readLine();

            s_out.println("Who is here?");
            String id_arr = s_in.readLine();
            System.out.println("Second connection has retrieved the following array:" + id_arr);

            if (!id_arr.equals("[]")) {
                System.out.println("TEST FAILED. Did not return empty array.");
            } else {
                System.out.println("TEST PASSED.");
            }

            s_out.println(".");
            System.out.println("Exited First Connection.");

        } catch (

        Exception e) {
            System.err.println("Identity Test Exception:");
            e.printStackTrace();
        }
    }
}
