

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:if test="${judges.size()==0}">
	<h2>
		<spring:message code="no.more.judges"></spring:message>
	</h2>
</jstl:if>
<jstl:if test="${judges.size()>0}">
	<form:form action="competition/organiser/addJudge.do"
		modelAttribute="competitionAddJudgeForm">
		<form:hidden path="id" />
		<fieldset>
			<legend>
				<spring:message code="competition.add" />
			</legend>
			<acme:select items="${judges}" itemLabel="name"
				code="competition.judge" path="judge" />
		</fieldset>
		<br />
		<acme:submit name="save" code="register.save" />
		<acme:cancel url="welcome/index.do" code="register.cancel" />
	</form:form>
</jstl:if>