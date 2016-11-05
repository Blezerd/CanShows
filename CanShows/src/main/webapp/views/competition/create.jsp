

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<jstl:if test="${create==true}">
	<form:form action="competition/organiser/create.do"
		modelAttribute="competitionOrganiserForm">
		<fieldset>
			<legend>
				<spring:message code="competition.create" />
			</legend>
			<form:label path="type">
				<spring:message code="competition.type" />
			</form:label>
			<form:select path="type">
				<form:option value="BEAUTY">
					<spring:message code="competition.beauty"></spring:message>
				</form:option>
				<form:option value="WORK">
					<spring:message code="competition.work"></spring:message>
				</form:option>
			</form:select>
			<form:errors path="type" cssClass="error" />
			<acme:textbox code="competition.title" path="title" />
			<acme:textbox code="competition.adress" path="adress" />
			<acme:textbox code="competition.inscriptionPrice"
				path="inscriptionPrice" />
			<acme:textbox code="competition.firstPrize" path="firstPrize" />
			<acme:textbox code="competition.secondPrize" path="secondPrize" />
			<acme:textbox code="competition.thirdPrize" path="thirdPrize" />
			<acme:textbox code="competition.minimumPoints" path="minimumPoints" />
			<acme:textbox code="competition.celebrationDate"
				path="celebrationDate" />
			<acme:textbox code="competition.inscriptionLimitDate"
				path="inscriptionLimitDate" />
		</fieldset>
		<br />
		<acme:submit name="save" code="register.save" />
		<acme:cancel url="welcome/index.do" code="register.cancel" />
	</form:form>
</jstl:if>
<jstl:if test="${create==false}">
	<form:form action="competition/organiser/edit.do"
		modelAttribute="competitionOrganiserForm">
		<form:hidden path="type" />
		<fieldset>
			<acme:textbox code="competition.title" path="title" readonly="true" />
			<acme:textbox code="competition.adress" path="adress" readonly="true" />
			<acme:textbox code="competition.inscriptionPrice"
				path="inscriptionPrice" readonly="true" />
			<acme:textbox code="competition.minimumPoints" path="minimumPoints"
				readonly="true" />
		</fieldset>
		<fieldset>
			<legend>
				<spring:message code="competition.edit" />
			</legend>

			<acme:textbox code="competition.firstPrize" path="firstPrize" />
			<acme:textbox code="competition.secondPrize" path="secondPrize" />
			<acme:textbox code="competition.thirdPrize" path="thirdPrize" />

			<acme:textbox code="competition.celebrationDate"
				path="celebrationDate" />
			<acme:textbox code="competition.inscriptionLimitDate"
				path="inscriptionLimitDate" />
		</fieldset>
		<br />
		<acme:submit name="save" code="register.save" />
		<acme:cancel url="welcome/index.do" code="register.cancel" />
	</form:form>
</jstl:if>