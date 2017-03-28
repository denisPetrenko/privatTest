package server;

import protocol.CustomRequest;
import protocol.CustomResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    public static void main(String[] args) throws IOException {
        try(
                ServerSocket socket = new ServerSocket(4444);
                Socket clientSocket = socket.accept();
                ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream())){

            while (true) {
                CustomRequest request = (CustomRequest) inputStream.readObject();
                CustomResponse response = new CustomResponse(new Controller().execute(request));
                outputStream.writeObject(response);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static class Controller{

        Object execute(CustomRequest request) throws IOException {
            switch (request.getCommand()) {
                case "list":return Dao.getAll();
                case "info_depositor": return Dao.getDepositsByDepositor(request.getDeposit().getDepositor());
                case "info_bank":return Dao.getDepositsByBank(request.getDeposit().getBankName());
                case "info_id": return Dao.getDepositById(request.getDeposit().getAccountID());
                case "info_type":return Dao.getDepositsByType(request.getDeposit().getType());
                case "insert":return Dao.insert(request.getDeposit());
                case "delete":return Dao.delete(request.getDeposit().getAccountID());

            }
            return null;
        }

    }


}
