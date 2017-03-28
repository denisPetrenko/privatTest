package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            System.err.println("Usage: java server <port number> or java server <port number> <storage path> ");
            System.exit(1);
        }
        if (args.length == 2){
            Dao.setPath(args[1]);
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
