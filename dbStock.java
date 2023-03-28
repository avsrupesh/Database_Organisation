package OnlineStore;

import java.sql.*;
import java.util.Scanner;

public class dbStock {
    dbWarehouse dbWarehouseObject = new dbWarehouse();
    public void UpdatebuyedStock(String prodname, String buyingQuantity){
        // this method is used to update the stock table when someone place an order.
        //Note this is not used for updating new stock.
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";
        int retrievedProdQty = 0;
        int finalQty = 0;

        String sql1 = "Update stock set quantity = ? where prodname = ?";
        retrievedProdQty = this.retrieveQty(prodname); // this retrieves the existing stock.
       // finalQty = retrievedProdQty - Integer.parseInt(buyingQuantity);
        //no need of above line instead use
         finalQty = retrievedProdQty;
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.
             PreparedStatement p1 = con.prepareStatement(sql1);

        ){
            //ResultSet R1 = st1.executeQuery(sql1);
            p1.setInt(1,finalQty);
            p1.setString(2,prodname);
            p1.executeUpdate();
            //call here the decrease warehouse stock method.
            dbWarehouseObject.decWarehouse(prodname);

        }catch(SQLException e){
            System.out.println(e);
        }

    }
    public void UpdateNewStock(String staffID){
        // this method is used to update new product stock the stock table
        //Note this is to be used by STAFF only.
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";
        int retrievedProdQty = 0;
        int finalQty = 0;

        String sql1 = "Update stock set quantity = ? where prodname = ?";
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the product name to update : ");
        String prodname = scan.nextLine();
        System.out.println("Please enter the product quantity to be updated: ");
        int updatingQty = scan.nextInt();
        retrievedProdQty = this.retrieveQty(prodname); // this retrieves the existing stock.
        finalQty = retrievedProdQty + updatingQty;
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.
             PreparedStatement p1 = con.prepareStatement(sql1);

        ){
            //ResultSet R1 = st1.executeQuery(sql1);
            p1.setInt(1,finalQty);
            p1.setString(2,prodname);
            p1.executeUpdate();
            System.out.println("Stock updated.");
            //call here the decrease warehouse stock method.
            dbWarehouseObject.decWarehouse(prodname);
            //this.modificationLog(prodname,staffID);
                    // this logs the data into modify table.
            //this is still under consideration.!!!!!!!

        }catch(SQLException e){
            System.out.println(e);
        }

    }
    public int retrieveQty(String prodname){
        //this method is used to retrive the existing stock of the particular product.

        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";
        int prodQty = 0;

        String sql1 = "select * from stock ";
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.
             //PreparedStatement p1 = con.prepareStatement(sql1);

        ){
            //p1.setString(1,warehouseID);
            ResultSet R1 = st1.executeQuery(sql1);
            while(R1.next()){
                if(prodname.equals(R1.getString("prodname"))){
                    prodQty = R1.getInt("quantity");
                }
            } return prodQty;

        }catch(SQLException e){
            System.out.println(e);
        }return prodQty;

    }
    public void showProdStock(){

        Scanner scan = new Scanner(System.in);
        System.out.println("Please select the following \n 1 - show all stock \t 2 - Filter by product");
        int userchoice = scan.nextInt();
        switch(userchoice){
            case 1:{
                this.showAllStock();
                break;
            }
            case 2:{
                this.showFilterStock();
                break;
            }
            default:{
                System.out.println("Default show all products.");
                this.showAllStock();
                break;
            }
        }

    }
    public void showFilterStock(){
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";

        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the product name: ");
        String prodname = scan.nextLine();
        String sql1 = "select * from stock";
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.

        ){
            ResultSet R1 = st1.executeQuery(sql1);
            System.out.println("Product Name \t|\t Quantity \t|\t Warehouse ID");

            while(R1.next()){
                if(prodname.equals(R1.getString("prodname"))){
                    System.out.println(R1.getString("prodname")+"\t|\t"+
                            R1.getInt("quantity")+"\t|\t"+
                            R1.getString("warehouseid"));
                }
            }

        }catch(SQLException e){
            System.out.println(e);
        }

    }
    public void showAllStock(){
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";
        String sql1 = "select * from stock";
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.

        ){
            ResultSet R1 = st1.executeQuery(sql1);
            System.out.println("Product Name \t|\t Quantity \t|\t Warehouse ID");
            while(R1.next()){
                System.out.println(R1.getString("prodname")+"\t|\t"+
                        R1.getInt("quantity")+"\t|\t"+
                        R1.getString("warehouseid"));
            }

        }catch(SQLException e){
            System.out.println(e);
        }
    }
    public void modificationLog(String prodname, String staffid){
        //ERROR: duplicate key value violates unique constraint "modify_pkey"

        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";


        String sql1 = "Insert into modify values (?,?)";
        try (Connection con = DriverManager.getConnection(url, user, password);
             //Statement st1 = con.createStatement();
             PreparedStatement p1 = con.prepareStatement(sql1);
             // this is for creating a statement which will be running sql.

        ){
            p1.setString(1,prodname);
            p1.setString(2,staffid);
            p1.executeUpdate();

        }catch(SQLException e){
            System.out.println(e);
        }

    }
    public void increaseStock(String prodname, int prodQty){
        //this is used to increase product stock in stock table.
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";

        String sql1 = "Update stock set quantity = ? where prodname = ? ";
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.
             PreparedStatement p1 = con.prepareStatement(sql1);

        ){
            //ResultSet R1 = st1.executeQuery(sql1);
            //int currentStock = this.retrieveQty(prodname);
            //prodQty = currentStock + prodQty;
            p1.setInt(1,prodQty);
            p1.setString(2,prodname);
           // p1.setString(3,warehouseid);
            p1.executeUpdate();
            //call here the decrease warehouse stock method.
            dbWarehouseObject.decWarehouse(prodname);

        }catch(SQLException e){
            System.out.println(e);
        }
    }
    public void decreaseStock(String prodname, int prodQty){
        //this is used to decrease product stock in stock table.
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";

        String sql1 = "Update stock set quantity = ? where prodname = ? ";
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.
             PreparedStatement p1 = con.prepareStatement(sql1);

        ){
            //ResultSet R1 = st1.executeQuery(sql1);
            // herer it should take avaialable stock - prodQty.
            int currentStock = this.retrieveQty(prodname);
            prodQty = currentStock - prodQty;
            p1.setInt(1,prodQty);
            p1.setString(2,prodname);
          //  p1.setString(3,warehouseid);
            p1.executeUpdate();
            //call here the decrease warehouse stock method.
            dbWarehouseObject.incWarehouse(prodname);

        }catch(SQLException e){
            System.out.println(e);
        }
    }
}
