import dao.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DatabaseConnection {

    static Connection connectToDatabase() {
        String url = "jdbc:mysql://localhost:3306/mydb";
        String username = "root";
        String password = "123456789";

        try {
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("Connection Successful!");
            return con;

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

        Connection conn = connectToDatabase();
        if (conn == null) {
            System.out.println("Failed to connect to database.");
            return;
        }

        Data data = getUserinput();
        System.out.println(data.toString()); // bydefult toString()

        String sql = "insert into student (name, age, number) values (?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, data.name);
        pstmt.setInt(2, data.age);
        pstmt.setLong(3, data.phonenumber);

        pstmt.executeUpdate();
        System.out.println("Data inserted successfully!😊");
    }
}