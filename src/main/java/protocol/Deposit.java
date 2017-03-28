package protocol;

import java.io.Serializable;

public class Deposit implements Serializable {

    public Deposit() {
    }

    public Deposit(String bankName, String country, String type, String depositor, int accountID, double amountOnDeposit, int profitability, int timeConstraints) {
        BankName = bankName;
        Country = country;
        Type = type;
        Depositor = depositor;
        AccountID = accountID;
        AmountOnDeposit = amountOnDeposit;
        Profitability = profitability;
        TimeConstraints = timeConstraints;
    }

    private String BankName;            //название банка
    private String Country;             //страна регистрации;
    private String Type;                //тип вклада;
    private String Depositor;           // имя вкладчика;
    private int AccountID;              //номер счета;
    private double AmountOnDeposit;     //сумма вклада;
    private int Profitability;          //годовой процент;
    private int TimeConstraints;        //срок вклада.


    @Override
    public String toString() {
        return  " AccountID=" + AccountID +
                ", Depositor=" + Depositor +
                ", BankName=" + BankName +
                ", Country=" + Country +
                ", Type=" + Type +
                ", AmountOnDeposit=" + AmountOnDeposit +
                ", Profitability=" + Profitability +
                ", TimeConstraints=" + TimeConstraints+";";
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getDepositor() {
        return Depositor;
    }

    public void setDepositor(String depositor) {
        Depositor = depositor;
    }

    public int getAccountID() {
        return AccountID;
    }

    public void setAccountID(int accountID) {
        AccountID = accountID;
    }

    public double getAmountOnDeposit() {
        return AmountOnDeposit;
    }

    public void setAmountOnDeposit(double amountOnDeposit) {
        AmountOnDeposit = amountOnDeposit;
    }

    public int getProfitability() {
        return Profitability;
    }

    public void setProfitability(int profitability) {
        Profitability = profitability;
    }

    public int getTimeConstraints() {
        return TimeConstraints;
    }

    public void setTimeConstraints(int timeConstraints) {
        TimeConstraints = timeConstraints;
    }
}
