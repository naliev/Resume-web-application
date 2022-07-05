<%@ page import="com.basejava.webapp.model.Resume" %>
<%@ page import="java.util.List" %>
<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Список резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"></jsp:include>
<table>
    <tr>
        <th> Name</th>
        <th> Email</th>
    </tr>
    <% for (Resume resume : (List<Resume>) request.getAttribute("resumes")) {%>
    <tr>
        <td><a href="resume?uuid = <%=resume.getUuid()%>"><%=resume.getFullName()%></a></td>
        <td><%=resume.getContact(ContactType.EMAIL)%></td>
    </tr>
    <%}%>
</table>
<jsp:include page="fragments/footer.jsp"></jsp:include>
</body>
</html>
