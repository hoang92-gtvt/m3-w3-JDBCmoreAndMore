<%--
  Created by IntelliJ IDEA.
  User: YEN
  Date: 5/30/2021
  Time: 9:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Form Edit</title>
</head>
<body>
<h1>Form sửa sản phẩm</h1>
<a href="/book?action=home">Quay về home</a>
<br>
<form method="post">
    <table border="1px solid">
        <tr>
            <td>Tên sách</td>
            <td>Giá sách</td>
            <td>Loại sách</td>

        </tr>

        <tr>
            <td><input type="text" name="name" value ="${old.name}"/></td>
            <td><input type="text" name="price" value ="${old.price}"/></td>

            <td><select name="categories" multiple>
                <c:forEach items="${categories}" var="category">
                    <option value="${category.id}">${category.name}</option>
                </c:forEach>
            </select>
            </td>

        </tr>
    </table>
    <button type="submit">Update</button>
</form>

</body>
</html>
