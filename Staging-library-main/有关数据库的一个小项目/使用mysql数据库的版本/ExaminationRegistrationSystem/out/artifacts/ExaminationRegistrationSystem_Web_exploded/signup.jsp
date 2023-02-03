<%@ page import="java.util.List" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<html class="no-js" lang="en" dir="ltr">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>报名信息</title>
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
                <div class="large-12 cell">
                    <%
                        List<String> list1 = (List<String>) request.getAttribute("list1");
                    %>
                    <div class="large-12 cell grid-x">
                        <h4>考试信息</h4>
                        <div class="cell">
                            <h5>考试科目</h5>
                            <p>
                                <%=list1.get(0)%>
                            </p>
                        </div>
                        <div class="cell">
                            <h5>考试时间</h5>
                            <p>
                                <%=list1.get(5)%>
                            </p>
                        </div>
                        <div class="cell">
                            <h5>考试地点</h5>
                            <p>
                                <%=list1.get(6)%>
                            </p>
                        </div>
                        <div class="cell">
                            <h5>考试简介</h5>
                            <p>
                                <%=list1.get(3)%>
                            </p>
                        </div>
                        <div class="cell">
                            <h5>考试要求</h5>
                            <p>
                                <%=list1.get(2)%>
                            </p>
                        </div>
                        <div class="cell">
                            <h5>考试内容</h5>
                            <p>
                                <%=list1.get(4)%>
                            </p>
                        </div>
                        <div class="cell">
                            <div class="large-12 cell">
                                <form action="/signup" method="post">
                                    <h5>姓名</h5>
                                    <input type="text" name="username" value="<%=userName%>"/>
                                    <h5>身份证号</h5>
                                    <input type="text" name="idcard" value="<%if(list!=null){%><%=list.get(1)%><%}%>"/>
                                    <input type="hidden" name="examination_subjects" value="<%=list1.get(0)%>">
                                    <input type="hidden" class="button" value='<%=userName%>' name="loginUserName"/>
                                    <h5>考试费用:100元</h5>
                                    <input type="hidden" name="examinationFee" value="100元"/>
                                    <h5>支付方式</h5>
                                    <select id="pay" name="pay">
                                        <option selected>支付宝</option>
                                        <option>微信</option>
                                        <option>中国工商银行</option>
                                    </select>
                                    <input type="button" class="button" onclick="alert('支付成功!')" value="支付"/>
                                    <input type="submit" class="button" value="报名"/>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="js/vendor/jquery.js"></script>
        <script src="js/vendor/what-input.js"></script>
        <script src="js/vendor/foundation.js"></script>
        <script src="js/app.js"></script>
    </body>
</html>