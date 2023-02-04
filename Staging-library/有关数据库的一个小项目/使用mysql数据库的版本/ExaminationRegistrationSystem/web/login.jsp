<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html class="no-js" lang="en" dir="ltr">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>登录</title>
        <link rel="stylesheet" href="css/foundation.css">
        <link rel="stylesheet" href="css/app.css">
    </head>
    <body>
        <%
            String message = (String) request.getAttribute("message");
            if (message != null && message.trim().length() > 0) {
        %>
        <script type="text/javascript">alert("<%=message%>");</script>
        <%
            }
        %>
        <div class="grid-container">
            <div class="top-bar">
                <div class="top-bar-left ">
                    <ul class="menu" data-dropdown-menu>
                        <li class="menu-text">
                            <form action="login.jsp" method="post">
                                <input type="submit" class="button" value="考试报名系统"/>
                            </form>
                        </li>
                        <li class="menu-text">
                            <form action="login.jsp" method="post">
                                <input type="submit" class="button" value="我的报名"/>
                            </form>
                        </li>
                    </ul>
                </div>
                <div class="top-bar-right">
                    <ul class="menu" data-dropdown-menu>
                        <li>
                            <form action="register.jsp" method="post">
                                <input type="submit" class="button" value="注册"/>
                            </form>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="grid-x grid-padding-x">
                <div class="large-12 cell">
                    <form action="login" method="post">
                        <%
                            String userName = (String) request.getAttribute("username");
                            String password = (String) request.getAttribute("password");
                        %>
                        <label>用户名</label>
                        <input type="text" name="username" value="<%if(userName!=null&&userName.trim().length()>0)%><%=userName%>"/>
                        <label>密码</label>
                        <input type="password" name="password" value="<%if(password!=null&&password.trim().length()>0)%><%=password%>"/>
                        <input type="submit" class="button" value="登录"/>
                    </form>
                </div>
            </div>
        </div>
        <script src="js/vendor/jquery.js"></script>
        <script src="js/vendor/what-input.js"></script>
        <script src="js/vendor/foundation.js"></script>
        <script src="js/app.js"></script>
    </body>
</html>