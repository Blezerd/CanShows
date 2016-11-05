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
		<spring:message code="competition.details"></spring:message>
	</legend>
	<spring:message code="competition.title.details"></spring:message>
	<jstl:out value="${competition.title}"></jstl:out>
	<br>
	<spring:message code="competition.organiser.details"></spring:message>
	<jstl:out value="${competition.organiser.name}"></jstl:out>
	<jstl:out value="${competition.organiser.surname}"></jstl:out>
	<br>
	<spring:message code="competition.type.details"></spring:message>
	<jstl:if test="${competition.type=='BEAUTY'}">
		<spring:message code="competition.beauty"></spring:message>
	</jstl:if>
	<jstl:if test="${competition.type=='WORK'}">
		<spring:message code="competition.work"></spring:message>
	</jstl:if>
	<br>
	<spring:message code="competition.address.details"></spring:message>
	<jstl:out value="${competition.adress}"></jstl:out>
	<br>
	<spring:message code="competition.inscriptionPrice.details"></spring:message>
	<jstl:out value="${competition.inscriptionPrice}"></jstl:out>
	<br>
	<spring:message code="competition.firstPrize.details"></spring:message>
	<jstl:out value="${competition.firstPrize}"></jstl:out>
	<br>
	<spring:message code="competition.secondPrize.details"></spring:message>
	<jstl:out value="${competition.secondPrize}"></jstl:out>
	<br>
	<spring:message code="competition.thirdPrize.details"></spring:message>
	<jstl:out value="${competition.thirdPrize}"></jstl:out>
	<br>
	<spring:message code="competition.minimumPoints.details"></spring:message>
	<jstl:out value="${competition.minimumPoints}"></jstl:out>
	<br>
	<spring:message code="competition.celebrationDate.details"></spring:message>
	<fmt:formatDate value="${competition.celebrationDate}"
		pattern="dd-MM-yyyy" />
	<br>
	<spring:message code="competition.inscriptionLimitDate.details"></spring:message>
	<fmt:formatDate value="${competition.inscriptionLimitDate}"
		pattern="dd-MM-yyyy" />
	<br>
	<security:authorize access="hasRole('ORGANISER')">
		<jstl:if test="${perros==0}">
			<a
				href="competition/organiser/delete.do?competitionId=${competition.id}"
				onclick="return confirm('<spring:message code="competition.delete.delete" />')"><spring:message
					code="competition.delete"></spring:message></a>
		</jstl:if>
	</security:authorize>
</fieldset>
<fieldset>
	<legend>
		<spring:message code="competition.judges"></spring:message>
	</legend>
	<display:table name="judges" id="judge" pagesize="100" class="displayTag">
		<display:column titleKey="competition.judge.name" sortable="true">
			<jstl:out value="${judge.name}"></jstl:out>
			<jstl:out value="${judge.surname}"></jstl:out>
		</display:column>
	</display:table>
</fieldset>
<fieldset>
	<legend>
		<spring:message code="competition.groups"></spring:message>
	</legend>
	<display:table name="groups" id="group" pagesize="100" class="displayTag">
		<display:column property="numberOfDogs"
			titleKey="competition.number.dogs" sortable="true" />
		<display:column property="ring" titleKey="competition.ring"
			sortable="true" />
		<display:column property="breed.name" titleKey="competition.breed"
			sortable="true" />
	</display:table>
</fieldset>
<fieldset>
	<legend>
		<spring:message code="competition.dogs"></spring:message>
	</legend>
	<display:table name="dogs" id="dog" pagesize="100" class="displayTag">
		<display:column titleKey="search.breed" sortable="true">
			<a href="breed/details.do?breedId=${dog.breed.id}"><jstl:out
					value="${dog.breed.name}"></jstl:out></a>
		</display:column>
		<display:column property="name" titleKey="dog.name" sortable="true" />
		<<display:column property="totalPoints" titleKey="dog.totalPoints"
			sortable="true" />
		<display:column titleKey="dog.details" sortable="true">
			<a href="dog/details.do?dogId=${dog.id}"><spring:message
					code="dog.details.see"></spring:message></a>
		</display:column>
	</display:table>
</fieldset>
<jstl:if test="${competition.resultsPublished==false}">
	<fieldset>
		<legend>
			<spring:message code="dog.results"></spring:message>
		</legend>
		<spring:message code="competition.results.not.availables"></spring:message>
	</fieldset>
</jstl:if>
<jstl:if test="${competition.resultsPublished==true}">
	<fieldset>
		<legend>
			<spring:message code="dog.results"></spring:message>
		</legend>
		<display:table name="results" id="row" pagesize="100" class="displayTag">
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
		</display:table>
	</fieldset>
</jstl:if>
