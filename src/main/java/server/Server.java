package server;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Deposit;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Server {

    static String path = "storage/";
    static File storage;
    public static void main(String[] args) {
        storage=new File(path);
    }

    public static String add(String bankName, String country, String type, String depositor, int accountID, double amountOnDeposit, int profitability, int timeConstraints) throws IOException {
        Deposit deposit = new Deposit(bankName, country, type, depositor, accountID, amountOnDeposit,
                profitability, timeConstraints);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(path+accountID+".json"),deposit);
        return null;
    }
    public static List<Deposit> getAll() throws IOException {
        Set<File> files = Arrays.stream(storage.listFiles()).collect(Collectors.toSet());
        ObjectMapper mapper = new ObjectMapper();
        List<Deposit> depositList = new ArrayList<>();
        for (File f:files) {
            Deposit d = mapper.readValue(f,Deposit.class);
            depositList.add(d);
        }
        return depositList;
    }

    public static String getDepositById(int id) throws IOException {
        File file = new File(path+id+".json");
        ObjectMapper mapper = new ObjectMapper();
        if (file.exists()){
           return mapper.readValue(file,Deposit.class).toString();
        }
        return null;
    }
    public static List<Deposit> getDepositsByDepositor(String depositor) throws IOException {
        Set<File> files = Arrays.stream(storage.listFiles()).collect(Collectors.toSet());
        ObjectMapper mapper = new ObjectMapper();
        List<Deposit> depositList = new ArrayList<>();
        for (File f:files) {
            Deposit d = mapper.readValue(f,Deposit.class);
            if(Objects.equals(d.getDepositor(), depositor)){
                depositList.add(d);
            }
        }
        return depositList;
    }
    public static List<Deposit> getDepositsByType(String type) throws IOException {
        Set<File> files = Arrays.stream(storage.listFiles()).collect(Collectors.toSet());
        ObjectMapper mapper = new ObjectMapper();
        List<Deposit> depositList = new ArrayList<>();
        for (File f:files) {
            Deposit d = mapper.readValue(f,Deposit.class);
            if(Objects.equals(d.getType(), type)){
                depositList.add(d);
            }
        }
        return depositList;
    }
    public static List<Deposit> getDepositsByBank(String bank) throws IOException {
        Set<File> files = Arrays.stream(storage.listFiles()).collect(Collectors.toSet());
        ObjectMapper mapper = new ObjectMapper();
        List<Deposit> depositList = new ArrayList<>();
        for (File f:files) {
            Deposit d = mapper.readValue(f,Deposit.class);
            if(Objects.equals(d.getBankName(), bank)){
                depositList.add(d);
            }
        }
        return depositList;
    }

    public static void delete(int id){
        File file=new File(path+id+".json");
        file.delete();
    }







}
