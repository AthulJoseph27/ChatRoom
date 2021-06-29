import java.net.Socket;
import java.io.*;
import java.util.*;

class ConnectionHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    public int id;
    public String name;

    ConnectionHandler(int id, Socket clientSocket) throws IOException {
        this.id = id;
        this.clientSocket = clientSocket;
        this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        this.out = new PrintWriter(this.clientSocket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            out.println("[SERVER] Connection Successful.");
            out.println("[SERVER] Name : ");

            this.name = in.readLine();

            while (true) {
                String message = in.readLine();

                if (message.equals("quit_chat")) {

                } else {
                    Server.sentMessageTo(id, message);
                }

            }
        } catch (IOException e) {
            System.err.println("IOException in ConnectionHandler");
            e.printStackTrace();
        } finally {
            out.close();
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean sentMessage(String message) {

        try {
            out.println(message);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}