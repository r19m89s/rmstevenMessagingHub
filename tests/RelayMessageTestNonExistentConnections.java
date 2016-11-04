
// Un-comment to incorporate into IDE.
// package tests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class RelayMessageTestNonExistentConnections {
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
            String[] id_arr = f_in.readLine().replace("[", "").replace("]", "").replace(" ", "").split(",");
            List<String> temp_arr = new ArrayList<String>();

            for (int i = 0; i < id_arr.length; i++) {
                temp_arr.add(id_arr[i]);
            }
            f_out.println("Send foobar to " + id_arr);
            System.out.println("Send foobar to " + id_arr);

            message = f_in.readLine();
            if (message.equals("Message sent to non-existent connections.")) {
                f_out.println("TEST PASSED. Correct message has been sent to connection.");
            } else {
                f_out.println("TEST FAILED. False message has been sent to connection.");
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
