package OnlineStore;

import java.sql.*;
import java.util.Scanner;

public class dbNewProductUpdates {
    dbWarehouse dbWarehouseObject = new dbWarehouse();

    public void newProductInsert(String staffid){
        // this method is exculsive to staff to insert new products into product table.
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";

        String sql1 = "Insert into product values (?,?,?)";

        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome, Please give the details to update new values");
        System.out.println("Please enter the product name: ");
        String newProdName = scan.nextLine();
        System.out.println("Please enter the Category of product: ");
        String newProdCategory = scan.nextLine();
        System.out.println("Please enter size of product: ");
        Double newProdSize = scan.nextDouble();


        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.
             PreparedStatement p1 = con.prepareStatement(sql1);
             //ResultSet R2 = st1.executeQuery(sql1);
        ){
            p1.setString(1,newProdName);
            p1.setString(2,newProdCategory);
            p1.setDouble(3,newProdSize);
            p1.executeUpdate();
            System.out.println("The following product is updated \n" );
            System.out.println("Product \t|\t Category \t|\t Size in Cu.Ft");
            System.out.println(newProdName+"\t|\t"+newProdCategory+"\t|\t"+newProdSize);
            //call here method for additional info.
            this.newProdAddInfo(newProdName);
            String Warehouseid = this.newProdQty(newProdName);
            //write code here to decrease warehouse capacity.
            dbWarehouseObject.decWarehouse(newProdName);
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    public void newProdAddInfo(String prodName){
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";

        String sql1 = "Insert into productinfo values (?,?)";

        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter any additional details of the product if exists: ");
        String newProdInfo = scan.nextLine();


        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.
             PreparedStatement p1 = con.prepareStatement(sql1);
             //ResultSet R2 = st1.executeQuery(sql1);
        ){
            p1.setString(1,prodName);
            p1.setString(2,newProdInfo);
            p1.executeUpdate();
            System.out.println("Product - "+prodName+" additional Info updated.");

            //call here method for additional info.

        }catch(SQLException e){
            System.out.println(e);
        }

    }
    public String newProdQty(String ProdName){
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";

        String sql1 = "Insert into stock values (?,?,?)";

        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter warehouse id: "); // assuming staff knows warehouse id.
        String newProdWarehouse = scan.nextLine();
        System.out.println("Please enter quantity of the product: ");
        int newProdQuantity = scan.nextInt();



        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.
             PreparedStatement p1 = con.prepareStatement(sql1);
             //ResultSet R2 = st1.executeQuery(sql1);
        ){
            p1.setString(1,newProdWarehouse);
            p1.setString(2,ProdName);
            p1.setInt(3,newProdQuantity);
            p1.executeUpdate();
            System.out.println("Product stock updated.");

            return newProdWarehouse;

            //call here method for additional info.

        }catch(SQLException e){
            System.out.println(e);
        }            return newProdWarehouse;

    }
}
