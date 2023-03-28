package OnlineStore;

import java.sql.*;
import java.util.Scanner;
import java.util.Random;

public class dbAddtocart {

    //dbPlaceOrder dbPlaceOrderObject = new dbPlaceOrder();
    dbProductPrice dbProductPriceObject = new dbProductPrice();
    dbStock dbStockObject = new dbStock();


    public void addProduct(String LoginId){
        //This method will add products to card.
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";
        double productStatePrice = 0;
        double productCategoryPrice = 0;
        double finalProductPrice = 0;

        Scanner scan = new Scanner(System.in);

        System.out.println("select the product-name to add to cart: ");
        String userProdName = scan.nextLine();
        System.out.println("select the desired Quantity to add to cart: ");
        int userProdQty = scan.nextInt();

//        Random rInt = new Random();
//        int sno = rInt.nextInt(10,9999);
      //  System.out.println("integer: "+ x);

//********* write code here to check for available stock and continue ************
        int AvailableProdStock = dbStockObject.retrieveQty(userProdName);


        productStatePrice = dbProductPriceObject.getStatePrice(LoginId); //this method gets the statewide prices.
        //get product category price from here
        productCategoryPrice = dbProductPriceObject.getProductCategory(userProdName);
        finalProductPrice = (productCategoryPrice * userProdQty) + productStatePrice;
        if(userProdQty <= AvailableProdStock) {

            String sql1 = "Insert into addtocart values(?,?,?,?)";

            try (Connection con = DriverManager.getConnection(url, user, password);
                 Statement st1 = con.createStatement();
                 // this is for creating a statement which will be running sql.

                 PreparedStatement p1 = con.prepareStatement(sql1);
            ) {
               // p1.setInt(1, sno);
                p1.setString(1, LoginId);
                p1.setString(2, userProdName);
                p1.setString(3, String.valueOf(finalProductPrice));
                p1.setString(4, String.valueOf(userProdQty));
                p1.executeUpdate();
                System.out.println("The following product is added to the cart.\n");
                System.out.println("Product \t|\t Quantity \t|\t Price \n");
                System.out.println(userProdName + "\t|\t" + userProdQty + "\t|\t" + finalProductPrice);

                //call decreasestock method
                dbStockObject.decreaseStock(userProdName,userProdQty);

                //calling place order method here

                //  dbPlaceOrderObject.InserttoPlaceOrder(LoginId);
                // this method updates the order detials in place order table because finish transaction method uses place order table to check for id.

            } catch (SQLException e) {
                System.out.println("Product already exists in cart. Instead please try to update quantity if you need more.");
            }
        }else{
            System.out.println("Product - "+userProdName+" has only limited quantity - "+AvailableProdStock);
        }
      //  placeOrder(LoginId,orderid);
    }

public void modifyCart(String LoginId){

        // call here show existing orders
   // dbPlaceOrderObject.showExistingCart(LoginId);
    Scanner scan = new Scanner(System.in);
    System.out.println("select the following\n 1 - Modify quantity 2 - Remove Product: ");
    int userProdQty = scan.nextInt();
    switch(userProdQty){
        case 1:{
            this.changeQty(LoginId);
            break;
        }
        case 2:{
            this.RemoveProduct();
            break;
        }
        default:{
            System.out.println("Default option exit");
            break;
        }
    }

}
public void changeQty(String LoginId){
    final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
    final String user = "postgres";
    final String password = "1998";
    double productStatePrice = 0;
    double productCategoryPrice = 0;
    double finalProductPrice = 0;
    int userProdQty = 0;

    Scanner scan = new Scanner(System.in);
    System.out.println("select the product-name to modify: ");
    String userProdName = scan.nextLine();
//    System.out.println("Please confirm the product serial number:  ");
//    int sno = scan.nextInt();
    int AvailableProdStock = dbStockObject.retrieveQty(userProdName);
    int finalStock = 0;
    String CartProdQty = null;

    System.out.println("Select action:\n 1 - Increase quantity 2 - Decrease quantity  ");
    int userIncDec = scan.nextInt();
    switch(userIncDec){
        case 1:{
            System.out.println("Please enter additional quantity to add to existing order: ");
            userProdQty = scan.nextInt();
            int refreshedQty = AvailableProdStock - userProdQty; // this line is to decrease stock from stock table.
            dbStockObject.decreaseStock(userProdName,refreshedQty);
            //finalStock = AvailableProdStock + userProdQty; // this line is to update in add to cart table.
            finalStock = Integer.parseInt(CartProdQty) + userProdQty;  // this line is to update in add to cart table.

            break;
        }
        case 2:{
            System.out.println("Please enter the quantity to subtract from existing order: ");
            userProdQty = scan.nextInt();
            int refreshedQty = AvailableProdStock + userProdQty; // this line is to increase stock from stock table.
            dbStockObject.increaseStock(userProdName,refreshedQty);
            CartProdQty = this.stockOfCart(userProdName);
            finalStock = Integer.parseInt(CartProdQty) - userProdQty;  // this line is to update in add to cart table.
            //int the above line, I need to get current stock from add to cart
            break;
        }
        default:{
            System.out.println("Default choice EXIT");
            break;
        }
    }


    productStatePrice = dbProductPriceObject.getStatePrice(LoginId); //this method gets the statewide prices.
    //get product category price from here
    productCategoryPrice = dbProductPriceObject.getProductCategory(userProdName);
    finalProductPrice = (productCategoryPrice * finalStock) + productStatePrice;

    String sql1 = "Update addtocart set quantity = ?,stateprice = ? where prodname = ?";
    //write code here to check for available stock and proceed.

    try (Connection con = DriverManager.getConnection(url, user, password);
         Statement st1 = con.createStatement();
         // this is for creating a statement which will be running sql.

         PreparedStatement p1 = con.prepareStatement(sql1);
    ){
        // p1.setString(1,orderid);
        p1.setString(1, String.valueOf(finalStock));
        p1.setString(2, String.valueOf(finalProductPrice));
        p1.setString(3, userProdName);
       // p1.setInt(4, sno);
        p1.executeUpdate();
        System.out.println("The following product is updated in the cart.\n");
        System.out.println("Product \t|\t Quantity \t|\t Price \n");
        System.out.println(userProdName+"\t|\t"+finalStock+"\t|\t"+finalProductPrice);
        // write code here to increase or decrease the product stock.

    }catch(SQLException e){
        System.out.println(e);
    }
}
public void RemoveProduct(){
    final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
    final String user = "postgres";
    final String password = "1998";

    Scanner scan = new Scanner(System.in);
    System.out.println("select the product-name to delete: ");
    String userProdName = scan.nextLine();


    String sql1 = "Delete from addtocart where prodname = ?";

    try (Connection con = DriverManager.getConnection(url, user, password);
         Statement st1 = con.createStatement();
         // this is for creating a statement which will be running sql.

         PreparedStatement p1 = con.prepareStatement(sql1);
    ){
        p1.setString(1,userProdName);
       // p1.setInt(2,sno);

        p1.executeUpdate();
        System.out.println("The following product is deleted from the cart.\n");
        System.out.println("Product - "+userProdName);
        //System.out.println(userProdName+"\t|\t"+sno);

        // write code here to increase the product stock.

    }catch(SQLException e){
        System.out.println(e);
    }
}
public String stockOfCart(String prodname){
    final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
    final String user = "postgres";
    final String password = "1998";
    String CartQty = null;

    String sql1 = "Select * from addtocart where prodname = ?";

    try (Connection con = DriverManager.getConnection(url, user, password);
         Statement st1 = con.createStatement();
         // this is for creating a statement which will be running sql.

         PreparedStatement p1 = con.prepareStatement(sql1);
    ){
        p1.setString(1,prodname);

        ResultSet R1 = p1.executeQuery();
        while(R1.next()) {
            CartQty = R1.getString("quantity");
        }
        return CartQty;

    }catch(SQLException e){
        System.out.println(e);
    } return CartQty;

}

}

