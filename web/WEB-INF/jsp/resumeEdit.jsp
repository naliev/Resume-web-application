<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page import="com.basejava.webapp.model.SectionType" %>
<%@ page import="com.basejava.webapp.model.OrganizationSection" %>
<%@ page import="com.basejava.webapp.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/resume-edit-view.css">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
    <script src="scripts/myScript.js"></script>
    <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume" scope="request"/>
    <title>Resume ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<div class="bloko-columns-wrapper">
<section>
<form action="resume" method="post">
    <input type="hidden" name="uuid" value="${resume.uuid}">
    <dl>
        <dt>Имя:</dt>
        <dd><input type="text" name="fullName" required size=50 value="${resume.fullName}"></dd>
    </dl>
    <div class="tabs">
        <input type="radio" name="inset" value="" id="tab_1" checked>
        <label for="tab_1" id="tab_1l">Contacts</label>

        <input type="radio" name="inset" value="" id="tab_2">
        <label for="tab_2" id="tab_2l">Sections</label>

        <div class="content" id="txt_1">
            <c:forEach var="contactType" items="<%=ContactType.values()%>">
                <dl>
                    <dt>${contactType.title}</dt>
                    <dd><input type="text" name="${contactType.name()}" size=30 value="${resume.getContact(contactType)}"></dd>
                </dl>
            </c:forEach>
        </div>
        <div class="content" id="txt_2">
            <c:forEach var="sectionType" items="<%=SectionType.values()%>">
                <c:set var="section" value="${resume.getSection(sectionType)}"/>
                    <jsp:useBean id="section" type="com.basejava.webapp.model.AbstractSection"/>

                <dl>
                    <dt>${sectionType.title}</dt>
                    <dd>
                        <c:choose>
                            <c:when test="${sectionType == SectionType.PERSONAL or sectionType == SectionType.OBJECTIVE}">
                                <input type="text" name="${sectionType}" size=30 value="${section}">
                            </c:when>
                            <c:when test="${sectionType == SectionType.ACHIEVEMENT or sectionType == SectionType.QUALIFICATIONS}">
                                <textarea name="${sectionType}" cols="30">${section}</textarea>
                            </c:when>
                            <c:when test="${sectionType == SectionType.EXPERIENCE or sectionType == SectionType.EDUCATION}">
                                <c:forEach var="org" items="<%=((OrganizationSection) section).getOrganizations()%>" varStatus="counter">
                                <dl>
                                    <dt>Institution name:</dt>
                                    <dd><input type="text" name='${sectionType}' size=100 value="${org.website.name}"></dd>
                                    <dt>Institution website:</dt>
                                    <dd><input type="text" name='${sectionType}url' size=100 value="${org.website.url}"></dd>
                                    <dd><div>
                                        <c:forEach var="period" items="${org.periods}">
                                            <jsp:useBean id="period" type="com.basejava.webapp.model.Period"/>
                                        <dl>
                                            <dt>From:</dt>
                                            <dd>
                                                <input type="text" name="${sectionType}${counter.index}from" size=30
                                                       value="<%=DateUtil.format(period.getFrom())%>" placeholder="MM/yyyy">
                                            </dd>
                                        </dl>
                                        <dl>
                                            <dt>To:</dt>
                                            <dd>
                                                <input type="text" name="${sectionType}${counter.index}to" size=30
                                                       value="<%=DateUtil.format(period.getTo())%>" placeholder="MM/yyyy">
                                        </dl>
                                        <dl>
                                            <dt>Position:</dt>
                                            <dd><input type="text" name='${sectionType}${counter.index}position' size=50
                                                       value="${period.position}">
                                        </dl>
                                        <dl>
                                            <dt>Description:</dt>
                                            <dd><textarea name="${sectionType}${counter.index}description" rows=5
                                                          cols=75>${period.description}</textarea></dd>
                                        </dl>
                                        </c:forEach>
                                    </div></dd>
                                </dl>
                                </c:forEach>
                            </c:when>
                        </c:choose>
                    </dd>
                </dl>
            </c:forEach>
        </div>
    </div>
    <div style="margin-top: 2%">
        <button type="submit" name="submit">Сохранить</button>
        <button type="reset" onclick="history.back()">Отменить</button>
    </div>
</form>
</section>
</div>
</body>
</html>
