<%--
 * index.jsp
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<div id="barritaloca"
	style="display: none; position: fixed; left: 0px; right: 0px; bottom: 0px; width: 100%; min-height: 40px; background: #333333; color: #dddddd; z-index: 99999;">
	<div
		style="width: 100%; position: absolute; padding-left: 5px; font-family: verdana; font-size: 12px; top: 30%;">
		<spring:message code="welcome.greeting.cookies" />
		<a href="javascript:void(0);"
			style="padding: 4px; background: #4682B4; text-decoration: none; color: #fff;"
			onclick="PonerCookie();"><b>OK</b></a> <a
			href="http://www.google.com.ar/intl/es-419/policies/technologies/types/"
			target="_blank"
			style="padding-left: 5px; text-decoration: none; color: #ffffff;"><spring:message
				code="welcome.greeting.masinfo" /> </a>
	</div>
</div>

<script>
	function getCookie(c_name) {
		var c_value = document.cookie;
		var c_start = c_value.indexOf(" " + c_name + "=");
		if (c_start == -1) {
			c_start = c_value.indexOf(c_name + "=");
		}
		if (c_start == -1) {
			c_value = null;
		} else {
			c_start = c_value.indexOf("=", c_start) + 1;
			var c_end = c_value.indexOf(";", c_start);
			if (c_end == -1) {
				c_end = c_value.length;
			}
			c_value = unescape(c_value.substring(c_start, c_end));
		}
		return c_value;
	}

	function setCookie(c_name, value, exdays) {
		var exdate = new Date();
		exdate.setDate(exdate.getDate() + exdays);
		var c_value = escape(value)
				+ ((exdays == null) ? "" : "; expires=" + exdate.toUTCString());
		document.cookie = c_name + "=" + c_value;
	}

	if (getCookie('aviso') != "1") {
		document.getElementById("barritaloca").style.display = "block";
	}

	function PonerCookie() {
		setCookie('aviso', '1', 365);
		document.getElementById("barritaloca").style.display = "none";
	}
</script>
<p>
	<spring:message code="welcome.greeting.prefix" />
	<spring:message code="welcome.greeting.suffix" />
</p>
<p>
	<spring:message code="welcome.greeting.current.time" />
	${moment}
</p>

