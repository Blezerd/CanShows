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
	<h2>
		<jstl:out value="${dog.breed.name}"></jstl:out>
	</h2>
	<fieldset>
		<legend>
			<spring:message code="dog.details"></spring:message>
		</legend>
		<spring:message code="dashboard.name"></spring:message>
		<jstl:out value="${dog.name}"></jstl:out>
		<br>
		<spring:message code="dashboard.nickname"></spring:message>
		<jstl:out value="${dog.nickname}"></jstl:out>
		<br>
		<spring:message code="dashboard.totalPoints"></spring:message>
		<jstl:out value="${dog.totalPoints}"></jstl:out>
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
		<spring:message code="dog.results"></spring:message>
		<display:table name="results" id="row" pagesize="5" class="datagrid">
			<display:column property="competition.title"
				titleKey="result.competition" sortable="true" />
			<display:column property="position" titleKey="result.position"
				sortable="true" />
			<display:column property="comment" titleKey="result.comment"
				sortable="true" />
		</display:table>
	</fieldset>
	<jstl:if test="${dog.father==null and dog.mother==null}">
		<h2>
			<spring:message code="pedigree.not"></spring:message>
		</h2>
	</jstl:if>
	<jstl:if test="${dog.father!=null || dog.mother!=null}">
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
			</jstl:if>
		</fieldset>
	</jstl:if>
</fieldset>
