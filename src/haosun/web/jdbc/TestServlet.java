package haosun.web.jdbc;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet(name = "TestServlet")
public class TestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    //define datasource/connection pool for resource injection
    @Resource(name = "jdbc/student_information")
    private DataSource dataSource;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //set up print writer
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        //get a connection to the database
        Connection myConn;
        Statement myStmt;
        ResultSet myRs;

        try {
            myConn = dataSource.getConnection();
            //create a SQL statement
            String sql = "select * from student_information.student";
            myStmt = myConn.createStatement();

            //execute SQL statement
            myRs = myStmt.executeQuery(sql);

            //precess result set
            while (myRs.next()) {
                String email = myRs.getString("email");
                out.println(email);
                out.println("</br>");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
