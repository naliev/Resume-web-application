package com.basejava.webapp.model;

import com.basejava.webapp.util.DateUtil;

import java.util.List;

public enum SectionType {
    PERSONAL("Личные качества") {
        @Override
        protected String toHtml0(AbstractSection section) {
            return (((TextSection) section).getText().length() > 0) ?
                    divSection(getTitle()) + "<div>" + ((TextSection) section).getText() + "</div>" :
                    "";
        }
    },
    OBJECTIVE("Позиция") {
        @Override
        protected String toHtml0(AbstractSection section) {
            return (((TextSection) section).getText().length() > 0) ?
                    divSection(getTitle()) + "<div style='font-weight: bold'>" + ((TextSection) section).getText() + "</div>" :
                    "";
        }
    },
    ACHIEVEMENT("Достижения") {
        @Override
        protected String toHtml0(AbstractSection section) {
            return !((((ListSection) section).getList()).isEmpty()) ?
                    divSection(getTitle()) + ulList(((ListSection) section).getList()) :
                    "";
        }
    },
    QUALIFICATIONS("Квалификация") {
        @Override
        protected String toHtml0(AbstractSection section) {
            return !((((ListSection) section).getList()).isEmpty()) ?
                    divSection(getTitle()) + ulList(((ListSection) section).getList()) :
                    "";
        }
    },
    EXPERIENCE("Опыт работы") {
        @Override
        protected String toHtml0(AbstractSection section) {
            return !(sectionWrapper(((OrganizationSection) section)).isEmpty()) ?
                    divSection(getTitle()) + sectionWrapper(((OrganizationSection) section)) :
                    "";
        }
    },
    EDUCATION("Образование") {
        @Override
        protected String toHtml0(AbstractSection section) {
            return !(sectionWrapper(((OrganizationSection) section)).isEmpty()) ?
                    divSection(getTitle()) + sectionWrapper(((OrganizationSection) section)) :
                    "";
        }
    };

    private final String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String toHtml(AbstractSection section) {
        return (section == null) ? "" : toHtml0(section);
    }

    protected String toHtml0(AbstractSection section) {
        return title + ": " + section;
    }

    protected String divSection(String title) {
        return "<div class='section'>" + title + "</div>";
    }

    protected String ulList(List<String> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("<ul>");
        for (String value : list) {
            sb.append("<li>").append(value).append("</li>");
        }
        sb.append("</ul>");
        return sb.toString();
    }

    protected String sectionWrapper(OrganizationSection section) {
        StringBuilder sbFinal = new StringBuilder();
        for (Organization org : section.getOrganizations()) {
            if (org.getWebsite().getName() == null || org.getWebsite().getName().length() == 0) {
                continue;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("<div class='section-wrapper'>");
            sb.append("<div style='font-weight: bold'>").
                    append(String.format("<a href = '%S'> %S </a>", org.getWebsite().getUrl(), org.getWebsite().getName()));
            sb.append("</div>");
            for (Period p : org.getPeriods()) {
                if (p.getFrom() == null) {
                    continue;
                }
                sb.append("<div>").
                        append("<div class='positionFromTo'>").
                        append("<div class='From'>").append(p.getFrom()).append("</div>").
                        append(" - ").append("<div class='To'>").
                        append(DateUtil.format(p.getTo())).append("</div>").
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
            sbFinal.append(sb);
        }
        return sbFinal.toString();
    }
}
