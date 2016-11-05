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
	<display:table name="organisers" requestURI="organiser/${actor}list.do"
		id="row" pagesize="5" class="displayTag">
		<display:column property="name" titleKey="organiser.name"
			sortable="true" />
		<display:column property="surname" titleKey="organiser.surname"
			sortable="true" />
		<display:column property="homePage" titleKey="organiser.homepage"
			sortable="true" />
		<display:column property="nationality"
			titleKey="organiser.nationality" sortable="true" />
	</display:table>


</div> 