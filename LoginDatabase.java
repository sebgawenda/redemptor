import java.sql.*;
import java.util.Scanner;

public class JavaDB {

    public static void main(String[] args) {
        
        Connection con;
        Statement st;
        ResultSet rs;
        
        int student_ID;
        String password;
        
        
        //Get Inputs
        Scanner keyboard = new Scanner(System.in);
        
        System.out.print("Student ID: ");
        student_ID = keyboard.nextInt();
        
        System.out.print("Password: ");
        password = keyboard.next();
        
        StudentLogin loginInput = new StudentLogin(student_ID, password);
        StudentLogin loginDB = new StudentLogin(0, "");

        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "23Secondsleft");
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM login_information WHERE student_ID = " + loginInput.getID() +" AND password = " + "'"+ loginInput.getPassword()+"'");

            while (rs.next()) {
                
                loginDB.setID(rs.getInt(1));
                loginDB.setPassword(rs.getString(2));
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        String student_1ID = Integer.toString(loginInput.getID());
        String DBStudentID = Integer.toString(loginDB.getID());
        String student_1pw = loginInput.getPassword();
        String DBStudentPW = loginDB.getPassword();
        
        if(student_1ID.equals(DBStudentID) && student_1pw.equals(DBStudentPW)){
            System.out.println("\nYou have successfully logged in.\n");
        }
        else{
            System.out.println("\nIncorrect username or password.\nPlease Try again.\n");
        }
        
    }

}