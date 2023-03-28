package OnlineStore;

import java.sql.*;
import java.util.Scanner;

public class dbPayments {
    public  String findCustCard(String Loginid){
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";
        String st = null;
        Boolean Flag = true;

        String sql1 = "select * from holds";
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.
             //PreparedStatement p1 = con.prepareStatement(sql1);

        ){
            //p1.setString(1,LoginId);
            ResultSet R2 = st1.executeQuery(sql1);
            //System.out.println("CustomerID \t Delivery  \t Permanent \t \n");
            while(R2.next()){
                if(Loginid.equals(R2.getString("customerid"))) {
                    st = R2.getString("cardnum");
                    Flag = false;
                    //************** IF new customer write code here to add new credit card ***********
                }
            }
//            if(Flag == true){
//                //************** IF new customer write code here to add new credit card ***********
//                newCreditCard(Loginid);
//               // custCard(Loginid);
//            }

            return st; // ******** ERROR here, this is returning null for new credit cards ********
        }catch(SQLException e){
            System.out.println(e);
        } return st;
    }

    public String newCreditCard(String Loginid){
            // try to return string from here.

            // this method is for new creditcard.
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";


        String sql1 = "INSERT INTO creditcard values (?,?,?)";

        System.out.println("New customer, Please update your card details!");
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter your card number: ");
        String cardnum = scan.nextLine();
        System.out.println("Please enter your card issuer: ");
        String cardissuer = scan.nextLine();
        System.out.println("Please enter your billing address: ");
        String billingaddress = scan.nextLine();

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.
             PreparedStatement p1 = con.prepareStatement(sql1);

        ){
            p1.setString(1,cardnum);
            p1.setString(2,cardissuer);
            p1.setString(3,billingaddress);
            p1.executeUpdate();
            System.out.println("Credit card details updated.");


            this.updateholds(Loginid,cardnum);

        return cardnum;
        }catch(SQLException e){
            System.out.println(e);
        }        return cardnum;


    }
    public void updateholds(String Loginid, String cardnum){
            // this method is to update the newly added card to the customer id in holds table.
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";

        String sql1 = "INSERT INTO holds values (?,?)";
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.
             PreparedStatement p1 = con.prepareStatement(sql1);

        ){
            p1.setString(1,Loginid);
            p1.setString(2,cardnum);
            p1.executeUpdate();
            //ResultSet R2 = p1.executeQuery();
        }catch(SQLException e){
            System.out.println(e);
        }

    }
    public void deleteCard(){
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";

        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the credit card number to delete it: ");
        String cardnum = scan.nextLine();

        String sql1 = "Delete * from creditcard where cardnum = ?";
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.
             PreparedStatement p1 = con.prepareStatement(sql1);

        ){
            p1.setString(1,cardnum);
            p1.executeUpdate();
            System.out.println("Credit card - "+cardnum+" Deleted.");
            //ResultSet R2 = p1.executeQuery();
        }catch(SQLException e){
            //System.out.println(e);
            System.out.println("Error - Incorrect Credit card number.");
        }

    }
    public void showAllCards(String Loginid){
        // this method is to update the newly added card to the customer id in holds table.
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";

        String sql1 = "Select * from creditcard natural join holds ";
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.
             //PreparedStatement p1 = con.prepareStatement(sql1);

        ){
            ResultSet R2 = st1.executeQuery(sql1);
            System.out.println("CustomerID \t|\t CreditCard \t|\t Card Issuer \t|\t Billing Address");
            while(R2.next()){
                if(Loginid.equals(R2.getString("customerid"))){
                    System.out.println(R2.getString("customerid")+"\t|\t"
                    +R2.getString("cardnum")+"\t|\t"
                    +R2.getString("cardissuer")+"\t|\t"
                    +R2.getString("billingaddress"));
                }
            }
        }catch(SQLException e){
            System.out.println(e);
        }

    }
    public void updateBillingAdd(String Loginid){
        // this method is to update the newly added card to the customer id in holds table.
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the credit card number to modify it: ");
        String cardnum = scan.nextLine();
        System.out.println("!!! Address constraints - Please end your address with USA state only (two letters only for state.) !!!");
        System.out.println("Please enter the address to update it:");
        String userBillAdd = scan.nextLine();
        String sql1 = "Update creditcard set billingaddress = ? where cardnum = ?";

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.
             PreparedStatement p1 = con.prepareStatement(sql1);

        ){
            p1.setString(1,userBillAdd);
            p1.setString(2,cardnum);

            p1.executeUpdate();
            System.out.println("Billing address updated.");
            //ResultSet R2 = p1.executeQuery();
        }catch(SQLException e){
            System.out.println(e);
        }

    }

}
