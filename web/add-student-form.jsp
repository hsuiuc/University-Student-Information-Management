<%--
  Created by IntelliJ IDEA.
  User: haosun
  Date: 2/27/18
  Time: 8:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Student</title>
    <link type="text/css" rel="stylesheet" href="css/style.css">
    <link type="text/css" rel="stylesheet" href="css/add-student-style.css">
</head>
<body>
    <div id="wrapper">
        <div id="header">
            <h2>University of Illinois at Urbana-Champaign</h2>
        </div>
    </div>
    <div id="container">
        <h3>Add Student</h3>
        <form action="student" method="GET">
            <input type="hidden" name="command" value="ADD">
            <table>
                <tbody>
                    <tr>
                        <td><label>First Name:</label></td>
                        <td><input type="text" name="firstName"></td>
                    </tr>
                    <tr>
                        <td><label>Last Name:</label></td>
                        <td><input type="text" name="lastName"></td>
                    </tr>
                    <tr>
                        <td><label>Email:</label></td>
                        <td><input type="text" name="email"></td>
                    </tr>
                    <tr>
                        <td><label></label></td>
                        <td><input type="submit" value="Save" class="save"></td>
                    </tr>
                </tbody>
            </table>
        </form>

        <div style="clear: both;"></div>

        <p><a href="student">Back To List</a></p>
    </div>
</body>
</html>
