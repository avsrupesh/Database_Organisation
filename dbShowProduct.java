package OnlineStore;

import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.Scanner;

public class dbShowProduct {

    dbAddtocart dbAddtocartObject = new dbAddtocart();
    dbProductPrice dbProductPriceObject = new dbProductPrice();
    public void filter(String @NotNull [] x,String Loginid){
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";
        int count1 = 0;
        //String sql1;
        String wareID = null;
        wareID = this.getWarehouseID(Loginid);
        Scanner scan = new Scanner(System.in);
        System.out.println("Available Categories: ");

        for (String s : x) {
            if(s != null) {
                System.out.println(count1 + " - " + s);
                count1++;
            }
        }
        System.out.println("Please select the required category to search: ");
        int userChoice = scan.nextInt();
        String choice = x[userChoice];
        //sql1 = "select * from product natural full outer join productinfo natural full outer join stock where category = ? ";
        String sql1 = "select * from product natural full outer join productinfo natural full outer join stock where warehouseid = ? and category = ?";


        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement(); // this is for creating a statement which will be running sql.
             PreparedStatement p1 = con.prepareStatement(sql1);
             // when we want to run a query with user inputs, we use prepared statements.
        ){
            p1.setString(1,wareID);
            p1.setString(2,choice);
            ResultSet R2 = p1.executeQuery();
            System.out.println("ProductName \t|\t Category \t|\t Available Stock \t|\t AdditionalInfo \t|\t Product Size (Cu.ft)  \n");
            while(R2.next()){
                System.out.println(R2.getString("prodname")+"\t"+"|"+"\t"
                        +R2.getString("category")+"\t"+"|"+"\t"
                        +R2.getInt("quantity")+ "\t"+"|"+"\t"
                        +R2.getString("additionalinfo")+"\t"+"|"+"\t"
                        +R2.getInt("size"));
            }

            System.out.println("0 - Exit \t 1 - Add to Cart");
            int addToCartChoice = scan.nextInt();
            if(addToCartChoice == 1){
                dbAddtocartObject.addProduct(Loginid);
            }


        }catch(SQLException e){
            System.out.println(e);
        }
    }

    public void categoryArr(String Loginid){
        //this method is used for filtering products based on category.
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";

        String[] catArray = new String[20];
        int count = 0;
        String wareID = null;
        wareID = this.getWarehouseID(Loginid);
        String sql1 = "select distinct category from product natural join stock where warehouseid = ?";
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement(); // this is for creating a statement which will be running sql.
             //ResultSet R2 = st1.executeQuery(sql1);
             PreparedStatement p1 = con.prepareStatement(sql1);
        ){
            p1.setString(1,wareID);
            ResultSet R2 = p1.executeQuery();

            while(R2.next()){
                catArray[count] = R2.getString("category");
                count++;
            }
           this.filter(catArray,Loginid);
        }catch(SQLException e){
            System.out.println(e);
        }
    }

    public void catalogue(String Loginid) {
        //This method is to show all prducts in the table product.
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";
        String wareID = null;
        wareID = this.getWarehouseID(Loginid);
        String sql1 = "select * from product natural full outer join productinfo natural full outer join stock where warehouseid = ?";
        //use a join on stock to show the live stock.
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.
             PreparedStatement p1 = con.prepareStatement(sql1);
             //ResultSet R2 = st1.executeQuery(sql1);
        ){
            p1.setString(1,wareID);
            ResultSet R2 = p1.executeQuery();
            System.out.println("ProductName \t|\t Category \t|\t Available Stock \t|\t AdditionalInfo \t|\t Size (Cu.ft)  \n");
            while(R2.next()){
                System.out.println(R2.getString("prodname")+"\t"+"|"+"\t"
                        +R2.getString("category")+"\t"+"|"+"\t"
                        +R2.getInt("quantity")+ "\t"+"|"+"\t"
                        +R2.getString("additionalinfo")+"\t"+"|"+"\t"
                        +R2.getInt("size"));
            }
            //********* add her add to cart method ***************
            //********* add her add to cart method ***************
           // System.out.println("press enter.");
            dbAddtocartObject.addProduct(Loginid);

        }catch(SQLException e){
            System.out.println(e);
        }
    }

    public String getWarehouseID(String LoginID){
        //this method takes the userID and gives warehouse ID(by calling getWarehouseID method) for that id.
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";
        String ware2Ch = null;
        String ware2ChFinal = null;
        String stateX = null;
        Boolean Flag = true;
        String wareID = null;

        String custState = dbProductPriceObject.getCustomerState(LoginID);
        String sql1 = "select * from warehouse";
        //use a join on stock to show the live stock.
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.
             ResultSet R2 = st1.executeQuery(sql1);
        ){
            while(R2.next()){
                ware2Ch = R2.getString("address");
                stateX =  ware2Ch.substring(Math.max(ware2Ch.length() - 2, 0));

                if(custState.equals(stateX)) {
                    ware2ChFinal = stateX;
                    Flag = false;
                    wareID = this.wareId(ware2ChFinal);

                }
            }
            if(Flag == true){
                System.out.println("There is no warehouse at your location. Showing items from default location.");
                wareID = this.wareId(stateX);
            }
            return wareID;


        }catch(SQLException e){
            System.out.println(e);
        } return wareID;

    }
    public String wareId(String warehouseLocation){
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";
        String ware2Ch = null;
        String wareIDFinal = null;
        String stateX = null;

        String sql1 = "select * from warehouse";
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.
             ResultSet R2 = st1.executeQuery(sql1);
        ){
            while(R2.next()){
                ware2Ch = R2.getString("address");
                stateX =  ware2Ch.substring(Math.max(ware2Ch.length() - 2, 0));

                if(warehouseLocation.equals(stateX)) {
                    wareIDFinal = R2.getString("warehouseid");
                    return wareIDFinal;
                }
            }
        }catch(SQLException e){
            System.out.println(e);
        } return wareIDFinal;
    }

}
