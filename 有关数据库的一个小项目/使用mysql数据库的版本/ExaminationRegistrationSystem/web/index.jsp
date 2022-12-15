<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<html class="no-js" lang="en" dir="ltr">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>网上考试报名系统</title>
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
                                <input type="submit" class="button" value="<%=userName.length() == 0?"登录":userName%>"/>
                            </form>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="grid-x grid-padding-x">
                <div class="large-12 cell">
                    <div class="large-12 cell grid-x">
                        <div class="large-6 medium-6 grid-y">
                            <h4>考试信息</h4>
                            <div class="cell">
                                <%
                                    List<List<String>> list1 =
                                            (
                                                    List
                                                            <
                                                                    List
                                                                            <
                                                                                    String
                                                                                    >
                                                                    >
                                                    )
                                                    request
                                                            .
                                                                    getAttribute
                                                                            (
                                                                                    "list1"
                                                                            );
                                %>
                                <p>
                                    <%=list1
                                            .
                                                    get
                                                            (
                                                                    0
                                                            )
                                            .
                                                    get
                                                            (
                                                                    0
                                                            )%>
                                </p>
                                <p>
                                    <%=list1
                                            .
                                                    get
                                                            (
                                                                    0
                                                            )
                                            .
                                                    get
                                                            (
                                                                    2
                                                            )%>
                                </p>
                            </div>
                            <div class="cell">
                                <p>
                                    <%=list1
                                            .
                                                    get
                                                            (
                                                                    1
                                                            )
                                            .
                                                    get
                                                            (
                                                                    0
                                                            )%>
                                </p>
                                <p>
                                    <%=list1
                                            .
                                                    get
                                                            (
                                                                    1
                                                            )
                                            .
                                                    get
                                                            (
                                                                    2
                                                            )%>
                                </p>
                            </div>
                        </div>
                        <div class="large-6 medium-6 cell grid-y grid-padding-y">
                            <h4>考试列表</h4>
                            <div class="cell">
                                <p>
                                    <%=list1
                                            .
                                                    get
                                                            (
                                                                    0
                                                            )
                                            .
                                                    get
                                                            (
                                                                    0
                                                            )%>
                                </p>
                                <p>
                                    <%
                                        String
                                                [
                                                ]
                                                ss
                                                =
                                                list1
                                                        .
                                                                get
                                                                        (
                                                                                0
                                                                        )
                                                        .
                                                                get
                                                                        (
                                                                                1
                                                                        )
                                                        .
                                                                split
                                                                        (
                                                                                " "
                                                                        );
                                        for
                                        (
                                                String
                                                        s
                                                :
                                                ss
                                        ) {
                                    %>
                                    <%=s%><br>
                                    <%
                                        }
                                    %>
                                </p>
                                <p>
                                    考试时间:<%=list1
                                        .
                                                get
                                                        (
                                                                0
                                                        )
                                        .
                                                get
                                                        (
                                                                5
                                                        )%>
                                </p>
                                <p>
                                    考试地点:<%=list1
                                        .
                                                get
                                                        (
                                                                0
                                                        )
                                        .
                                                get
                                                        (
                                                                6
                                                        )%>
                                </p>
                                <form action="/roll?examination_subjects=<%=list1.get(0).get(0)%>" method="post" id="enroll">
                                    <input type="submit" class="button" value="报名"/>
                                </form>
                            </div>
                            <div class="cell">
                                <p>
                                    <%=list1
                                            .
                                                    get
                                                            (
                                                                    1
                                                            )
                                            .
                                                    get
                                                            (
                                                                    0
                                                            )%>
                                </p>
                                <p>
                                    <%
                                        ss
                                                =
                                                list1
                                                        .
                                                                get
                                                                        (
                                                                                1
                                                                        )
                                                        .
                                                                get
                                                                        (
                                                                                1
                                                                        )
                                                        .
                                                                split
                                                                        (
                                                                                " "
                                                                        )
                                        ;
                                        for
                                        (
                                                String
                                                        s
                                                :
                                                ss
                                        ) {
                                    %>
                                    <%=s%><br>
                                    <%
                                        }
                                    %>
                                </p>
                                <p>
                                    考试时间:<%=list1
                                        .
                                                get
                                                        (
                                                                1
                                                        )
                                        .
                                                get
                                                        (
                                                                5
                                                        )%>
                                </p>
                                <p>
                                    考试地点:<%=list1
                                        .
                                                get
                                                        (
                                                                1
                                                        )
                                        .
                                                get
                                                        (
                                                                6
                                                        )%>
                                </p>
                                <form action="/roll?examination_subjects=<%=list1.get(1).get(0)%>" method="post">
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
