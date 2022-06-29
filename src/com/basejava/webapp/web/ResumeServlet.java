package com.basejava.webapp.web;

import com.basejava.webapp.Config;
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
        if (parameterMap.isEmpty()) {
            PrintWriter writer = response.getWriter();
            StringBuilder sB = new StringBuilder();
            sB.append("<table><tr><th> UUID </th><th> Name </th><th> Contacts </th></tr>");
            for (Resume resume: storage.getAllSorted()) {
                sB.append("<tr><td>").append(resume.getUuid()).append("</td>").
                        append("<td>").append(resume.getFullName()).append("</td>").
                        append("<td>").append(resume.getContacts().toString()).append("</td></tr>");
            }
            sB.append("</table>");
            writer.write(sB.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
