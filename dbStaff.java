package OnlineStore;

import java.sql.*;
import java.util.Scanner;

public class dbStaff {
    public void viewAllCust(){
// this is to view all customers.
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";

        String sql1 = "Select * from customer natural join customeraddress order by customerid";
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.
             //PreparedStatement p1 = con.prepareStatement(sql1);

        ){
            ResultSet R2 = st1.executeQuery(sql1);
            System.out.println("CustomerID \t|\t Name \t|\t Delivery Address \t|\t Permanent Address");
            while(R2.next()){
                    System.out.println(R2.getString("customerid")+"\t"+"|"+"\t"
                            +R2.getString("customername")+"\t"+"|"+"\t"
                            +R2.getString("delivery")+"\t"+"|"+"\t"
                            +R2.getString("permanent"));
            }
        }catch(SQLException e){
            System.out.println(e);
        }

    }
    public void modifyOrderR(){
        //With this method, staff can modify the details of order -- issued, send received
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";
        String ordStatus = "issued";
        //showing all orders
        this.showAllOrders();
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter order id to update status: ");
        String ordID = scan.nextLine();
        System.out.println("Please select the following status:\n 1 - send \t 2 - received");
        int ordStatusInt = scan.nextInt();
        if(ordStatusInt == 1){
            ordStatus = "send";
        }else if(ordStatusInt == 2){
            ordStatus = "received";
        }

        String sql1 = "Update orderr set orderstatus = ? where orderid = ?";

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.
             PreparedStatement p1 = con.prepareStatement(sql1);

        ){
            //ResultSet R1 = st1.executeQuery(sql1);
            p1.setString(1,ordStatus);
            p1.setString(2,ordID);
            p1.executeUpdate();
            System.out.println("Order - "+ordID+" status updated to "+ordStatus);
        }catch(SQLException e){
            System.out.println(e);
        }
    }

    public void showAllOrders(){
// this is to view all orders.

        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";

        String sql1 = "Select * from orderr order by issuedate";
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.
             //PreparedStatement p1 = con.prepareStatement(sql1);

        ){
            ResultSet R2 = st1.executeQuery(sql1);
            System.out.println("Issued date \t|\t OrderID \t|\t Product Name \t|\t Quantity \t|\t Order status");
            while(R2.next()){
                System.out.println(R2.getString("issuedate")+"\t"+"|"+"\t"
                        +R2.getString("orderid")+"\t"+"|"+"\t"
                        +R2.getString("prodname")+"\t"+"|"+"\t"
                        +R2.getString("productqty")+"\t"+"|"+"\t"
                        +R2.getString("orderstatus"));
            }
        }catch(SQLException e){
            System.out.println(e);
        }

    }
    public void addWarehouse(){
        // this method adds new warehouses.
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";

        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter new warehouse ID: ");
        String wareID = scan.nextLine();
        System.out.println("!!! Address constraints - Please end your address with USA state only (two letters only for state.) !!!");

        System.out.println("Please enter warehouse Address: ");
        String wareAdd = scan.nextLine();
        System.out.println("Please enter warehouse storage capacity in Cu.Ft: ");
        Double wareCap = scan.nextDouble();

        String sql1 = "Insert into warehouse values(?,?,?)";
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.
             PreparedStatement p1 = con.prepareStatement(sql1);

        ){
            p1.setString(1,wareID);
            p1.setString(2,wareAdd);
            p1.setDouble(3,wareCap);
            p1.executeUpdate();


        }catch(SQLException e){
            System.out.println(e);
        }
    }

}
