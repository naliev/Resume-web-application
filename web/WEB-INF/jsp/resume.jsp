<%@ page import="com.basejava.webapp.model.Resume" %>
<%@ page import="java.util.List" %>
<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"></jsp:include>
<table>
    <tr>
        <th> Name</th>
        <th> Email</th>
    </tr>
    <c:forEach var="resume" items="${resumes}">
        <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume"/>
        <tr>
            <td><a href="resume?uuid =${resume.uuid}">${resume.fullName}</a></td>
            <td>${resume.getContact(ContactType.EMAIL)}</td>
        </tr>
    </c:forEach>
</table>
<jsp:include page="fragments/footer.jsp"></jsp:include>
</body>
</html>
