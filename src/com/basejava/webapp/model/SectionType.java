package com.basejava.webapp.model;

import com.basejava.webapp.util.DateUtil;

import java.util.ArrayList;

public enum SectionType {
    PERSONAL("Личные качества") {
        @Override
        protected String toHtml0(AbstractSection section) {
            return  divSection(getTitle()) + "<div>" + ((TextSection) section).getText() + "</div>";
        }
    },
    OBJECTIVE("Позиция") {
        @Override
        protected String toHtml0(AbstractSection section) {
            return divSection(getTitle()) + "<div style='font-weight: bold'>" + ((TextSection) section).getText() + "</div>";
        }
    },
    ACHIEVEMENT("Достижения") {
        @Override
        protected String toHtml0(AbstractSection section) {
            return divSection(getTitle()) + ulList(((ListSection) section).getList());
        }
    },
    QUALIFICATIONS("Квалификация") {
        @Override
        protected String toHtml0(AbstractSection section) {
            return divSection(getTitle()) + ulList(((ListSection) section).getList());
        }
    },
    EXPERIENCE("Опыт работы") {
        @Override
        protected String toHtml0(AbstractSection section) {
            return divSection(getTitle()) + sectionWrapper(((OrganizationSection) section));
        }
    },
    EDUCATION("Образование") {
        @Override
        protected String toHtml0(AbstractSection section) {
            return divSection(getTitle()) + sectionWrapper(((OrganizationSection) section));
        }
    };

    private final String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

//    public String toHtml(String value) {
//        return (value == null) ? "" : toHtml0(value);
//    }

    public String toHtml(AbstractSection section) {
        return (section == null) ? "" : toHtml0(section);
    }

    protected String toHtml0 (AbstractSection section) {
        return title + ": " + section;
    }

    protected String divSection(String title) {
        return "<div class='section'>" + title + "</div>";
    }

    protected String ulList(ArrayList<String> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("<ul>");
        for (String value: list) {
            sb.append("<li>").append(value).append("</li>");
        }
        sb.append("</ul>");
        return sb.toString();
    }

    protected String sectionWrapper(OrganizationSection section) {
        StringBuilder sb = new StringBuilder();
        for (Organization org: section.getOrganizations()) {
            sb.append("<div class='section-wrapper'>");
                sb.append("<div style='font-weight: bold'>").
                        append(String.format("<a href = '%S'> %S </a>", org.getWebsite().getUrl(), org.getWebsite().getName()));
                sb.append("</div>");
            for (Period p:org.getPeriods()) {
                sb.append("<div>").
                        append("<div class='positionFromTo'>").
                            append("<div class='From'>").append(p.getFrom()).append("</div>").
                            append(" - ").append("<div class='To'>").
                            append((p.getTo().equals(DateUtil.NOW))?"Now":p.getTo()).append("</div>").
                            append("<div class='Position'>").
                            append(p.getPosition()).
                            append("</div>").
                        append("</div>");
                        if (!(p.getDescription() == null)) {
                            sb.append("<div class='Descriptionb'>").
                                    append(p.getDescription()).
                                    append("</div>");
                        }
                sb.append("</div>");
            }
            sb.append("</div>");
        }
        return sb.toString();
    }
}
