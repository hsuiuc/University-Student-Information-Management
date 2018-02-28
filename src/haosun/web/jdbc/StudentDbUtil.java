package haosun.web.jdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentDbUtil {
    private DataSource dataSource;

    public StudentDbUtil(DataSource theDataSource) {
        dataSource = theDataSource;
    }

    public List<Student> getStudents() throws Exception {
        List<Student> students = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            //get a connection
            myConn = dataSource.getConnection();
            //create sql statement
            String sql = "select * from student order by last_name";
            myStmt = myConn.createStatement();
            //execute query
            myRs = myStmt.executeQuery(sql);
            //process results set
            while (myRs.next()) {
                //retrieve data from each row
                int id = myRs.getInt("id");
                String firstName = myRs.getString("first_name");
                String lastName = myRs.getString("last_name");
                String email = myRs.getString("email");

                //create student object
                Student tempStudent = new Student(id, firstName, lastName, email);

                //add temp to list
                students.add(tempStudent);
            }
            return students;
        }
        finally {
            //close jdbc object
            close(myConn, myStmt, myRs);
        }
    }

    private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
        try {
            if (myRs != null) {
                myRs.close();
            }

            if (myStmt != null) {
                myStmt.close();
            }

            if (myConn != null) {
                myConn.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addStudent(Student theStudent) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            //get connection
            connection = dataSource.getConnection();
            //create sql for insert
            String sql = "insert into student "
                       + "(first_name, last_name, email) "
                       + "values (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            //set the param values for the student
            preparedStatement.setString(1, theStudent.getFirstName());
            preparedStatement.setString(2, theStudent.getLastName());
            preparedStatement.setString(3, theStudent.getEmail());
            //execute sql insert
            preparedStatement.execute();
        }
        finally {
            //clean up jdbc objects
            close(connection, preparedStatement, null);
        }
    }

    public Student getStudent(String theStudentID) throws Exception {
        Student theStudent = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int studentId;

        try {
            //convert student id to int
            studentId = Integer.parseInt(theStudentID);
            //get connection to db
            connection = dataSource.getConnection();
            //create sql to get selected student
            String sql = "select * from student where id=?";
            //create prepared statement
            preparedStatement = connection.prepareStatement(sql);
            //set params
            preparedStatement.setInt(1, studentId);
            //execute statement
            resultSet = preparedStatement.executeQuery();
            //retrieve data from result set
            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                theStudent = new Student(studentId, firstName, lastName, email);
            }
            else {
                throw new Exception("could not find student id: " + theStudentID);
            }
        }
        finally {
            //clean up jdbc objects
            close(connection, preparedStatement, resultSet);
        }

        return theStudent;
    }

    public void updateStudent(Student theStudent) throws Exception {
        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            //get db connection
            myConn = dataSource.getConnection();
            //create sql update statement
            String sql = "update student "
                    + "set first_name = ?, last_name = ?, email = ? "
                    + "where id = ?";
            //prepare statement
            myStmt = myConn.prepareStatement(sql);
            //set params
            myStmt.setString(1, theStudent.getFirstName());
            myStmt.setString(2, theStudent.getLastName());
            myStmt.setString(3, theStudent.getEmail());
            myStmt.setInt(4, theStudent.getId());
            //execute sql statement
            myStmt.execute();
        }
        finally {
            close(myConn, myStmt, null);
        }
    }

    public void deleteStudent(String theStudentId) throws Exception {
        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            //convert student id to int
            int studentId = Integer.parseInt(theStudentId);

            //get connection to db
            myConn = dataSource.getConnection();

            //create sql to delete student
            String sql = "delete from student where id = ?";

            //prepare statement
            myStmt = myConn.prepareStatement(sql);

            //set params
            myStmt.setInt(1, studentId);

            //execute sql statement
            myStmt.execute();
        }
        finally {
            //clean jdbc objects
            close(myConn, myStmt, null);
        }
    }
}
