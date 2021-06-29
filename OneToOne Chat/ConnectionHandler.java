import java.net.Socket;
import java.io.*;
import java.util.*;

class ConnectionHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    public int id;
    public int chatWithId = -1;
    public String name;

    ConnectionHandler(int id, Socket clientSocket) throws IOException {
        this.id = id;
        this.clientSocket = clientSocket;
        this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        this.out = new PrintWriter(this.clientSocket.getOutputStream(), true);
    }

    public static class ReadMessage implements Runnable {
        @Override
        public void run() {
        }
    }

    public static class SendMessage implements Runnable {
        @Override
        public void run() {
        }
    }

    private boolean validateChatWithId() {
        return !((chatWithId < 1 && chatWithId > Server.getNoOfUsersOnline()) || chatWithId == id);
    }

    @Override
    public void run() {
        try {
            out.println("[SERVER] Connection Successful.");
            out.println("[SERVER] Name : ");
            // System.out.println("DEBUGGING heloo...");
            this.name = in.readLine();
            // System.out.println("DEBUGGING " + name);
            // out.println(this.name);
            // while (true) {
            // String temp = in.readLine();
            // System.out.println(temp);
            // out.println("[SERVER] " + "Message Received " + temp);
            // }
            while (true) {
                String message = in.readLine();

                System.out.println("DEBUGGING " + message);

                if (message.equals("quit_chat")) {

                } else if (message.equals("view_online_users")) {
                    List<String> users = Server.getOnlineUsers();
                    out.println(users.toString());
                    out.println("[SERVER] Select a person to chat with:");
                    chatWithId = Integer.parseInt(in.readLine());
                    while (!validateChatWithId()) {
                        out.println("[SERVER] Invalid Choice!");
                        out.println("[SERVER] Select a person to chat with:");
                        chatWithId = Integer.parseInt(in.readLine());
                    }
                    System.out.println("[SERVER] Connected Succesfully.");
                    out.println("[SERVER] Connected Succesfully.");
                } else {
                    Server.sentMessageTo(chatWithId, id, message);
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