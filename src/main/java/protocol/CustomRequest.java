package protocol;


import java.io.Serializable;

public class CustomRequest implements Serializable {

    String command;

    Deposit deposit;

    public CustomRequest(Deposit deposit, String command) {
        this.deposit = deposit;
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Deposit getDeposit() {
        return deposit;
    }

    public void setDeposit(Deposit deposit) {
        this.deposit = deposit;
    }
}
