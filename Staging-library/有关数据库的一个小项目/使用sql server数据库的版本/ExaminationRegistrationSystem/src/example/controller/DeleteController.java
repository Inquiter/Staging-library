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

public class DeleteController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-Type", "text/html;charset=UTF-8");
        String table = req.getParameter("table").trim();
        String data = req.getParameter("data").trim();
        try {
            Connection connection = ConnectionSqlServer.getConnectionSqlServer().getSqlServerConnection();
            if (connection == null)
                resp.getWriter().write("<script>alert('数据库连接错误!请联系管理员!');history.back();</script>");
            else {
                if (table.equals("请选择")) resp.getWriter().write("请选择表!");
                else if (data.length() == 0) resp.getWriter().write("请输入要删除的数据!");
                else if (data.length() != 1) resp.getWriter().write("数据格式错误!请重新输入!");
                else {
                    PreparedStatement preparedStatement;
                    switch (table) {
                        case "用户信息":
                            preparedStatement = connection.prepareStatement("delete from users where id = ?");
                            preparedStatement.setString(1, data);
                            int result = preparedStatement.executeUpdate();
                            if (result > 0) resp.getWriter().write("删除成功!");
                            else resp.getWriter().write("删除异常!请联系管理员!");
                            break;
                        case "考试信息":
                            preparedStatement = connection.prepareStatement("delete from examination where id = ?");
                            preparedStatement.setString(1, data);
                            result = preparedStatement.executeUpdate();
                            if (result > 0) resp.getWriter().write("删除成功!");
                            else resp.getWriter().write("删除异常!请联系管理员!");
                            break;
                        case "报名信息":
                            preparedStatement = connection.prepareStatement("delete from roll where id = ?");
                            preparedStatement.setString(1, data);
                            result = preparedStatement.executeUpdate();
                            if (result > 0) resp.getWriter().write("删除成功!");
                            else resp.getWriter().write("删除异常!请联系管理员!");
                            break;
                    }
                    preparedStatement = connection.prepareStatement("select * from users where username = ?");
                    Cookie[] cookies = req.getCookies();
                    String userName = "";
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("username")) {
                            userName = cookie.getValue();
                        }
                    }
                    preparedStatement.setString(1, userName);
                    index(req, preparedStatement);
                }
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
