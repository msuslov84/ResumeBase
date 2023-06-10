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

    public static void main(String[] args) {
        Resume testResume = new Resume("Григорий Кислин");

        testResume.setContact(ContactType.SKYPE, "grigory.kislin");
        testResume.setContact(ContactType.EMAIL, "gkislin@yandex.ru");
        testResume.setContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        testResume.setContact(ContactType.GITHUB, "https://github.com/gkislin");
        testResume.setContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/grigory-kislin");
        testResume.setContact(ContactType.HOMEPAGE, "http://gkislin.ru/");

        testResume.setSection(SectionType.OBJECTIVE, new Personal("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        testResume.setSection(SectionType.PERSONAL, new Personal("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        testResume.setSection(SectionType.ACHIEVEMENT, new SkillList(fillAchievements()));
        testResume.setSection(SectionType.QUALIFICATIONS, new SkillList(fillQualifications()));
        testResume.setSection(SectionType.EXPERIENCE, new ExperienceList(fillExperience()));
        testResume.setSection(SectionType.EDUCATION, new ExperienceList(fillEducation()));

        for (ContactType type : ContactType.values()) {
            System.out.println(type.getTitle());
            System.out.println(testResume.getContact(type));
        }

        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
            System.out.println(testResume.getSection(type));
        }
    }

    private static List<String> fillAchievements() {
        List<String> elements = new ArrayList<>();
        elements.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное " +
                "взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        elements.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        elements.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, " +
                "Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. " +
                "Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        elements.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, " +
                "GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        elements.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base " +
                "архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему " +
                "мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        elements.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, " +
                "Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");

        return elements;
    }

    private static List<String> fillQualifications() {
        List<String> elements = new ArrayList<>();
        elements.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        elements.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        elements.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");
        elements.add("MySQL, SQLite, MS SQL, HSQLDB");
        elements.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        elements.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        elements.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, " +
                "Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, " +
                "Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements)");
        elements.add("Python: Django");
        elements.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        elements.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        elements.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, " +
                "JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT");
        elements.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix");
        elements.add("Администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer");
        elements.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального " +
                "программирования");
        elements.add("Родной русский, английский \"upper intermediate");

        return elements;
    }

    public static List<Experience> fillExperience() {
        List<Experience> elements = new ArrayList<>();
        elements.add(new Experience("", "RIT Center",
                new Experience.Period("Java архитектор", DateUtil.of(2012, Month.APRIL),
                        DateUtil.of(2016, Month.JANUARY),
                        "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование,\n" +
                                "ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx),\n" +
                                "AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2,\n" +
                                "1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN\n" +
                                "для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons,\n" +
                                "Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote\n" +
                                "scripting via ssh tunnels, PL/Python")));
        elements.add(new Experience("https://javaops.ru/", "Java Online Projects",
                new Experience.Period("Автор проекта", DateUtil.of(2013, Month.OCTOBER),
                        DateUtil.NOW, null)));
        //"Создание, организация и проведение Java онлайн проектов и стажировок.")));
        elements.add(new Experience("https://www.wrike.com/vn/", "Wrike",
                new Experience.Period("Старший разработчик (backend)", DateUtil.of(2014, Month.OCTOBER),
                        DateUtil.of(2016, Month.JANUARY),
                        "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven,\n" +
                                "Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1,\n" +
                                "OAuth2, JWT SSO.")));
        elements.add(new Experience("http://www.luxoft.ru/", "Luxoft (Deutsche Bank)",
                new Experience.Period("Ведущий программист", DateUtil.of(2010, Month.DECEMBER),
                        DateUtil.of(2012, Month.APRIL),
                        "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle).\n" +
                                "Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и\n" +
                                "анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock,\n" +
                                "Commet, HTML5.")));
        elements.add(new Experience("https://www.yota.ru/", "Yota",
                new Experience.Period("Ведущий специалист", DateUtil.of(2008, Month.JUNE),
                        DateUtil.of(2010, Month.DECEMBER),
                        "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3,\n" +
                                "JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка.\n" +
                                "Разработка online JMX клиента (Python/ Jython, Django, ExtJS).")));
        elements.add(new Experience("http://enkata.com/", "Enkata",
                new Experience.Period("Разработчик ПО", DateUtil.of(2007, Month.MARCH),
                        DateUtil.of(2008, Month.JUNE),
                        "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE\n" +
                                "приложения (OLAP, Data mining).")));
        elements.add(new Experience("http://www.alcatel.ru/", "Alcatel",
                new Experience.Period("Инженер по аппаратному и программному тестированию", DateUtil.of(1997, Month.SEPTEMBER),
                        DateUtil.of(2005, Month.JANUARY),
                        "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).")));
        elements.add(new Experience("https://new.siemens.com/ru/ru.html", "Siemens AG",
                new Experience.Period("Разработчик ПО", DateUtil.of(2005, Month.JANUARY),
                        DateUtil.of(2007, Month.FEBRUARY),
                        "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе\n" +
                                "Siemens @vantage (Java, Unix).")));
        return elements;
    }

    public static List<Experience> fillEducation() {
        List<Experience> elements = new ArrayList<>();
        elements.add(new Experience("https://www.coursera.org/course/progfun", "Coursera",
                new Experience.Period("", DateUtil.of(2013, Month.MARCH),
                        DateUtil.of(2013, Month.MAY),
                        "\"Functional Programming Principles in Scala\" by Martin Odersky")));
        elements.add(new Experience("https://new.siemens.com/ru/ru.html", "Siemens AG",
                new Experience.Period("", DateUtil.of(2005, Month.JANUARY),
                        DateUtil.of(2005, Month.APRIL),
                        "3 месяца обучения мобильным IN сетям (Берлин)")));
        elements.add(new Experience("http://www.luxoft-training.ru/training/catalog/course.html?ID=22366", "Luxoft",
                new Experience.Period("", DateUtil.of(2011, Month.MARCH),
                        DateUtil.of(2011, Month.APRIL),
                        "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.")));
        elements.add(new Experience("http://www.alcatel.ru/", "Alcatel",
                new Experience.Period("", DateUtil.of(1997, Month.SEPTEMBER),
                        DateUtil.of(1998, Month.MARCH),
                        "6 месяцев обучения цифровым телефонным сетям (Москва)")));
        elements.add(new Experience("https://itmo.ru/ru/", "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                new Experience.Period("", DateUtil.of(1993, Month.SEPTEMBER),
                        DateUtil.of(1996, Month.JULY), "Аспирантура (программист С, С++)"),
                new Experience.Period("", DateUtil.of(1987, Month.SEPTEMBER),
                        DateUtil.of(1993, Month.JULY), "Инженер (программист Fortran, C)")));
        elements.add(new Experience("https://school.mipt.ru/", "Заочная физико-техническая школа при МФТИ",
                new Experience.Period("", DateUtil.of(1984, Month.SEPTEMBER),
                        DateUtil.of(1987, Month.JUNE),
                        "Закончил с отличием")));
        return elements;
    }

    public static Resume create(String uuid, String fullName) {
        Resume newResume = new Resume(uuid, fullName);
        newResume.setContact(ContactType.TELEPHONE, "+7(999) 111-" + (int) (Math.random() * 10000));
        newResume.setContact(ContactType.EMAIL, "mmm" + (int) (Math.random() * 100) + "@mail.ru");

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

        List<Experience> expList = new ArrayList<>();
        expList.add(new Experience("", "IBM",
                new Experience.Period("Programmer", DateUtil.of(2020, Month.MARCH),
                        DateUtil.of(2021, Month.MAY),
                        "Programmer for Java")));
        expList.add(new Experience("www.google.com", "GOOGLE",
                new Experience.Period("Lead Programmer", DateUtil.of(2021, Month.MAY),
                        DateUtil.of(2022, Month.JANUARY),
                        "Lead Programmer for C#"), new Experience.Period("Team Lead", DateUtil.of(2022, Month.JANUARY),
                LocalDate.now(),
                "Team Lead for C# programmers")));
        newResume.setSection(SectionType.EXPERIENCE, new ExperienceList(expList));

        expList.clear();
        expList.add(new Experience("", "Stepik",
                new Experience.Period("listener", DateUtil.of(2019, Month.JANUARY),
                        DateUtil.of(2019, Month.AUGUST),
                        "Java for beginners")));
        expList.add(new Experience("", "Java Rush",
                new Experience.Period("student", DateUtil.of(2019, Month.SEPTEMBER),
                        DateUtil.of(2020, Month.FEBRUARY),
                        "Java course")));
        newResume.setSection(SectionType.EDUCATION, new ExperienceList(expList));

        return newResume;
    }
}
