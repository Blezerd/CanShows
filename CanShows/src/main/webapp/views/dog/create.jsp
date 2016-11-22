

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="dog/participant/create.do" modelAttribute="dogForm">
	<fieldset class="register">
		<fieldset>
			<legend>
				<spring:message code="dog.create" />
			</legend>
			<acme:select items="${breeds}" itemLabel="name" code="dog.breed"
				path="breed" />
			<acme:textbox code="dog.name" path="name" />
			<acme:textbox code="dog.nickname" path="nickname" />
			<acme:textbox code="dog.weight" path="weight" />
			<acme:textbox code="dog.height" path="height" />
			<form:label path="sex">
				<spring:message code="dog.sex" />
			</form:label>
			<form:select path="sex">
				<form:option value="MALE">
					<spring:message code="dog.male"></spring:message>
				</form:option>
				<form:option value="FEMALE">
					<spring:message code="dog.female"></spring:message>
				</form:option>
			</form:select>
			<form:errors path="sex" cssClass="error" />
		</fieldset>
		<br />
		<acme:submit name="save" code="register.save" />
		<acme:cancel url="welcome/index.do" code="register.cancel" />
	</fieldset>
</form:form>