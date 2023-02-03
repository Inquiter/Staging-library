package example.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SelectController extends HttpServlet {
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
            } else {
                String s = "";
                PreparedStatement preparedStatement;
                if (data.length() == 0) {
                    switch (table) {
                        case "用户信息":
                            s = selectUser(connection.prepareStatement("select * from user"), new StringBuilder("<p><h6>id&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;用户名&emsp;&emsp;&emsp;&emsp;&emsp;密码&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;生日&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;身份证号&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;账户类型</h6></p>"));
                            break;
                        case "考试信息":
                            s = selectExamination(connection.prepareStatement("select * from examination"), new StringBuilder("<p><h6>id&emsp;&emsp;&emsp;&emsp;考试科目&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;考试时间&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;考试地点&emsp;&emsp;&emsp;&emsp;&emsp;考试简介&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;考试信息&emsp;&emsp;&emsp;&emsp;&emsp;考试要求&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;考试内容</h6></p>"));
                            break;
                        case "报名信息":
                            s = selectRoll(connection.prepareStatement("select * from roll"), new StringBuilder("<p><h6>id&emsp;&emsp;&emsp;&emsp;&emsp;用户名&emsp;&emsp;&emsp;&emsp;&emsp;身份证号&emsp;&emsp;&emsp;&emsp;&emsp;考试科目&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;考试时间&emsp;&emsp;&emsp;&emsp;&emsp;考试地点&emsp;&emsp;&emsp;&emsp;考试费用&emsp;&emsp;&emsp;&emsp;&emsp;支付类型&emsp;&emsp;&emsp;&emsp;&emsp;支付状态</h6></p>"));
                            break;
                    }
                } else {
                    switch (table) {
                        case "用户信息":
                            preparedStatement = connection.prepareStatement("select * from user");
                            ResultSet resultSet = preparedStatement.executeQuery();
                            boolean f = false;
                            while (resultSet.next()) {
                                if (resultSet.getString("id").equals(data)) {
                                    f = true;
                                    break;
                                }
                            }
                            if (f) {
                                preparedStatement = connection.prepareStatement("select * from user where id = ?");
                                preparedStatement.setString(1, data);
                                s = selectUser(preparedStatement, new StringBuilder("<p><h6>id&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;用户名&emsp;&emsp;&emsp;&emsp;&emsp;密码&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;生日&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;身份证号&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;账户类型</h6></p>"));
                            } else resp.getWriter().write("id不存在!");
                            break;
                        case "考试信息":
                            preparedStatement = connection.prepareStatement("select * from examination");
                            resultSet = preparedStatement.executeQuery();
                            f = false;
                            while (resultSet.next()) {
                                if (resultSet.getString("id").equals(data)) {
                                    f = true;
                                    break;
                                }
                            }
                            if (f) {
                                preparedStatement = connection.prepareStatement("select * from examination where id = ?");
                                preparedStatement.setString(1, data);
                                s = selectExamination(preparedStatement, new StringBuilder("<p><h6>id&emsp;&emsp;&emsp;&emsp;考试科目&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;考试时间&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;考试地点&emsp;&emsp;&emsp;&emsp;&emsp;考试简介&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;考试信息&emsp;&emsp;&emsp;&emsp;&emsp;考试要求&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;考试内容</h6></p>"));
                            } else resp.getWriter().write("id不存在!");
                            break;
                        case "报名信息":
                            preparedStatement = connection.prepareStatement("select * from roll");
                            resultSet = preparedStatement.executeQuery();
                            f = false;
                            while (resultSet.next()) {
                                if (resultSet.getString("id").equals(data)) {
                                    f = true;
                                    break;
                                }
                            }
                            if (f) {
                                preparedStatement = connection.prepareStatement("select * from roll where id = ?");
                                preparedStatement.setString(1, data);
                                s = selectRoll(preparedStatement, new StringBuilder("<p><h6>id&emsp;&emsp;&emsp;&emsp;&emsp;用户名&emsp;&emsp;&emsp;&emsp;&emsp;身份证号&emsp;&emsp;&emsp;&emsp;&emsp;考试科目&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;考试时间&emsp;&emsp;&emsp;&emsp;&emsp;考试地点&emsp;&emsp;&emsp;&emsp;考试费用&emsp;&emsp;&emsp;&emsp;&emsp;支付类型&emsp;&emsp;&emsp;&emsp;&emsp;支付状态</h6></p>"));
                            } else resp.getWriter().write("id不存在!");
                            break;
                    }
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
                resp.getWriter().write(s);
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

    private String selectUser(PreparedStatement preparedStatement, StringBuilder s) {
        try {
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                s.append("<div>").append(result.getString("id")).append("&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;");
                s.append(result.getString("username")).append("&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;");
                s.append(result.getString("password")).append("&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;");
                s.append(result.getString("birthday")).append("&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;");
                s.append(result.getString("idcard")).append("&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;");
                s.append(result.getString("account_type").equals("1") ? "管理员" : "普通用户").append("</div>");
            }
            result.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s.toString();
    }

    private String selectExamination(PreparedStatement preparedStatement, StringBuilder s) {
        try {
            ResultSet result = preparedStatement.executeQuery();
            String id, ss;
            while (result.next()) {
                id = result.getString("id");
                s.append("<div>").append(id).append("&emsp;&emsp;&emsp;");
                s.append(result.getString("examination_subjects")).append("&emsp;&emsp;&emsp;");
                s.append(result.getString("examination_time")).append("&emsp;&emsp;&emsp;");
                s.append(result.getString("examination_place")).append("&emsp;&emsp;&emsp;");
                ss = result.getString("examination_introduction");
                s.append("<a href='/content?id=").append(id).append("&title=考试简介' target='_blank'>").append(ss, 0, Math.min(ss.length(), 7)).append("</a>").append("&emsp;&emsp;&emsp;");
                ss = result.getString("examination_information");
                s.append("<a href='/content?id=").append(id).append("&title=考试信息' target='_blank'>").append(ss, 0, Math.min(ss.length(), 7)).append("</a>").append("&emsp;&emsp;&emsp;");
                ss = result.getString("examination_requirements");
                s.append("<a href='/content?id=").append(id).append("&title=考试要求' target='_blank'>").append(ss, 0, Math.min(ss.length(), 7)).append("</a>").append("&emsp;&emsp;&emsp;");
                ss = result.getString("examination_content");
                s.append("<a href='/content?id=").append(id).append("&title=考试内容' target='_blank'>").append(ss, 0, Math.min(ss.length(), 7)).append("</a></div>");
            }
            result.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s.toString();
    }

    private String selectRoll(PreparedStatement preparedStatement, StringBuilder s) {
        try {
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                s.append("<div>").append(result.getString("id")).append("&emsp;&emsp;&emsp;&emsp;&emsp;");
                s.append(result.getString("username")).append("&emsp;&emsp;&emsp;&emsp;");
                s.append(result.getString("idcard")).append("&emsp;");
                s.append(result.getString("examination_subjects")).append("&emsp;&emsp;");
                s.append(result.getString("examination_time")).append("&emsp;&emsp;");
                s.append(result.getString("examination_place")).append("&emsp;&emsp;&emsp;&emsp;");
                s.append(result.getString("examination_fee")).append("&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;");
                s.append(result.getString("payment_method")).append("&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;");
                s.append(result.getString("payment_status")).append("</div>");
            }
            result.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s.toString();
    }
}
