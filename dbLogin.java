package OnlineStore;
import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class dbLogin {

    public String[] Login(){
        String Xid = null;
        String LoginType = null;
        String[] LoginArr = new String[2];
        Scanner scan = new Scanner(System.in);
        System.out.println("Please select the following option: \n 1 - New User \t 2 - Customer Login \t 3 - Staff Login.");
        int userInput = scan.nextInt();
        switch(userInput){
            case 1: {

                Scanner scan1 = new Scanner(System.in);
                System.out.println("Please select the following option: \n 1 - Customer Registration \t 2 - Staff Registration");
                int userint1 = scan1.nextInt();
                switch(userint1){
                    case 1:{
                        this.CustomerRegistration();
                        System.out.println("Registration successful. Login now! ");
                        Xid = this.CustLogin();
                        LoginType = "Customer";
                        break;
                    }
                    case 2:{
                        this.StaffRegistration();
                        System.out.println("Registration successful. Login now! ");
                        Xid = this.staffLogin();
                        LoginType = "Staff";

                        break;
                    }
                }
                break;
            }
            case 2:{
                Xid = this.CustLogin();
                LoginType = "Customer";

                break;
            }
            case 3:{
                Xid = this.staffLogin();
                LoginType = "Staff";

                break;
            }
            default:{
                System.out.println("Default option new customer login.");
                this.CustomerRegistration();
                LoginType = "Customer";

                break;
            }
        }
        LoginArr[0] = Xid;
        LoginArr[1] = LoginType;
        return LoginArr;
    }


    public String CustLogin(){

        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";
        String custId = null;
        Boolean flag = false;


        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter your id: ");

        String userid = scan.nextLine();
        System.out.println("Userid is: "+userid);
        String sql1 = "select * " + " from customer";

        try(Connection con = DriverManager.getConnection(url,user,password);
            Statement st1 = con.createStatement(); // this is for creating a statement which will be running sql.

            ResultSet R1 = st1.executeQuery(sql1);
        ){
            //System.out.println("Connected to db;");
            while(R1.next()){
                if(userid.equals(R1.getString("customerid"))){
                    //System.out.println("Welcome Customer!");
                    System.out.println("Welcome "+R1.getString("customername"));
                    flag = true;
                    custId = R1.getString("customerid");
                }
            }
            if(flag == false){
                System.out.println("Wrong ID!");
                custId = "False";
            } return custId;

        }catch(SQLException e){
            System.out.println(e);
        }return custId;

    }

    public String staffLogin(){
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";
        String StaffidX = null;

        Boolean flag = false;
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter your id: ");

        String userid = scan.nextLine();
        System.out.println("Userid is: "+userid);
        String sql2 = "select * "+" from staff";
        try(Connection con = DriverManager.getConnection(url,user,password);
            Statement st1 = con.createStatement(); // this is for creating a statement which will be running sql.

            ResultSet R2 = st1.executeQuery(sql2);
        ){
            //System.out.println("Connected to db;");
            while(R2.next()){
                if(userid.equals(R2.getString("staffid"))){
                    //System.out.println("Welcome Staff!");
                    System.out.println("Welcome "+R2.getString("staffname"));
                    flag = true;
                    StaffidX = R2.getString("staffid");
                }
            }
            if(flag == false){
                System.out.println("Wrong ID!");
                StaffidX = "False";

            } return StaffidX;


        }catch(SQLException e){
            System.out.println(e);
        }
        return StaffidX;
    }

    public void CustomerRegistration(){

        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";


        Scanner scan = new Scanner(System.in);
//        System.out.println("Please enter your desired id: ");
//        String userid = scan.nextLine();


        System.out.println("Please enter your user name: ");
        String userName = scan.nextLine();
        System.out.println("User name is: "+userName);

        Random rInt = new Random();
        int userid = rInt.nextInt(10000,999999);
        String userid1 = String.valueOf(userid);
        System.out.println("Hello "+ userName+" Your auto generated Userid is: "+userid1);

        String sql1 = "INSERT INTO customer values (?,?)";


        //String sql1 = "select * " + " from customer";

        try(Connection con = DriverManager.getConnection(url,user,password);
            Statement st1 = con.createStatement(); // this is for creating a statement which will be running sql.
            PreparedStatement p1 = con.prepareStatement(sql1);

        ){
            p1.setString(1, userid1);
            p1.setString(2,userName);
            p1.executeUpdate(); // we can either use this statement or the below statement.
            System.out.println("Customer details updated.");
            //ResultSet R1 = p1.executeQuery();

            //System.out.println("Connected to db;");

            //** add here customer address method *********
            this.custAddress(userid1);



        }catch(SQLException e){
            System.out.println(e);
        }


    }

    public void custAddress(String LoginID){

        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";


        Scanner scan = new Scanner(System.in);
        System.out.println("!!! Address constraints - Please end your address with USA state only (two letters only for state.) !!!");
        System.out.println("Please enter your delivery address: ");
        String userDelAdd = scan.nextLine();
        System.out.println("Delivery address is: "+userDelAdd);

        System.out.println("Please enter your Permanent address: ");
        String userPermAdd = scan.nextLine();
        System.out.println("Delivery address is: "+userPermAdd);

        String sql1 = "INSERT INTO customeraddress values (?,?,?)";


        //String sql1 = "select * " + " from customer";

        try(Connection con = DriverManager.getConnection(url,user,password);
            Statement st1 = con.createStatement(); // this is for creating a statement which will be running sql.
            PreparedStatement p1 = con.prepareStatement(sql1);

        ){
            p1.setString(1,LoginID);
            p1.setString(2,userDelAdd);
            p1.setString(3,userPermAdd);
            p1.executeUpdate(); // we can either use this statement or the below statement.
            System.out.println("Address updated.");
            //Note - executeupdate for prepared statement and executeQuery for Result set.

            //ResultSet R1 = p1.executeQuery();

            //System.out.println("Connected to db;");

            //** add here customer address method *********


        }catch(SQLException e){
            System.out.println(e);
        }


    }
    public void modifyCustAddress(String LoginID){
        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";


        Scanner scan = new Scanner(System.in);
        System.out.println("!!! Address constraints - Please end your address with USA state only (two letters only for state.) !!!");
        System.out.println("Please enter your delivery address: ");
        String userDelAdd = scan.nextLine();
        System.out.println("Delivery address is: "+userDelAdd);

        System.out.println("Please enter your Permanent address: ");
        String userPermAdd = scan.nextLine();
        System.out.println("Delivery address is: "+userPermAdd);

        String sql1 = "Update customeraddress set delivery = ?,permanent = ? where customerid = ?";


        //String sql1 = "select * " + " from customer";

        try(Connection con = DriverManager.getConnection(url,user,password);
            Statement st1 = con.createStatement(); // this is for creating a statement which will be running sql.
            PreparedStatement p1 = con.prepareStatement(sql1);

        ){
            p1.setString(1,userDelAdd);
            p1.setString(2,userPermAdd);
            p1.setString(3,LoginID);

            p1.executeUpdate(); // we can either use this statement or the below statement.
            System.out.println("Address updated.");
            //Note - executeupdate for prepared statement and executeQuery for Result set.

            //ResultSet R1 = p1.executeQuery();

            //System.out.println("Connected to db;");

            //** add here customer address method *********


        }catch(SQLException e){
            System.out.println(e);
        }

    }
    public void StaffRegistration(){

        final String url = "jdbc:postgresql://localhost:5432/OnlineGrocery";
        final String user = "postgres";
        final String password = "1998";


        Scanner scan = new Scanner(System.in);
//        System.out.println("Please enter your desired id: ");
//        String userid = scan.nextLine();
//        System.out.println("Userid is: "+userid);

        System.out.println("Please enter your user name: ");
        String userName = scan.nextLine();
        System.out.println("User name is: "+userName);

        Random rInt = new Random();
        int userid = rInt.nextInt(10000,999999);
        String userid1 = String.valueOf(userid);
        System.out.println("Hello "+ userName+" Your auto generated Userid is: "+userid1);

        System.out.println("!!! Address constraints - Please end your address with state (two letters only for state.) !!!");
        System.out.println("Please enter your address: ");
        String userAddress = scan.nextLine();
        System.out.println("User address is: "+userAddress);

        System.out.println("Please enter your job title: \n 1 - Level1,\t 2 - Level2,\t 3 - Level3 ");
        int userchoice1 = scan.nextInt();
        String userTitle = "Level1";
        Double userSalary = 1000.25;

        switch (userchoice1){
            case 1:{
                userTitle = "Level1";
                userSalary = rInt.nextDouble(1000,1999);
                break;
            }
            case 2:{
                userTitle = "Level2";
                userSalary =  (rInt.nextDouble(2000,2999));
                break;
            }
            case 3:{
                userTitle = "Level3";
                userSalary = rInt.nextDouble(3000,4999);
                break;
            }
            default:{
                 userTitle = "Level1";
                 userSalary = 1000.25;
                 break;
            }
        }

        //String userTitle = scan.nextLine();
        System.out.println("User job title is: "+userTitle);

        //System.out.println("Please Update your salary: ");
        //Double userSalary = scan.nextDouble();
        userSalary = Math.floor(userSalary);
        System.out.println("User salary is: "+ userSalary);

        String sql1 = "INSERT INTO staff values (?,?,?,?,?)";


        //String sql1 = "select * " + " from customer";

        try(Connection con = DriverManager.getConnection(url,user,password);
            Statement st1 = con.createStatement(); // this is for creating a statement which will be running sql.
            PreparedStatement p1 = con.prepareStatement(sql1);

        ){
            p1.setString(1,userid1);
            p1.setString(2,userName);
            p1.setString(3,userAddress);
            p1.setDouble(4,userSalary);
            p1.setString(5,userTitle);

            p1.executeUpdate();
            System.out.println("Staff details updated.");

            //ResultSet R1 = p1.executeQuery();
            //System.out.println("Connected to db;");


        }catch(SQLException e){
            System.out.println(e);
        }


    }

}







