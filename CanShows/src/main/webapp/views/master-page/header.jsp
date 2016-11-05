<%--
 * header.jsp
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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div>
	<a><img src="images/banner.jpg"
		style="height: 100px; margin-left: 10%;" alt="CANSHOWS Co., Inc."
		onclick="javascript: window.location.replace('welcome/index.do')" /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message
						code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="register/registerAdmin.do"><spring:message
								code="master.page.registerAdmin" /></a></li>
					<li><a href="register/registerJudge.do"><spring:message
								code="master.page.registerJudge" /></a></li>
					<li><a href="request/administrator/list.do"><spring:message
								code="master.page.admin.request.list" /></a></li>
					<li><a href="breed/administrator/create.do"><spring:message
								code="master.page.admin.breed" /></a></li>
					<li><a href="breed/administrator/list.do"><spring:message
								code="master.page.admin.breed.list" /></a></li>

				</ul></li>
			<li><a class="fNiv" href="organiser/list.do"><spring:message
						code="master.page.organiser.list" /></a></li>
			<li><a class="fNiv" href="dog/list.do"><spring:message
						code="master.page.dog.list" /></a></li>
			<li><a class="fNiv"><spring:message
						code="master.page.dashboard" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a
						href="dashboard/administrator/organisersWithCompetition.do"><spring:message
								code="master.page.organisersWithCompetition" /></a></li>
					<li><a href="dashboard/administrator/bestDog.do"><spring:message
								code="master.page.dogsWithPoints" /></a></li>
					<li><a href="dashboard/administrator/judgeWithCompetitions.do"><spring:message
								code="master.page.judgeWithCompetitions" /></a></li>
				</ul></li>
		</security:authorize>
		<security:authorize access="hasRole('PARTICIPANT')">
			<li><a class="fNiv"><spring:message
						code="master.page.participant" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="search/participant/buscar.do"><spring:message
								code="master.page.search.participant" /></a></li>
					<li><a href="dog/participant/create.do"><spring:message
								code="master.page.dog.create" /></a></li>
					<li><a href="dog/participant/list.do"><spring:message
								code="master.page.dog.participant" /></a></li>
					<li><a href="participant/participant/list.do"><spring:message
								code="master.page.participant.friends" /></a></li>
					<li><a href="competition/participant/list.do"><spring:message
								code="master.page.participant.competitions" /></a></li>
				</ul></li>
			<li><a class="fNiv"><spring:message
						code="master.page.requests" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="request/participant/list.do"><spring:message
								code="master.page.participant.request.list" /></a></li>

					<li><a href="request/participant/edit.do"><spring:message
								code="master.page.participant.request" /></a></li>
				</ul></li>
			<li><a class="fNiv"><spring:message
						code="master.page.messages" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="folder/participant/list.do"><spring:message
								code="master.page.participant.messages.list" /></a></li>
					<li><a href="message/participant/edit.do"><spring:message
								code="master.page.participant.messages.create" /></a></li>
				</ul></li>
			<li><a class="fNiv" href="organiser/list.do"><spring:message
						code="master.page.organiser.list" /></a></li>
			<li><a class="fNiv" href="dog/list.do"><spring:message
						code="master.page.dog.list" /></a></li>
		</security:authorize>
		<security:authorize access="hasRole('ORGANISER')">
			<li><a class="fNiv"><spring:message
						code="master.page.organiser" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="competition/organiser/list.do"><spring:message
								code="master.page.organiser.competitions" /></a></li>
					<li><a href="competition/organiser/create.do"><spring:message
								code="master.page.organiser.competitions.create" /></a></li>
				</ul></li>
			<li><a class="fNiv" href="organiser/list.do"><spring:message
						code="master.page.organiser.list" /></a></li>
			<li><a class="fNiv" href="dog/list.do"><spring:message
						code="master.page.dog.list" /></a></li>

		</security:authorize>
		<security:authorize access="hasRole('JUDGE')">
			<li><a class="fNiv"><spring:message code="master.page.judge" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="competition/judge/list.do"><spring:message
								code="master.page.competition.judge" /></a></li>
				</ul></li>
			<li><a class="fNiv" href="organiser/list.do"><spring:message
						code="master.page.organiser.list" /></a></li>
			<li><a class="fNiv" href="dog/list.do"><spring:message
						code="master.page.dog.list" /></a></li>
		</security:authorize>
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv"><spring:message
						code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="register/registerParticipant.do"><spring:message
								code="master.page.register.participant" /></a></li>
					<li><a href="register/registerOrganiser.do"><spring:message
								code="master.page.register.organiser" /></a></li>
				</ul></li>
			<li><a class="fNiv" href="competition/list.do"><spring:message
						code="master.page.competitions" /></a></li>
			<li><a class="fNiv" href="search/buscar.do"><spring:message
						code="master.page.search.dog" /></a></li>
			<li><a class="fNiv" href="organiser/list.do"><spring:message
						code="master.page.organiser.list" /></a></li>
			<li><a class="fNiv" href="dog/list.do"><spring:message
						code="master.page.dog.list" /></a></li>
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
			<li><a class="fNiv" href="legal/legal.do"><spring:message
						code="master.page.legal" /></a></li>
		</security:authorize>
		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv" href="competition/list.do"><spring:message
						code="master.page.competitions" /></a></li>
			<li><a class="fNiv" href="search/buscar.do"><spring:message
						code="master.page.search.dog" /></a></li>
			<li><a class="fNiv" href="j_spring_security_logout"> <spring:message
						code="master.page.logout" /> (<security:authentication
						property="principal.username" />)
			</a></li>
			<li><a class="fNiv" href="legal/legal.do"><spring:message
						code="master.page.legal" /></a></li>
		</security:authorize>
		<li><a class="fNiv"><img src="images/lang.png" alt="LANG" height="20px"></a>
			<ul>
				<li class="arrow"></li>
				<li><a href="?language=en"><img src="images/icono_ingles.png" height="25px" alt="EN"></a></li>
				<li><a href="?language=es"><img src="images/icono-idioma-espanol.png" height="25px" alt="ES"></a></li>
			</ul></li>
	</ul>
</div>