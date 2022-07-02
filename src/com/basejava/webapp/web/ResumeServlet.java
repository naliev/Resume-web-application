package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.ContactType;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.SqlStorage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class ResumeServlet extends HttpServlet {

    private SqlStorage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getConfig().getSqlStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        StringBuilder sB = new StringBuilder();
        if (parameterMap.isEmpty()) {
            sB.append("<table><tr><th> UUID </th><th> Name </th><th> Contacts </th></tr>");
            for (Resume resume: storage.getAllSorted()) {
                sB.append("<tr><td>").append(resume.getUuid()).append("</td>").
                        append("<td>").append(resume.getFullName()).append("</td>").
                        append("<td>").append(resume.getContacts().toString()).append("</td></tr>");
            }
            sB.append("</table>");
        } else if (parameterMap.containsKey("uuid")) {
            Resume r = storage.get(parameterMap.get("uuid")[0]);
            sB.append(String.format("<h1> %S </h1> <br>", r.getFullName()));
            for (Map.Entry<ContactType,String> entry: r.getContacts().entrySet()) {
                sB.append(String.format("<b>%S:</b> %S <br>", entry.getKey().getTitle(), entry.getValue()));
            }
        } else {
            sB.append("Error: unknown parameters sent");
        }
        writer.write(sB.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
