import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseConnection {

    static PreparedStatement connectToDatabase() {
        String url = "jdbc:mysql://localhost:3306/mydb";
        String username = "root";
        String password = "";

        try {
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("Connection Successful!");
            PreparedStatement pstmt = con.prepareStatement();

            return pstmt;

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

        PreparedStatement pstmt = connectToDatabase();
        if (pstmt == null) {
            System.out.println("PreparedStatement is null. Cannot insert data.");
            return;
        }

        Data data = getUserinput();
        System.out.println(data.toString()); // bydefult toString()

        String sql = "insert into student (name, age, number) values (data.name, data.age, data.phonenumber)";
        System.out.println(sql);

        pstmt.setString(1, data.name);
        pstmt.setInt(2, data.age);
        pstmt.setLong(3, data.phonenumber);

        pstmt.executeUpdate();
        System.out.println("Data inserted successfully!😊");
    }

}