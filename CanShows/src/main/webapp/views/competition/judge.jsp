

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<security:authorize access="hasRole('JUDGE')">

	<jstl:if test="${dogs.size()==0}">
	<h2><spring:message code="nohayperrosquejudgar"/></h2>

	</jstl:if>
	<jstl:if test="${dogs.size()>0}">
		<div>
			<display:table name="dogs"
				requestURI="competition/judge/judge.do${requestUri}" id="dog"
				pagesize="5" class="datagrid">
				<display:column titleKey="search.breed" sortable="true">
					<a href="breed/details.do?breedId=${dog.breed.id}"><jstl:out
							value="${dog.breed.name}"></jstl:out></a>
				</display:column>
				<display:column titleKey="dog.name" property="name" sortable="true" />
				<display:column titleKey="dog.nickname" property="nickname"
					sortable="true" />
				<display:column titleKey="competition.c.judge" sortable="true">
					<a
						href="competition/judge/judgeDog.do?dogId=${dog.id}&competitionId=${competition.id}"><spring:message
							code="competition.judge.dog"></spring:message></a>


				</display:column>
			</display:table>
		</div>
	</jstl:if>
</security:authorize>

