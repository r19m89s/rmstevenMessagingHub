package tests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ListTestPositiveScenario {
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

            // Creating second server connection and accompanying parameters
            t = new Socket("localhost", 5000);
            System.out.println("Created socket connection.");
            t_in = new BufferedReader(
                    new InputStreamReader(t.getInputStream()));
            t_out = new PrintWriter(t.getOutputStream(), true);

            s_in.readLine();
            s_out.println("Who Am I?");
            String s_id = s_in.readLine();
            System.out.println("First connection's ID is: " + s_id);

            t_in.readLine();
            t_out.println("Who Am I?");
            String t_id = t_in.readLine();
            System.out.println("Second connection's ID is: " + t_id);

            t_out.println("Who is here?");
            String id_arr = t_in.readLine();
            System.out.println("Second connection has retrieved the following array:" + id_arr);

            if (!id_arr.contains(s_id)) {
                System.out.println("TEST FAILED. Second ID contained in id array.");
            } else if (id_arr.contains(t_id)) {
                System.out.println("TEST FAILED. First ID contained in id array.");
            } else {
                System.out.println("TEST PASSED.");
            }

            s_out.println(".");
            System.out.println("Exited First Connection.");

            t_out.println(".");
            System.out.println("Exited Second Connection.");

        } catch (

        Exception e) {
            System.err.println("Identity Test Exception:");
            e.printStackTrace();
        }
    }
}
