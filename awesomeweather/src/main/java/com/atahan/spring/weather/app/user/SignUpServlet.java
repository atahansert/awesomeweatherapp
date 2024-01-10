package com.atahan.spring.weather.app.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {

    final String url = "jdbc:h2:mem:testdb"; 
    final String user = "sa"; 
    final String password = "admin"; 

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // form data
        String email = request.getParameter("email");
        String confirmEmail = request.getParameter("confirmEmail");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");


        try {
            // connection to the H2 database (its not working im too tired for this!!!)
            Connection connection = DriverManager.getConnection(url, user, password);

            // create the 'users' table if it doesn't exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY, email VARCHAR(255), password VARCHAR(255))";
            Statement statement = connection.createStatement();
            statement.execute(createTableSQL);

            // to insert data into the 'users' table
            String sql = "INSERT INTO users (email, password) VALUES (?, ?)";

            // to execute the SQL
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            // to insert data
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                PrintWriter out = response.getWriter();
                out.println("<h1>Sign Up Successful!</h1>");
            } else {
                PrintWriter out = response.getWriter();
                out.println("<h1>Failed to sign up. Please try again.</h1>");
            }

            // close
            preparedStatement.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            PrintWriter out = response.getWriter();
            out.println("<h1>Failed to sign up due to a database error.</h1>");
        }
    }
}
