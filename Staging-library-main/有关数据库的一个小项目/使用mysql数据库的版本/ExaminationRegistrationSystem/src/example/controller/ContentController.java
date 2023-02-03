package example.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ContentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-Type", "text/html;charset=UTF-8");
        String id = req.getParameter("id");
        if (id == null || id.length() == 0) resp.getWriter().write("<script>alert('id异常!请联系管理员!');</script>");
        else {
            id = id.trim();
            String title = req.getParameter("title");
            if (title == null || title.length() == 0)
                resp.getWriter().write("<script>alert('title异常!请联系管理员!');</script>");
            else {
                title = title.trim();
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection("jdbc:mysql://mysql.sqlpub.com/database1", "database1", "c223ef85b15dbe08");
                    PreparedStatement preparedStatement = connection.prepareStatement("select * from examination where id = ?");
                    preparedStatement.setString(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        req.setAttribute("title", title);
                        req.setAttribute("value", resultSet.getString(title.equals("考试简介") ? "examination_introduction" : title.equals("考试信息") ? "examination_information" : title.equals("考试要求") ? "examination_requirements" : title.equals("考试内容") ? "examination_content" : "examination_subjects"));
                    }
                    req.getRequestDispatcher("content.jsp").forward(req, resp);
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
