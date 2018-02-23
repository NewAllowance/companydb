package lv.tsi.javacourses.companydb;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.*;

/**
 * @author Dimitrijs Fedotovs <a href="http://www.bug.guru">www.bug.guru</a>
 * @version 1.0
 * @since 1.0
 */
@WebServlet(name = "SelectServlet", urlPatterns = "/select")
public class SelectServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/companydb");
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM PERSON");
             PrintWriter out = response.getWriter()
        ) {
            out.format("| %5s | %-30s | %15s | %10s | %10s |\n", "ID", "NAME", "SALARY", "POSITION", "DEPARTMENT");
            while (rs.next()) {
                long id = rs.getLong("ID");
                String name = rs.getString("FULL_NAME");
                long positionId = rs.getLong("POSITION_ID");
                long departmentId = rs.getLong("DEPARTMENT_ID");
                BigDecimal salary = rs.getBigDecimal("SALARY");
                out.format("| %5d | %-30s | %15.2f | %10d | %10d |\n", id, name, salary, positionId, departmentId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
