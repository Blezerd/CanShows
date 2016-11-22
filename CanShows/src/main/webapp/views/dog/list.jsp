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
	<display:table name="dogs"
		requestURI="dog/${actor}list${friend}.do${requestUri}" id="dog"
		pagesize="5" class="datagrid" >
		<display:column titleKey="search.breed" sortable="true">
			<a href="breed/details.do?breedId=${dog.breed.id}"><jstl:out
					value="${dog.breed.name}"></jstl:out></a>
		</display:column>
		<display:column property="name" titleKey="dog.name" sortable="true" />
		<<display:column property="totalPoints" titleKey="dog.totalPoints"
			sortable="true" />
		<display:column titleKey="dog.details" sortable="true">
			<jstl:if test="${dog.request.status!='REJECTED'}">
				<a href="dog/details.do?dogId=${dog.id}"><spring:message
						code="dog.details.see"></spring:message></a>
			</jstl:if>
			<jstl:if test="${dog.request.status=='REJECTED'}">
				<a href="dog/participant/delete.do?dogId=${dog.id}"
					onclick="return confirm('<spring:message code="dog.confirm.delete" />')"><spring:message
						code="dog.delete.this" /></a>
			</jstl:if>
		</display:column>
		<security:authorize access="hasRole('PARTICIPANT')">
			<jstl:if test="${participant!=null}">
				<display:column titleKey="dog.results">
					<jstl:if
						test="${dog.participant.id==participant.id and dog.request.status!='REJECTED'}">
						<a href="result/participant/list.do?dogId=${dog.id}"><spring:message
								code="dog.show.results" /></a>
					</jstl:if>
					<jstl:if
						test="${dog.participant.id!=participant.id and dog.request.status!='REJECTED'}">
						<a href="result/participant/listFriend.do?dogId=${dog.id}"><spring:message
								code="dog.show.results" /></a>
					</jstl:if>
					<jstl:if test="${dog.request.status=='REJECTED'}">
						<a href="dog/participant/delete.do?dogId=${dog.id}"
							onclick="return confirm('<spring:message code="dog.confirm.delete" />')"><spring:message
								code="dog.delete.this" /></a>
					</jstl:if>
				</display:column>
				<jstl:if
					test="${dog.participant.id==participant.id and actor=='participant/'}">
					<display:column titleKey="pedigree.father">
						<jstl:if
							test="${dog.father==null and dog.request.status!='REJECTED'}">
							<a href="dog/participant/addFather.do?dogId=${dog.id}"><spring:message
									code="dog.add.father" /></a>
						</jstl:if>
						<jstl:if
							test="${dog.father!=null and dog.request.status!='REJECTED'}">
							<spring:message code="added"></spring:message>
						</jstl:if>
						<jstl:if test="${dog.request.status=='REJECTED'}">
							<a href="dog/participant/delete.do?dogId=${dog.id}"
								onclick="return confirm('<spring:message code="dog.confirm.delete" />')"><spring:message
									code="dog.delete.this" /></a>
						</jstl:if>
					</display:column>
					<display:column titleKey="pedigree.mother">
						<jstl:if
							test="${dog.mother==null and dog.request.status!='REJECTED'}">
							<a href="dog/participant/addMother.do?dogId=${dog.id}"><spring:message
									code="dog.add.mother" /></a>
						</jstl:if>
						<jstl:if
							test="${dog.mother!=null and dog.request.status!='REJECTED'}">
							<spring:message code="added"></spring:message>
						</jstl:if>
						<jstl:if test="${dog.request.status=='REJECTED'}">
							<a href="dog/participant/delete.do?dogId=${dog.id}"
								onclick="return confirm('<spring:message code="dog.confirm.delete" />')"><spring:message
									code="dog.delete.this" /></a>
						</jstl:if>
					</display:column>
					<display:column titleKey="dog.delete">
						<jstl:if test="${dog.request.status=='REJECTED'}">
							<a href="dog/participant/delete.do?dogId=${dog.id}"
								onclick="return confirm('<spring:message code="dog.confirm.delete" />')"><spring:message
									code="dog.delete.this" /></a>
						</jstl:if>
						<jstl:if
							test="${dog.request.status!='REJECTED' and dog.groups.size()==0}">
							<a href="dog/participant/delete.do?dogId=${dog.id}"><spring:message
									code="dog.delete.this" /></a>
						</jstl:if>
						<jstl:if
							test="${dog.request.status!='REJECTED' and dog.groups.size()>0}">
							<spring:message code="dog.cant.delete"></spring:message>
						</jstl:if>
					</display:column>
					<display:column titleKey="dog.participate">
						<jstl:if test="${dog.request.status=='REJECTED'}">
							<spring:message code="dog.rejected"></spring:message>
						</jstl:if>
						<jstl:if
							test="${dog.request.status!='REJECTED' and dog.canParticipate==true}">
							<spring:message code="dog.can.participate"></spring:message>
						</jstl:if>
						<jstl:if
							test="${dog.request.status!='REJECTED' and dog.canParticipate==false}">
							<spring:message code="dog.cant.participate"></spring:message>
						</jstl:if>
					</display:column>
				</jstl:if>
			</jstl:if>
		</security:authorize>
	</display:table>

</div>