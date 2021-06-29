import java.net.Socket;
import java.io.*;

class Client {
    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 9086;
    private static final String STOP = "quit";
    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in, userInput;
    // private static boolean typing = false;
    private static boolean stopChat = false;
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
                    // if (STOP.equals(message)) {
                    // stopConnection();
                    // break;
                    // }
                    System.out.println("Debugging " + message);
                    out.println(message);
                    System.out.println("Message Sent");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // typing = false;
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

            // String temp = "";

            // while (true) {
            // temp = userInput.readLine();
            // out.println(temp);
            // temp = in.readLine();
            // System.out.println(temp);
            // }
            readThread = new Thread(new ReadMessageThread());
            readThread.start();

            sendThread = new Thread(new SendMessageThread());
            sendThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // try {

        // String message = "";

        // String connectionStatus = in.readLine();

        // System.out.println(connectionStatus);

        // if (!connectionStatus.equals("[SERVER] Connection Successful.")) {
        // out.close();
        // in.close();
        // userInput.close();
        // clientSocket.close();
        // throw null;
        // }

        // String serverLog = in.readLine();

        // System.out.println(serverLog);
        // message = userInput.readLine();
        // out.println(message);

        // while (!STOP.equals(message)) {
        // message = userInput.readLine();
        // if (message.equals("view_online_users")) {
        // out.println(message);
        // serverLog = in.readLine();
        // System.out.println(serverLog);
        // serverLog = in.readLine();
        // System.out.println(serverLog);

        // message = userInput.readLine();
        // out.println(message);
        // serverLog = in.readLine();
        // System.out.println(serverLog);

        // while (!serverLog.equals("[SERVER] Connected Succesfully.")) {
        // serverLog = in.readLine();
        // System.out.println(serverLog);

        // message = userInput.readLine();
        // out.println(message);
        // serverLog = in.readLine();
        // }

        // } else {
        // out.println(message);
        // System.out.println(in.readLine());

        // }
        // // System.out.println("[ME] : \n" + message);
        // }

        // out.close();
        // in.close();
        // userInput.close();
        // clientSocket.close();

        // } catch (Exception e) {
        // System.out.println(e.getStackTrace());
        // }

    }

    // public static void stopConnection() {
    // readThread.stopThread();
    // sendThread.stopThread();
    // out.close();
    // try {
    // in.close();
    // userInput.close();
    // clientSocket.close();
    // } catch (Exception e) {

    // }
    // }
}