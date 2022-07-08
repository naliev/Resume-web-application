<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume" scope="request"/>
    <title>Resume ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<form action="resume" method="post">
    <input type="hidden" name="uuid" value="${resume.uuid}">
    <dl>
        <dt>Имя:</dt>
        <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
    </dl>
    <h3>Contacts</h3>
    <c:forEach var="contactType" items="<%=ContactType.values()%>">

        <dl>
            <dt>${contactType.title}</dt>
            <dd><input type="text" name="${contactType.name()}" size=30 value="${resume.getContact(contactType)}"></dd>
        </dl>
    </c:forEach>
    <button>Send</button>
</form>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
