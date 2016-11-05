
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div>
	<form:form action="search/buscar.do" modelAttribute="searchForm">
		<acme:textbox code="search.introduced" path="text" />

		<acme:submit name="search" code="search.search" />
	</form:form>
	<jstl:if test="${dogs!= null}">
		<h2>
			<spring:message code="search.explanation" />
			<jstl:out value="'${searchForm.text}'"></jstl:out>
			<spring:message code="search.explanation.dosd" />
		</h2>
		<display:table name="dogs" id="dog" requestURI="search/buscar.do"
			pagesize="5" class="displayTag">
			<display:column property="name" titleKey="search.name"
				sortable="true" />
			<display:column property="nickname" titleKey="search.nickname"
				sortable="true" />
			<display:column titleKey="search.breed" sortable="true">
				<a href="breed/details.do?breedId=${dog.breed.id}"><jstl:out
						value="${dog.breed.name}"></jstl:out></a>
			</display:column>
			<display:column titleKey="dog.details" sortable="true">
				<a href="dog/details.do?dogId=${dog.id}"><spring:message
						code="dog.details.see"></spring:message></a>
			</display:column>
		</display:table>
	</jstl:if>
</div>

