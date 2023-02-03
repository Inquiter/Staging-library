package example.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-Type", "text/html;charset=UTF-8");
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        if (userName == null || userName.trim().length() == 0)
            resp.getWriter().write("<script>alert('请输入用户名!');history.back();</script>");
        else if (password == null || password.trim().length() == 0)
            resp.getWriter().write("<script>alert('请输入密码!');history.back();</script>");
        else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://mysql.sqlpub.com/database1", "database1", "c223ef85b15dbe08");
                String sql = "select password from user where username = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, userName);
                ResultSet result = preparedStatement.executeQuery();
                if (result.next()) {
                    if (result.getString("password").equals(password)) {
                        preparedStatement = connection.prepareStatement("select * from user where username = ?");
                        preparedStatement.setString(1, userName);
                        index1(req, preparedStatement);
                        index2(req, connection.prepareStatement("select * from examination"));
                        Cookie[] cookies = req.getCookies();
                        for (Cookie cookie : cookies) {
                            if (cookie.getName().equals("username")) {
                                cookie.setValue(userName);
                            }
                        }
                        resp.addCookie(new Cookie("username", userName));
                        req.getRequestDispatcher("index.jsp").forward(req, resp);
                    } else resp.getWriter().write("<script>alert('用户名或密码错误!');history.back();</script>");
                } else {
                    req.setAttribute("message", "你还未注册!");
                    req.getRequestDispatcher("register.jsp").forward(req, resp);
                }
                result.close();
                preparedStatement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    private void index2(HttpServletRequest req, PreparedStatement preparedStatement) {
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
}