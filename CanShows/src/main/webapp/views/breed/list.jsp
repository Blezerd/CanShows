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
	<display:table name="breeds"
		requestURI="breed/${actor}list.do${requestUri}" id="breed"
		pagesize="5" class="displayTag">
		<security:authorize access="hasRole('ADMIN')">
		<display:column property="name" titleKey="breed.name" sortable="true" />
		<display:column property="description" titleKey="breed.description"
			sortable="true" />
				
		</security:authorize>
	</display:table>

</div>