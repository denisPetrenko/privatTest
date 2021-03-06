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
        if (args.length != 2) {
            System.err.println("Usage: java Client <host> <port number>");
            System.exit(1);
        }
        Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
        inputStream = new ObjectInputStream(socket.getInputStream());
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter help to get a list of commands");
        while (true){
            String command;
            String data = "";
            System.out.print("command:");
            command = scanner.next();
            if (!command.equals("sum")&&!command.equals("list")&&!command.equals("count")&&!command.equals("help")) {
                if (command.equals("exit"))System.exit(1);
                if (command.equals("add")){
                    insertRequest(buildDeposit(scanner));
                    continue;
                }else {
                    System.out.print("data:");
                    data = scanner.next();
                }
            }
            System.out.println("\nresults:");
            execute(command,data);
            System.out.println();
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
            case "sum":sumRequest();break;
            case "count":countRequest();break;
            case "help":helpPrint();break;
            default:
                System.out.println("bad command");
        }
        return true;
    }

    private static void getAllRequest() throws IOException, ClassNotFoundException {
        outputStream.writeObject(new CustomRequest(null,"list"));
        listResponse();
    }

    private static void infoByTypeRequest(String data) throws IOException, ClassNotFoundException {
        Deposit deposit = new Deposit();
        deposit.setType(data);
        outputStream.writeObject(new CustomRequest(deposit,"info_type"));
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
    private static void insertRequest(Deposit deposit) throws IOException, ClassNotFoundException {
        outputStream.writeObject(new CustomRequest(deposit,"insert"));
        CustomResponse response = (CustomResponse) inputStream.readObject();
        boolean status = (boolean) response.getData();
        if (status){
            System.out.println("Insert is successful!");
        }else {
            System.out.println("Insert is unsuccessful!");
        }
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
    private static void sumRequest() throws IOException, ClassNotFoundException {
        outputStream.writeObject(new CustomRequest(null,"sum"));
        CustomResponse response = (CustomResponse) inputStream.readObject();
        double sum = (double) response.getData();
        System.out.println("Total: "+sum);
    }
    private static void countRequest() throws IOException, ClassNotFoundException {
        outputStream.writeObject(new CustomRequest(null,"count"));
        CustomResponse response = (CustomResponse) inputStream.readObject();
        int count = (int) response.getData();
        System.out.println("Count of deposits: "+count);
    }


    private static void listResponse() throws IOException, ClassNotFoundException {
        CustomResponse customResponse = (CustomResponse) inputStream.readObject();
        List<Deposit> depositList = (List<Deposit>) customResponse.getData();
        for (Deposit d:depositList) {
            System.out.println(d.toString());
        }
    }
    private static void helpPrint(){
        System.out.println("Commands:");
        System.out.println("   list: all deposits list;");
        System.out.println("   info_account: get deposit by account id; ");
        System.out.println("   info_depositor: get deposit list by depositor name; ");
        System.out.println("   info_type: get deposit list by type; ");
        System.out.println("   info_bank: get deposit  list by bank; ");
        System.out.println("   add: add new deposit");
        System.out.println("   delete: delete deposit by id");
        System.out.println("   sum: total amount of deposits");
        System.out.println("   count: count of deposits");
        System.out.println("   exit: exit");
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
