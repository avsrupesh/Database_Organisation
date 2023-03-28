package OnlineStore;

import java.sql.*;

public class dbProductPrice {
    public double getStatePrice(String LoginId){
// this gets userid from login page and derives its state
// from delivery address and sets price according to product category.

        //This method is to show all prducts in the table product.
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";
        String stateX = null;
        String st = null;
        double price = 100;

        String sql1 = "select * from customeraddress";
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.
             //PreparedStatement p1 = con.prepareStatement(sql1);

        ){
            //p1.setString(1,LoginId);
            ResultSet R2 = st1.executeQuery(sql1);
            // System.out.println("CustomerID \t Delivery  \t Permanent \t \n");
            while(R2.next()){
                if(LoginId.equals(R2.getString("customerid"))) {
                    st = R2.getString("delivery");
                }
            }
            stateX =  st.substring(Math.max(st.length() - 2, 0));
            price = this.getstateTax(stateX);
            return price;

        }catch(SQLException e){
            System.out.println(e);
        } return price;
    }
    public String getCustomerState(String LoginId){
// this gets userid from login page and derives its state
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";
        String stateX = null;
        String st = null;

        String sql1 = "select * from customeraddress";
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.
             //PreparedStatement p1 = con.prepareStatement(sql1);

        ){
            //p1.setString(1,LoginId);
            ResultSet R2 = st1.executeQuery(sql1);
            // System.out.println("CustomerID \t Delivery  \t Permanent \t \n");
            while(R2.next()){
                if(LoginId.equals(R2.getString("customerid"))) {
                    st = R2.getString("delivery");
                }
            }
            stateX =  st.substring(Math.max(st.length() - 2, 0));
            return stateX;

        }catch(SQLException e){
            System.out.println(e);
        } return stateX;
    }
    public double getstateTax(String stateX) {
        double price = 0;
        switch (stateX.toUpperCase()) {
            // code methods for different category of the product.
            case "AL", "AK","AZ","AR","CA","CO","CT","DE","DC","FL": {
                price = 100;
                break;
            }
            case "GA", "HI","ID","IL","IN","IA","KS","KY","LA","ME": {
                price = 120;
                break;

            }
            case "MD", "MA","MI","MN","MS","MO","MT","NE","NV","NH": {
                price = 140;
                break;
            }
            case "NJ", "NM","NY","NC","ND","OH","OK","OR","PA","RI": {
                price = 160;
                break;

            }
            case "SC", "SD","TN","TX","UT","VT","VA","WA","WV","WI","WY": {
                price = 180;
                break;

            }
            default:{
                price = 145;
                break;
            }
        }return price;
    }

    public Double getProductCategory(String productName){

        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";

        String productCategory = null;
        Double productCategoryPrice = 0.0;

        String sql1 = "select * from product";
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st1 = con.createStatement();
             // this is for creating a statement which will be running sql.

        ){ ResultSet R1 = st1.executeQuery(sql1);
            while(R1.next()){
                if(productName.equals(R1.getString("prodname"))){
                    productCategory = R1.getString("category");
                }
            }
            productCategoryPrice = this.getProductCategoryPrice(productCategory);
            return productCategoryPrice;

        }catch(SQLException e){
            System.out.println(e);
        }return productCategoryPrice;

    }
    public Double getProductCategoryPrice(String category){
        double price = 50.0;
        switch (category){
            case "Food":{
                price = 50.0;
                break;
            }
            case "Alcohol":{
                price = 100.0;
                break;
            }
            case "Phone":{
                price = 400.0;
                break;
            }
            case "Electronics":{
                price = 800.0;
                break;
            }
            default:{
                price = 55.0;
                break;
            }
        }
        return price;
    }
}
