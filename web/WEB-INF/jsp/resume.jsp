<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/resume-list.css">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
    <script src="scripts/myScript.js"></script>
    <title>Список резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<div class="table-wrapper">
    <div class="add-resume">
        <a class="no-underline-anchor" href="resume?action=add">
            <img src="img/light/add-person.svg" alt="">
        </a>
        <a class="text-anchor" href="resume?action=add">
            <p class="add-resume-title">Добавить резюме</p>
        </a>
    </div>
    <div class="resumes-list">
        <table>
            <tbody><tr class="t-header">
                <th class="name-column">Имя</th>
                <th class="info-column">Контакты</th>
                <th class="img-column">Редактировать</th>
                <th class="img-column">Удалить</th>
            </tr>

            <jsp:useBean id="resumes" scope="request" type="java.util.List"/>
            <c:forEach var="resume" items="${resumes}">
                <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume"/>
                <tr class="t-body">
                    <td class="name-column">
                        <a class="contact-link" href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                    <td class="info-column">
                        <a class="contact-link" href="mailto:${resume.getContact(ContactType.EMAIL)}">
                                ${resume.getContact(ContactType.EMAIL)}
                        </a></td>
                    <td class="img-column"><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png" alt="edit"></a></td>
                    <td class="img-column"><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete.png" alt="edit"></a></td>
                </tr>
        </c:forEach>
    </table>
        </div>
    </div>
</div>
</body>
</html>
