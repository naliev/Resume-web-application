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
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<div class="bloko-columns-wrapper">

    <section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png" alt="edit"></a></h2>
    <div class="tabs">
        <input type="radio" name="inset" value="" id="tab_1" checked>
        <label for="tab_1" id="tab_1l">Contacts</label>

        <input type="radio" name="inset" value="" id="tab_2">
        <label for="tab_2" id="tab_2l">Sections</label>


    <% if (!resume.getContacts().isEmpty()) {%>
        <div class="content" id="txt_1">
            <c:forEach var="contactEntry" items="${resume.contacts}">
                <jsp:useBean id="contactEntry"
                             type="java.util.Map.Entry<com.basejava.webapp.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
            </c:forEach>
        </div>
    <%}%>
    <% if (!resume.getSections().isEmpty()) {%>
        <div class="content" id="txt_2">
            <c:forEach var="sectionEntry" items="${resume.sections}">
                <jsp:useBean id="sectionEntry"
                             type="java.util.Map.Entry<com.basejava.webapp.model.SectionType, com.basejava.webapp.model.AbstractSection>"/>
                ${sectionEntry.key.toHtml(sectionEntry.value)}
            </c:forEach>
        </div>
    <%}%>
    </div>
    </section>
</div>
</body>
</html>
