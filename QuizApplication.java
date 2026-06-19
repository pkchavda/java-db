import dao.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class QuizApplication {

    public static final Scanner scanner = new Scanner(System.in);

    static Data addStudentinput() {

        Data data = new Data();

        System.out.print("Enter your name: ");
        data.name = scanner.nextLine();

        System.out.print("Enter your age: ");
        data.age = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter your Phone Number: ");
        data.phonenumber = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter your Enrollment Number: ");
        data.enrollment = scanner.nextInt();
        scanner.nextLine();

        System.out.println("\nName: " + data.name);
        System.out.println("Age: " + data.age);
        System.out.println("Phone Number: " + data.phonenumber);
        System.out.println("Enrollment Number: " + data.enrollment);

        return data;
    }

    static String[] addQuestion() {
        
        System.out.println("Enter your question: ");
        String question = scanner.nextLine();

        System.out.println("Enter Otion A: ");
        String optionA = scanner.nextLine();

        System.out.println("Enter Otion B: ");
        String optionB = scanner.nextLine();

        System.out.println("Enter option C: ");
        String optionC = scanner.nextLine();

        System.out.println("Enter option D: ");
        String optionD = scanner.nextLine();

        System.out.println("Enter the COURRECT Option: ");
        String courrect_option = scanner.nextLine();

        // Because multipal // String retun so make a // array
        return new String[] { question, optionA, optionB, optionC, optionD, courrect_option };
    }

    static void startQuiz(Connection conn) throws SQLException {
        int totalQuestions = 0;
        int score = 0;

        System.out.print("How many questions do you want to attempt? : ");
        int limit = scanner.nextInt();
        scanner.nextLine();

        List<String> correctAnswerList = new ArrayList<>();
        List<String> wrongQuestionList = new ArrayList<>();
        List<String> userAnswerList = new ArrayList<>();

        String sql = "SELECT * FROM questions LIMIT ?";

        PreparedStatement pstmt3 = conn.prepareStatement(sql);
        pstmt3.setInt(1, limit);

        ResultSet rs = pstmt3.executeQuery();

        System.out.println("\n==================================");
        System.out.println("         QUIZ STARTED");
        System.out.println("====================================");

        while (rs.next()) {

            totalQuestions++;

            String qText = rs.getString("question");
            String opA = rs.getString("option_a");
            String opB = rs.getString("option_b");
            String opC = rs.getString("option_c");
            String opD = rs.getString("option_d");
            String dbCorrect = rs.getString("correct_option");

            System.out.println("\nQuestion " + totalQuestions + ": " + qText);
            System.out.println("A) " + opA);
            System.out.println("B) " + opB);
            System.out.println("C) " + opC);
            System.out.println("D) " + opD);


            System.out.print("Your Answer (A/B/C/D): ");
            String userAns = scanner.nextLine().trim().toUpperCase();

            if (userAns.equals("A") || userAns.equals("B") || userAns.equals("C") || userAns.equals("D")) {

                if (userAns.equals(dbCorrect.toUpperCase())) {
                    score++;
                } 
                else 
                    {
                    correctAnswerList.add(dbCorrect);
                    wrongQuestionList.add(qText);
                    userAnswerList.add(userAns);
                }
            }
            else{
                System.out.println("Invalid Answer! Please enter A, B, C or D.");
                break;
            }
        }

        int newScore = totalQuestions - score;

        System.out.println("\n==================================");
        System.out.println("          QUIZ RESULT");
        System.out.println("==================================");

        System.out.println("Total Questions : " + totalQuestions);
        System.out.println("Correct Answers : " + score + " ✅");
        System.out.println("Wrong Answers   : " + newScore + " ❌");

        int percentage = (score * 100) / totalQuestions;
        System.out.println("Percentage      : " + percentage + "%");

        if (newScore < 5) {
            System.out.println("😊 Try Again and more improvment in this quiz 😊");
        } else {
            System.out.println("");
        }

    }


    static void viewAllStudents(Connection conn) throws SQLException {

        String sql = "SELECT * FROM student";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        System.out.println("--- 📋 ALL REGISTERED STUDENTS 📋 ---");
        while (rs.next()) {

            System.out.println("┌─────────────────────────────────────────────────────────┐");
            System.out.println("│ Name  : " + rs.getString("name"));
            System.out.println("│ Age   : " + rs.getInt("age"));
            System.out.println("│ Phone : " + rs.getString("mo_number"));
            System.out.println("│Enrollment : " + rs.getInt("enrollment_number"));
            System.out.println("└──────────────────────────────────────────────────────────┘");
        }

        System.out.println("Students displayed successfully!");
    }

    static void viewAllQuestion(Connection conn) throws SQLException{
        
        
        String sql3 = "select * from questions";
        PreparedStatement pstmt2 = conn.prepareStatement(sql3);
        ResultSet rs3 = pstmt2.executeQuery();

        System.out.println("----  ALL REGISTERED Questions --------");

        while (rs3.next()) {
            
            System.out.println("┌─────────────────────────────────────────────────────────┐");
            System.out.println("| Question : " + rs3.getString("question"));
            System.out.println("| Option A : " + rs3.getString("option_a"));
            System.out.println("| option B : " + rs3.getString("option_b"));
            System.out.println("| option C : " + rs3.getString("option_c"));
            System.out.println("| Option D : " + rs3.getString("option_d"));
            System.out.println("| Correct Option : " + rs3.getString("correct_option"));
            System.out.println("└───────────────────────────────────────────────────────────┘");

        }

        System.out.println("Question displayed successfully!");
    }
        static void deleteStudent(Connection conn) throws SQLException {

            System.out.print("Enter Student Enrollment Number: ");
            int enrollmentNo = scanner.nextInt();
            scanner.nextLine();

            String sql4 = "DELETE FROM student WHERE enrollment_number = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql4);
            pstmt.setInt(1, enrollmentNo);

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Student Deleted Successfully!");
            } else {
                System.out.println("Enrollment Number Not Found!");
            }
        }

    public static void main(String[] args) throws SQLException {

        Connection connection = new DatabaseConnection().connection;
        if (connection == null) {
            System.out.println("Failed to connect to database.");
            return;
        }

        System.out.println("\n");
        System.out.println("Welcome to the Database Connection Program! \n ");
        System.out.println("1. Add Student Info: ");
        System.out.println("2. Add Question: ");
        System.out.println("3. Start Quiz: ");
        System.out.println("4. View all Questions:");
        System.out.println("5. View all Students: ");
        System.out.println("6. Delete Student: ");
        System.out.println("0. Exit");

        System.out.println("\n");
        System.out.println("Please select an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println("\n");

        switch (choice) {
            case 1:
                System.out.println("\n");
                Data data = addStudentinput();
                System.out.println(data.toString()); // bydefult toString()

                String sql1 = "insert into student (name, age, mo_number, enrollment_number) values (?, ?, ?, ?)";
                PreparedStatement pstmt1 = connection.prepareStatement(sql1);

                pstmt1.setString(1, data.name);
                pstmt1.setInt(2, data.age);
                pstmt1.setLong(3, data.phonenumber);
                pstmt1.setInt(4, data.enrollment);

                pstmt1.executeUpdate();
                System.out.println("\n");
                System.out.println("Data inserted successfully!😊");

                break;

            case 2:

                String[] data1 = addQuestion();

                String sql2 = "insert into questions (question, option_a, option_b, option_c, option_d, correct_option) values (?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt2 = connection.prepareStatement(sql2);

                pstmt2.setString(1, data1[0]);
                pstmt2.setString(2, data1[1]);
                pstmt2.setString(3, data1[2]);
                pstmt2.setString(4, data1[3]);
                pstmt2.setString(5, data1[4]);
                pstmt2.setString(6, data1[5]);

                pstmt2.executeUpdate();

                System.out.println("Question inserted successfuly");

                break;

            case 3:

                startQuiz(connection);

                break;

            case 4:

                System.out.println("All Student Result: ");
                System.out.println("\n");

                viewAllQuestion(connection);

                break;
            
            case 5:

                System.out.println("All Student: ");
                System.out.println("\n");

                viewAllStudents(connection);

                break;

            case 6:

                deleteStudent(connection);
                break;

            default:
                System.out.println("Invalid choice! Please select either 1 or 2.");
        }
        scanner.close();
    }
}

// javac -cp ".;mysql.jar" QuizApplication.java
// java -cp ".;mysql.jar" QuizApplication