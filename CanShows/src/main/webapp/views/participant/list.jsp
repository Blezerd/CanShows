
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div>
	<security:authorize access="hasRole('PARTICIPANT')">
		<jstl:if test="${friends.size()==0||friends==null }">
			<h2>
				<spring:message code="search.no.friends"></spring:message>
			</h2>

		</jstl:if>
		<jstl:if test="${friends.size()>0}">
			<display:table name="friends" id="friend"
				requestURI="participant/participant/list.do" pagesize="5"
				class="datagrid">
				<display:column property="name" titleKey="search.name"
					sortable="true" />
				<display:column property="surname" titleKey="search.surname"
					sortable="true" />
				<display:column property="email" titleKey="search.email"
					sortable=" true" />
				<display:column titleKey="search.c.follow" sortable="true">
					<jstl:if test="${friend.id!=yo.id && !yo.friends.contains(friend)}">
						<a
							href="participant/participant/follow.do?participantId=${friend.id}?text=${text}"><spring:message
								code="search.follow" /></a>
					</jstl:if>
					<jstl:if test="${friend.id!=yo.id && yo.friends.contains(friend)}">
						<a
							href="participant/participant/unFollow.do?participantId=${friend.id}"><spring:message
								code="search.unfollow" /></a>
					</jstl:if>
				</display:column>
				<display:column titleKey="search.dogs" sortable="true">
					<a href="dog/participant/listFriend.do?friendId=${friend.id}"><spring:message
							code="search.see.dogs" /></a>
				</display:column>
			</display:table>
		</jstl:if>
	</security:authorize>
</div>

