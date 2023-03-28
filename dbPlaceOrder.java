package OnlineStore;

import java.sql.*;
import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;

public class dbPlaceOrder {
    dbPayments dbPaymentsObject = new dbPayments();
    dbStock dbStockObject = new dbStock();
    dbWarehouse dbWarehouseObject = new dbWarehouse();
    dbCustomerAccount dbCustomerAccountObject = new dbCustomerAccount();
    dbAddtocart dbAddtocartObject = new dbAddtocart();
    public void InserttoPlaceOrder(String LoginId, String orderid, String prodname){

        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";



        String sql1 = "Insert into placeorder values(?,?,?)";
        String custCard = null;
        custCard = dbPaymentsObject.findCustCard(LoginId);
        if(custCard == null){
            custCard = dbPaymentsObject.newCreditCard(LoginId);
        }
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.
             PreparedStatement p1 = con.prepareStatement(sql1);

        ){
            p1.setString(1,LoginId);
            p1.setString(2,orderid);
            p1.setString(3,prodname);
            p1.executeUpdate();
            //System.out.println("Order Placed.");


        }catch(SQLException e){
            System.out.println(e);
        }
    }

    public void showExistingCart(String LoginId){
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";
        int count = 0;


        String sql1 = "select * from addtocart";

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.

        ){
            ResultSet R1 = st1.executeQuery(sql1);
            System.out.println("CustomerId \t|\t Product Name \t|\t Quantity \t|\t Final Price ");
            while(R1.next()){
                if(LoginId.equals(R1.getString("customerid"))){
                    System.out.println(R1.getString("customerid") + "\t|\t" +
                            R1.getString("prodname") + "\t|\t" +
                            R1.getString("quantity") + "\t|\t" +
                            R1.getString("stateprice"));
                    count++;
                }
            }
            if(count <= 0){
                System.out.println("============ CART EMPTY ===========");
            }else {
                Scanner scan = new Scanner(System.in);
                System.out.println("Do you want to Modify Cart?\n 1 - Yes \t 2 - No ");
                int userPurchaseChoice = scan.nextInt();
                if (userPurchaseChoice == 1) {
                    dbAddtocartObject.modifyCart(LoginId);
                }
                System.out.println("Do you want to finish transaction?\n 1 - Yes \t 2 - No ");
                int userPurchaseChoice1 = scan.nextInt();
                if (userPurchaseChoice1 == 1) {
                    this.getOrderDetails(LoginId); // this code is to get order details
                }
            }

        }catch(SQLException e){
            System.out.println(e);
        }

    }

    public void getOrderDetails(String LoginId){

        // this method is to retrieve the order details of that particular id entered by user. and
        // send it to finish transaction method to add to oderR table.

        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";
        //String orderid = null;
        String prodname = null;
        String quantity = null;
        String finalPrice;

        String sql1 = "select * from addtocart";

//        Scanner scan = new Scanner(System.in);
//        System.out.println("Please enter the orderId which you want to purchase: ");
//        String userOrderID = scan.nextLine();
        Random rInt = new Random();
        int x = rInt.nextInt(10000,999999);
        //  System.out.println("integer: "+ x);
        String orderid = String.valueOf(x);
        //this.InserttoPlaceOrder(LoginId,orderid);

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.

        ){
            ResultSet R1 = st1.executeQuery(sql1);
            //System.out.println("CustomerId \t\t OrderId \t\t Card Number");

            while(R1.next()){
                if(LoginId.equals(R1.getString("customerid"))){
                    //ObjectArr[count][0] = R1.getInt("sno");
                    prodname = R1.getString("prodname");
                    quantity = R1.getString("quantity");
                    finalPrice= R1.getString("stateprice");
                    //ObjectArr[count][1] = R1.getString("customerid");

                   // orderid = R1.getString("orderid");
                    this.InserttoPlaceOrder(LoginId,orderid,prodname);

                    this.finishTransaction(orderid,prodname,quantity,LoginId,finalPrice);
                    // get order price here.

                }
            }
            //this.finishTransaction(ObjectArr);

           // System.out.println(ObjectArr[0].getString("customerid"));
           // this.InserttoPlaceOrder(LoginId,orderid);

        }catch(SQLException e){
            System.out.println(e);
        }


    }
    public void finishTransaction(String orderid,String prodname,String quantity,String LoginId,String finalPrice){
            //String orderid,String prodname,String quantity,String LoginId,String finalPrice){
        // this method is to add the data into the orderR table.

        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";
        String orderstatus = "Issued";

        //String LoginId = ResultObj[0].getString("customerid");
        LocalDate Today = java.time.LocalDate.now();
        String custCard = null;
        custCard = dbPaymentsObject.findCustCard(LoginId);

        String sql1 = "Insert into orderr values(?,?,?,?,?,?)";
        Boolean Flag = dbCustomerAccountObject.balanceCut(LoginId, Double.parseDouble(finalPrice));
        if(Flag == true) {
            try (Connection con = DriverManager.getConnection(url, user, password);
                 Statement st1 = con.createStatement();
                 // this is for creating a statement which will be running sql.
                 PreparedStatement p1 = con.prepareStatement(sql1);

            ) {
                //ResultSet R1 = st1.executeQuery(sql1);
                p1.setString(1, orderid);
                p1.setString(2, prodname);
                p1.setInt(3, Integer.parseInt(quantity));
                p1.setString(4, orderstatus); // Initially we are setting up Issued, will check it later.
                p1.setDate(5, Date.valueOf(Today));
                p1.setString(6, custCard);
                p1.executeUpdate();
                System.out.println("Transaction completed.");

// now write code here to delete that order details from addtocart and placeorder table.
                this.deleteAddtocart(LoginId);
                this.deletePlaceorder(orderid);
                //this quantity should not be coming here. it is addtocart qty, we need current - addtocart qty.
                dbStockObject.UpdatebuyedStock(prodname, quantity);
                // here write code to increase the warehouse capacity.
                dbWarehouseObject.incWarehouse(prodname);
                // call here customer balance method.

            } catch (SQLException e) {
                System.out.println(e);
            }
        }else{
            dbCustomerAccountObject.balanceCut(LoginId, Double.parseDouble(finalPrice));
            System.out.println("Inside else of final transaction.");
        }


    }

    public void deleteAddtocart(String LoginId){

        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";

        String sql1 = "Delete from addtocart where customerid = ?";
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.
             PreparedStatement p1 = con.prepareStatement(sql1);

        ){
            //ResultSet R1 = st1.executeQuery(sql1);
            p1.setString(1,LoginId);
            p1.executeUpdate();

        }catch(SQLException e){
            System.out.println(e);
        }
    }
    public void deletePlaceorder(String orderId){
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";

        String sql1 = "Delete from placeorder where orderid = ?";
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.
             PreparedStatement p1 = con.prepareStatement(sql1);

        ){
            //ResultSet R1 = st1.executeQuery(sql1);
            p1.setString(1,orderId);
            p1.executeUpdate();

        }catch(SQLException e){
            System.out.println(e);
        }

    }


}
