
import java.io.*;
import java.net.*;

/**
 * Comm
 *
 * Comm handles client input and sends it to server.
 * It is implemented as a thread to support simultaneous processing of client input.
 *
 */
public class Comm extends Thread {

    private Socket sock;
    private Server server;                // the main server instance
    private BufferedReader in;                // from client
    private PrintWriter out;                // to client
    private int id;                     //  client (player) ID

    // constructor
    public Comm(Socket sock, Server server, int id) {
        this.sock = sock;
        this.server = server;
        this.id = id;
    }


    // get ID
    public int getIdnum() {
        return id;
    }

    /**
     * Handles client input.
     */
    public void run() {
        try {
            System.out.println(id + " connected");

            // Setup communication channel
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            out = new PrintWriter(sock.getOutputStream(), true);

            out.println("start");   // send starting signal

            String line;
            while ((line = in.readLine()) != null) {    // read from client
                if (line.equals("won")  || line.equals("lost")) {  // exit if game over
                    System.out.println("Player " + id + line);
                    break;
                }
                server.storeMove(line, id);     // ask the server to process the move
            }

            // clean up
            server.removeCommunicator(this);
            out.close();
            in.close();
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Sends a message to the client
     *
     * @param msg
     */
    public void send(String msg) {
        out.println(msg);
    }

}
