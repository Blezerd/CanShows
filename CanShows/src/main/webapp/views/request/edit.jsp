

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="request/${actor}edit.do"
	modelAttribute="request${admin}Form">
	<fieldset class="register">
		<jstl:if test="${actor!='administrator/'}">
			<security:authorize access="hasRole('PARTICIPANT')">
				<jstl:if test="${puedeRegistrar==false}">

					<h2>
						<spring:message code="request.no.puede.crear.mas" />
					</h2>
				</jstl:if>
				<jstl:if test="${puedeRegistrar==true}">
					<acme:textbox code="request.title" path="title" />
					<acme:textbox code="request.description" path="description" />
					<acme:select items="${administrators}" itemLabel="name"
						code="request.administrator" path="administrator" />
					<acme:select items="${dogs}" itemLabel="name" code="request.dogs"
						path="dog" />
					<acme:submit name="save" code="request.send" />
				</jstl:if>
				<acme:cancel url="welcome/index.do" code="request.cancel" />
			</security:authorize>
		</jstl:if>
		<jstl:if test="${actor=='administrator/'}">
			<form:hidden path="title" />
			<form:hidden path="id" />
			<security:authorize access="hasRole('ADMIN')">
				<fieldset>
					<spring:message code="request.status.disp">
					</spring:message>
					<jstl:out value="${requestAdminForm.status}" />
					<br />
					<spring:message code="request.title.disp">
					</spring:message>
					<jstl:out value="${requestAdminForm.title}" />
					<br />
					<spring:message code="request.description.disp">
					</spring:message>
					<jstl:out value="${requestAdminForm.description}" />
					<br /> <a href="breed/details.do?breedId=${dog.breed.id}"><jstl:out
							value="${dog.breed.name}"></jstl:out></a> <br>
					<spring:message code="dashboard.name"></spring:message>
					<jstl:out value="${dog.name}">
					</jstl:out>
					<br>
					<spring:message code="dashboard.nickname"></spring:message>
					<jstl:out value="${dog.nickname}">
					</jstl:out>
					<br>
					<jstl:if test="${dog.sex=='MALE'}">
						<spring:message code="dashboard.sex"></spring:message>
						<spring:message code="dog.male"></spring:message>
						<br>
					</jstl:if>
					<jstl:if test="${dog.sex=='FEMALE'}">
						<spring:message code="dashboard.sex"></spring:message>
						<spring:message code="dog.female"></spring:message>
						<br>
					</jstl:if>
					<spring:message code="dashboard.weight"></spring:message>
					<jstl:out value="${dog.weight}"></jstl:out>
					<spring:message code="dashboard.weight.unidad"></spring:message>
					<br>
					<spring:message code="dashboard.height"></spring:message>
					<jstl:out value="${dog.height}"></jstl:out>
					<spring:message code="dashboard.height.unidad"></spring:message>

					<br>
				</fieldset>
				<fieldset>
					<acme:textbox code="request.comment" path="comment" />
					<center>
						<form:checkbox id="checkbox" path="status" value="${'ACCEPTED'}" />
						<spring:message code="request.accepted" />
						<form:checkbox id="checkbox" path="status" value="${'REJECTED'}" />
						<spring:message code="request.rejected" />
					</center>
				</fieldset>
				<acme:submit name="saveedit" code="request.save" />
				<acme:cancel url="request/administrator/list.do"
					code="request.cancel" />
			</security:authorize>
		</jstl:if>
	</fieldset>
</form:form>