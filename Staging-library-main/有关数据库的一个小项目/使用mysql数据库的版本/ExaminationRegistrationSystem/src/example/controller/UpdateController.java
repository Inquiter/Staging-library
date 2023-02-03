package example.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UpdateController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-Type", "text/html;charset=UTF-8");
        String table = req.getParameter("table").trim();
        String data = req.getParameter("data").trim();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://mysql.sqlpub.com/database1", "database1", "c223ef85b15dbe08");
            if (table.equals("请选择")) {
                resp.getWriter().write("请选择表!");
            } else if (data.length() == 0) {
                resp.getWriter().write("请输入要修改的数据!");
            } else {
                String[] datas = data.split("\\|");
                for (String s : datas) {
                    if (s.trim().length() == 0) {
                        connection.close();
                        resp.getWriter().write("数据格式错误!请重新输入!");
                        return;
                    }
                }
                PreparedStatement preparedStatement;
                switch (table) {
                    case "用户信息":
                        if (datas.length < 2 || datas.length > 5) {
                            resp.getWriter().write("数据格式错误!请重新输入!");
                        } else {
                            preparedStatement = connection.prepareStatement("select id from user");
                            ResultSet resultSet = preparedStatement.executeQuery();
                            List<String> ids = new ArrayList<>();
                            while (resultSet.next()) ids.add(resultSet.getString("id"));
                            if (!ids.contains(datas[0])) resp.getWriter().write("id不存在!");
                            else {
                                preparedStatement = connection.prepareStatement("select * from user where id = ?");
                                preparedStatement.setString(1, datas[0]);
                                resultSet = preparedStatement.executeQuery();
                                ids.clear();
                                while (resultSet.next()) {
                                    ids.add(resultSet.getString("id"));
                                    ids.add(resultSet.getString("username"));
                                    ids.add(resultSet.getString("password"));
                                    ids.add(resultSet.getString("birthday"));
                                    ids.add(resultSet.getString("idcard"));
                                    ids.add(resultSet.getString("account_type"));
                                }
                                String[] s;
                                for (int i = 1; i < datas.length; i++) {
                                    s = datas[i].split("=");
                                    if (s.length != 2 || s[0].trim().length() == 0 || s[1].trim().length() == 0)
                                        continue;
                                    switch (s[0].trim()) {
                                        case "用户名":
                                            ids.set(1, s[1].trim());
                                            break;
                                        case "密码":
                                            ids.set(2, s[1].trim());
                                            break;
                                        case "生日":
                                            ids.set(3, s[1].trim());
                                            break;
                                        case "身份证号":
                                            ids.set(4, s[1].trim());
                                            break;
                                        case "账户类型":
                                            ids.set(5, s[1].trim().equals("管理员") ? "1" : "0");
                                            break;
                                        default:
                                            resultSet.close();
                                            preparedStatement.close();
                                            connection.close();
                                            resp.getWriter().write("数据异常!请重新输入!");
                                            return;
                                    }
                                }
                                preparedStatement = connection.prepareStatement("update user set username = ?,password = ?,birthday = ?,idcard = ?,account_type = ? where id = ?");
                                for (int i = 1; i < 7; i++) preparedStatement.setString(i, ids.get(i % 6));
                                int result = preparedStatement.executeUpdate();
                                if (result > 0) resp.getWriter().write("修改成功!");
                                else resp.getWriter().write("修改异常!请联系管理员!");
                            }
                            resultSet.close();
                        }
                        break;
                    case "考试信息":
                        if (datas.length < 2 || datas.length > 7) {
                            resp.getWriter().write("数据格式错误!请重新输入!");
                        } else {
                            preparedStatement = connection.prepareStatement("select id from examination");
                            ResultSet resultSet = preparedStatement.executeQuery();
                            List<String> ids = new ArrayList<>();
                            while (resultSet.next()) ids.add(resultSet.getString("id"));
                            if (!ids.contains(datas[0])) resp.getWriter().write("id不存在!");
                            else {
                                preparedStatement = connection.prepareStatement("select * from examination where id = ?");
                                preparedStatement.setString(1, datas[0]);
                                resultSet = preparedStatement.executeQuery();
                                ids.clear();
                                while (resultSet.next()) {
                                    ids.add(resultSet.getString("id"));
                                    ids.add(resultSet.getString("examination_subjects"));
                                    ids.add(resultSet.getString("examination_time"));
                                    ids.add(resultSet.getString("examination_place"));
                                    ids.add(resultSet.getString("examination_introduction"));
                                    ids.add(resultSet.getString("examination_information"));
                                    ids.add(resultSet.getString("examination_requirements"));
                                    ids.add(resultSet.getString("examination_content"));
                                }
                                String[] s;
                                for (int i = 1; i < datas.length; i++) {
                                    s = datas[i].split("=");
                                    if (s.length != 2 || s[0].trim().length() == 0 || s[1].trim().length() == 0)
                                        continue;
                                    switch (s[0].trim()) {
                                        case "考试科目":
                                            ids.set(1, s[1].trim());
                                            break;
                                        case "考试时间":
                                            ids.set(2, s[1].trim());
                                            break;
                                        case "考试地点":
                                            ids.set(3, s[1].trim());
                                            break;
                                        case "考试简介":
                                            ids.set(4, s[1].trim());
                                            break;
                                        case "考试信息":
                                            ids.set(5, s[1].trim());
                                            break;
                                        case "考试要求":
                                            ids.set(6, s[1].trim());
                                            break;
                                        case "考试内容":
                                            ids.set(7, s[1].trim());
                                            break;
                                        default:
                                            resultSet.close();
                                            preparedStatement.close();
                                            connection.close();
                                            resp.getWriter().write("数据异常!请重新输入!");
                                            return;
                                    }
                                }
                                preparedStatement = connection.prepareStatement("update examination set examination_subjects = ?,examination_time = ?,examination_place = ?,examination_introduction = ?,examination_information = ?,examination_requirements = ?,examination_content = ? where id = ?");
                                for (int i = 1; i < 9; i++) preparedStatement.setString(i, ids.get(i % 8));
                                int result = preparedStatement.executeUpdate();
                                if (result > 0) resp.getWriter().write("修改成功!");
                                else resp.getWriter().write("修改异常!请联系管理员!");
                            }
                            resultSet.close();
                        }
                        break;
                    case "报名信息":
                        if (datas.length < 2 || datas.length > 4) {
                            resp.getWriter().write("数据格式错误!请重新输入!");
                        } else {
                            preparedStatement = connection.prepareStatement("select id from roll");
                            ResultSet resultSet = preparedStatement.executeQuery();
                            List<String> ids = new ArrayList<>();
                            while (resultSet.next()) ids.add(resultSet.getString("id"));
                            if (!ids.contains(datas[0])) resp.getWriter().write("id不存在!");
                            else {
                                preparedStatement = connection.prepareStatement("select * from roll where id = ?");
                                preparedStatement.setString(1, datas[0]);
                                resultSet = preparedStatement.executeQuery();
                                ids.clear();
                                while (resultSet.next()) {
                                    ids.add(resultSet.getString("id"));
                                    ids.add(resultSet.getString("username"));
                                    ids.add(resultSet.getString("examination_subjects"));
                                    ids.add(resultSet.getString("examination_fee"));
                                    ids.add(resultSet.getString("payment_method"));
                                }
                                String[] s;
                                for (int i = 1; i < datas.length; i++) {
                                    s = datas[i].split("=");
                                    if (s.length != 2 || s[0].trim().length() == 0 || s[1].trim().length() == 0)
                                        continue;
                                    switch (s[0].trim()) {
                                        case "用户名":
                                            ids.set(1, s[1].trim());
                                            break;
                                        case "考试科目":
                                            ids.set(2, s[1].trim());
                                            break;
                                        case "考试费用":
                                            ids.set(3, s[1].trim());
                                            break;
                                        case "支付类型":
                                            ids.set(4, s[1].trim());
                                            break;
                                        default:
                                            resultSet.close();
                                            preparedStatement.close();
                                            connection.close();
                                            resp.getWriter().write("数据异常!请重新输入!");
                                            return;
                                    }
                                }
                                preparedStatement = connection.prepareStatement("update roll set username = ?,examination_subjects = ?,examination_fee = ?,payment_method = ? where id = ?");
                                for (int i = 1; i < 6; i++) preparedStatement.setString(i, ids.get(i % 5));
                                int result = preparedStatement.executeUpdate();
                                if (result > 0) resp.getWriter().write("修改成功!");
                                else resp.getWriter().write("修改异常!请联系管理员!");
                            }
                            resultSet.close();
                        }
                        break;
                }
                preparedStatement = connection.prepareStatement("select * from user where username = ?");
                Cookie[] cookies = req.getCookies();
                String userName = "";
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("username")) {
                        userName = cookie.getValue();
                    }
                }
                preparedStatement.setString(1, userName);
                index(req, preparedStatement);
                preparedStatement.close();
            }
            connection.close();
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
