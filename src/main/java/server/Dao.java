package server;

import com.fasterxml.jackson.databind.ObjectMapper;
import protocol.Deposit;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Dao {

    private static String path = "storage/";

    private static File storage = new File(path);

    static{
        storage.mkdir();
    }

    public static List<Deposit> getAll() throws IOException {
        try {
            Set<File> files = Arrays.stream(storage.listFiles()).collect(Collectors.toSet());
            ObjectMapper mapper = new ObjectMapper();
            List<Deposit> depositList = new ArrayList<>();
            for (File f : files) {
                Deposit d = mapper.readValue(f, Deposit.class);
                depositList.add(d);
            }
            return depositList;
        }catch (NullPointerException e){
            return Collections.EMPTY_LIST;
        }
    }


    public static Deposit getDepositById(int id) throws IOException {
        File file = new File(path+id+".json");
        ObjectMapper mapper = new ObjectMapper();
        if (file.exists()){
           return mapper.readValue(file,Deposit.class);
        }
        return null;
    }
    public static List<Deposit> getDepositsByDepositor(String depositor) throws IOException {
        try {
            Set<File> files = Arrays.stream(storage.listFiles()).collect(Collectors.toSet());
            ObjectMapper mapper = new ObjectMapper();
            List<Deposit> depositList = new ArrayList<>();
            for (File f : files) {
                Deposit d = mapper.readValue(f, Deposit.class);
                if (Objects.equals(d.getDepositor(), depositor)) {
                    depositList.add(d);
                }
            }
            return depositList;
        }catch (NullPointerException e){
            return Collections.EMPTY_LIST;
        }
    }

    public static List<Deposit> getDepositsByType(String type) throws IOException {
        try {
            Set<File> files = Arrays.stream(storage.listFiles()).collect(Collectors.toSet());
            ObjectMapper mapper = new ObjectMapper();
            List<Deposit> depositList = new ArrayList<>();
            for (File f : files) {
                Deposit d = mapper.readValue(f, Deposit.class);
                if (Objects.equals(d.getType(), type)) {
                    depositList.add(d);
                }
            }
            return depositList;
        }catch (NullPointerException e){
            return Collections.EMPTY_LIST;
        }
    }
    public static List<Deposit> getDepositsByBank(String bank) throws IOException {
        try {
            Set<File> files = Arrays.stream(storage.listFiles()).collect(Collectors.toSet());
            ObjectMapper mapper = new ObjectMapper();
            List<Deposit> depositList = new ArrayList<>();
            for (File f : files) {
                Deposit d = mapper.readValue(f, Deposit.class);
                if (Objects.equals(d.getBankName(), bank)) {
                    depositList.add(d);
                }
            }
            return depositList;
        }catch (NullPointerException e){
            return Collections.EMPTY_LIST;
        }
    }
    public static boolean insert(Deposit deposit) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(path+deposit.getAccountID()+".json");
        if (!file.exists()) {
            mapper.writeValue(new File(path + deposit.getAccountID() + ".json"), deposit);
            return true;
        }else return false;
    }
    public static double sum() throws IOException {
        double sum = 0;
        List<Deposit> depositList = getAll();
        for (Deposit d: depositList){
           sum+=d.getAmountOnDeposit();
        }
        return sum;
    }
    public  static int count() throws IOException {
        return getAll().size();
    }

    public static boolean delete(int id){
        File file=new File(path+id+".json");
        return file.delete();
    }

    public static void setPath(String path) {
        Dao.path = path;
        storage = new File(path);
        storage.mkdir();
    }


}
