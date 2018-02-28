package haosun.web.jdbc;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private StudentDbUtil studentDbUtil;

    @Resource(name = "jdbc/student_information")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();

        //create student db util
        try {
            studentDbUtil = new StudentDbUtil(dataSource);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        //list the students in MVC fashion
        try {
            //read the command parameter
            String theCommand = request.getParameter("command");
            if (theCommand == null) {
                theCommand = "LIST";
            }

            //route to appropriate method
            switch (theCommand) {
                case "LIST":
                    //System.out.println("list");
                    listStudents(request, response);
                    break;
                case "ADD":
                    //System.out.println("add");
                    addStudent(request, response);
                    break;
                case "LOAD":
                    loadStudent(request, response);
                    break;
                case "UPDATE":
                    updateStudent(request, response);
                    break;
                case "DELETE":
                    deleteStudent(request, response);
                    break;
                default:
                    listStudents(request, response);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //read student id from form data
        String theStudentId = request.getParameter("studentId");

        //delete student from db
        studentDbUtil.deleteStudent(theStudentId);

        //send user back to list students page
        listStudents(request, response);
    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //read student info from the form data
        int id = Integer.parseInt(request.getParameter("studentId"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        //create a new student object
        Student theStudent = new Student(id, firstName, lastName, email);
        //perform update on db
        studentDbUtil.updateStudent(theStudent);
        //send user back to list students page
        listStudents(request, response);
    }

    private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //read student id from form data
        String theStudentID = request.getParameter("studentId");
        //get student from db
        Student theStudent = studentDbUtil.getStudent(theStudentID);
        //place student in the request attribute
        request.setAttribute("THE_STUDENT", theStudent);
        //send to jsp page : update-student-page.jsp
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/update-student-form.jsp");
        requestDispatcher.forward(request, response);
    }

    private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //read student info from form data
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        //create new student object
        Student theStudent = new Student(firstName, lastName, email);
        //add student to the data base
        studentDbUtil.addStudent(theStudent);
        //send back to the main page
        listStudents(request, response);
    }

    private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //get students from db util
        List<Student> students = studentDbUtil.getStudents();
        //add students to the request
        request.setAttribute("STUDENT_LIST", students);
        //relay the request to the jsp page
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/list-students.jsp");
        requestDispatcher.forward(request, response);
    }
}
