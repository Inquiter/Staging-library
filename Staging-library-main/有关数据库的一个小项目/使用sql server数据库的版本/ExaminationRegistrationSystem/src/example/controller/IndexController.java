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

public class IndexController extends HttpServlet {
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
                index(req, connection.prepareStatement("select * from examination"));
                Cookie[] cookies = req.getCookies();
                String userName = "";
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("username")) {
                        userName = cookie.getValue();
                    }
                }
                PreparedStatement preparedStatement = connection.prepareStatement("select * from users where username = ?");
                preparedStatement.setString(1, userName);
                index1(req, preparedStatement);
                req.getRequestDispatcher("index.jsp").forward(req, resp);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

    private void index(HttpServletRequest req, PreparedStatement preparedStatement) {
        try {
            ResultSet result = preparedStatement.executeQuery();
            List<List<String>> list = new ArrayList<>();
            while (result.next()) {
                list.add(new ArrayList<>());
                list.get(list.size() - 1).add(result.getString("examination_subjects"));
                list.get(list.size() - 1).add(result.getString("examination_introduction"));
                list.get(list.size() - 1).add(result.getString("examination_information"));
                list.get(list.size() - 1).add(result.getString("examination_requirements"));
                list.get(list.size() - 1).add(result.getString("examination_content"));
                list.get(list.size() - 1).add(result.getString("examination_time"));
                list.get(list.size() - 1).add(result.getString("examination_place"));
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
