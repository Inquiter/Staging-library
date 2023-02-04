<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html class="no-js" lang="en" dir="ltr">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>报名信息统计</title>
        <link rel="stylesheet" href="css/foundation.css">
        <link rel="stylesheet" href="css/app.css">
    </head>
    <body>
        <div class="grid-container">
            <div class="top-bar">
                <div class="top-bar-left">
                    <ul class="menu" data-dropdown-menu>
                        <li class="menu-text">
                            <form action="${pageContext.request.contextPath}/index" method="post">
                                <input type="submit" class="button" value="考试报名系统"/>
                            </form>
                        </li>
                        <li class="menu-text">
                            <form action="${pageContext.request.contextPath}/enroll" method="post">
                                <input type="submit" class="button" value="我的报名"/>
                            </form>
                        </li>
                        <li class="menu-text">
                            <form action="${pageContext.request.contextPath}/admin" method="post">
                                <input type="submit" class="button" value="管理"/>
                            </form>
                        </li>
                    </ul>
                </div>
                <div class="top-bar-right">
                    <ul class="menu" data-dropdown-menu>
                        <li>
                            <form action="statistics" method="post">
                                <input type="submit" class="button" value="统计"/>
                            </form>
                        </li>
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
                                <input type="submit" class="button" value="<%=userName.length() == 0?"登录":userName%>"/>
                            </form>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="grid-x grid-padding-x">
                <%
                    List<List<List<String>>> list1 = (List<List<List<String>>>) request.getAttribute("list1");
                    for (List<List<String>> lists : list1) {

                        if (lists.size() > 0) {
                %>
                <div class="large-12 cell">
                    <h5>
                        <%=lists.get(0).get(9)%>
                    </h5>
                </div>
                <div class="large-1 cell"><strong>用户名</strong></div>
                <div class="large-2 cell"><strong>身份证号</strong></div>
                <div class="large-2 cell"><strong>报名时间</strong></div>
                <div class="large-1 cell"><strong>考试科目</strong></div>
                <div class="large-2 cell"><strong>考试时间</strong></div>
                <div class="large-1 cell"><strong>考试地点</strong></div>
                <div class="large-1 cell"><strong>考试费用</strong></div>
                <div class="large-1 cell"><strong>支付方式</strong></div>
                <div class="large-1 cell"><strong>支付状态</strong></div>
                <%
                    for (List<String> list : lists) {
                        if (list.size() > 0) {
                %>
                <div class="large-1 cell">
                    <%=list.get(0)%>
                </div>
                <div class="large-2 cell">
                    <%=list.get(1)%>
                </div>
                <div class="large-2 cell">
                    <%=list.get(2)%>
                </div>
                <div class="large-1 cell">
                    <%=list.get(3)%>
                </div>
                <div class="large-2 cell">
                    <%=list.get(4)%>
                </div>
                <div class="large-1 cell">
                    <%=list.get(5)%>
                </div>
                <div class="large-1 cell">
                    <%=list.get(6)%>
                </div>
                <div class="large-1 cell">
                    <%=list.get(7)%>
                </div>
                <div class="large-1 cell">
                    <%=list.get(8)%>
                </div>
                <%
                                }
                            }
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
