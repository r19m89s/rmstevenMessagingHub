package src;

// import util.properties packages
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Create java class named “MessagingHub”
public class MessagingHubServer {
    private static List<Integer> client_ids = new ArrayList<Integer>();
    private static ExecutorService pool = Executors.newFixedThreadPool(255);
    private static HashMap<Integer, PrintWriter> writers = new HashMap<Integer, PrintWriter>();

    public static void main(String[] args) throws Exception {
        ServerSocket s = null;
        PrintStream out = null;
        BufferedReader in = null;

        int curr_id;
        try {
            // 1. creating a server socket - 1st parameter is port number and 2nd is the backlog
            s = new ServerSocket(5000);
            echo("Server socket created.Waiting for connection...");
            while (true) {
                // get the connection socket
                final Socket conn = s.accept();
                Runnable task = new Runnable() {
                    public void run() {
                        handleRequest(conn);
                    }
                };
                pool.execute(task);

            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        // 5. close the connections and stream
        try {
            in.close();
            out.close();
            s.close();
        }

        catch (IOException ioException) {
            System.err.println("Unable to close. IOexception");
        }
    }

    public static void handleRequest(Socket conn) {
        try {
            // print the hostname and port number of the connection
            echo("Connection received from " + conn.getInetAddress().getHostName() + " : " + conn.getPort());
            int curr_id = (int) (Math.random() * 50 + 1);

            // create new thread to handle client
            new client_handler(conn, curr_id).run();
        } catch (Exception e) {
            System.out.println("catch: " + e.toString());
        }
    }

    public static void echo(String msg) {
        System.out.println(msg);
    }

    public List<Integer> getIds() {
        return client_ids;
    }

    public void addId(int id) {
        client_ids.add(id);
    }

    public static class client_handler implements Runnable {
        private Socket conn;
        private int id;

        client_handler(Socket conn, int id) {
            this.conn = conn;
            this.id = id;
        }

        public synchronized void run() {
            String line, input = "";

            try {
                // get socket writing and reading streams
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                PrintStream out_stream = new PrintStream(conn.getOutputStream());
                PrintWriter out_writer = new PrintWriter(conn.getOutputStream(), true);

                // Send welcome message to client
                out_stream.println("Welcome to the Server. Enter '.' to exit server connection.");

                synchronized (client_ids) {
                    if (!client_ids.contains(this.id)) {
                        client_ids.add(this.id);
                    }
                }
                writers.put(this.id, out_writer);

                // Now start reading input from client
                while ((line = in.readLine()) != null && !line.equals(".")) {
                    // If asking for id, print current ID
                    if (line.equals("Who Am I?")) {
                        out_stream.println(this.id);
                    }
                    // If asking for presence of current ID's, respond with an array of all ids excluding current ID
                    else if (line.equals("Who is here?")) {
                        List<Integer> temp_list = new ArrayList<Integer>();
                        for (int i = 0; i < client_ids.size(); i++) {
                            if (client_ids.get(i) != this.id) {
                                temp_list.add(client_ids.get(i));
                            }
                        }
                        out_stream.println(temp_list.toString());
                    }
                    // In the case that the current socket is attempting to send messages, send messages to particular
                    // sockets.
                    else if (line.startsWith("Send")) {
                        String[] line_arr = line.split(" to ");
                        String message = line_arr[0].replace("Send ", "");
                        String receiving = line_arr[1].replace(" and ", ",");
                        String[] receive_list = receiving.split(",");
                        Iterator it = writers.entrySet().iterator();
                        while (it.hasNext()) {
                            Integer key = ((Integer) ((HashMap.Entry) it.next()).getKey());
                            if (Arrays.asList(receive_list).contains(key.toString())) {
                                writers.get(key).println(message);
                            }
                        }
                    } else {
                        out_stream.println("NOT RECOGNIZED COMMAND. PLEASE RETRY.");
                    }
                }

                // client disconnected, so close socket
                client_ids.remove(client_ids.indexOf(this.id));
                conn.close();
            }

            catch (IOException e) {
                System.out.println("IOException on socket : " + e);
                e.printStackTrace();
            }
        }
    }
}
