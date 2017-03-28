package client;

import protocol.CustomRequest;
import protocol.CustomResponse;
import protocol.Deposit;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Client {
    private static ObjectInputStream inputStream;
    private static ObjectOutputStream outputStream;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost",4444);
        inputStream = new ObjectInputStream(socket.getInputStream());
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        Scanner scanner = new Scanner(System.in);
        while (true){
            String command;
            String data = "";
            System.out.print("command:");
            command = scanner.next();
            if (!command.equals("sum")&&!command.equals("list")&&!command.equals("count")) {
                if (command.equals("add")){
                    insert(buildDeposit(scanner));
                    continue;
                }else {
                    System.out.print("data:");
                    data = scanner.next();
                }
            }
            System.out.println("\nresults:");
            execute(command,data);
        }
    }
    private static boolean execute(String command, String data) throws IOException, ClassNotFoundException {

        switch (command){
            case "list": getAllRequest(); break;
            case "delete": deleteRequest(data);break;
            case "info_depositor": infoByDepositorRequest(data); break;
            case "info_account": infoByIDRequest(data); break;
            case "info_bank":infoByBankRequest(data);break;
            case "info_type":infoByTypeRequest(data);break;
            default:
                System.out.println("bad command");
        }
        return true;
    }

    private static void deleteRequest(String data) throws IOException, ClassNotFoundException {
        Deposit deposit = new Deposit();
        deposit.setAccountID(Integer.parseInt(data));
        outputStream.writeObject(new CustomRequest(deposit,"delete"));

        CustomResponse response = (CustomResponse) inputStream.readObject();
        boolean status = (boolean)response.getData();
        if (status){
            System.out.println("Delete is successful!");
        }else {
            System.out.println("Delete is unsuccessful!");
        }
    }

    private static void infoByTypeRequest(String data) throws IOException, ClassNotFoundException {
        Deposit deposit = new Deposit();
        deposit.setType(data);
        outputStream.writeObject(new CustomRequest(deposit,"info_depositor"));
        listResponse();
    }

    private static void getAllRequest() throws IOException, ClassNotFoundException {
        outputStream.writeObject(new CustomRequest(null,"list"));
        listResponse();

    }

    private static void infoByDepositorRequest(String data) throws IOException, ClassNotFoundException {
        Deposit deposit = new Deposit();
        deposit.setDepositor(data);
        outputStream.writeObject(new CustomRequest(deposit,"info_depositor"));
        listResponse();
    }
    private static void infoByBankRequest(String data) throws IOException, ClassNotFoundException {
        Deposit deposit = new Deposit();
        deposit.setBankName(data);
        outputStream.writeObject(new CustomRequest(deposit,"info_bank"));
        listResponse();
    }

    private static void infoByIDRequest(String data) throws IOException, ClassNotFoundException {
        Deposit deposit = new Deposit();
        deposit.setAccountID(Integer.parseInt(data));
        outputStream.writeObject(new CustomRequest(deposit,"info_id"));
        CustomResponse customResponse = (CustomResponse) inputStream.readObject();
        deposit = (Deposit) customResponse.getData();
        if(deposit != null){
            System.out.println(deposit.toString());
        }else {
            System.out.println("Account not found");
        }

    }
    private static void insert(Deposit deposit) throws IOException, ClassNotFoundException {
        outputStream.writeObject(new CustomRequest(deposit,"insert"));
        CustomResponse response = (CustomResponse) inputStream.readObject();
        boolean status = (boolean) response.getData();
        if (status){
            System.out.println("Insert is successful!");
        }else {
            System.out.println("Insert is unsuccessful!");
        }
    }


    private static void listResponse() throws IOException, ClassNotFoundException {
        CustomResponse customResponse = (CustomResponse) inputStream.readObject();
        List<Deposit> depositList = (List<Deposit>) customResponse.getData();
        for (Deposit d:depositList) {
            System.out.println(d.toString());
        }
    }

    private static Deposit buildDeposit(Scanner scanner){
        Deposit deposit = new Deposit();
        System.out.print("Id: ");
        deposit.setAccountID(scanner.nextInt());
        System.out.print("Depositor: ");
        deposit.setDepositor(scanner.next());
        System.out.print("Amount on deposit: ");
        deposit.setAmountOnDeposit(scanner.nextDouble());
        System.out.print("Bank: ");
        deposit.setBankName(scanner.next());
        System.out.print("Type: ");
        deposit.setType(scanner.next());
        System.out.print("Country: ");
        deposit.setCountry(scanner.next());
        System.out.print("Profitability: ");
        deposit.setProfitability(scanner.nextInt());
        System.out.print("Time Constraints (months): ");
        deposit.setTimeConstraints(scanner.nextInt());
        return deposit;
    }


}
