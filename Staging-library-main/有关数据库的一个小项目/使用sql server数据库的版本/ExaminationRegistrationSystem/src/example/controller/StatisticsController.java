package example.controller;

import example.uTils.ConnectionSqlServer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StatisticsController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-Type", "text/html;charset=UTF-8");
        try {
            Connection connection = ConnectionSqlServer.getConnectionSqlServer().getSqlServerConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where username = ?");
            Cookie[] cookies = req.getCookies();
            String userName = "";
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    userName = cookie.getValue();
                }
            }
            preparedStatement.setString(1, userName);
            index(req, preparedStatement);
            preparedStatement = connection.prepareStatement("select * from roll order by registration_time");
            index1(req, preparedStatement);
            req.getRequestDispatcher("statistics.jsp").forward(req, resp);
        } catch (Exception e) {
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

    private void index1(HttpServletRequest req, PreparedStatement preparedStatement) {
        try {
            ResultSet result = preparedStatement.executeQuery();
            List<List<List<String>>> list = new ArrayList<>();
            List<Long> times = new ArrayList<>();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar ca = Calendar.getInstance();
            String s;
            Long time;
            Date date;
            List<String> li;
            while (result.next()) {
                try {
                    s = result.getString("registration_time");
                    date = simpleDateFormat.parse(s);
                    time = date.getTime() / 86400000;
                    if (!times.contains(time)) {
                        times.add(time);
                        list.add(new ArrayList<>());
                        ca.setTime(date);
                    }
                    list.get(times.indexOf(time)).add(new ArrayList<>());
                    li = list.get(list.size() - 1).get(list.get(list.size() - 1).size() - 1);
                    li.add(result.getString("username"));
                    li.add(result.getString("idcard"));
                    li.add(s);
                    li.add(result.getString("examination_subjects"));
                    li.add(result.getString("examination_time"));
                    li.add(result.getString("examination_place"));
                    li.add(result.getString("examination_fee"));
                    li.add(result.getString("payment_method"));
                    li.add(result.getString("payment_status"));
                    li.add(ca.get(Calendar.YEAR) + "年".concat(ca.get(Calendar.MONTH) + 1 + "月").concat(ca.get(Calendar.DAY_OF_MONTH) + "日"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            req.setAttribute("list1", list);
            result.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
