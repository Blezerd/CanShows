

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<fieldset class="register">
	<form:form action="message/${actor}edit.do"
		modelAttribute="message${admin}Form">

		<security:authorize access="hasRole('PARTICIPANT')">

			<acme:textbox code="message.subject" path="subject" />
			<acme:textarea code="message.body" path="body"/>
			<acme:select items="${participants}" itemLabel="name"
				code="message.participant" path="participant"/>

			<acme:submit name="save" code="message.send" />

			<acme:cancel url="welcome/index.do" code="request.cancel" />
		</security:authorize>

	</form:form>
</fieldset>