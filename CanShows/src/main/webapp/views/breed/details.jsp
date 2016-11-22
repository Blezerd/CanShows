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
<fieldset>
	<legend>
		<spring:message code="breed.details"></spring:message>
	</legend>
	<spring:message code="breed.name.details"></spring:message>
	<jstl:out value="${breed.name}"></jstl:out>
	<br>
	<spring:message code="breed.description.details"></spring:message>
	<jstl:out value="${breed.description}"></jstl:out>
	<br>
</fieldset>
<fieldset>
	<legend>
		<spring:message code="competition.dogs"></spring:message>
	</legend>
	<display:table name="dogs" id="dog" pagesize="5" class="datagrid">
		<display:column property="breed.name" titleKey="search.breed"
			sortable="true" />
		<display:column property="name" titleKey="dog.name" sortable="true" />
		<<display:column property="totalPoints" titleKey="dog.totalPoints"
			sortable="true" />
		<display:column titleKey="dog.details" sortable="true">
			<a href="dog/details.do?dogId=${dog.id}"><spring:message
					code="dog.details.see"></spring:message></a>
		</display:column>
	</display:table>
</fieldset>

