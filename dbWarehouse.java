package OnlineStore;

import java.sql.*;
import java.util.Scanner;

public class dbWarehouse {
    public void incWarehouse(String prodName){
        {
            // this method is used to update the storage capacity of warehouse
            final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
            final String user = "postgres";
            final String password = "1998";
            double productStock = 0.0;
            int finalCapacity = 0;
            String warehouseID = this.getWarehouseID(prodName);
            int liveStorageCapacity = this.retrieveWarehouseCapacity(warehouseID);



            productStock = this.getproductStock(warehouseID,prodName);
            finalCapacity = (int) (liveStorageCapacity + productStock);

            String sql1 = "Update warehouse set storagecapacity = ? where warehouseid = ?";


            try (Connection con = DriverManager.getConnection(url, user, password);
                 Statement st1 = con.createStatement();
                 // this is for creating a statement which will be running sql.
                 PreparedStatement p1 = con.prepareStatement(sql1);

            ){
                //ResultSet R1 = st1.executeQuery(sql1);
                p1.setInt(1,finalCapacity);
                p1.setString(2,warehouseID);
                p1.executeUpdate();

            }catch(SQLException e){
                System.out.println(e);
            }

        }

    }
    public void decWarehouse(String prodName){
        // this method is used to update the storage capacity of warehouse
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";
      double productStock = 0.0;
      int finalCapacity = 0;
      String warehouseID = this.getWarehouseID(prodName);
      int liveStorageCapacity = this.retrieveWarehouseCapacity(warehouseID);

        productStock = this.getproductStock(warehouseID,prodName);
        finalCapacity = (int) (liveStorageCapacity - productStock);

        String sql1 = "Update warehouse set storagecapacity = ? where warehouseid = ?";


        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.
             PreparedStatement p1 = con.prepareStatement(sql1);

        ){
            //ResultSet R1 = st1.executeQuery(sql1);
            p1.setInt(1,finalCapacity);
            p1.setString(2,warehouseID);
            p1.executeUpdate();

        }catch(SQLException e){
            System.out.println(e);
        }

    }
    //write code here to get the live warehouse storage.
    public int retrieveWarehouseCapacity(String warehouseID){
        //this method is used to retrive the existing stock of the particular product.

        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";
        int WarehouseCapacity = 0;

        String sql1 = "select * from warehouse";
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.

        ){
            ResultSet R1 = st1.executeQuery(sql1);
            while(R1.next()){
                if(warehouseID.equals(R1.getString("warehouseid"))){
                    WarehouseCapacity = R1.getInt("storagecapacity");
                }
            } return WarehouseCapacity;

        }catch(SQLException e){
            System.out.println(e);
        }return WarehouseCapacity;

    }

    public double getproductStock(String warehouseID, String prodName){
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";
        double productStock = 0.0;
        double productQty = 0.0;
        double productSize = 0.0;

        String sql1 = "select * from product natural join stock";

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.
             //PreparedStatement p1 = con.prepareStatement(sql1);
             ResultSet R2 = st1.executeQuery(sql1);
        ){
            while(R2.next()){
                if(warehouseID.equals(R2.getString("warehouseid")) && prodName.equals(R2.getString("prodname"))){
                    productQty = R2.getInt("quantity");
                    productSize = R2.getDouble("size");
                }
            }
            productStock = productQty * productSize;

            return productStock;

            //call here method for additional info.

        }catch(SQLException e){
            System.out.println(e);
        }            return productStock;

    }

    //write code here to take prodname and give warehouse id/
    // also ware house quantity should change for updating old stock also.

    public String getWarehouseID(String prodName){
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";
        String WareID = null;

        String sql1 = "select * from stock";

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.
             //PreparedStatement p1 = con.prepareStatement(sql1);
             ResultSet R2 = st1.executeQuery(sql1);
        ){
            while(R2.next()){
                if( prodName.equals(R2.getString("prodname"))){
                    WareID = R2.getString("warehouseid");
                }
            }

            return WareID;

            //call here method for additional info.

        }catch(SQLException e){
            System.out.println(e);
        }            return WareID;

    }


}
