<%--
  Created by IntelliJ IDEA.
  User: YEN
  Date: 5/30/2021
  Time: 9:29 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Danh sách Sách</title>
</head>
<body>
    <h1> Danh sách sản phẩm </h1>
    <table border="1px solid" >
        <tr>
            <td>Tên Sách</td>
            <td>Giá Bán</td>

            <td>Tên loại sách</td>
        </tr>
        <c:forEach items="${bookList}" var="book">
            <tr>
                <td>${book.name}</td>
                <td>${book.price}</td>
                <td><c:forEach items="${book.categories}" var="category">
                    <span>${category.name}</span>
                </c:forEach>

                </td>

            </tr>

        </c:forEach>

    </table>
    <a href="/book?action=creat">Thêm mới sách</a>

</body>
</html>
