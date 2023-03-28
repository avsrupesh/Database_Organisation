package OnlineStore;

import java.util.Scanner;

public class dbMenu {
    dbLogin dbLoginObject = new dbLogin();
    dbShowProduct dbShowProductObject = new dbShowProduct();
    dbPlaceOrder dbPlaceOrderObject = new dbPlaceOrder();
    dbStock dbStockObject = new dbStock();
    dbNewProductUpdates dbNewProductUpdatesObject = new dbNewProductUpdates();
    dbPayments dbPaymentsObject = new dbPayments();
    dbStaff dbStaffObject = new dbStaff();

    //String LoginID = null;
    Boolean flag = true;
    Boolean loginFlag = false;
    String[] LoginType = new String[2];


    public void menu() {



        while (flag == true && loginFlag == false) {
            Scanner scanx = new Scanner(System.in);
            System.out.println("Please select Below option to proceed: ");
            System.out.println("0 - EXIT \t 1 - Login");
            int userChoicex = scanx.nextInt();

            switch (userChoicex) {
                case 0: {
                    flag = false;
                    loginFlag = false;
                    break;
                }
                case 1: {
                    //dbLogin db1 = new dbLogin();
                    LoginType = dbLoginObject.Login();
                    //System.out.println(LoginID);
                    loginFlag = true;
                    String type = LoginType[1];
                    String id = LoginType[0];
                    if(type.equals("Customer") && !id.equals("False")) {
                        CustomerMenu(id);
                    }else if(type.equals("Staff") && !id.equals("False")) {
                        StaffMenu(id);
                    }
                    break;
                }
                default: {
                    System.out.println("Default choice is EXIT.");
                    flag = false;
                    loginFlag = false;

                    break;
                }
            }
        }


    }
    public void CustomerMenu (String LoginID) {
        while (flag == true && loginFlag == true) {
            //This is for customer!!!
            Scanner scan = new Scanner(System.in);
            System.out.println("Please select Below option to proceed: ");
            System.out.println("0 - EXIT \t 1 - Show Products \t 2 - Show existing cart \t 3 - Credit cards ");
            int userChoice = scan.nextInt();

            switch (userChoice) {
                case 0: {
                    flag = false;
                    //loginFlag = false;

                    break;
                }
                case 1: {
                    //Scanner scan1 = new Scanner(System.in);
                    System.out.println("Please select Below option to proceed: ");
                    System.out.println("0 - Show all products \t 1 - Filter by Category ");
                    int userChoice1 = scan.nextInt();
                    //dbShowProduct db2 = new dbShowProduct();
                    switch (userChoice1) {
                        case 0: {

                            dbShowProductObject.catalogue(LoginID);
                            break;
                        }
                        case 1: {
                            dbShowProductObject.categoryArr(LoginID);
                            break;
                        }
                        default: {
                            System.out.println("Default - Show all products.");
                            dbShowProductObject.catalogue(LoginID);
                            break;
                        }
                    }
                    break;
                }
                case 2: {
                    dbPlaceOrderObject.showExistingCart(LoginID);
                    break;
                }
                case 3: {
                    {
                        //Scanner scan1 = new Scanner(System.in);
                        System.out.println("Please select Below option to proceed: ");
                        System.out.println("0 - Show all Credit cards \t 1 - Delete Cards \t 2 - Add new Card \t 3 - Manage Address ");
                        int userChoice1 = scan.nextInt();
                        //dbShowProduct db2 = new dbShowProduct();
                        switch (userChoice1) {
                            case 0: {

                                dbPaymentsObject.showAllCards(LoginID);
                                break;
                            }
                            case 1: {
                                dbPaymentsObject.deleteCard();
                                break;
                            }
                            case 2: {
                                dbPaymentsObject.newCreditCard(LoginID);
                                break;
                            }
                            case 3: {
                                System.out.println("Please select Below option to proceed: ");
                                System.out.println(" 1 - Billing Address \t 2 - Residential Address ");
                                int userChoiceR = scan.nextInt();
                                switch (userChoiceR){
                                    case 1:{
                                        dbPaymentsObject.updateBillingAdd(LoginID);
                                        break;
                                    }
                                    case 2:{
                                        dbLoginObject.modifyCustAddress(LoginID);
                                        break;
                                    }
                                    default:{
                                        System.out.println("Default option exit.");
                                        break;
                                    }
                                }

                            }
                            default: {
                                System.out.println("Default - Show all Cards.");
                                dbPaymentsObject.showAllCards(LoginID);
                                break;
                            }
                        }
                        break;
                    }

                }

                default: {
                    System.out.println("Default choice is EXIT.");
                    flag = false;
                    // loginFlag = false;

                    break;
                }
            }
        }
    }
    public void StaffMenu(String staffId){
        while (flag == true && loginFlag == true) {
            //This is for customer!!!
            Scanner scan = new Scanner(System.in);
            System.out.println("Please select Below option to proceed: ");
            System.out.println("0 - EXIT \t 1 - Insert new products \t 2 - Update Products Quantity \t 3 - Show Product Stock\n");
            System.out.println("4 - View Customers \t 5 -  Orders \t 6 - Add Warehouse");
            int userChoice1 = scan.nextInt();
            switch(userChoice1){
                case 0:{
                    flag = false;
                    //loginFlag = false;
                    break;
                }
                case 1:{
                    dbNewProductUpdatesObject.newProductInsert(staffId);
                    break;
                }
                case 2:{
                    dbStockObject.UpdateNewStock(staffId);
                    break;
                }
                case 3:{
                    dbStockObject.showProdStock();
                    break;
                }
                case 4:{
                    dbStaffObject.viewAllCust();
                    break;
                }
                case 5:{
                    System.out.println("Please select Below option to proceed: ");
                    System.out.println("0 - Show all Orders \t 1 - Modify orders ");
                    int staffchoice1 = scan.nextInt();
                    switch(staffchoice1){
                        case 0:{
                            dbStaffObject.showAllOrders();
                            break;
                        }
                        case 1:{
                            dbStaffObject.modifyOrderR();
                            break;
                        }
                        default:{
                            System.out.println("Default Show all orders.");
                            dbStaffObject.showAllOrders();
                            break;
                        }
                    }

                }
                case 6:{
                    dbStaffObject.addWarehouse();
                    break;
                }
                default: {
                    System.out.println("Default choice is EXIT.");
                    flag = false;
                    // loginFlag = false;

                    break;
                }
            }
        }

    }

}
