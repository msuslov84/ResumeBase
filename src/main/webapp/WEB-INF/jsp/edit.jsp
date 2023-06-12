<%@ page import="com.suslov.basejava.model.section.SkillList" %>
<%@ page import="com.suslov.basejava.model.section.ExperienceList" %>
<%@ page import="com.suslov.basejava.model.section.Personal" %>
<%@ page import="com.suslov.basejava.model.ContactType" %>
<%@ page import="com.suslov.basejava.model.section.SectionType" %>
<%@ page import="com.suslov.basejava.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="theme" type="java.lang.String" scope="request"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/theme/${theme}.css">
    <link rel="stylesheet" href="css/styles.css">
    <link rel="stylesheet" href="css/edit-resume-styles.css">
    <jsp:useBean id="resume" type="com.suslov.basejava.model.Resume" scope="request"/>
    <title>Edit resume: ${resume.fullName}</title>
</head>

<body>

<jsp:include page="fragments/header.jsp"/>

<form method="post" action="resume" enctype="application/x-www-form-urlencoded">
    <input type="hidden" name="uuid" value="${resume.uuid}">
    <input type="hidden" name="theme" value="${theme}">
    <div class="scrollable-panel">
        <div class="form-wrapper">
            <div class="section">Full name</div>
            <label>
                <input class="field" type="text" name="fullName" size=55 placeholder="Full name"
                       value="${resume.fullName}" required>
            </label>

            <div class="section">Contacts</div>

            <c:forEach var="type" items="<%=ContactType.values()%>">
                <label>
                    <input class="field" type="text" name="${type.name()}" size=30 placeholder="${type.title}"
                           value="${resume.getContact(type)}">
                </label>
            </c:forEach>

            <div class="spacer"></div>

            <div class="section">Sections</div>

            <c:forEach var="type" items="<%=SectionType.values()%>">
                <c:set var="section" value="${resume.getSection(type)}"/>
                <jsp:useBean id="section" type="com.suslov.basejava.model.section.AbstractSection"/>
                <div class="field-label">${type.title}</div>
                <c:choose>
                    <c:when test="${type=='OBJECTIVE' || type=='PERSONAL'}">
                        <label>
                            <textarea class="field" name='${type}'><%=((Personal) section).getContent()%></textarea>
                        </label>
                    </c:when>
                    <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                        <label>
                            <textarea class="field"
                                      name='${type}'><%=String.join("\n", ((SkillList) section).getSkills())%></textarea>
                        </label>
                    </c:when>
                    <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                        <c:forEach var="experience" items="<%=((ExperienceList) section).getExperiences()%>"
                                   varStatus="exp">
                            <c:choose>
                                <c:when test="${exp.index == 0}">
                                </c:when>
                                <c:otherwise>
                                    <div class="spacer"></div>
                                </c:otherwise>
                            </c:choose>

                            <label>
                                <input class="${exp.index == 0 ? "new-field" : "field"}" type="text" placeholder="Name" name='${type}' size=100
                                       value="${experience.homePage.name}">
                            </label>
                            <label>
                                <input class="${exp.index == 0 ? "new-field" : "field"}" type="text" placeholder="Link" name='${type}url' size=100
                                       value="${experience.homePage.url}">
                            </label>

                            <c:forEach var="period" items="${experience.periods}" varStatus="per">
                                <jsp:useBean id="period" type="com.suslov.basejava.model.Experience.Period"/>
                                <div class="date-section">
                                    <label>
                                        <input class="${per.index == 0 ? "new-field" : "field"} date" name="${type}${exp.index}periodFrom"
                                               placeholder="From, MM.yyyy"
                                               size=10
                                               value="<%=DateUtil.format(period.getPeriodFrom())%>">
                                    </label>
                                    <label>
                                        <input class="${per.index == 0 ? "new-field" : "field"} date date-margin" name="${type}${exp.index}periodTo"
                                               placeholder="To, MM.yyyy"
                                               size=10
                                               value="<%=DateUtil.format(period.getPeriodTo())%>">
                                    </label>
                                </div>
                                <label>
                                    <input class="${per.index == 0 ? "new-field" : "field"}" type="text" placeholder="Title"
                                           name='${type}${exp.index}title' size=75
                                           value="${period.title}">
                                </label>
                                <label>
                                    <textarea class="${per.index == 0 ? "new-field" : "field"}" placeholder="Description"
                                              name="${type}${exp.index}description">${period.description}</textarea>
                                </label>
                            </c:forEach>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </c:forEach>

            <div class="spacer"></div>

            <div class="button-section">
                <button class="red-cancel-button" type="button" onclick="window.history.back()">Cancel</button>
                <button class="green-submit-button" type="submit">Save</button>
            </div>
        </div>
    </div>
</form>

<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>

</body>
</html>