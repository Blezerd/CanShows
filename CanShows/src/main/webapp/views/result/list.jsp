<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<jstl:if test="${results.size()==0 or results==null }">
	<h2>
		<security:authorize access="hasRole('PARTICIPANT')">
			<spring:message code="result.nothing" />
			<br>
		</security:authorize>
	</h2>
</jstl:if>
<jstl:if test="${results.size()>0 }">
	<div>
		<security:authorize access="hasRole('PARTICIPANT')">
			<display:table name="results"
				requestURI="result/${actor}list${friend}.do${requestUri}" id="row"
				pagesize="5" class="datagrid">
				<display:column property="dog.name" titleKey="dog.name"
					sortable="true" />
				<display:column titleKey="search.breed" sortable="true">
					<a href="breed/details.do?breedId=${row.dog.breed.id}"><jstl:out
							value="${row.dog.breed.name}"></jstl:out></a>
				</display:column>
				<display:column property="position" titleKey="result.position"
					sortable="true" />
				<display:column property="comment" titleKey="result.comment"
					sortable="true" />
				<display:column property="points" titleKey="result.points"
					sortable="true" />
				<jstl:if test="${friend=='Friend'}">
					<display:column property="dog.participant.name"
						titleKey="dog.participant" sortable="true" />
				</jstl:if>
				<display:column titleKey="dog.details" sortable="true">
					<a href="dog/details.do?dogId=${row.dog.id}"><spring:message
							code="dog.details.see"></spring:message></a>
				</display:column>


			</display:table>
		</security:authorize>

	</div>
</jstl:if>