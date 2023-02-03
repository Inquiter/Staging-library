<%@ page import="java.util.List" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<html class="no-js" lang="en" dir="ltr">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Foundation for Sites</title>
        <link rel="stylesheet" href="css/foundation.css">
        <link rel="stylesheet" href="css/app.css">
    </head>
    <body>
        <div class="grid-container">
            <div class="top-bar">
                <div class="top-bar-left">
                    <ul class="menu" data-dropdown-menu>
                        <li class="menu-text">
                            <form action="/index" method="post">
                                <input type="submit" class="button" value="考试报名系统"/>
                            </form>
                        </li>
                        <li class="menu-text">
                            <form action="/enroll" method="post">
                                <input type="submit" class="button" value="我的报名"/>
                            </form>
                        </li>
                        <%
                            List<String> list = (List<String>) request.getAttribute("list");
                            if (list != null && list.get(2).equals("1")) {
                        %>
                        <li class="menu-text">
                            <form action="/admin" method="post">
                                <input type="submit" class="button" value="管理"/>
                            </form>
                        </li>
                        <%
                            }
                        %>
                    </ul>
                </div>
                <div class="top-bar-right">
                    <ul class="menu" data-dropdown-menu>
                        <li>
                            <%
                                Cookie[] cookies = request.getCookies();
                                String userName = "";
                                for (Cookie cookie : cookies) {
                                    if (cookie.getName().equals("username")) {
                                        userName = cookie.getValue();
                                        break;
                                    }
                                }
                            %>
                            <form action="login.jsp" method="post">
                                <input type="submit" class="button" value='<%=userName.length() == 0?"登录":userName%>'/>
                            </form>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="grid-x grid-padding-x">
                <%
                    List<List<String>> list1 = (List<List<String>>) request.getAttribute("list1");
                    List<List<String>> list2 = (List<List<String>>) request.getAttribute("list2");
                    if (list == null || list.size() == 0 || list1 == null || list1.size() == 0 || list2 == null || list2.size() == 0) {
                %>
                <div class="large-4 cell">无</div>
                <%
                } else {
                    for (List<String> li1 : list1) {
                %>
                <div class="large-4 cell">
                    <h5>报名信息</h5>
                    <div>
                        姓名：<%=list.get(0)%>
                    </div>
                    <div>
                        身份证号：<%=list.get(1)%>
                    </div>
                    <%
                        for (List<String> li2 : list2) {
                            if (li2.contains(li1.get(1))) {
                    %>
                    <div>
                        考试科目：<%=li2.get(0)%>
                    </div>
                    <div>
                        考试时间：<%=li2.get(5)%>
                    </div>
                    <div>
                        考试地点：<%=li2.get(6)%>
                    </div>
                    <%
                                break;
                            }
                        }
                    %>
                    <div>
                        支付费用：<%=li1.get(2)%>
                    </div>
                    <div>
                        支付方式：<%=li1.get(3)%>
                    </div>
                    <div>支付状态：已支付</div>
                </div>
                <%
                        }
                    }
                %>
            </div>
        </div>
        <script src="js/vendor/jquery.js"></script>
        <script src="js/vendor/what-input.js"></script>
        <script src="js/vendor/foundation.js"></script>
        <script src="js/app.js"></script>
    </body>
</html>