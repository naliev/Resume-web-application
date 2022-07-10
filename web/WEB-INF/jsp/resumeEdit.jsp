<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page import="com.basejava.webapp.model.SectionType" %>
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
        <dd><input type="text" name="fullName" required size=50 value="${resume.fullName}"></dd>
    </dl>
    <div class="tabs">
        <input type="radio" name="inset" value="" id="tab_1" checked>
        <label for="tab_1">Contacts</label>

        <input type="radio" name="inset" value="" id="tab_2">
        <label for="tab_2">Sections</label>

        <div id="txt_1">
            <c:forEach var="contactType" items="<%=ContactType.values()%>">
                <dl>
                    <dt>${contactType.title}</dt>
                    <dd><input type="text" name="${contactType.name()}" size=30 value="${resume.getContact(contactType)}"></dd>
                </dl>
            </c:forEach>
        </div>
        <div id="txt_2">
            <c:forEach var="sectionType" items="<%=SectionType.values()%>">
                <dl>
                    <dt>${sectionType.title}</dt>
                    <dd>
                        <c:choose>
                            <c:when test="${sectionType == SectionType.PERSONAL or sectionType == SectionType.OBJECTIVE}">
                                <input type="text" name="${sectionType.name()}" size=30 value="${resume.getSection(sectionType)}">
                            </c:when>
                            <c:when test="${sectionType == SectionType.ACHIEVEMENT or sectionType == SectionType.QUALIFICATIONS}">
                                <textarea name="${sectionType.name()}" cols="30">${resume.getSection(sectionType)}</textarea>
                            </c:when>
                        </c:choose>
                    </dd>
                </dl>
            </c:forEach>
        </div>
    </div>
    <hr>
    <button type="submit" name="submit">Сохранить</button>
    <button type="reset" onclick="history.back()">Отменить</button>
</form>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
