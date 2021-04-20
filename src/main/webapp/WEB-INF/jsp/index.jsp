<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello</title>
</head>
<body>
Hello,${name}, time is [${now}]

<h3>用户列表展示</h3>
<table border="1">
    <tr>
        <th>红包总金额</th>
        <th>红包总个数</th>
        <th>当前红包库存</th>
    </tr>
    <tr>
        <td>${redpocket.amount}</td>
        <td>${redpocket.total}</td>
        <td>${redpocket.stock}</td>
    </tr>
</table>
</body>
</html>