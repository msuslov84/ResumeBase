package com.suslov.basejava.web;

import com.suslov.basejava.config.StorageConfig;
import com.suslov.basejava.exception.WebServletException;
import com.suslov.basejava.model.ContactType;
import com.suslov.basejava.model.Experience;
import com.suslov.basejava.model.Resume;
import com.suslov.basejava.model.section.*;
import com.suslov.basejava.storage.Storage;
import com.suslov.basejava.util.DateUtil;
import com.suslov.basejava.util.HtmlUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ResumeServlet extends HttpServlet {

    private final Storage storage = StorageConfig.getInstance().getStorage();
    private final Set<String> themes = new HashSet<>(Set.of(WebTheme.LIGHT.getName(), WebTheme.DARK.getName()));

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        request.setAttribute("theme", getCheckedTheme(request));

        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }

        Resume resume;
//        Map<SectionType, AbstractSection> sections;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                resume = storage.get(uuid);
//                sections = resume.getSections();
                break;
            case "edit":
                resume = storage.get(uuid);
                // Refill sections with empty fields for entering new data on edit form
                for (SectionType type : SectionType.values()) {
                    AbstractSection section = resume.getSection(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            section = getNotNullPersonalInformation(section);
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            section = getNotNullSkills(section);
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            section = getExperienceWithEmptyField((ExperienceList) section);
                            break;
                    }
                    resume.setSection(type, section);
                }
//                sections = resume.getSections();
                break;
            case "create":
                resume = Resume.EMPTY;
                resume.setSection(SectionType.OBJECTIVE, Personal.EMPTY);
//                sections = resume.getSections();
                break;
            default:
                throw new WebServletException("Error: entered action '" + action + "' is illegal");
        }
        request.setAttribute("resume", resume);
//        request.setAttribute("sections", sections);
        String nextPage = "view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp";
        request.getRequestDispatcher(nextPage).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");

        if (HtmlUtil.isEmpty(fullName)) {
            response.sendRedirect(String.format("resume?uuid=%s&action=edit", uuid));
            return;
        }

        Resume r;
        boolean isNew = false;
        if (HtmlUtil.isEmpty(uuid)) {
            r = new Resume(fullName);
            isNew = true;
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (!HtmlUtil.isEmpty(value)) {
                r.setContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }

        for (SectionType type : SectionType.values()) {
            String typeName = type.name();
            String value = request.getParameter(typeName);
            String[] values = request.getParameterValues(typeName);
            if (HtmlUtil.isEmpty(value) && values.length < 2) {
                r.getSections().remove(type);
            } else {
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        r.setSection(type, new Personal(value.trim()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        r.setSection(type, new SkillList(Arrays.stream(value.split("\n"))
                                .filter(x -> !x.trim().isEmpty())
                                .collect(Collectors.toList())));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        String[] urls = request.getParameterValues(typeName + "url");

                        List<Experience> experiences = new ArrayList<>();

                        for (int i = 0; i < values.length; i++) {
                            String nameOrg = values[i];
                            String periodMark = typeName + i;
                            if (!HtmlUtil.isEmpty(nameOrg)) {
                                List<Experience.Period> periods = new ArrayList<>();

                                String[] titles = request.getParameterValues(periodMark + "title");
                                String[] descriptions = request.getParameterValues(periodMark + "description");
                                String[] periodsFrom = request.getParameterValues(periodMark + "periodFrom");
                                String[] periodsTo = request.getParameterValues(periodMark + "periodTo");

                                for (int j = 0; j < titles.length; j++) {
                                    String namePeriod = titles[j];
                                    if (!HtmlUtil.isEmpty(namePeriod)) {
                                        periods.add(new Experience.Period(namePeriod, DateUtil.parse(periodsFrom[j]),
                                                DateUtil.parse(periodsTo[j]), descriptions[j]));
                                    }
                                }
                                experiences.add(new Experience(urls[i], nameOrg, periods));
                            }
                        }
                        r.setSection(type, new ExperienceList(experiences));
                        break;
                }
            }
        }

        if (isNew) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");

    }

    private String getCheckedTheme(HttpServletRequest request) {
        String theme = request.getParameter("theme");
        return (theme == null || !themes.contains(theme)) ? WebTheme.LIGHT.getName() : theme;
    }


    private AbstractSection getNotNullPersonalInformation(AbstractSection section) {
        if (section == null) {
            section = Personal.EMPTY;
        }
        return section;
    }

    private AbstractSection getNotNullSkills(AbstractSection section) {
        if (section == null) {
            section = SkillList.EMPTY;
        }
        return section;
    }

    private AbstractSection getExperienceWithEmptyField(ExperienceList section) {
        List<Experience> experiencesWithEmpty = new ArrayList<>();
        experiencesWithEmpty.add(Experience.EMPTY);
        if (section != null) {
            for (Experience exp : section.getExperiences()) {
                List<Experience.Period> periodsWithEmpty = new ArrayList<>();
                periodsWithEmpty.add(Experience.Period.EMPTY);
                periodsWithEmpty.addAll(exp.getPeriods());
                experiencesWithEmpty.add(new Experience(exp.getHomePage().getUrl(), exp.getHomePage().getName(), periodsWithEmpty));
            }
        }
        return new ExperienceList(experiencesWithEmpty);
    }
}
