package example.controller;

import example.uTils.ConnectionSqlServer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InfoController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-Type", "text/html;charset=UTF-8");
        try {
            Connection connection = ConnectionSqlServer.getConnectionSqlServer().getSqlServerConnection();
            if (connection == null)
                resp.getWriter().write("<script>alert('数据库连接错误!请联系管理员!');history.back();</script>");
            else {
                PreparedStatement preparedStatement = connection.prepareStatement("select * from examination where examination_subjects = ?");
                preparedStatement.setString(1, req.getParameter("examination_subjects"));
                index(req, preparedStatement);
                Cookie[] cookies = req.getCookies();
                String userName = "";
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("username")) {
                        userName = cookie.getValue();
                    }
                }
                preparedStatement = connection.prepareStatement("select * from users where username = ?");
                preparedStatement.setString(1, userName);
                index1(req, preparedStatement);
                req.getRequestDispatcher("signup.jsp").forward(req, resp);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

    private void index(HttpServletRequest req, PreparedStatement preparedStatement) {
        try {
            ResultSet result = preparedStatement.executeQuery();
            List<String> list = new ArrayList<>();
            while (result.next()) {
                list.add(result.getString("examination_subjects"));
                list.add(result.getString("examination_introduction"));
                list.add(result.getString("examination_information"));
                list.add(result.getString("examination_requirements"));
                list.add(result.getString("examination_content"));
                list.add(result.getString("examination_time"));
                list.add(result.getString("examination_place"));
            }
            req.setAttribute("list1", list);
            result.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void index1(HttpServletRequest req, PreparedStatement preparedStatement) {
        try {
            ResultSet result = preparedStatement.executeQuery();
            List<String> list = new ArrayList<>();
            while (result.next()) {
                list.add(result.getString("username"));
                list.add(result.getString("idcard"));
                list.add(result.getString("account_type"));
            }
            req.setAttribute("list", list);
            result.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
