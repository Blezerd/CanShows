<%--
 * dashboard.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<security:authorize access="hasRole('ADMIN')">
	<jstl:choose>
		<jstl:when test="${option==1 }">
			<div>
				<display:table name="organisers" id="row"
					requestURI="dashboard/administrator/organisersWithCompetition.do"
					pagesize="5" class="displayTag">

					<display:column property="name" titleKey="organiser.name"
						sortable="true" />


					<display:column titleKey="organiser.competitions" sortable="true">
						<jstl:out value="${row.competitions.size()} ">
						</jstl:out>
					</display:column>

				</display:table>
			</div>
		</jstl:when>
		<jstl:when test="${option==2 }">
			<div>
				<h3>
					<a href="breed/details.do?breedId=${dog.breed.id}"><jstl:out
							value="${dog.breed.name}"></jstl:out></a> <br>
					<spring:message code="dashboard.name"></spring:message>
					<jstl:out value="${dog.name}">
					</jstl:out>
					<br>
					<spring:message code="dashboard.nickname"></spring:message>
					<jstl:out value="${dog.nickname}">
					</jstl:out>
					<br>
					<jstl:if test="${dog.sex=='MALE'}">
						<spring:message code="dashboard.sex"></spring:message>
						<spring:message code="dog.male"></spring:message>
						<br>
					</jstl:if>
					<jstl:if test="${dog.sex=='FEMALE'}">
						<spring:message code="dashboard.sex"></spring:message>
						<spring:message code="dog.female"></spring:message>
						<br>
					</jstl:if>
					<spring:message code="dashboard.weight"></spring:message>
					<jstl:out value="${dog.weight}"></jstl:out>
					<spring:message code="dashboard.weight.unidad"></spring:message>
					<br>
					<spring:message code="dashboard.height"></spring:message>
					<jstl:out value="${dog.height}"></jstl:out>
					<spring:message code="dashboard.height.unidad"></spring:message>

					<br>
					<spring:message code="dashboard.totalPoints"></spring:message>
					<jstl:out value="${dog.totalPoints}"></jstl:out>
					<br>
				</h3>
				<display:table name="results" id="row" pagesize="5"
					class="displayTag">
					<display:column property="competition.title"
						titleKey="dog.competition" sortable="true" />
					<display:column property="position" titleKey="dog.position"
						sortable="true" />
				</display:table>
				<hr>
				<fieldset>
					<legend>
						<spring:message code="pedigree"></spring:message>
					</legend>
					<jstl:if test="${dog.father!=null}">
						<fieldset>
							<legend>
								<spring:message code="pedigree.father"></spring:message>
							</legend>
							<spring:message code="dashboard.name"></spring:message>
							<jstl:out value="${dog.father.name}"></jstl:out>
							<br>
							<spring:message code="dashboard.nickname"></spring:message>
							<jstl:out value="${dog.father.nickname}"></jstl:out>
							<br>
							<spring:message code="dashboard.totalPoints"></spring:message>
							<jstl:out value="${dog.father.totalPoints}"></jstl:out>
							<br>
						</fieldset>
					</jstl:if>
					<jstl:if test="${dog.mother!=null}">
						<fieldset>
							<legend>
								<spring:message code="pedigree.mother"></spring:message>
							</legend>
							<spring:message code="dashboard.name"></spring:message>
							<jstl:out value="${dog.mother.name}"></jstl:out>
							<br>
							<spring:message code="dashboard.nickname"></spring:message>
							<jstl:out value="${dog.mother.nickname}"></jstl:out>
							<br>
							<spring:message code="dashboard.totalPoints"></spring:message>
							<jstl:out value="${dog.mother.totalPoints}"></jstl:out>
							<br>
						</fieldset>
					</jstl:if>
					<jstl:if test="${dog.father.father!=null}">
						<fieldset>
							<legend>
								<spring:message code="pedigree.father.father"></spring:message>
							</legend>
							<spring:message code="dashboard.name"></spring:message>
							<jstl:out value="${dog.father.father.name}"></jstl:out>
							<br>
							<spring:message code="dashboard.nickname"></spring:message>
							<jstl:out value="${dog.father.father.nickname}"></jstl:out>
							<br>
							<spring:message code="dashboard.totalPoints"></spring:message>
							<jstl:out value="${dog.father.father.totalPoints}"></jstl:out>
							<br>
						</fieldset>
					</jstl:if>
					<jstl:if test="${dog.father.mother!=null}">
						<fieldset>
							<legend>
								<spring:message code="pedigree.father.mother"></spring:message>
							</legend>
							<spring:message code="dashboard.name"></spring:message>
							<jstl:out value="${dog.father.mother.name}"></jstl:out>
							<br>
							<spring:message code="dashboard.nickname"></spring:message>
							<jstl:out value="${dog.father.mother.nickname}"></jstl:out>
							<br>
							<spring:message code="dashboard.totalPoints"></spring:message>
							<jstl:out value="${dog.father.mother.totalPoints}"></jstl:out>
							<br>
						</fieldset>
					</jstl:if>
					<jstl:if test="${dog.mother.father!=null}">
						<fieldset>
							<legend>
								<spring:message code="pedigree.mother.father"></spring:message>
							</legend>
							<spring:message code="dashboard.name"></spring:message>
							<jstl:out value="${dog.mother.father.name}"></jstl:out>
							<br>
							<spring:message code="dashboard.nickname"></spring:message>
							<jstl:out value="${dog.mother.father.nickname}"></jstl:out>
							<br>
							<spring:message code="dashboard.totalPoints"></spring:message>
							<jstl:out value="${dog.mother.father.totalPoints}"></jstl:out>
							<br>
						</fieldset>
					</jstl:if>
					<jstl:if test="${dog.mother.mother!=null}">
						<fieldset>
							<legend>
								<spring:message code="pedigree.father.mother"></spring:message>
							</legend>
							<spring:message code="dashboard.name"></spring:message>
							<jstl:out value="${dog.mother.mother.name}"></jstl:out>
							<br>
							<spring:message code="dashboard.nickname"></spring:message>
							<jstl:out value="${dog.mother.mother.nickname}"></jstl:out>
							<br>
							<spring:message code="dashboard.totalPoints"></spring:message>
							<jstl:out value="${dog.mother.mother.totalPoints}"></jstl:out>
							<br>
						</fieldset>
					</jstl:if>
				</fieldset>
			</div>
		</jstl:when>
		<jstl:when test="${option==3 }">
			<div>
				<display:table name="judges" id="row"
					requestURI="dashboard/administrator/judgeWithCompetitions.do"
					pagesize="5" class="displayTag">

					<display:column property="name" titleKey="judge.name"
						sortable="true" />


					<display:column titleKey="judge.competitions" sortable="true">
						<jstl:out value="${row.competitions.size()} ">
						</jstl:out>
					</display:column>

					<display:column titleKey="judge.results" sortable="true">
						<jstl:out value="${row.results.size()} ">
						</jstl:out>
					</display:column>

				</display:table>
			</div>
		</jstl:when>


	</jstl:choose>

</security:authorize>



