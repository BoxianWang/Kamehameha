import java.net.*;
import java.util.*;
import java.io.*;

/**
 * Server
 *
 * Server is responsible for establishing communication between clients.
 * It uses Comms to receive message from clients;
 * Subsequently, it broadcasts message to clients when appropriate.
 *
 */
public class Server {
    private ServerSocket listen;	            // accept connection
    private ArrayList<Comm> comms;              // list of client handlers
    private String[] moves = new String[2];  // used for storing moves
    private boolean filled = false;

    /***
     * Create server with a listening port
     * @param listen
     */

    public Server(ServerSocket listen) {
        this.listen = listen;
        comms = new ArrayList<Comm>();
    }

    /**
     * Get connections from client and assign to comms
     * @throws IOException
     */

    public void getConnections() throws IOException {
        while (comms.size() < 2) {
            //listen.accept in next line blocks until new connection
            Comm comm = new Comm(listen.accept(), this, comms.size());
            comm.setDaemon(false);
            addCommunicator(comm);
        }
        for (Comm c: comms) {
            c.start();  // start client handlers
        }
    }

    /**
     * To be invoked by comms when they received a message from client.
     * Will block until both messages are in place,
     * Then broadcast to all clients (exchange information).
     * @param s move
     * @param id comm id
     */
    public synchronized void storeMove(String s, int id) {
        moves[id] = s;
        if (!filled) {      // if not not both messages are here
            filled = true;
            System.out.println(s);
            try {
                wait();         // block client handler
            } catch(InterruptedException e) {
                System.out.println("got interrupted!");
            }
        } else {
            filled = false;
            System.out.println(s);
            broadcast();    // broadcast move
            notifyAll();    // wake up clients
        }
    }


    /**
     * Add or remove the list of current client handlers
     */
    public synchronized void addCommunicator(Comm comm) {
        comms.add(comm);
    }

    public synchronized void removeCommunicator(Comm comm) {
        comms.remove(comm);
    }

    /**
     * Broadcast the stored moves to the other player
     */
    public synchronized void broadcast() {
        for (Comm c : comms) {
            c.send(moves[1 - c.getIdnum()]);
        }
    }

    /**
     * Kick off Server!
     * @param args
     * @throws Exception
     */

    public static void main(String[] args) throws Exception {
        System.out.println("waiting for connections");
        new Server(new ServerSocket(4242)).getConnections();
    }
}

