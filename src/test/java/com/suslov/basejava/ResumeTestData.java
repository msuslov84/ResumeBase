package com.suslov.basejava;

import com.suslov.basejava.model.ContactType;
import com.suslov.basejava.model.Experience;
import com.suslov.basejava.model.Resume;
import com.suslov.basejava.model.section.ExperienceList;
import com.suslov.basejava.model.section.Personal;
import com.suslov.basejava.model.section.SectionType;
import com.suslov.basejava.model.section.SkillList;
import com.suslov.basejava.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {

    public static Resume create(String uuid, String fullName) {
        Resume newResume = new Resume(uuid, fullName);
        newResume.setContact(ContactType.TELEPHONE, "+7(999) 111-" + (int) (Math.random() * 10000));
        newResume.setContact(ContactType.EMAIL, "mmm" + (int) (Math.random() * 100) + "@mail.ru");
        newResume.setContact(ContactType.GITHUB, "https://github.com/");

        newResume.setSection(SectionType.OBJECTIVE, new Personal("Должность такая-то"));
        newResume.setSection(SectionType.PERSONAL, new Personal("Личные качества такие-то"));

        List<String> strList = new ArrayList<>();
        strList.add("Делал то.");
        strList.add("Делал сё.");
        newResume.setSection(SectionType.ACHIEVEMENT, new SkillList(strList));

        strList.clear();
        strList.add("Умею то.");
        strList.add("Умею сё.");
        newResume.setSection(SectionType.QUALIFICATIONS, new SkillList(strList));

        newResume.setSection(SectionType.EXPERIENCE, new ExperienceList(fillExperience()));

        newResume.setSection(SectionType.EDUCATION, new ExperienceList(fillEducation()));

        return newResume;
    }

    public static List<Experience> fillExperience() {
        List<Experience> elements = new ArrayList<>();
        elements.add(new Experience("", "IBM",
                new Experience.Period("Programmer", DateUtil.of(2020, Month.MARCH),
                        DateUtil.of(2021, Month.MAY),
                        "Programmer for Java")));
        elements.add(new Experience("www.google.com", "GOOGLE",
                new Experience.Period("Lead Programmer", DateUtil.of(2021, Month.MAY),
                        DateUtil.of(2022, Month.JANUARY),
                        "Lead Programmer for C#"), new Experience.Period("Team Lead", DateUtil.of(2022, Month.JANUARY),
                LocalDate.now(),
                "Team Lead for C# programmers")));

        return elements;
    }

    public static List<Experience> fillEducation() {
        List<Experience> elements = new ArrayList<>();
        elements.add(new Experience("", "Stepik",
                new Experience.Period("listener", DateUtil.of(2019, Month.JANUARY),
                        DateUtil.of(2019, Month.AUGUST),
                        "Java for beginners")));
        elements.add(new Experience("", "Java Rush",
                new Experience.Period("student", DateUtil.of(2019, Month.SEPTEMBER),
                        DateUtil.of(2020, Month.FEBRUARY),
                        "Java course")));
        return elements;
    }
}
