package iznauy.runner;

import iznauy.utils.Config;
import iznauy.utils.DataBase;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by iznauy on 2017/6/7.
 */
public class Server {

    private ServerSocket serverSocket;

    public static void main(String[] args) {

        if (!DataBase.init()) {
            System.out.println("Error in init DataBase");
            System.exit(1);
        }

        try {
            new Server(Config.getPort()).run();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void run() throws IOException {
        boolean stop = false;
        Socket clinet = null;
        while (!stop) {
            System.out.println("Waiting for connection!");
            clinet = serverSocket.accept();
            System.out.println("Connected from " + clinet.getInetAddress());
            new Thread(new ClientThread(clinet)).run();
        }
        serverSocket.close();
    }

}
