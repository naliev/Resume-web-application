package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.*;
import com.basejava.webapp.storage.SqlStorage;
import com.basejava.webapp.util.DateUtil;
import com.basejava.webapp.util.StringUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
                resume = Resume.EMPTY;
                break;
            case "view":
            case "edit":
                resume = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    AbstractSection section = resume.getSection(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = TextSection.EMPTY;
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = ListSection.EMPTY;
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            OrganizationSection orgSection = (OrganizationSection) section;
                            List<Organization> emptyFirstOrganizations = new ArrayList<>();
                            emptyFirstOrganizations.add(Organization.EMPTY);
                            if (orgSection != null) {
                                for (Organization org : orgSection.getOrganizations()) {
                                    List<Period> emptyFirstPositions = new ArrayList<>();
                                    emptyFirstPositions.add(Period.EMPTY);
                                    emptyFirstPositions.addAll(org.getPeriods());
                                    emptyFirstOrganizations.add(new Organization("", org.getWebsite(), emptyFirstPositions));
                                }
                            }
                            section = new OrganizationSection(emptyFirstOrganizations);
                            break;
                    }
                    resume.addSection(type, section);
                }
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
        boolean resumeCreation = StringUtil.isEmpty(uuid);
        Resume r;
        if (resumeCreation) {
            r = new Resume(fullName);
        } else {
            r = storage.get(uuid);
            String cleanFullName = fullName.replaceAll("[^a-zA-Z\\d]","");
            r.setFullName(StringUtil.isEmpty(cleanFullName) ? "Incognito" : fullName);
        }
        if (!StringUtil.isEmpty(fullName)) {
            r.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (!StringUtil.isEmpty(value)) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());
            if (StringUtil.isEmpty(value) && values.length < 2) {
                r.getSections().remove(type);
            } else {
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        r.addSection(type, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        r.addSection(type, new ListSection(Arrays.stream(value.split("\\r\\n")).
                                filter(x -> !x.isEmpty()).toArray(String[]::new))
                        );
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<Organization> orgs = new ArrayList<>();
                        String[] urls = request.getParameterValues(type.name() + "url");
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (!StringUtil.isEmpty(name)) {
                                List<Period> periods = new ArrayList<>();
                                String pfx = type.name() + i;
                                String[] startDates = request.getParameterValues(pfx + "from");
                                String[] endDates = request.getParameterValues(pfx + "to");
                                String[] titles = request.getParameterValues(pfx + "position");
                                String[] descriptions = request.getParameterValues(pfx + "description");
                                for (int j = 0; j < titles.length; j++) {
                                    if (!StringUtil.isEmpty(titles[j])) {
                                        periods.add(new Period(DateUtil.parse(startDates[j]), DateUtil.parse(endDates[j]), titles[j], descriptions[j]));
                                    }
                                }
                                orgs.add(new Organization(new Link(name, urls[i]), periods));
                            }
                        }
                        r.addSection(type, new OrganizationSection(orgs));
                        break;
                }
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
