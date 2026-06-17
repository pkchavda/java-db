    import dao.Data;
    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.PreparedStatement;
    import java.sql.SQLException;
    import java.util.Scanner;

    public class DatabaseConnection {

        public static final Scanner scanner = new Scanner(System.in);

        static Connection connectToDatabase() {
            String url = "jdbc:mysql://localhost:3306/mydb";
            String username = "root";
            String password = "";

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

        static Data addStudentinput() {

            Data data = new Data();

            System.out.print("Enter your name: ");
            scanner.nextLine();
            data.name = scanner.nextLine();

            System.out.print("Enter your age: ");
            data.age = scanner.nextInt();

            System.out.print("Enter your Phone Number: ");
            data.phonenumber = scanner.nextInt();

            System.out.println("Name: " + data.name);
            System.out.println("Age: " + data.age);
            System.out.println("Phone Number: " + data.phonenumber);

            return data;
        }

        static String addQuestion() {
                System.out.println("Enter your question: ");
                String question = scanner.nextLine();

                System.out.println("Question: " + question);

                return question;
        }

        public static void main(String[] args) throws SQLException {


            System.out.println("Welcome to the Database Connection Program! \n Please select an option: ");
            System.out.println("1. Add Student: ");
            System.out.println("2. Add Question: ");

            int choice = scanner.nextInt();

            Connection conn = connectToDatabase();

            switch (choice) {
            
                case 1:

                    if (conn == null) {
                        System.out.println("Failed to connect to database.");
                        return;
                    }

                    Data data = addStudentinput();
                    System.out.println(data.toString()); // bydefult toString()

                    String sql = "insert into student (name, age, number) values (?, ?, ?)";
                    PreparedStatement pstmt = conn.prepareStatement(sql);

                    pstmt.setString(1, data.name);
                    pstmt.setInt(2, data.age);
                    pstmt.setLong(3, data.phonenumber);

                    pstmt.executeUpdate();
                    System.out.println("Data inserted successfully!😊");
                    
                    scanner.close();

                    break;

                case 2:

                    if (conn == null) {
                        System.out.println("Failed to connect to database.");
                        return;
                    }

                    String question = addQuestion();
                    System.out.println("Question: " + question);

                    String sql1 = "insert into question_table (question) values (?)";
                    PreparedStatement pstmt1 = conn.prepareStatement(sql1);

                    pstmt1.setString(1, question);
                    pstmt1.executeUpdate();

                    System.out.println("Question inserted successfuly");

                default:
                    System.out.println("Invalid choice! Please select either 1 or 2.");
            }
        }
    }

    // javac -cp ".;mysql.jar" DatabaseConnection.java 
    // java -cp ".;mysql.jar" DatabaseConnection