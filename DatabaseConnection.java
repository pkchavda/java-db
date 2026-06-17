import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseConnection {

    static Statement connectToDatabase() {
        String url = "jdbc:mysql://localhost:3306/mydb";
        String username = "root";
        String password = "";

        try {
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("Connection Successful!");
            Statement stmt = con.createStatement();

            return stmt;

        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
            return null;
        }
    }

    static Data getUserinput() {
        Scanner scanner = new Scanner(System.in);

        Data data = new Data();

        System.out.print("Enter your name: ");
        data.name = scanner.nextLine();

        System.out.print("Enter your age: ");
        data.age = scanner.nextInt();

        System.out.print("Enter your Phone Number: ");
        data.phonenumber = scanner.nextInt();

        System.out.println("Name: " + data.name);
        System.out.println("Age: " + data.age);
        System.out.println("Phone Number: " + data.phonenumber);

        scanner.close();
        return data;
    }

    public static void main(String[] args) throws SQLException {

        Statement stmt = connectToDatabase();
        if (stmt == null) {
            System.out.println("Statement is null. Cannot insert data.");
            return;
        }

        Data data = getUserinput();
        System.out.println(data.toString()); // bydefult toString()

        String sql = "insert into student (name, age, number) values (data.name, data.age, data.phonenumber)";
        System.out.println(sql);

        stmt.executeUpdate(sql);
        System.out.println("Data inserted successfully!😊");
    }

}