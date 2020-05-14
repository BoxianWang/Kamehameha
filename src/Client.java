
import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Client
 *
 * Client handles player input and process the opponent's move.
 * It keeps the status of both the player and the opponent,
 * adjusting them as the game progresses.
 *
 * Finally, it decides whether it's game over and alert the server.
 *
 */
public class Client {
    private Scanner console;					// input from the user	    // input from the user
    private BufferedReader in;		// from server
    private PrintWriter out;		// to server
    private Player self, opp;
    private Socket sock;

    // Initialization
    public Client (Socket sock) throws IOException {
        // For reading lines from the console
        console = new Scanner(System.in);
        in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        out = new PrintWriter(sock.getOutputStream(), true);
        this.sock = sock;
    }

    /**
     * Playing loop.
     * @throws IOException
     */
    public void play() throws IOException {
        String line = in.readLine();
        // wait for starting signal
        while (!line.equals("start")) {try {
            Thread.sleep(500);
        } catch(InterruptedException e) {
            System.out.println("got interrupted!");
        }}

        self = new Player();
        opp = new Player();

        // enters playing loop
        while (true) {
            String input;
            self.resetDefense();             // reset status
            opp.resetDefense();

            System.out.println("Current breath: " + self.getBreath());              // prompt user

            while (!self.handleMove((input = console.nextLine())));             // validate and process user move
            send(input);             // send to the server

            String opponent = in.readLine();
            System.out.println("The opponent's move is: " + opponent);          // read opponent move and promt user

            opp.handleMove(opponent);
            self.processOpponent(opponent);                             // processing opponent's move
            opp.processOpponent(input);

            if (self.isDead()) {                                    // checking if game over
                System.out.println("You Lost.");
                send("lost");
                break;
            } else if (opp.isDead()){
                System.out.println("You Won!!");
                send("won");
                break;
            }
        }

        // clean up
        out.close();
        in.close();
        sock.close();
    }

    /**
     * Send message to client handler.
     * @param msg
     */
    public void send(String msg) {
        this.out.println(msg);
    }


    // Kick off client!
    public static void main(String[] args) throws IOException {
        new Client(new Socket("localhost", 4242)).play();
    }

}
