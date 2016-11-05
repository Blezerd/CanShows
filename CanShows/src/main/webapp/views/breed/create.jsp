

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="breed/administrator/create.do"
	modelAttribute="breedForm">
	<fieldset>
		<legend>
			<spring:message code="breed.create" />
		</legend>
		<acme:textbox code="breed.name" path="name" />
		<acme:textarea code="breed.description" path="description" />
	</fieldset>
	<br />
	<acme:submit name="save" code="register.save" />
	<acme:cancel url="welcome/index.do" code="register.cancel" />
</form:form>