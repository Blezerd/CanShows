
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
	<security:authorize access="hasRole('PARTICIPANT')">
		<form:form action="search/participant/buscar.do"
			modelAttribute="searchForm">
			<acme:textbox code="search.introduce" path="text" />

			<acme:submit name="search" code="search.search" />
		</form:form>
		<jstl:if test="${participants!= null}">
			<h2>
				<spring:message code="search.explanation" />
				<jstl:out value="'${searchForm.text}'"></jstl:out>
				<spring:message code="search.explanation.dos" />
			</h2>
			<display:table name="participants" id="participant"
				requestURI="search/participant/buscar.do" pagesize="5"
				class="displayTag">
				<display:column property="name" titleKey="search.name"
					sortable="true" />
				<display:column property="surname" titleKey="search.surname"
					sortable="true" />
				<display:column property="email" titleKey="search.email"
					sortable=" true" />
				<display:column titleKey="search.c.follow" sortable="true">
					<jstl:if
						test="${participant.id!=yo.id && !yo.friends.contains(participant)}">
						<a
							href="participant/participant/follow.do?participantId=${participant.id}&text=${text}"><spring:message
								code="search.follow" /></a>
					</jstl:if>
					<jstl:if
						test="${participant.id!=yo.id && yo.friends.contains(participant)}">
						<a
							href="participant/participant/unFollow.do?participantId=${participant.id}"><spring:message
								code="search.unfollow" /></a>
					</jstl:if>
				</display:column>

			</display:table>
		</jstl:if>
	</security:authorize>
</div>

