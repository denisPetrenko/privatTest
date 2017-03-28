package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java server <port number>");
            System.exit(1);
        }
        try(ServerSocket socket = new ServerSocket(Integer.parseInt(args[0]))) {
            while (true) {
                Socket clientSocket = socket.accept();
                Thread thread = new Thread(new ClientThread(clientSocket));
                thread.start();
            }
        }
    }




}
