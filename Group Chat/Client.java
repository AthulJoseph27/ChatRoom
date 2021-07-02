import java.net.Socket;
import java.io.*;

class Client {
    private static final String ADDRESS = "3.7.253.234";
    private static final int PORT = 9086;
    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in, userInput;
    private static Thread readThread, sendThread;

    public static class ReadMessageThread implements Runnable {
        private boolean exit = false;

        @Override
        public void run() {
            while (!exit) {
                try {
                    String message = in.readLine();
                    System.out.println(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void stopThread() {
            exit = true;
        }
    }

    public static class SendMessageThread implements Runnable {
        private boolean exit = false;

        @Override
        public void run() {
            while (!exit) {
                try {
                    String message = userInput.readLine();
                    out.println(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void stopThread() {
            exit = true;
        }
    }

    public static void main(String[] args) throws IOException {
        startConnection();
    }

    public static void startConnection() {

        try {
            clientSocket = new Socket(ADDRESS, PORT);

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            userInput = new BufferedReader(new InputStreamReader(System.in));

            out = new PrintWriter(clientSocket.getOutputStream(), true);

            readThread = new Thread(new ReadMessageThread());
            readThread.start();

            sendThread = new Thread(new SendMessageThread());
            sendThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}