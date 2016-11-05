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


	<display:table name="messages"
		requestURI="folder/${actor}details.do${requestUri}" id="message"
		pagesize="5" class="displayTag">
		<security:authorize access="hasRole('PARTICIPANT')">
			
		
		<display:column property="subject" titleKey="message.subject" sortable="true"/>
		<display:column property="body" titleKey="message.body" sortable="true"/>
		
		<display:column titleKey="message.moment"
			sortable="true">
			<fmt:formatDate value="${message.moment}"
				pattern="dd-MM-yyyy HH:mm" />
		</display:column>
						
		</security:authorize>
	</display:table>
	
