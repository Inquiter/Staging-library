<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html class="no-js" lang="en" dir="ltr">
    <head>
        <meta charset="utf-8">
        <title>
            <%=(String) request.getAttribute("title")%>
        </title>
    </head>
    <body>
        <div>
            <%=((String) request.getAttribute("value")).replaceAll(" ", "<br>")%>
        </div>
    </body>
</html>