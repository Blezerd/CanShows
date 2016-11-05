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
	<display:table name="folders"
		requestURI="folder/${actor}list.do${requestUri}" id="folder"
		pagesize="5" class="displayTag">
		<security:authorize access="hasRole('PARTICIPANT')">
		
		<display:column property="name" titleKey="folder.name" sortable="true"/>
		<display:column titleKey="folder.details" sortable="true">
			<a href="folder/participant/details.do?folderId=${folder.id}"><spring:message
					code="folder.details.see"></spring:message></a>
		</display:column>
		
		
						
		</security:authorize>
	</display:table>

</div>