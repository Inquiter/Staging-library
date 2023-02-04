<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html class="no-js" lang="en" dir="ltr">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>管理</title>
        <link rel="stylesheet" href="css/foundation.css">
        <link rel="stylesheet" href="css/app.css">
        <style>
            ::-webkit-input-placeholder {
                color: #505050;
            }

            :-moz-placeholder {
                color: #505050;
            }

            ::-moz-placeholder {
                color: #505050;
            }

            :-ms-input-placeholder {
                color: #505050;
            }
        </style>
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
                        <%
                            List<String> list = (List<String>) request.getAttribute("list");
                            if (list != null && list.get(2).equals("1")) {
                        %>
                        <li class="menu-text">
                            <form action="${pageContext.request.contextPath}/admin" method="post">
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
                            <form action="/statistics" method="post">
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
                                <input type="submit" class="button" value='<%=userName.length() == 0?"登录":userName%>'/>
                            </form>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="grid-x grid-padding-x">
                <div class="large-6 cell">
                    <label for="select"></label>
                    <select id="select">
                        <option>请选择</option>
                        <option>用户信息</option>
                        <option>考试信息</option>
                        <option>报名信息</option>
                    </select>
                    <label for="select_input"></label><input type="text" id="select_input" placeholder="请输入id或者不输入"/>
                    <button id="select_button" class="button" onclick="select()">查询</button>
                </div>
                <div class="large-6 cell">
                    <label for="add"></label>
                    <select id="add">
                        <option>请选择</option>
                        <option>用户信息</option>
                        <option>考试信息</option>
                        <option>报名信息</option>
                    </select>
                    <label>
                        <input type="text" id="add_input" placeholder="请输入完整数据，以|隔开，id不用写入"/>
                    </label>
                    <button id="add_button" class="button" onclick="add()">添加</button>
                </div>
                <div class="large-6 cell">
                    <label for="update"></label>
                    <select id="update">
                        <option>请选择</option>
                        <option>用户信息</option>
                        <option>考试信息</option>
                        <option>报名信息</option>
                    </select>
                    <label>
                        <input type="text" id="update_input" placeholder="请以 \'id|列名=数据\' 的格式写入需要修改的数据,id不能修改"/>
                    </label>
                    <button id="update_button" class="button" onclick="update()">修改</button>
                </div>
                <div class="large-6 cell">
                    <label for="del"></label>
                    <select id="del">
                        <option>请选择</option>
                        <option>用户信息</option>
                        <option>考试信息</option>
                        <option>报名信息</option>
                    </select>
                    <label>
                        <input type="text" id="del_input" placeholder="请输入id"/>
                    </label>
                    <button id="del_button" class="button" onclick="del()">删除</button>
                </div>
                <div class="large-12 cell" id="output"></div>
            </div>
        </div>
        <script src="js/vendor/jquery.js"></script>
        <script src="js/vendor/what-input.js"></script>
        <script src="js/vendor/foundation.js"></script>
        <script src="js/app.js"></script>
        <script>
            function select() {
                $.ajax({
                    url: '/select',
                    type: 'post',
                    dataType: 'html',
                    data: {"table": $("#select option:selected").val(), "data": $("#select_input").val()},
                    success: function (result) {
                        if (result === "请选择表!") alert("请选择表!");
                        else $("#output").html(result);
                    },
                    error: function () {
                        alert("连接失败!请联系管理员!");
                    }
                })
            }

            function add() {
                $.ajax({
                    url: '/add',
                    type: 'post',
                    dataType: 'html',
                    data: {"table": $("#add option:selected").val(), "data": $("#add_input").val()},
                    success: function (result) {
                        alert(result);
                        select();
                    },
                    error: function () {
                        alert("连接失败!请联系管理员!");
                    }
                })
            }

            function update() {
                $.ajax({
                    url: '/update',
                    type: 'post',
                    dataType: 'html',
                    data: {"table": $("#update option:selected").val(), "data": $("#update_input").val()},
                    success: function (result) {
                        alert(result);
                        select();
                    },
                    error: function () {
                        alert("连接失败!请联系管理员!");
                    }
                })
            }

            function del() {
                $.ajax({
                    url: '/del',
                    type: 'post',
                    dataType: 'html',
                    data: {"table": $("#del option:selected").val(), "data": $("#del_input").val()},
                    success: function (result) {
                        alert(result);
                        select();
                    },
                    error: function () {
                        alert("连接失败!请联系管理员!");
                    }
                })
            }
        </script>
    </body>
</html>