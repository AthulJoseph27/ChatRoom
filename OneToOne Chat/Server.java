import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Server {
    private static ServerSocket serverSocket;
    private static final int PORT = 9086;
    private static List<ConnectionHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);

    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(PORT);
        waitForConnections();
    }

    private static void waitForConnections() throws IOException {
        while (true) {
            System.out.println("[SERVER] Waiting for connection...");
            Socket client = serverSocket.accept();
            System.out.println("[SERVER] Connected to client");
            ConnectionHandler connectionHandler = new ConnectionHandler(clients.size() + 1, client);
            clients.add(connectionHandler);

            pool.execute(connectionHandler);
        }
    }

    public static int getNoOfUsersOnline() {
        return clients.size();
    }

    public static List<String> getOnlineUsers() {
        List<String> list = new ArrayList<>();

        for (ConnectionHandler cl : clients) {
            list.add("ID: " + cl.id + " " + cl.name);
        }

        return list;
    }

    public static boolean sentMessageTo(int toID, int fromID, String message) {

        try {
            ConnectionHandler from = clients.get(0), to = clients.get(0);

            for (ConnectionHandler cl : clients) {
                if (cl.id == toID)
                    to = cl;
                else if (cl.id == fromID)
                    from = cl;
            }

            if (to.id == from.id)
                return false;

            message = "[" + from.name + "]:\n" + message;

            boolean result = to.sentMessage(message);
            return result;
        } catch (Exception e) {
            return false;
        }

    }

}