<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: haosun
  Date: 2/27/18
  Time: 5:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Students Tracker App</title>
    <link type="text/css" rel="stylesheet" href="css/style.css">
</head>



<body>

    <div id="wrapper">
        <div id="header">
            <h2>University of Illinois at Urbana-Champaign</h2>
        </div>
    </div>

    <div id="container">
        <div id="content">
            <input type="button" value="Add Student"
                onclick="window.location.href='add-student-form.jsp'; return false;"
                class="add-student-button"
            />

            <table>
                <tr>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Email</th>
                    <th>Action</th>
                </tr>

                <c:forEach var="temp" items="${STUDENT_LIST}">
                    <!-- set up a link for each student -->
                    <c:url var="tempLink" value="student">
                        <c:param name="command" value="LOAD" />
                        <c:param name="studentId" value="${temp.id}" />
                    </c:url>

                    <!-- set up a delete link-->
                    <c:url var="deleteLink" value="student">
                        <c:param name="command" value="DELETE" />
                        <c:param name="studentId" value="${temp.id}" />
                    </c:url>

                    <tr>
                        <td> ${temp.firstName} </td>
                        <td> ${temp.lastName} </td>
                        <td> ${temp.email} </td>
                        <td> <a href="${tempLink}">Update</a>
                             |
                             <a href="${deleteLink}"
                                onclick="if (!(confirm('Are you sure you want to delete this student'))) return false">Delete</a>
                        </td>
                    </tr>
                </c:forEach>

            </table>
        </div>
    </div>

</body>
</html>
