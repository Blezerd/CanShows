

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<fieldset>
	<jstl:if test="${addMother==true}">
		<jstl:if test="${hembras.size()==0 || hembras==null}">
			<h3>
				<spring:message code="dog.cant.add"></spring:message>
			</h3>
			<form:form action="dog/participant/addMother.do"
				modelAttribute="addFOMForm">
				<acme:cancel url="dog/participant/list.do" code="register.cancel" />
			</form:form>
		</jstl:if>
		<jstl:if test="${hembras.size()>0}">
			<form:form action="dog/participant/addMother.do"
				modelAttribute="addFOMForm">
				<form:hidden path="boolMadre" />
				<form:hidden path="dogId" />
				<fieldset>
					<legend>
						<spring:message code="dog.add.mother" />
					</legend>
					<acme:select items="${hembras}" itemLabel="name"
						code="pedigree.mother" path="parent" />
				</fieldset>
				<br />
				<acme:submit name="save" code="register.save" />
				<acme:cancel url="dog/participant/list.do" code="register.cancel" />
			</form:form>
		</jstl:if>
	</jstl:if>
	<jstl:if test="${addMother==false}">
		<jstl:if test="${machos.size()==0 || machos==null}">
			<h3>
				<spring:message code="dog.cant.add"></spring:message>
			</h3>
		</jstl:if>
		<jstl:if test="${machos.size()>0}">
			<form:form action="dog/participant/addFather.do"
				modelAttribute="addFOMForm">
				<form:hidden path="boolMadre" />
				<form:hidden path="dogId" />
				<fieldset>
					<legend>
						<spring:message code="dog.add.father" />
					</legend>
					<acme:select items="${machos}" itemLabel="name"
						code="pedigree.father" path="parent" />
				</fieldset>
				<br />
				<acme:submit name="save" code="register.save" />
				<acme:cancel url="dog/participant/list.do" code="register.cancel" />
			</form:form>
		</jstl:if>
	</jstl:if>
</fieldset>