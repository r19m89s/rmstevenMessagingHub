package tests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RelayMessageTestPositiveScenario {
    public static void main(String args[]) {
        Socket f = null;
        Socket s = null;
        Socket t = null;
        PrintWriter f_out = null;
        BufferedReader f_in = null;
        PrintWriter s_out = null;
        BufferedReader s_in = null;
        PrintWriter t_out = null;
        BufferedReader t_in = null;
        try {
            // Creating first server connection and accompanying parameters
            f = new Socket("localhost", 5000);
            System.out.println("Created first socket connection.");
            f_in = new BufferedReader(
                    new InputStreamReader(f.getInputStream()));
            f_out = new PrintWriter(f.getOutputStream(), true);
            String message = f_in.readLine();

            // Creating second server connection and accompanying parameters
            s = new Socket("localhost", 5000);
            System.out.println("Created second socket connection.");
            s_in = new BufferedReader(
                    new InputStreamReader(s.getInputStream()));
            s_out = new PrintWriter(s.getOutputStream(), true);
            message = s_in.readLine();

            // Creating third server connection and accompanying parameters
            t = new Socket("localhost", 5000);
            System.out.println("Created third socket connection.");
            t_in = new BufferedReader(
                    new InputStreamReader(t.getInputStream()));
            t_out = new PrintWriter(t.getOutputStream(), true);
            message = t_in.readLine();

            // Sending message to 2nd and 3rd Connections through 1st Connection. After getting ids for 2nd and 3rd
            // connections through 1st connection.
            f_out.println("Who is here?");
            String id_arr = f_in.readLine().replace("[", "").replace("]", "").replace(" ", "");
            f_out.println("Send foobar to " + id_arr);
            System.out.println("Send foobar to " + id_arr);

            // Second connection attempting to retrieve message
            String s_message = s_in.readLine();
            System.out.println("Second connection's message is: " + s_message);

            // Third connection attempting to retrieve message
            String t_message = t_in.readLine();
            System.out.println("Third connection's ID is: " + t_message);

            if (!s_message.equals("foobar") || !t_message.equals("foobar")) {
                System.out.println("Second and Third Connections haven't received messages.");
            }

            // Exiting connections
            f_out.println(".");
            System.out.println("Exited First Connection.");

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
