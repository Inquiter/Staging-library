package example.controller;

import example.uTils.ConnectionSqlServer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignupController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-Type", "text/html;charset=UTF-8");
        String userName = req.getParameter("username");
        String idcard = req.getParameter("idcard");
        String loginUserName = req.getParameter("loginUserName");
        String examinationSubjects = req.getParameter("examination_subjects");
        String examinationFee = req.getParameter("examinationFee");
        String pay = req.getParameter("pay");
        if (userName == null || userName.trim().length() == 0)
            resp.getWriter().write("<script>alert('请输入用户名!');history.back();</script>");
        else if (idcard == null || idcard.trim().length() != 18) {
            if (idcard != null && idcard.trim().length() > 0)
                resp.getWriter().write("<script>alert('身份证号应为18位!');history.back();</script>");
            else resp.getWriter().write("<script>alert('请输入身份证号!');history.back();</script>");
        } else if (!userName.equals(loginUserName))
            resp.getWriter().write("<script>alert('你只能给自已报名!');history.back();</script>");
        else {
            try {
                Connection connection = ConnectionSqlServer.getConnectionSqlServer().getSqlServerConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("select * from users where username = ?");
                preparedStatement.setString(1, userName);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    resp.getWriter().write("<script>alert('用户不存在!');history.back();</script>");
                } else if (!resultSet.getString("idcard").equals(idcard)) {
                    resp.getWriter().write("<script>alert('身份证号错误!请重新输入!');history.back();</script>");
                } else {
                    preparedStatement = connection.prepareStatement("select * from examination where examination_subjects = ?");
                    preparedStatement.setString(1, examinationSubjects);
                    resultSet = preparedStatement.executeQuery();
                    String examinationTime = null, examinationPlace = null, paymentStatus = "已支付";
                    while (resultSet.next()) {
                        examinationTime = resultSet.getString("examination_time");
                        examinationPlace = resultSet.getString("examination_place");
                    }
                    preparedStatement = connection.prepareStatement("insert into roll(username, idcard, registration_time, examination_subjects, examination_time, examination_place, examination_fee, payment_method, payment_status) values(?,?,?,?,?,?,?,?,?)");
                    preparedStatement.setString(1, userName);
                    preparedStatement.setString(2, idcard);
                    preparedStatement.setString(3, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    preparedStatement.setString(4, examinationSubjects);
                    preparedStatement.setString(5, examinationTime);
                    preparedStatement.setString(6, examinationPlace);
                    preparedStatement.setString(7, examinationFee);
                    preparedStatement.setString(8, pay);
                    preparedStatement.setString(9, paymentStatus);
                    preparedStatement.executeUpdate();
                    preparedStatement = connection.prepareStatement("select * from roll where username = ?");
                    preparedStatement.setString(1, userName);
                    index1(req, preparedStatement);
                    index2(req, connection.prepareStatement("select * from examination"));
                    preparedStatement = connection.prepareStatement("select * from users where username = ?");
                    preparedStatement.setString(1, userName);
                    index(req, preparedStatement);
                    req.getRequestDispatcher("enroll.jsp").forward(req, resp);
                }
                resultSet.close();
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    private void index1(HttpServletRequest req, PreparedStatement preparedStatement) {
        try {
            ResultSet result = preparedStatement.executeQuery();
            List<List<String>> list = new ArrayList<>();
            while (result.next()) {
                list.add(new ArrayList<>());
                list.get(list.size() - 1).add(result.getString("username"));
                list.get(list.size() - 1).add(result.getString("examination_subjects"));
                list.get(list.size() - 1).add(result.getString("examination_fee"));
                list.get(list.size() - 1).add(result.getString("payment_method"));
            }
            req.setAttribute("list1", list);
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
            req.setAttribute("list2", list);
            result.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
