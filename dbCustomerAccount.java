package OnlineStore;

import java.util.Scanner;

public class dbCustomerAccount {
    private String ID;
    private double balanceAmt;
    double money;
    Boolean Flag = false;


    public Boolean balanceCut(String ID, double billAmt){
        this.ID = ID;

        while(balanceAmt <= 0 || balanceAmt < billAmt){
            System.out.println("Insufficient balance.");
            System.out.println("Please load balance: ");
            // write method to load balance
            balanceAmt = this.loadBalance(ID);
            Flag = true;
        }
        balanceAmt = balanceAmt - billAmt;
        this.balanceAmt = balanceAmt;
        System.out.println("Your current balance after purchase is: "+balanceAmt);
        return Flag;

    }
    public double loadBalance(String ID){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter amount to load: ");
        double money1 = scan.nextDouble();
        money = money + money1;
        return money;
    }

}
