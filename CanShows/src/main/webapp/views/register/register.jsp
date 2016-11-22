

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="register/register${Admin}.do"
	modelAttribute="${actor}Form">
	<fieldset class="register">
		<fieldset>
			<legend>
				<spring:message code="register.useraccount" />
			</legend>
			<acme:textbox code="register.userName" path="username" />
			<acme:password code="register.password" path="password" />
			<acme:password code="register.password2" path="password2" />
		</fieldset>
		<fieldset>
			<legend>
				<spring:message code="register.user" />
			</legend>
			<acme:textbox code="register.name" path="name" />
			<acme:textbox code="register.surname" path="surname" />
			<acme:textbox code="register.email" path="email" />
			<acme:textbox code="register.phone" path="phone" />
			<acme:textbox code="register.homePage" path="homePage" />
			<acme:textbox code="register.nationality" path="nationality" />

		</fieldset>
		<jstl:if test="${actor=='participant' or actor=='organiser'}">
			<fieldset>
				<legend>
					<spring:message code="register.creditCard" />
				</legend>
				<acme:textbox code="register.holdername"
					path="creditCard.holderName" />
				<acme:textbox code="register.brandname" path="creditCard.brandName" />
				<acme:textbox code="register.number" path="creditCard.number" />
				<acme:textbox code="register.expirationMonth"
					path="creditCard.expirationMonth" />
				<acme:textbox code="register.expirationYear"
					path="creditCard.expirationYear" />
				<acme:textbox code="register.cvv" path="creditCard.cvv" />
			</fieldset>
		</jstl:if>
		<jstl:if test="${actor=='judge'}">
			<fieldset>
				<legend>
					<spring:message code="register.judgedata" />
				</legend>
				<acme:textbox code="register.judge.number" path="judgeNumber" />
			</fieldset>
		</jstl:if>
		<br />
		<form:checkbox id="checkbox" path="isCondition" />
		<spring:message code="register.condition" />

		<br />
		<acme:submit name="save" code="register.save" />
		<acme:cancel url="welcome/index.do" code="register.cancel" />
	</fieldset>
</form:form>