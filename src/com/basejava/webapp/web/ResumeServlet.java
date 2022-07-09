package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.*;
import com.basejava.webapp.storage.SqlStorage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ResumeServlet extends HttpServlet {

    private SqlStorage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getConfig().getSqlStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String uuid = request.getParameter("uuid");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/resume.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "add":
                resume = new Resume();
                break;
            case "view":
            case "edit":
                resume = storage.get(uuid);
                break;
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                        action.equals("view") ? "/WEB-INF/jsp/resumeView.jsp" : "/WEB-INF/jsp/resumeEdit.jsp").
                forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        boolean resumeCreation = (uuid == null) || (uuid.length() == 0);
        Resume r;
        if (resumeCreation) {
            r = new Resume(fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }
        if (fullName != null && fullName.trim().length() != 0) {
            r.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        r.addSection(type, new TextSection(value));
                        break;
                    case QUALIFICATIONS:
                    case ACHIEVEMENT:
                        List<String> sectionList = Arrays.stream(value.split("\n"))
                                .filter((s -> s.length() > 0)).collect(Collectors.toList());
                        r.addSection(type, new ListSection(new ArrayList<>(sectionList)));
                        break;
                }
            } else {
                r.getSections().remove(type);
            }
        }
        if (resumeCreation) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }
}
