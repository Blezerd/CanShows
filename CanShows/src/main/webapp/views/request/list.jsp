
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div>
	<display:table name="requests"
		requestURI="request/${actor}list.do${requestUri}" id="row"
		pagesize="5" class="datagrid">

		<display:column property="code" titleKey="request.code"
			sortable="true" />
		<display:column property="title" titleKey="request.title"
			sortable="true" />
		<display:column property="description" titleKey="request.description"
			sortable="true" />
		<display:column titleKey="request.moment" sortable="true">
			<fmt:formatDate value="${row.creationMoment}" pattern="dd-MM-yyyy" />
		</display:column>
		<display:column titleKey="request.status" sortable="true">
			<jstl:if test="${row.status=='PENDING' }">
				<spring:message code="request.pending" />
			</jstl:if>
			<jstl:if test="${row.status=='ACCEPTED' }">
				<spring:message code="request.accepted" />
			</jstl:if>
			<jstl:if test="${row.status=='REJECTED' }">
				<spring:message code="request.rejected" />
			</jstl:if>

		</display:column>
		<display:column property="comment" titleKey="request.comment"
			sortable="true" />
		<security:authorize access="hasRole('PARTICIPANT')">
			<display:column property="administrator.name"
				titleKey="request.admin" sortable="true" />
		</security:authorize>
		
		<security:authorize access="hasRole('ADMIN')">
			<display:column property="dog.participant.name"
				titleKey="request.participant" sortable="true" />
			<display:column titleKey="request.edit">
				<jstl:if test="${row.status =='PENDING' }">
					<a href="request/administrator/edit.do?requestId=${row.id}"> <spring:message
							code="request.edit" />
					</a>
				</jstl:if>
				<jstl:if test="${row.status !='PENDING' }">
					<spring:message code="request.cant.edit"></spring:message>
				</jstl:if>
			</display:column>

		</security:authorize>



	</display:table>

</div>