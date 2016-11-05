

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<security:authorize access="hasRole('JUDGE')">
	<form:form action="competition/judge/judgeDog.do"
		modelAttribute="resultado">
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="dog" />
		<form:hidden path="judge" />
		<form:hidden path="competition" />
		<fieldset>
			<legend>
				<spring:message code="competition.judge.dog" />
			</legend>
			<acme:textbox code="result.position" path="position" />
			<acme:textbox code="result.points" path="points" />
			<acme:textarea code="result.comment" path="comment" />
		</fieldset>
		<br />
		<acme:submit name="save" code="register.save" />
		<acme:cancel url="welcome/index.do" code="register.cancel" />
	</form:form>
</security:authorize>

