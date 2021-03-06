<%--
 * layout.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!DOCTYPE html>

<html>
<head>

<base
	href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="shortcut icon" href="favicon.ico" />
<script type="text/javascript" src="scripts/jquery.js"></script>
<script type="text/javascript" src="scripts/jquery-ui.js"></script>
<script type="text/javascript" src="scripts/jmenu.js"></script>
<link rel="stylesheet" href="styles/common.css" type="text/css">
<link rel="stylesheet" href="styles/displaytag.css" type="text/css">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />


<link rel="stylesheet" href="styles/jmenu.css" media="screen"
	type="text/css" />

<title><tiles:insertAttribute name="title" ignore="true" /></title>

<script type="text/javascript">
	$(document).ready(function() {
		$("#jMenu").jMenu();
	});

	function askSubmission(msg, form) {
		if (confirm(msg))
			form.submit();
	}
</script>

<script type="text/javascript">
	function relativeRedir(loc) {
		var b = document.getElementsByTagName('base');
		if (b && b[0] && b[0].href) {
			if (b[0].href.substr(b[0].href.length - 1) == '/'
					&& loc.charAt(0) == '/')
				loc = loc.substr(1);
			loc = b[0].href + loc;
		}
		window.location.replace(loc);
	}
</script>

</head>
<body
	background="http://www.delabahia.es/wp-content/uploads/2015/08/dog-313357_1280.jpg">

	<div>
		<tiles:insertAttribute name="header" />
	</div>
	<h1>
		<tiles:insertAttribute name="title" />
	</h1>
	<div class="row">

		<div style="padding-bottom: 20px;">
			<tiles:insertAttribute name="body" />
		</div>
	</div>
	<jstl:if test="${message != null}">
		<br />
		<span class="message red-text"><spring:message
				code="${message}" /></span>

	</jstl:if>


	<div>
		<tiles:insertAttribute name="footer" />
	</div>

</body>
</html>