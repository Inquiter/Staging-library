package example.controller;

import example.uTils.ConnectionSqlServer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegisterController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-Type", "text/html;charset=UTF-8");
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        String birthday = req.getParameter("birthday");
        String idcard = req.getParameter("idcard");
        if (userName == null || userName.trim().length() == 0)
            resp.getWriter().write("<script>alert('请输入用户名!');history.back();</script>");
        else if (password == null || password.trim().length() == 0)
            resp.getWriter().write("<script>alert('请输入密码!');history.back();</script>");
        else if (birthday == null || birthday.trim().length() == 0)
            resp.getWriter().write("<script>alert('请输入生日!');history.back();</script>");
        else if (idcard == null || idcard.trim().length() == 0)
            resp.getWriter().write("<script>alert('请输入身份证号!');history.back();</script>");
        else if (idcard.trim().length() != 18)
            resp.getWriter().write("<script>alert('身份证号错误!请重新输入!');history.back();</script>");
        else {
            try {
                Connection connection = ConnectionSqlServer.getConnectionSqlServer().getSqlServerConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("select * from users where username = ?");
                preparedStatement.setString(1, userName);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    req.setAttribute("message", "你已经注册过了!");
                    req.getRequestDispatcher("login.jsp").forward(req, resp);
                } else {
                    resultSet.close();
                    preparedStatement.close();
                    preparedStatement = connection.prepareStatement("select idcard from users");
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next())
                        if (idcard.equals(resultSet.getString("idcard").trim())) {
                            resp.getWriter().write("<script>alert('身份证号错误!请重新输入!');history.back();</script>");
                            resultSet.close();
                            preparedStatement.close();
                            return;
                        }
                    preparedStatement = connection.prepareStatement("insert into users(username, password, birthday, idcard, account_type) values(?,?,?,?,?)");
                    preparedStatement.setString(1, userName);
                    preparedStatement.setString(2, password);
                    preparedStatement.setString(3, birthday);
                    preparedStatement.setString(4, idcard);
                    preparedStatement.setString(5, "0");
                    int result = preparedStatement.executeUpdate();
                    if (result > 0) {
                        req.setAttribute("message", "注册成功!");
                        req.setAttribute("username", userName);
                        req.setAttribute("password", password);
                        req.getRequestDispatcher("login.jsp").forward(req, resp);
                    } else resp.getWriter().write("<script>alert('注册异常!请联系管理员!');history.back();</script>");
                }
                resultSet.close();
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
