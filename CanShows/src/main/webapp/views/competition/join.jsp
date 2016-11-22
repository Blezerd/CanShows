<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<fieldset class="register">
	<jstl:if test="${groups.size()==0}">
		<h3>
			<spring:message code="nohaygrupos"></spring:message>
		</h3>
	</jstl:if>
	<jstl:if test="${groups.size()>0}">
		<security:authorize access="hasRole('PARTICIPANT')">
			<form:form action="competition/participant/join.do"
				modelAttribute="competitionGroupForm">
				<form:hidden path="compId" />
				<fieldset>
					<acme:select items="${groups}" itemLabel="breed.name"
						code="competition.groups" path="grupo" />
				</fieldset>
				<acme:submit name="save" code="request.save" />
				<acme:cancel url="competition/list.do" code="request.cancel" />
			</form:form>
		</security:authorize>
	</jstl:if>
</fieldset>