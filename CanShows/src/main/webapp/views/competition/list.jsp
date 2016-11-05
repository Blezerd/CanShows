<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div>
	<display:table name="competitions"
		requestURI="competition/${actor}list.do${requestUri}" id="competition"
		pagesize="5" class="displayTag">

		<display:column property="title" titleKey="competition.title"
			sortable="true" />
		<display:column titleKey="competition.type" sortable="true">
			<jstl:if test="${competition.type=='BEAUTY' }">
				<spring:message code="competition.beauty"></spring:message>
			</jstl:if>
			<jstl:if test="${competition.type=='WORK' }">
				<spring:message code="competition.work"></spring:message>
			</jstl:if>
		</display:column>
		<display:column property="inscriptionPrice"
			titleKey="competition.inscription" sortable="true" />
		<display:column titleKey="competition.inscription.date"
			sortable="true">
			<fmt:formatDate value="${competition.inscriptionLimitDate}"
				pattern="dd-MM-yyyy" />
		</display:column>
		<display:column titleKey="competition.celebration" sortable="true">
			<fmt:formatDate value="${competition.celebrationDate}"
				pattern="dd-MM-yyyy" />
		</display:column>
		<display:column property="minimumPoints"
			titleKey="competition.min.points" sortable="true" />
		<display:column titleKey="competition.details" sortable="true">
			<a
				href="competition/${actor}details.do?competitionId=${competition.id}"><spring:message
					code="competition.details"></spring:message></a>
		</display:column>
		<jstl:if test="${yo==true}">
			<security:authorize access="hasRole('ORGANISER')">
				<display:column titleKey="competition.c.edit" sortable="true">
					<jstl:if test="${now.after(competition.inscriptionLimitDate)}">
						<spring:message code="competition.cant.edit"></spring:message>
					</jstl:if>
					<jstl:if test="${now.before(competition.inscriptionLimitDate)}">
						<a
							href="competition/organiser/edit.do?competitionId=${competition.id}"><spring:message
								code="competition.edit"></spring:message></a>
					</jstl:if>
				</display:column>
				<display:column titleKey="competition.c.add" sortable="true">
					<jstl:if test="${now.after(competition.celebrationDate)}">
						<spring:message code="competition.cant.add"></spring:message>
					</jstl:if>
					<jstl:if test="${now.before(competition.celebrationDate)}">
						<a
							href="competition/organiser/addJudge.do?competitionId=${competition.id}"><spring:message
								code="competition.add"></spring:message></a>
					</jstl:if>
				</display:column>
				<display:column titleKey="competition.c.addGroup" sortable="true">
					<jstl:if test="${now.after(competition.celebrationDate)}">
						<spring:message code="competition.cant.addGroup"></spring:message>
					</jstl:if>
					<jstl:if test="${now.before(competition.celebrationDate)}">
						<a
							href="competition/organiser/addGroup.do?competitionId=${competition.id}"><spring:message
								code="competition.addGroup"></spring:message></a>
					</jstl:if>
				</display:column>
				<display:column titleKey="competition.c.result" sortable="true">
					<jstl:if
						test="${now.after(competition.celebrationDate) and competition.resultsPublished==false and competition.results.size()>0}">
						<a
							href="competition/organiser/publish.do?competitionId=${competition.id}"><spring:message
								code="competition.publish"></spring:message></a>
					</jstl:if>
					<jstl:if
						test="${now.before(competition.celebrationDate) or competition.results.size()==0 and competition.resultsPublished==false}">
						<spring:message code="competition.cant.publish"></spring:message>

					</jstl:if>
					<jstl:if test="${competition.resultsPublished==true}">
						<spring:message code="competition.published"></spring:message>

					</jstl:if>
				</display:column>
			</security:authorize>
		</jstl:if>
		<security:authorize access="hasRole('PARTICIPANT')">
			<display:column titleKey="competition.c.join" sortable="true">
				<jstl:if test="${competition.inscriptionLimitDate.before(now)}">
					<spring:message code="competition.cant.inscribirse"></spring:message>
				</jstl:if>
				<jstl:if test="${competition.inscriptionLimitDate.after(now)}">
					<a
						href="competition/participant/join.do?competitionId=${competition.id}"><spring:message
							code="competition.join"></spring:message></a>
				</jstl:if>
			</display:column>
		</security:authorize>

		<security:authorize access="hasRole('JUDGE')">
			<jstl:if test="${judge!=null}">
				<display:column titleKey="competition.c.judge" sortable="true">
					<jstl:if
						test="${competition.judges.contains(judge) and competition.celebrationDate.before(now) and competition.resultsPublished==false}">
						<a
							href="competition/judge/judge.do?competitionId=${competition.id}"><spring:message
								code="competition.judge"></spring:message></a>
					</jstl:if>
					<jstl:if
						test="${competition.judges.contains(judge) and competition.celebrationDate.before(now) and competition.resultsPublished==true}">
						<spring:message code="competition.results.availables"></spring:message>
					</jstl:if>
					<jstl:if
						test="${competition.judges.contains(judge) and competition.celebrationDate.after(now)}">
						<spring:message code="competition.not.celebrated"></spring:message>
					</jstl:if>
					<jstl:if test="${!competition.judges.contains(judge)}">
						<spring:message code="competition.not.judge"></spring:message>
					</jstl:if>
				</display:column>
			</jstl:if>
		</security:authorize>
	</display:table>
	<security:authorize access="hasRole('ORGANISER')">
		<p>
			<a href="competition/organiser/create.do"><spring:message
					code="competition.create"></spring:message></a>
		</p>
	</security:authorize>
</div>