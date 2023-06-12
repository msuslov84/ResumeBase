<%@ page import="com.suslov.basejava.model.section.SkillList" %>
<%@ page import="com.suslov.basejava.model.section.ExperienceList" %>
<%@ page import="com.suslov.basejava.util.HtmlUtil" %>
<%@ page import="com.suslov.basejava.model.section.Personal" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="theme" type="java.lang.String" scope="request"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/theme/${theme}.css">
    <link rel="stylesheet" href="css/styles.css">
    <link rel="stylesheet" href="css/view-resume-styles.css">
    <jsp:useBean id="resume" type="com.suslov.basejava.model.Resume" scope="request"/>
    <title>Resume: ${resume.fullName}</title>
</head>

<body>

<jsp:include page="fragments/header.jsp"/>

<div class="scrollable-panel">
    <div class="form-wrapper">
        <div class="full-name">${resume.fullName}
            <a class="no-underline-anchor" href="resume?uuid=${resume.uuid}&action=edit&theme=${theme}">
                <img src="img/pencil.png" alt="">
            </a>
        </div>

        <div class="contacts">
            <c:forEach var="contactEntry" items="${resume.contacts}">
                <jsp:useBean id="contactEntry"
                             type="java.util.Map.Entry<com.suslov.basejava.model.ContactType, java.lang.String>"/>
                <div>
                    <%=contactEntry.getKey().toHtml(contactEntry.getValue())%>
                </div>
            </c:forEach>
        </div>

        <div class="spacer"></div>

        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.suslov.basejava.model.section.SectionType,
                         com.suslov.basejava.model.section.AbstractSection>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="com.suslov.basejava.model.section.AbstractSection"/>
            <div class="section">
                    ${type.title}
            </div>
            <c:choose>
                <c:when test="${type=='OBJECTIVE' || type=='PERSONAL'}">
                    <div class="personal-position">
                        <%=((Personal) section).getContent()%>
                    </div>
                </c:when>
                <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                    <ul class="list">
                        <c:forEach var="item" items="<%=((SkillList) section).getSkills()%>">
                            <li>${item}</li>
                        </c:forEach>
                    </ul>
                </c:when>
                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:forEach var="expierence" items="<%=((ExperienceList) section).getExperiences()%>">
                        <div class="section-wrapper">
                            <c:choose>
                                <c:when test="${empty expierence.homePage.url}">
                                    <div class="job-name">${expierence.homePage.name}</div>
                                </c:when>
                                <c:otherwise>
                                    <div class="job-name"><a class="contact-link"
                                                             href="${expierence.homePage.url}">${expierence.homePage.name}</a>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <c:forEach var="period" items="${expierence.periods}">
                                <jsp:useBean id="period" type="com.suslov.basejava.model.Experience.Period"/>
                                <div class="period-position">
                                    <div class="period"><%=HtmlUtil.formatDates(period)%>
                                    </div>
                                    <div class="position">${period.title}</div>
                                </div>
                                <c:choose>
                                    <c:when test="${empty period.description}">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="description">${period.description}</div>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </div>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <div class="footer-spacer"></div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>

</body>
</html>
