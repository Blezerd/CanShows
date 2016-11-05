

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:if test="${breeds.size()==0}">
	<h2>
		<spring:message code="no.more.breeds"></spring:message>
	</h2>
</jstl:if>
<jstl:if test="${breeds.size()>0}">
	<form:form action="competition/organiser/addGroup.do"
		modelAttribute="competitionAddGroupForm">
		<form:hidden path="id" />
		<fieldset>
			<legend>
				<spring:message code="competition.add.group" />
			</legend>
			<acme:select items="${breeds}" itemLabel="name"
				code="competition.breed" path="breed" />
			<acme:textbox code="competition.number.dogs" path="numberOfDogs" />
			<acme:textbox code="competition.ring" path="ring" />
		</fieldset>
		<br />
		<acme:submit name="save" code="register.save" />
		<acme:cancel url="welcome/index.do" code="register.cancel" />
	</form:form>
</jstl:if>