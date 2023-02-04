package example.controller;

import example.uTils.ConnectionSqlServer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-Type", "text/html;charset=UTF-8");
        String table = req.getParameter("table").trim();
        String data = req.getParameter("data").trim();
        try {
            Connection connection = ConnectionSqlServer.getConnectionSqlServer().getSqlServerConnection();
            if (table.equals("请选择")) {
                resp.getWriter().write("请选择表!");
            } else if (data.length() == 0) {
                resp.getWriter().write("请输入要添加的数据!");
            } else {
                String[] datas = data.split("\\|");
                PreparedStatement preparedStatement;
                switch (table) {
                    case "用户信息":
                        if (datas.length > 5) {
                            resp.getWriter().write("数据格式错误!请重新输入!");
                        } else if (datas.length < 5) {
                            resp.getWriter().write("数据缺省!请重新输入!");
                        } else {
                            preparedStatement = connection.prepareStatement("insert into users(username, password, birthday, idcard, account_type) values(?,?,?,?,?)");
                            for (int i = 0; i < 4; i++) preparedStatement.setString(i + 1, datas[i]);
                            preparedStatement.setString(5, datas[4].equals("管理员") ? "1" : "0");
                            int result = preparedStatement.executeUpdate();
                            if (result > 0) resp.getWriter().write("添加成功!");
                            else resp.getWriter().write("添加异常!请联系管理员!");
                        }
                        break;
                    case "考试信息":
                        if (datas.length > 7) {
                            resp.getWriter().write("数据格式错误!请重新输入!");
                        } else if (datas.length < 7) {
                            resp.getWriter().write("数据缺省!请重新输入!");
                        } else {
                            preparedStatement = connection.prepareStatement("insert into examination(examination_subjects, examination_time, examination_place, examination_introduction, examination_information, examination_requirements, examination_content) values(?,?,?,?,?,?,?)");
                            for (int i = 0; i < 7; i++) preparedStatement.setString(i + 1, datas[i]);
                            int result = preparedStatement.executeUpdate();
                            if (result > 0) resp.getWriter().write("添加成功!");
                            else resp.getWriter().write("添加异常!请联系管理员!");
                        }
                        break;
                    case "报名信息":
                        if (datas.length > 8) {
                            resp.getWriter().write("数据格式错误!请重新输入!");
                        } else if (datas.length < 8) {
                            resp.getWriter().write("数据缺省!请重新输入!");
                        } else {
                            preparedStatement = connection.prepareStatement("insert into roll(username, idcard, registration_time, examination_subjects, examination_time, examination_place, examination_fee, payment_method, payment_status) values(?,?,?,?,?,?,?,?,?)");
                            for (int i = 0; i < 8; i++)
                                preparedStatement.setString(i + 1, i == 2 ? new Date().toString() : datas[i]);
                            int result = preparedStatement.executeUpdate();
                            if (result > 0) resp.getWriter().write("添加成功!");
                            else resp.getWriter().write("添加异常!请联系管理员!");
                        }
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
